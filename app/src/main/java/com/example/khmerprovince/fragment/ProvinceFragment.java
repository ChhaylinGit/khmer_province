package com.example.khmerprovince.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khmerprovince.model.Province;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.ProvinceAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ProvinceFragment extends Fragment {


    private RecyclerView recyclerView;
//    private FirebaseDatabase database;

    private List<Province> provinceList = new ArrayList<Province>();
    private ProvinceAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_province_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerProvince);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getAllProvince();
        return view;
    }


    private void getAllProvince()
    {
        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Province");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    provinceList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Province province = snapshot.getValue(Province.class);
                        provinceList.add(province);
//                        Log.e("oooooo", snapshot.getKey() + snapshot.getValue());
                    }

                adapter = new ProvinceAdapter(provinceList,getActivity());
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
