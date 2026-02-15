package com.example.emissionen.reportreview;

import com.example.emissionen.report.Report;
import com.example.emissionen.usermanagement.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReportReview {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Report report;

    @ManyToOne
    private User reviewer;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String comment;

    private LocalDateTime reviewDate;

    // ===== Konstruktor =====

    public ReportReview() {
        this.reviewDate = LocalDateTime.now();
    }

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
}
