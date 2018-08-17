package com.dx.Booker;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dx.Booker.generator.mapper")
public class BookerApplication
//		extends SpringBootServletInitializer
{
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(BookerApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookerApplication.class, args);
	}
}
