package com.mycompany.sidebysidesample.gen;

import com.mycompany.sidebysidesample.gen.config.AsyncSyncConfiguration;
import com.mycompany.sidebysidesample.gen.config.EmbeddedSQL;
import com.mycompany.sidebysidesample.gen.config.JacksonConfiguration;
import com.mycompany.sidebysidesample.gen.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { SidebysidesampleApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedSQL
public @interface IntegrationTest {
}
