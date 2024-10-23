package br.dev.ferreiras.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	/**
	 * Application entry point
	 * @param args no args
	 */

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	/**
	 * Indicates start time, zone and Java version
	 * @param args no args
	 * @throws Exception UnsupportedOperationException
	 */
	@Override
	public void run(String... args) throws Exception {
		final LocalDateTime localDateTime = LocalDateTime.now();
		final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
		final String javaVersion = System.getProperty("java.version");

		if (Application.logger.isInfoEnabled() ) {
			Application.logger.info("Application startup at {}, zone {}, running java {}",
			localDateTime, zonedDateTime, javaVersion);
		}

	}
}
