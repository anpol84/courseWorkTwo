package org.example.configuration;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@EnableMBeanExport
@Import({DatabaseConfiguration.class,
         SecurityConfiguration.class})
public class ApplicationConfiguration {
}
