package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.repository.ReportReviewRepository;
import com.example.emissionen.reportreview.ReportReview;
import com.example.emissionen.usermanagement.User;
import com.example.emissionen.usermanagement.UserLoginBean;
import com.example.emissionen.usermanagement.UserRole;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportDetailsBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportReviewRepository reportReviewRepository;

    @Inject
    private UserLoginBean userLoginBean;

    private Long reportId;
    private Report selectedReport;

    private ReportReview latestReview;

    @PostConstruct
    public void init() {
        loadReport();
    }

    public void loadReport() {
        if (reportId == null) {
            selectedReport = null;
            latestReview = null;
            return;
        }

        selectedReport = reportRepository.findById(reportId);

        if (selectedReport != null) {
            latestReview = reportReviewRepository.findLatestByReportId(reportId);
        } else {
            latestReview = null;
        }
    }

    public ReportReview getLatestReview() {
        return latestReview;
    }

    public String getLatestReviewerName() {
        if (latestReview == null || latestReview.getReviewer() == null) return "";
        return latestReview.getReviewer().getFirstname() + " " + latestReview.getReviewer().getName();
    }

    public boolean canEditReport() {
        User currentUser = userLoginBean.getLoggedInUser();
        if (currentUser == null || selectedReport == null) return false;

        if (currentUser.getRole() == UserRole.ADMIN) return true;

        // Nur Autor darf bearbeiten
        return selectedReport.getSubmittedBy() != null
                && currentUser.getId().equals(selectedReport.getSubmittedBy().getId());
    }

    public boolean canReview() {
        User currentUser = userLoginBean.getLoggedInUser();
        if (currentUser == null) return false;
        return currentUser.getRole() == UserRole.REVIEWER || currentUser.getRole() == UserRole.ADMIN;
    }


    public String getAuthorName() {
        if (selectedReport == null || selectedReport.getSubmittedBy() == null) return "";
        User author = selectedReport.getSubmittedBy();
        return author.getFirstname() + " " + author.getName();
    }

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public Report getSelectedReport() { return selectedReport; }
    public void setSelectedReport(Report selectedReport) { this.selectedReport = selectedReport; }
}
