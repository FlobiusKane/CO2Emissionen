package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
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
    private UserLoginBean userLoginBean;

    private Long reportId;
    private Report selectedReport;

    /*
        ====== Initialisierung ======
     */
    @PostConstruct
    public void init() {
        loadReport();
    }

    public void loadReport() {
        if (reportId == null) {
            selectedReport = null;
            return;
        }

        selectedReport = reportRepository.findById(reportId);
    }

    /*
        ====== Zugriffskontrolle ======
     */
    public boolean canEditReport() {

        User currentUser = userLoginBean.getLoggedInUser();

        if (currentUser == null || selectedReport == null) {
            return false;
        }

        // Admin darf immer
        if (currentUser.getRole() == UserRole.ADMIN) {
            return true;
        }

        // Reviewer darf prüfen
        if (currentUser.getRole() == UserRole.REVIEWER) {
            return true;
        }

        // Researcher darf nur eigene Reports bearbeiten
        return selectedReport.getSubmittedBy() != null
                && currentUser.getId()
                .equals(selectedReport.getSubmittedBy().getId());
    }

    /*
        ====== Anzeige Autorname ======
     */
    public String getAuthorName() {

        if (selectedReport == null
                || selectedReport.getSubmittedBy() == null) {
            return "";
        }

        User author = selectedReport.getSubmittedBy();
        return author.getFirstname() + " " + author.getName();
    }

    /*
        ====== Getter & Setter ======
     */

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Report getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(Report selectedReport) {
        this.selectedReport = selectedReport;
    }
}
