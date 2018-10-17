package com.mballem.curso.boot;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.server.i18n.FixedLocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootApplication
public class DemoMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMvcApplication.class, args);
	}
	
//	public LocaleResolver localeResolver() {
//		return (LocaleResolver) new FixedLocaleContextResolver(new Locale("pt", "BR"));
//	}
}
