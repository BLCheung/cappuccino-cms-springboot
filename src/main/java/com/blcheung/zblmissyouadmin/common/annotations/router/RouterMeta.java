package com.blcheung.zblmissyouadmin.common.annotations.router;

import java.lang.annotation.*;

/**
 * 路由元信息注解
 *
 * @author BLCheung
 * @date 2022/1/1 3:53 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RouterMeta {

    String name() default "";

    String module() default "";

    boolean mount() default true;
}
