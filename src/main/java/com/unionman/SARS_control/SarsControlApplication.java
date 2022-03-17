package com.unionman.SARS_control;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.unionman.SARS_control.mapper")
public class SarsControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarsControlApplication.class, args);
	}

}
