package com.example.khmerprovince.model;

import android.view.View;
import android.widget.TextView;

import com.example.khmerprovince.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class AboutProvinceViewHolder extends ChildViewHolder {
    TextView textView;
    public AboutProvinceViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tvExpandAboutProvince);
    }

    public void bind(ExpandProvince province)
    {
        textView.setText(province.name);
    }
}
