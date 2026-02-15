package com.example.emissionen.reportreview;

import com.example.emissionen.report.Report;
import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.repository.ReportReviewRepository;
import com.example.emissionen.usermanagement.User;
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
    private ReportReviewRepository reportReviewRepository;

    @Inject
    private UserLoginBean userLoginBean;

    @Inject
    private FacesContext facesContext;

    private List<Report> pendingReports;

    private Report selectedReport;
    private String comment;

    @PostConstruct
    public void init() {
        pendingReports = reportRepository.findPending();
    }

    /*
        ====== APPROVE ======
     */
    public void approve() {

        User reviewer = userLoginBean.getLoggedInUser();

        if (reviewer == null) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nicht eingeloggt.", null));
            return;
        }

        ReportReview review = new ReportReview();
        review.setReport(selectedReport);
        review.setReviewer(reviewer);
        review.setStatus(ReviewStatus.APPROVED);
        review.setComment(comment);

        selectedReport.setStatus(ReviewStatus.APPROVED);

        reportReviewRepository.save(review);
        reportRepository.update(selectedReport);

        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Report wurde freigegeben.", null));

        pendingReports = reportRepository.findPending();
    }

    /*
        ====== REJECT ======
     */
    public void reject() {

        User reviewer = userLoginBean.getLoggedInUser();

        if (reviewer == null) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nicht eingeloggt.", null));
            return;
        }

        ReportReview review = new ReportReview();
        review.setReport(selectedReport);
        review.setReviewer(reviewer);
        review.setStatus(ReviewStatus.REJECTED);
        review.setComment(comment);

        selectedReport.setStatus(ReviewStatus.REJECTED);

        reportReviewRepository.save(review);
        reportRepository.update(selectedReport);

        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Report wurde abgelehnt.", null));

        pendingReports = reportRepository.findPending();
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
