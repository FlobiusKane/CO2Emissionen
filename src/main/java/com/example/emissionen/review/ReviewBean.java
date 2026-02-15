package com.example.emissionen.review;


import com.example.emissionen.report.Report;
import com.example.emissionen.reportreview.ReportReview;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.repository.ReportReviewRepository;
import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReviewBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportReviewRepository reviewRepository;

    @Inject
    private UserLoginBean loginBean;

    private Report selectedReport;
    private String comment;

    public List<Report> getPendingReports() {
        return reportRepository.findPending();
    }

    public void approve() {

        Review review = new Review();
        review.setReport(selectedReport);
        review.setReviewer(loginBean.getLoggedInUser());
        review.setStatus(ReviewStatus.APPROVED);

        selectedReport.setStatus(ReviewStatus.APPROVED);

        ReportReview ReportReview = new ReportReview();
        reviewRepository.save(ReportReview);
        reportRepository.update(selectedReport);
    }

    public void reject() {

        Review review = new Review();
        review.setReport(selectedReport);
        review.setReviewer(loginBean.getLoggedInUser());
        review.setStatus(ReviewStatus.REJECTED);
        review.setComment(comment);

        selectedReport.setStatus(ReviewStatus.REJECTED);

        ReportReview ReportReview = new ReportReview();
        reviewRepository.save(ReportReview);
        reportRepository.update(selectedReport);
    }

    public ReportRepository getReportRepository() {
        return reportRepository;
    }

    public void setReportRepository(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public void setReviewRepository(ReportReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public UserLoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(UserLoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Report getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(Report selectedReport) {
        this.selectedReport = selectedReport;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

