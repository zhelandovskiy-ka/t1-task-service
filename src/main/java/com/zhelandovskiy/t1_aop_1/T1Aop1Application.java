package com.zhelandovskiy.t1_aop_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class T1Aop1Application {

	public static void main(String[] args) {
		SpringApplication.run(T1Aop1Application.class, args);
	}

}
