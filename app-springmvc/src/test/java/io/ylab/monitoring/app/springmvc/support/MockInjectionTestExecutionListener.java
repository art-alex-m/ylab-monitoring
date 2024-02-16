package io.ylab.monitoring.app.springmvc.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * MockInjectionTestExecutionListener
 * Организация работы с изолированным тестированием бинов контроллеров для MockMvc
 *
 * <p>
 *     <a href="https://docs.spring.io/spring-framework/docs/5.0.x/spring-framework-reference/testing.html#testcontext-tel-config">3.5.3. TestExecutionListener configuration</a><br>
 *     <a href="https://www.baeldung.com/spring-reinitialize-singleton-bean">Reinitialize Singleton Bean in Spring Context</a><br>
 * </p>
 *
 * @see MockMvcTest
 */
public class MockInjectionTestExecutionListener extends AbstractTestExecutionListener {

    private final Set<BeanConfig> controllers = new HashSet<>();

    private final Set<BeanConfig> declaredMockBeans = new HashSet<>();

    @Override
    public int getOrder() {
        return 1600;
    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        Arrays.stream(testContext.getTestClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MockBean.class))
                .map(BeanConfig::new)
                .forEach(declaredMockBeans::add);

        MockMvcTest mockMvcTest = testContext.getTestClass().getAnnotation(MockMvcTest.class);
        Arrays.stream(mockMvcTest.value()).map(BeanConfig::new).forEach(controllers::add);
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        DefaultSingletonBeanRegistry beanRegistry = (DefaultSingletonBeanRegistry) testContext.getApplicationContext()
                .getAutowireCapableBeanFactory();

        removeBeans(beanRegistry, controllers);
        removeBeans(beanRegistry, declaredMockBeans);

        initDeclaredMocks(testContext);
        initControllers(testContext);
    }

    private void initDeclaredMocks(TestContext testContext) throws Exception {
        DefaultSingletonBeanRegistry beanRegistry = (DefaultSingletonBeanRegistry) testContext.getApplicationContext()
                .getAutowireCapableBeanFactory();

        declaredMockBeans.forEach(beanConfig -> {
            try {
                boolean accessible = beanConfig.field.canAccess(testContext.getTestInstance());
                beanConfig.field.setAccessible(true);
                Object mockObj = Mockito.mock(beanConfig.getType());
                beanConfig.field.set(testContext.getTestInstance(), mockObj);
                beanConfig.field.setAccessible(accessible);
                if (beanConfig.isContextAble()) {
                    beanRegistry.registerSingleton(beanConfig.getName(), mockObj);
                }
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void initControllers(TestContext testContext) {
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) testContext.getApplicationContext();

        controllers.forEach(config -> {
            BeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(config.getType());
            genericApplicationContext.registerBeanDefinition(config.getName(), beanDefinition);
        });

        genericApplicationContext.getBeansOfType(RequestMappingHandlerMapping.class)
                .forEach((name, requestMappingHandlerMapping) -> {
                    requestMappingHandlerMapping.getHandlerMethods().keySet()
                            .forEach(requestMappingHandlerMapping::unregisterMapping);
                    requestMappingHandlerMapping.afterPropertiesSet();
                });
    }

    private void removeBeans(DefaultSingletonBeanRegistry beanRegistry, Set<BeanConfig> beanConfigSet) {
        beanConfigSet.forEach(config -> beanRegistry.destroySingleton(config.getName()));
    }

    @AllArgsConstructor
    @Getter
    protected static class BeanConfig {
        private final Field field;
        private final Class<?> type;

        public BeanConfig(Field field) {
            this.field = field;
            this.type = field.getType();
        }

        public BeanConfig(Class<?> type) {
            this.field = null;
            this.type = type;
        }

        public String getName() {
            return Optional.ofNullable(field)
                    .map(field -> field.getAnnotation(MockBean.class))
                    .map(MockBean::value)
                    .filter(name -> !name.isEmpty())
                    .orElse(type.getName());
        }

        public boolean isContextAble() {
            return Optional.ofNullable(field)
                    .map(field -> field.getAnnotation(MockBean.class))
                    .map(MockBean::contextAble)
                    .orElse(true);
        }
    }
}
