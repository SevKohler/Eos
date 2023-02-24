package org.bih.eos.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = {@ComponentScan("org.bih.eos.jpabase.jpa.dao"),
        @ComponentScan("org.bih.eos.jpabase.service")})
@EntityScan(basePackages = "org.bih.openehromopbridge.jpabase.model.entity")
public class JPAConfig {
}
