package com.org.apibancookay.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataConfiguration {
	public void showConfiguration() {
		System.out.println("configurações de desenvolvimento");
	}
}
