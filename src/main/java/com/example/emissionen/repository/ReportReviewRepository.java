package com.example.emissionen.repository;

import com.example.emissionen.reportreview.ReportReview;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

@ApplicationScoped
public class ReportReviewRepository {

    private final EntityManager em =
            Persistence.createEntityManagerFactory("emissionenPU")
                    .createEntityManager();

    public void save(ReportReview review) {
        em.getTransaction().begin();
        em.persist(review);
        em.getTransaction().commit();
    }

    public List<ReportReview> findByReportId(Long reportId) {
        return em.createQuery(
                        "SELECT rr FROM ReportReview rr " +
                                "WHERE rr.report.id = :id " +
                                "ORDER BY rr.reviewDate DESC",
                        ReportReview.class
                )
                .setParameter("id", reportId)
                .getResultList();
    }

    public ReportReview findLatestByReportId(Long reportId) {
        var list = em.createQuery(
                        "SELECT rr FROM ReportReview rr " +
                                "WHERE rr.report.id = :id " +
                                "ORDER BY rr.reviewDate DESC",
                        ReportReview.class
                )
                .setParameter("id", reportId)
                .setMaxResults(1)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }
}
