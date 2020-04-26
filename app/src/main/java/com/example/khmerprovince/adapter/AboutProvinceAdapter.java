package com.example.khmerprovince.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khmerprovince.R;
import com.example.khmerprovince.model.AboutProvinceViewHolder;
import com.example.khmerprovince.model.ExpandAboutProvince;
import com.example.khmerprovince.model.ExpandProvince;
import com.example.khmerprovince.model.Province;
import com.example.khmerprovince.model.ProvinceViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class AboutProvinceAdapter extends ExpandableRecyclerViewAdapter<ProvinceViewHolder, AboutProvinceViewHolder> {

    public AboutProvinceAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ProvinceViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_expand_province,parent,false);
        return new ProvinceViewHolder(view);
    }

    @Override
    public AboutProvinceViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_expand_about_province,parent,false);
        return new AboutProvinceViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(AboutProvinceViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ExpandProvince province = (ExpandProvince) group.getItems().get(childIndex);
        holder.bind(province);
    }

    @Override
    public void onBindGroupViewHolder(ProvinceViewHolder holder, int flatPosition, ExpandableGroup group) {
        final ExpandAboutProvince province = (ExpandAboutProvince) group;
        holder.bind(province);
    }
}
