package com.example.emissionen.repository;

import com.example.emissionen.reportreview.ReportReview;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class ReportReviewRepository {

    private final EntityManager em =
            Persistence.createEntityManagerFactory("emissionPU")
                    .createEntityManager();

    public void save(ReportReview review) {
        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
    }
}

