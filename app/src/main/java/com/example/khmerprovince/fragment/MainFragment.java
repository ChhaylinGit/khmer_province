package com.example.khmerprovince.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.ProvinceAdapter;
import com.example.khmerprovince.model.Province;
import com.example.khmerprovince.model.Slider;
import com.example.khmerprovince.model.ViewSliderLayout;
import com.google.firebase.FirebaseApp;
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
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private SliderLayout sliderLayout;
    private ViewSliderLayout slider = new ViewSliderLayout();
    private List<Province> provinceList = new ArrayList<Province>();
    private ProvinceAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewProvince);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        sliderLayout = view.findViewById(R.id.slider);
        slider.showSlider(view,getActivity());
        getAllProvince();
        return view;
    }

    private void getAllProvince()
    {
        FirebaseApp.initializeApp(getActivity());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Province");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provinceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Province province = snapshot.getValue(Province.class);
                    provinceList.add(province);
                    Log.e("oooooo",province.getUrl());
                }
                adapter = new ProvinceAdapter(provinceList,getActivity());
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("oooooo",databaseError.getMessage());
            }
        });

    }

}
