package io.ylab.monitoring.db.jdbc.service;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TestConnection {
}
