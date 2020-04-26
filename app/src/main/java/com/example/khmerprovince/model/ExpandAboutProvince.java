package com.example.khmerprovince.model;

import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandAboutProvince extends ExpandableGroup<ExpandProvince> {


    public ExpandAboutProvince(String title, List<ExpandProvince> items) {
        super(title, items);
    }
}
