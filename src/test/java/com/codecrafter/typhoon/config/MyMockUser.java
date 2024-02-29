package com.codecrafter.typhoon.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyMockUser {

	String email() default "mytest@mytest.com";

	String password() default "1111";

	String shopName() default "forTestShopname";
}
