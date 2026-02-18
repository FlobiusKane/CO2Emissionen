package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.service.ReportService;
import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportEditBean implements Serializable {

    @Inject private ReportRepository reportRepository;
    @Inject private ReportService reportService;
    @Inject private UserLoginBean userLoginBean;

    private Long reportId;
    private Report report;

    @PostConstruct
    public void init() {
        // leer – load wird per f:viewAction gemacht
    }

    public void load() {
        if (reportId == null) return;
        report = reportRepository.findById(reportId);
    }

    public String save() {
        if (report == null) return null;

        boolean ok = reportService.update(report.getId(), report, userLoginBean.getLoggedInUser());

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(ok ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR,
                        ok ? "Gespeichert – Status wieder PENDING." : "Keine Berechtigung / Fehler.", null));

        return ok ? "/report-details.xhtml?faces-redirect=true&reportId=" + report.getId() : null;
    }

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public Report getReport() { return report; }
}
