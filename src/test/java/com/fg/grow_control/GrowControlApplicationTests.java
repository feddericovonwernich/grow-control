package com.fg.grow_control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest(classes = GrowControlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {GrowControlApplicationTests.Initializer.class})
class GrowControlApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	// Will be shared between test methods
	@Container
	private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer();

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + MY_SQL_CONTAINER.getJdbcUrl(),
					"spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
					"spring.datasource.password=" + MY_SQL_CONTAINER.getPassword(),
					"spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
					"spring.jpa.show-sql=true",
					"spring.jpa.hibernate.ddl-auto=update",
					"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect"
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
	
	@Test
	void contextLoads() {
		// If context loads, it means it successfully started and connected to the database.
	}

	@Test
	void testPingPongEndpoint() {

		// Another http library could be used here, using what we were using in the dependencies in the meantime.

		HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/ping"),
				HttpMethod.GET,
				entity,
				String.class
		);

		String expected = "pong";

		Assertions.assertEquals(expected, response.getBody());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
