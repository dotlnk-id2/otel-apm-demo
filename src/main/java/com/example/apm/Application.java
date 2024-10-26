package com.example.apm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// String defaultNameserverString = SystemPropertyUtil.get("io.netty.resolver.dns.defaultNameServerFallback", null);
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public OpenTelemetry openTelemetry() {
		return GlobalOpenTelemetry.get();
	}


	@Bean
	public Tracer initTracer(OpenTelemetry openTelemetry){
		return openTelemetry.getTracer(Application.class.getName());
	}

	@Bean
	public Meter initMeter(OpenTelemetry openTelemetry){
		return openTelemetry.getMeter(Application.class.getName());
	}
	@Bean
	public LongHistogram initHistogram(Meter meter){
		return meter.histogramBuilder("do-work").ofLongs().build();
	}
}
