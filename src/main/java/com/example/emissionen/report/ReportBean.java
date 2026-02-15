package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.usermanagement.UserLoginBean;
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
        report.setSubmittedBy(loginBean.getLoggedInUser());
        reportRepository.save(report);
        report = new Report();
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public Report getReport() {
        return report;
    }
}
