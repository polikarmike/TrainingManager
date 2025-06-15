package edu.epam.training.manager.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("edu.epam.training.manager")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
