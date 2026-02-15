package com.example.emissionen.report;


import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.usermanagement.UserLoginBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class MyReportsBean {

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private UserLoginBean loginBean;

    public List<Report> getMyReports() {
        return reportRepository.findByUser(
                loginBean.getLoggedInUser().getId());
    }
}

