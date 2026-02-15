package com.example.emissionen.report;

import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReportBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private UserLoginBean loginBean;

    private Report report = new Report();

    public void submit() {
        var user = loginBean.getLoggedInUser();

        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bitte einloggen.", null));
            return;
        }

        // Nur Researcher oder Admin darf einreichen
        if (!(user.isResearcher() || user.isAdmin())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Keine Berechtigung.", null));
            return;
        }

        report.setSubmittedBy(user);
        report.setStatus(ReviewStatus.PENDING);   // wichtig: immer erstmal pending
        reportRepository.save(report);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Report eingereicht (Status: PENDING).", null));

        report = new Report(); // Formular reset
    }


    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public Report getReport() {
        return report;
    }
}
