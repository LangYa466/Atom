package org.atom.client.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author LangYa
 * @since 2024/06/19/下午7:05
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    String name();
    Category category();

    boolean array() default true;
    String description() default "";
    int key() default 114514;

    boolean startEnable() default false;
}
