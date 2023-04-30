package org.example.configuration;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@EnableMBeanExport
@Import({DatabaseConfiguration.class,
         SecurityConfiguration.class,
         })
public class ApplicationConfiguration {

}
