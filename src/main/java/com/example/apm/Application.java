package com.example.apm;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.log4j.Log4j2;

@Log4j2 
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// String defaultNameserverString = SystemPropertyUtil.get("io.netty.resolver.dns.defaultNameServerFallback", null);
		SpringApplication app = new SpringApplication(Application.class);
		app.setAllowBeanDefinitionOverriding(false);
		app.run(args);
		
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady(ApplicationReadyEvent event) {
		log.info("Application is ready");

		log.trace("This is a trace message {}",log.isTraceEnabled());
		log.debug("This is a debug message {}",log.isDebugEnabled());
		log.info("This is an info message {}",log.isInfoEnabled());
		log.warn("This is a warn message {}",log.isWarnEnabled());
		log.error("This is an error message {}",log.isErrorEnabled());

		DataSource ds = event.getApplicationContext().getBean(DataSource.class);
		try(java.sql.Connection conn = ds.getConnection()) {
			log.info("Database connection established: {}", conn.getMetaData().getURL());
		} catch (Exception e) {
			log.error("Failed to establish database connection", e);
		}

	
	}
}

