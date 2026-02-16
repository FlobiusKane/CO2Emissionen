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


    public List<Report> search(String country,
                               Integer yearFrom,
                               Integer yearTo,
                               ReviewStatus status,
                               String sortBy,
                               String sortDir) {

        // Whitelist für sortBy (wichtig: schützt vor JPQL Injection)
        String sortField = switch (sortBy) {
            case "country" -> "r.country";
            case "year" -> "r.year";
            case "emissionValue" -> "r.emissionValue";
            case "status" -> "r.status";
            default -> "r.year";
        };

        String direction = "ASC".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";

        StringBuilder jpql = new StringBuilder("SELECT r FROM Report r WHERE 1=1");

        if (country != null && !country.trim().isEmpty()) {
            jpql.append(" AND LOWER(r.country) LIKE :country");
        }
        if (yearFrom != null) {
            jpql.append(" AND r.year >= :yearFrom");
        }
        if (yearTo != null) {
            jpql.append(" AND r.year <= :yearTo");
        }
        if (status != null) {
            jpql.append(" AND r.status = :status");
        }

        jpql.append(" ORDER BY ").append(sortField).append(" ").append(direction);

        var query = em.createQuery(jpql.toString(), Report.class);

        if (country != null && !country.trim().isEmpty()) {
            query.setParameter("country", "%" + country.trim().toLowerCase() + "%");
        }
        if (yearFrom != null) query.setParameter("yearFrom", yearFrom);
        if (yearTo != null) query.setParameter("yearTo", yearTo);
        if (status != null) query.setParameter("status", status);

        return query.getResultList();
    }

}
