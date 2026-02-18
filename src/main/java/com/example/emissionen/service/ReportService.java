package com.example.emissionen.service;

import com.example.emissionen.accessmanagement.LoggedInOnly;
import com.example.emissionen.report.Report;
import com.example.emissionen.reportreview.ReviewStatus;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.usermanagement.User;
import com.example.emissionen.usermanagement.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReportService {

    @Inject
    private ReportRepository reportRepository;

    public boolean canEdit(User user, Report report) {
        if (user == null || report == null) return false;
        if (user.getRole() == UserRole.ADMIN) return true;

        return report.getSubmittedBy() != null
                && report.getSubmittedBy().getId().equals(user.getId());
    }

    @LoggedInOnly
    public void submit(Report report, User author) {
        report.setSubmittedBy(author);
        report.setStatus(ReviewStatus.PENDING);
        reportRepository.save(report);
    }

    @LoggedInOnly
    public boolean update(Long reportId, Report newData, User currentUser) {
        Report existing = reportRepository.findById(reportId);
        if (!canEdit(currentUser, existing)) return false;

        existing.setCountry(newData.getCountry());
        existing.setYear(newData.getYear());
        existing.setEmissionValue(newData.getEmissionValue());

        // WICHTIG: Änderung => erneut ins Peer-Review
        existing.setStatus(ReviewStatus.PENDING);

        reportRepository.update(existing);
        return true;
    }
}
