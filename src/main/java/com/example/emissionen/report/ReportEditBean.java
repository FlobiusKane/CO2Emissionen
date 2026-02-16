package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.usermanagement.User;
import com.example.emissionen.usermanagement.UserLoginBean;
import com.example.emissionen.usermanagement.UserRole;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportEditBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private UserLoginBean userLoginBean;

    @Inject
    private FacesContext facesContext;

    private Long reportId;
    private Report report;

    public void load() {
        if (reportId == null) {
            report = null;
            return;
        }

        report = reportRepository.findById(reportId);

        User currentUser = userLoginBean.getLoggedInUser();
        if (report == null || currentUser == null) {
            report = null;
            return;
        }

        // Zugriff: Admin oder Autor
        boolean allowed = currentUser.getRole() == UserRole.ADMIN
                || (report.getSubmittedBy() != null && currentUser.getId().equals(report.getSubmittedBy().getId()));

        if (!allowed) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Keine Berechtigung", "Du darfst diesen Report nicht bearbeiten."));
            report = null;
        }
    }

    public String save() {
        if (report == null) return null;

        // Wenn bereits reviewed wurde: wieder auf PENDING setzen
        if (report.getStatus() == ReviewStatus.APPROVED || report.getStatus() == ReviewStatus.REJECTED) {
            report.setStatus(ReviewStatus.PENDING);
        }

        reportRepository.update(report);

        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Gespeichert", "Der Report wurde gespeichert (ggf. erneut zur Prüfung eingereicht)."));

        return "/report-details.xhtml?faces-redirect=true&reportId=" + report.getId();
    }

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public Report getReport() { return report; }
}
