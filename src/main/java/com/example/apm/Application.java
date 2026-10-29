package com.example.apm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// String defaultNameserverString = SystemPropertyUtil.get("io.netty.resolver.dns.defaultNameServerFallback", null);
		SpringApplication.run(Application.class, args);
	}

	
}
