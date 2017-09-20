package com.warchm.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * 其中的@interface是一个关键字，在设计annotations的时候必须把一 个类型定义为@interface，而不能用class或interface关键字
 * @Retention(RetentionPolicy.SOURCE)    //注解仅存在于源码中，在class字节码文件中不包含
 * @Retention(RetentionPolicy.CLASS)       // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
 * @Retention(RetentionPolicy.RUNTIME)   // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
 * @Target(ElementType.TYPE)    // 接口、类、枚举、注解
 * @Document：说明该注解将被包含在javadoc中
 * @Inherited：说明子类可以继承父类中的该注解
 * @author liubin
 * @version 2017年7月21日 下午2:39:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisDao {

	/**
	 * The value may indicate a suggestion for a logical component name, to be
	 * turned into a Spring bean in case of an autodetected component.
	 * 
	 * @return the suggested component name, if any
	 */
	String value() default "";

}
