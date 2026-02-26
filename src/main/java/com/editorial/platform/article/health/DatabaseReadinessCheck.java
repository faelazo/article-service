package com.editorial.platform.article.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class DatabaseReadinessCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager
                    .createNativeQuery("SELECT 1")
                    .getSingleResult();

            return HealthCheckResponse.named("database-readiness-check")
                    .up()
                    .build();

        } catch (Exception e) {
            return HealthCheckResponse.named("database-readiness-check")
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}