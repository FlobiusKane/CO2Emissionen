package com.example.emissionen.reportreview;

import com.example.emissionen.report.Report;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReportReviewBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private UserLoginBean userLoginBean;

    @Inject
    private com.example.emissionen.service.ReviewService reviewService;

    @Inject
    private FacesContext facesContext;

    private List<Report> pendingReports;

    private Report selectedReport;
    private String comment;

    @PostConstruct
    public void init() {
        pendingReports = reportRepository.findPending();
    }

    public void approve() {
        boolean ok = reviewService.approve(selectedReport.getId(), userLoginBean.getLoggedInUser(), comment);
        facesContext.addMessage(null, new FacesMessage(ok ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR,
                ok ? "Freigegeben" : "Fehlgeschlagen", null));
        pendingReports = reportRepository.findPending();
        comment = null;
    }

    public void reject() {
        boolean ok = reviewService.reject(selectedReport.getId(), userLoginBean.getLoggedInUser(), comment);
        facesContext.addMessage(null, new FacesMessage(ok ? FacesMessage.SEVERITY_WARN : FacesMessage.SEVERITY_ERROR,
                ok ? "Abgelehnt" : "Fehlgeschlagen", null));
        pendingReports = reportRepository.findPending();
        comment = null;
    }


    public List<Report> getPendingReports() {
        return pendingReports;
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


