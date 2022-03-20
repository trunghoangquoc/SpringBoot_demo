package com.laptrinhjavaweb;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

 

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//(exclude = {SecurityAutoConfiguration.class }) bỏ default passwork trong spring security 
//vì cta chưa xét phân quyền tại khóa học này
public class Application {
	   public static void main(String[] args) {

	        SpringApplication.run(Application.class, args);

	    }
}
