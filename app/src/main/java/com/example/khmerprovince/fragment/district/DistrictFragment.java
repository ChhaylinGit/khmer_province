package com.example.khmerprovince.fragment.district;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.DistrictAdapter;
import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.District;
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
public class DistrictFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<District> districtList = new ArrayList<>();
    private DistrictAdapter adapter;
    private String proid;

    public DistrictFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_district, container, false);
        recyclerView = view.findViewById(R.id.recyclerDistrict);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        proid = getActivity().getIntent().getStringExtra(ConstantField.PROID);
        getAllDistrict();
        return view;
    }

    private void getAllDistrict()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("District").child(proid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                districtList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    District districts = snapshot.getValue(District.class);
                    districtList.add(districts);
//                        Log.e("oooooo", snapshot.getKey() + snapshot.getValue());
                }
                adapter = new DistrictAdapter(districtList,getActivity());
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
