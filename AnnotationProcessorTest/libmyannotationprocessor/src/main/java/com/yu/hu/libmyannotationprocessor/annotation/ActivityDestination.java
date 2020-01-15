package com.yu.hu.libmyannotationprocessor.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


/**
 * Created by Hy on 2020/01/15 21:24
 * <p>
 * 用于标记Activity
 **/
@Target(ElementType.TYPE) //标记在类、接口上
public @interface ActivityDestination {

    String pageUrl();  //url

    boolean needLogin() default false;  //是否需要登录 默认false

    boolean asStarter() default false;  //是否是首页  默认false
}
