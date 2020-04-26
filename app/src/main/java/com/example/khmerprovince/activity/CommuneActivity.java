package com.example.khmerprovince.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.khmerprovince.Navigation;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.CommuneAdapter;
import com.example.khmerprovince.model.Commune;
import com.example.khmerprovince.model.ConstantField;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommuneActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Commune> communeList = new ArrayList<>();
    private CommuneAdapter adapter;
    private String district,disid,proid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commune);
        recyclerView = findViewById(R.id.recyclerViewCommune);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        district = getIntent().getStringExtra(ConstantField.DIS_NAME);
        disid = getIntent().getStringExtra(ConstantField.DISID);
        proid = getIntent().getStringExtra(ConstantField.PROID);
        getSupportActionBar().setTitle(district);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllCommune();
    }

    private void getAllCommune()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Commune").child(proid).child(disid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                communeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Commune commune = snapshot.getValue(Commune.class);
                    Log.e("oooooo", snapshot.getKey() + snapshot.getValue());
                    communeList.add(commune);
                }
                adapter = new CommuneAdapter(communeList,CommuneActivity.this);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }else if(id == R.id.action_home)
        {
            finish();
            startActivity(new Intent(this, Navigation.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}
