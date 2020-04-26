package com.example.khmerprovince.model;

public class District {

    String disid;
    String district;
    int commune;
    int distype;
    String proid;
    int village;

    public int getVillage() {
        return village;
    }

    public void setVillage(int village) {
        this.village = village;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getDisid() {
        return disid;
    }

    public void setDisid(String disid) {
        this.disid = disid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCommune() {
        return commune;
    }

    public void setCommune(int commune) {
        this.commune = commune;
    }

    public int getDistype() {
        return distype;
    }

    public void setDistype(int distype) {
        this.distype = distype;
    }
}
