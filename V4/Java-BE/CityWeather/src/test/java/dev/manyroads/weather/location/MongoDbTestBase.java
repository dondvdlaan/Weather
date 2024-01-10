package dev.manyroads.weather.location;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

/*
Single Container Patterns, a base class is launched. See also testcontainers.org. With this Docker test container
there is no use to start up Netty server
 */
public abstract class MongoDbTestBase {
  private static MongoDBContainer database = new MongoDBContainer("mongo:latest");
  
  static {
    database.start();
  } 
  
  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.host", database::getContainerIpAddress);
    registry.add("spring.data.mongodb.port", () -> database.getMappedPort(27017));
    registry.add("spring.data.mongodb.database", () -> "test");
  }
}
