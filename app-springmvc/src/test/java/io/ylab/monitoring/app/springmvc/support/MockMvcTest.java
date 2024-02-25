package io.ylab.monitoring.app.springmvc.support;

import io.ylab.monitoring.app.springmvc.controller.AppRestControllerAdvice;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = {
        MockInjectionTestExecutionListener.class
})
@SpringJUnitWebConfig(classes = {
        AppTestControllerConfiguration.class,
        AppRestControllerAdvice.class
})
public @interface MockMvcTest {
    /**
     * Список классов контроллеров
     */
    Class<?>[] value() default {};
}
