package com.org.apibancookay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.org.apibancookay.interfaces.ApplicationConfiguration;

@SpringBootApplication
public class ApiBancoOkayApplication implements ApplicationRunner {
	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(ApiBancoOkayApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		applicationConfiguration.showConfiguration();
	}
}
