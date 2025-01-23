package com.home;

public class JobDetail {
    private String Key;
    private String TitleLocation;
    private String BasicDetails;
    private String Qualifications;
    private String FullJobDescription;

    private String JobTitle;
    private String Company;
    private String JobLocation;
    private  String ptime;

    public JobDetail(String titleLocation, String basicDetails, String qualifications, String fullJobDescription) {
        TitleLocation = titleLocation;
        BasicDetails = basicDetails;
        Qualifications = qualifications;
        FullJobDescription = fullJobDescription;
    }

    public String getTitleLocation() {
        return TitleLocation;
    }

    public void setTitleLocation(String titleLocation) {
        TitleLocation = titleLocation;
    }

    public String getBasicDetails() {
        return BasicDetails;
    }

    public void setBasicDetails(String basicDetails) {
        BasicDetails = basicDetails;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
    }

    public String getFullJobDescription() {
        return FullJobDescription;
    }

    public void setFullJobDescription(String fullJobDescription) {
        FullJobDescription = fullJobDescription;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getJobLocation() {
        return JobLocation;
    }

    public void setJobLocation(String jobLocation) {
        JobLocation = jobLocation;
    }

    public String getTime() {
        return ptime;
    }

    public void setTime(String ptime) {
        this.ptime = ptime;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
