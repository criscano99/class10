package com.criscano.project.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    private static final String DEFAULT_AUDITOR = "system";
    private static final String USER_HEADER = "X-User";

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(resolveCurrentAuditor());
    }

    private String resolveCurrentAuditor() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            HttpServletRequest request = attributes.getRequest();
            String user = request.getHeader(USER_HEADER);

            if (StringUtils.hasText(user)) {
                return user.trim();
            }
        }

        return DEFAULT_AUDITOR;
    }
}
