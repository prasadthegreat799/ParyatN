package com.nappdeveloper.paryatn.Model;

public class Model {

    String companyLogo, companyName, companyLocation, companyType, companyRating;
    String filterName;

    public Model() {}

    public Model(String companyLogo, String companyName, String companyLocation, String companyType, String companyRating, String filterName) {
        this.companyLogo = companyLogo;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyType = companyType;
        this.companyRating = companyRating;
        this.filterName = filterName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(String companyRating) {
        this.companyRating = companyRating;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}