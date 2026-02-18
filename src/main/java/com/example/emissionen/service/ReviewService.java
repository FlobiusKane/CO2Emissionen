package com.example.emissionen.service;

import com.example.emissionen.accessmanagement.ReviewerOnly;
import com.example.emissionen.report.Report;
import com.example.emissionen.reportreview.ReportReview;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.repository.ReportReviewRepository;
import com.example.emissionen.usermanagement.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReviewService {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportReviewRepository reportReviewRepository;

    @ReviewerOnly
    public boolean approve(Long reportId, User reviewer, String comment) {
        Report report = reportRepository.findById(reportId);
        if (report == null) return false;

        ReportReview rr = new ReportReview();
        rr.setReport(report);
        rr.setReviewer(reviewer);
        rr.setStatus(ReviewStatus.APPROVED);
        rr.setComment(comment);

        report.setStatus(ReviewStatus.APPROVED);

        reportReviewRepository.save(rr);
        reportRepository.update(report);
        return true;
    }

    @ReviewerOnly
    public boolean reject(Long reportId, User reviewer, String comment) {
        Report report = reportRepository.findById(reportId);
        if (report == null) return false;

        ReportReview rr = new ReportReview();
        rr.setReport(report);
        rr.setReviewer(reviewer);
        rr.setStatus(ReviewStatus.REJECTED);
        rr.setComment(comment);

        report.setStatus(ReviewStatus.REJECTED);

        reportReviewRepository.save(rr);
        reportRepository.update(report);
        return true;
    }
}
