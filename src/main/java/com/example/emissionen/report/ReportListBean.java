package com.example.emissionen.report;

import com.example.emissionen.repository.ReportRepository;
import com.example.emissionen.reportreview.ReviewStatus;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReportListBean implements Serializable {

    @Inject
    private ReportRepository reportRepository;

    // Filter
    private String country;
    private Integer yearFrom;
    private Integer yearTo;
    private ReviewStatus status;

    // Sort
    private String sortBy = "year"; // Standard
    private String sortDir = "DESC";      // ASC oder DESC

    private List<Report> reports;

    @PostConstruct
    public void init() {
        search();
    }

    public void search() {
        reports = reportRepository.search(country, yearFrom, yearTo, status, sortBy, sortDir);
    }

    public void reset() {
        country = null;
        yearFrom = null;
        yearTo = null;
        status = null;
        sortBy = "year";
        sortDir = "DESC";
        search();
    }

    public List<Report> getReports() { return reports; }

    public List<ReviewStatus> getAvailableStatuses() {
        return List.of(ReviewStatus.values());
    }

    // Getter/Setter
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getYearFrom() { return yearFrom; }
    public void setYearFrom(Integer yearFrom) { this.yearFrom = yearFrom; }

    public Integer getYearTo() { return yearTo; }
    public void setYearTo(Integer yearTo) { this.yearTo = yearTo; }

    public ReviewStatus getStatus() { return status; }
    public void setStatus(ReviewStatus status) { this.status = status; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDir() { return sortDir; }
    public void setSortDir(String sortDir) { this.sortDir = sortDir; }
}
