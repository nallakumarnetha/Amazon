package com.amazon;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.amazon.common.Logger.log;

@SpringBootApplication
@EnableJpaAuditing
public class AmazonBackendApplication {

	public static void main(String[] args) {
		// To skip debugger at SilentExitException
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            if ("SilentExitException".equals(throwable.getClass().getSimpleName())) {
                // silently ignore
                return;
            }
            // handle other exceptions normally
            throwable.printStackTrace();
        });
        
		SpringApplication.run(AmazonBackendApplication.class, args);
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + "@@@@@");
	}

}
