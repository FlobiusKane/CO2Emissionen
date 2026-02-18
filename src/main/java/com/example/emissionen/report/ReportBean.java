package com.example.emissionen.report;

import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportBean implements Serializable {

    @Inject
    private UserLoginBean loginBean;

    @Inject
    private com.example.emissionen.service.ReportService reportService;

    private Report report = new Report();

    public void submit() {
        reportService.submit(report, loginBean.getLoggedInUser());
        report = new Report();
    }

    public Report getReport() { return report; }
}

