package com.example.khmerprovince.fragment.district;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.AboutProvinceAdapter;

import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.ExpandAboutProvince;
import com.example.khmerprovince.model.ExpandProvince;
import com.example.khmerprovince.model.AboutProvince;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutProvinceFragment extends Fragment {

    private ImageView imageView;
    private String proid,proname,imgurl;
    private RecyclerView recyclerView;
    private List<AboutProvince> aboutProvinces = new ArrayList<>();
    private AboutProvinceAdapter adapter;


    public AboutProvinceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_province, container, false);
        imageView = view.findViewById(R.id.imgAboutProvince);
        recyclerView = view.findViewById(R.id.recyclerAboutProvince);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imgurl = getActivity().getIntent().getStringExtra(ConstantField.IMAGE_PROVINCE_DETAIL);
        proid = getActivity().getIntent().getStringExtra(ConstantField.PROID);

        Glide.with(this).load(imgurl).into(imageView);
        getAllDistrict();
        return view;
    }


    private void getAllDistrict()
    {
        final ArrayList<ExpandAboutProvince> provinces = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("AboutProvince").child(proid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aboutProvinces.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AboutProvince aboutProvince = snapshot.getValue(AboutProvince.class);
                    ArrayList<ExpandProvince> expandProvinces = new ArrayList<>();
                    ExpandAboutProvince one = new ExpandAboutProvince(aboutProvince.getTitle(),expandProvinces);
                    expandProvinces.add(new ExpandProvince(aboutProvince.desc));
                    provinces.add(one);
                    adapter = new AboutProvinceAdapter(provinces);
                    recyclerView.setAdapter(adapter);

                }
                if(adapter != null) {
                    for (int i = adapter.getGroups().size() - 1; i >= 0; i--) {
                        expandGroup(i);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void expandGroup (int gPos){
        if(adapter.isGroupExpanded(gPos)){
            return;
        }
        adapter.toggleGroup(gPos);
    }

}
