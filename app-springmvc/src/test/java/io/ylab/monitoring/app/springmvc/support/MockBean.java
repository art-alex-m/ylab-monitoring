package io.ylab.monitoring.app.springmvc.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MockBean {
    /**
     * Квалификатор, при необходимости, иначе используется имя типа
     */
    String value() default "";

    /**
     * Добавлять ли мок объекта в контекст Spring приложения
     */
    boolean contextAble() default true;
}
