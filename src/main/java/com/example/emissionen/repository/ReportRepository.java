package com.example.emissionen.repository;

import com.example.emissionen.report.Report;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.usermanagement.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

@ApplicationScoped
public class ReportRepository {

    private final EntityManager em =
            Persistence.createEntityManagerFactory("emissionenPU")
                    .createEntityManager();

    public void save(Report report) {
        em.getTransaction().begin();
        em.persist(report);
        em.getTransaction().commit();
    }

    public void update(Report report) {
        em.getTransaction().begin();
        em.merge(report);
        em.getTransaction().commit();
    }

    public List<Report> findAll() {
        return em.createQuery("SELECT r FROM Report r", Report.class)
                .getResultList();
    }

    public List<Report> findPending() {
        return em.createQuery(
                        "SELECT r FROM Report r WHERE r.status = :status",
                        Report.class)
                .setParameter("status", ReviewStatus.PENDING)
                .getResultList();
    }

    public Report findById(Long id) {
        return em.find(Report.class, id);
    }


    public List<Report> findByUser(User user) {
        return em.createQuery(
                        "SELECT r FROM Report r WHERE r.submittedBy = :user",
                        Report.class)
                .setParameter("user", user)
                .getResultList();
    }
}
