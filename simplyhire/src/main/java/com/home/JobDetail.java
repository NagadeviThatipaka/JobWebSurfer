package com.home;

public class JobDetail {
    private String TitleLocation;
    private String BasicDetails;
    private String Qualifications;
    private String FullJobDescription;

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
}
