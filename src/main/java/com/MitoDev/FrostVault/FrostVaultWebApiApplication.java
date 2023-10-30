package com.MitoDev.FrostVault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class FrostVaultWebApiApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(FrostVaultWebApiApplication.class, args);
	}

}
