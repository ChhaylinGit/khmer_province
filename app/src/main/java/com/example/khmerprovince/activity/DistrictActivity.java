package com.example.khmerprovince.activity;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.DistrictAdapter;
import com.example.khmerprovince.adapter.ProvinceAdapter;
import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.District;
import com.example.khmerprovince.model.Province;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    private List<District> districtList = new ArrayList<>();
    private DistrictAdapter adapter;
    private ListView lstDistrict;
    private String proid,proname,imgurl;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        lstDistrict = findViewById(R.id.lstDistrict);
        imageView = findViewById(R.id.imgProvinceDetail);
        proid = getIntent().getStringExtra(ConstantField.PROID);
        proname = getIntent().getStringExtra(ConstantField.PRO_NAME);
        imgurl = getIntent().getStringExtra(ConstantField.IMAGE_PROVINCE_DETAIL);
        getAllDistrict();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(proname);
        Glide.with(this).load(imgurl).into(imageView);
    }

    private void getAllDistrict()
    {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("District").child(proid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                districtList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    District districts = snapshot.getValue(District.class);
                    districtList.add(districts);
                }
//              adapter = new DistrictAdapter(districtList);
//              lstDistrict.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
