package com.example.emissionen.report;

import com.example.emissionen.reportreview.ReportReview;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.usermanagement.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;

    private String country;
    @Column(name = "report_year")
    private int year;

    private double emissionValue;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status = ReviewStatus.PENDING;

    @ManyToOne
    private User submittedBy;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportReview> reportReviews = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getEmissionValue() {
        return emissionValue;
    }

    public void setEmissionValue(double emissionValue) {
        this.emissionValue = emissionValue;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
    }

    public List<ReportReview> getReportReviews() {
        return reportReviews;
    }

    public void setReportReviews(List<ReportReview> reportReviews) {
        this.reportReviews = reportReviews;
    }
}
