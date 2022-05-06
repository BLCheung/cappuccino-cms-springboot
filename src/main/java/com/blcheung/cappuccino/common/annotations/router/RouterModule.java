package com.blcheung.cappuccino.common.annotations.router;

import java.lang.annotation.*;

/**
 * 路由模块注解，打在控制器类上
 *
 * @author BLCheung
 * @date 2022/1/1 3:36 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RouterModule {

    String name() default "";
}
