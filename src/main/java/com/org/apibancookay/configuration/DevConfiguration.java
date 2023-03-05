package com.org.apibancookay.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.org.apibancookay.interfaces.ApplicationConfiguration;

@Component
@Profile("dev")
public class DevConfiguration implements ApplicationConfiguration {
	@Override
	public void showConfiguration() {
		System.out.println("configuração de desenvolvimento");
	}
}
