package com.example.coronavirusstats;

import java.util.Date;

class CoronaItem {
    public String country;
    public int cases;
    public int todayCases;
    public int deaths;
    public int todayDeaths;
    public int recovered;
    public int active;
    public int critical;
    public int casesPerMillion;
    public int deathsPerMillion;
    public int totalTests;
    public int testsPerMillion;
    /*public Date updateDate;


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }*/
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getCasesPerMillion() {
        return casesPerMillion;
    }

    public void setCasesPerMillion(int casesPerMillion) {
        this.casesPerMillion = casesPerMillion;
    }

    public int getDeathsPerMillion() {
        return deathsPerMillion;
    }

    public void setDeathsPerMillion(int deathsPerMillion) {
        this.deathsPerMillion = deathsPerMillion;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public int getTestsPerMillion() {
        return testsPerMillion;
    }

    public void setTestsPerMillion(int testsPerMillion) {
        this.testsPerMillion = testsPerMillion;
    }
}
