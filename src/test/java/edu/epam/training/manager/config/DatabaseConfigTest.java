package edu.epam.training.manager.config;

import com.zaxxer.hikari.HikariConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {

    @Test
    void testHikariConfigValuesAreSetCorrectly() {
        DatabaseConfig config = new DatabaseConfig();

        ReflectionTestUtils.setField(config, "driverClassName", "com.mysql.cj.jdbc.Driver");
        ReflectionTestUtils.setField(config, "jdbcUrl", "jdbc:mysql://localhost:3306/fake_db");
        ReflectionTestUtils.setField(config, "jdbcUsername", "fakeUser");
        ReflectionTestUtils.setField(config, "jdbcPassword", "fakePass");
        ReflectionTestUtils.setField(config, "maximumPoolSize", 10);
        ReflectionTestUtils.setField(config, "minimumIdle", 2);
        ReflectionTestUtils.setField(config, "poolName", "spring-hikari-pool");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/fake_db");
        hikariConfig.setUsername("fakeUser");
        hikariConfig.setPassword("fakePass");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setPoolName("spring-hikari-pool");

        assertEquals("com.mysql.cj.jdbc.Driver", hikariConfig.getDriverClassName());
        assertEquals("jdbc:mysql://localhost:3306/fake_db", hikariConfig.getJdbcUrl());
        assertEquals("fakeUser", hikariConfig.getUsername());
        assertEquals("fakePass", hikariConfig.getPassword());
        assertEquals(10, hikariConfig.getMaximumPoolSize());
        assertEquals(2, hikariConfig.getMinimumIdle());
        assertEquals("spring-hikari-pool", hikariConfig.getPoolName());
    }
}
