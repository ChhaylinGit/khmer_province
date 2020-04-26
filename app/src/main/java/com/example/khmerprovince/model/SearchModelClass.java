package com.example.khmerprovince.model;

import java.util.List;

public class SearchModelClass {

    public String name;
    public String id;
    public List<String> cuisines;
    public boolean isCuisine;
    public int numberOfCuisine;

    public SearchModelClass(String id, String name, List<String> cuisines, boolean isCuisine, int numberOfCuisine) {

        this.name = name;
        this.id = id;
        this.cuisines = cuisines;
        this.isCuisine = isCuisine;
        this.numberOfCuisine = numberOfCuisine;
    }

}
