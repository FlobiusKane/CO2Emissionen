package com.example.emissionen.usermanagement;

import com.example.emissionen.dto.UserRegistrationDTO;
import com.example.emissionen.report.Report;
import com.example.emissionen.reportreview.ReportReview;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String firstname;
    private String name;
    private String email;
    private String location;
    private String state;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    /*
        ========== BEZIEHUNGEN ==========
    */

    // Reports, die dieser User eingereicht hat
    @OneToMany(mappedBy = "submittedBy", cascade = CascadeType.ALL)
    private List<Report> submittedReports = new ArrayList<>();

    // Reviews, die dieser User durchgeführt hat
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<ReportReview> reportReviews = new ArrayList<>();


    /*
        ========== KONSTRUKTOREN ==========
    */

    public User() {}

    public User(String username, String firstname, String name,
                String email, String location, String state,
                String password, UserRole role) {

        this.username = username;
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.location = location;
        this.state = state;
        this.password = password;
        this.role = role;
    }

    public User(UserRegistrationDTO dto) {
        this.username = dto.getUsername();
        this.firstname = dto.getFirstname();
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.location = dto.getLocation();
        this.state = dto.getState();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }


    /*
        ========== ROLLEN-HILFSMETHODEN ==========
    */

    public boolean isResearcher() {
        return role == UserRole.RESEARCHER;
    }

    public boolean isReviewer() {
        return role == UserRole.REVIEWER;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }


    /*
        ========== GETTER & SETTER ==========
    */

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return firstname + " " + name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Report> getSubmittedReports() {
        return submittedReports;
    }

    public void setSubmittedReports(List<Report> submittedReports) {
        this.submittedReports = submittedReports;
    }

    public List<ReportReview> getReportReviews() {
        return reportReviews;
    }

    public void setReportReviews(List<ReportReview> reportReviews) {
        this.reportReviews = reportReviews;
    }


}
