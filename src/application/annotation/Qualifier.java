package application.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Qualifier.
 * 
 * @author anbang
 * @date 2023-03-19 18:14
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

  String value() default "";

}
