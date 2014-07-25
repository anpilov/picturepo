package com.anpilov.picturepo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAsync
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = { ApplicationConfig.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationConfig {
}
