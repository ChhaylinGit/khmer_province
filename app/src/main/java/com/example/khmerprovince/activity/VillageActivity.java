package com.example.khmerprovince.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.khmerprovince.Navigation;
import com.example.khmerprovince.R;
import com.example.khmerprovince.adapter.CommuneAdapter;
import com.example.khmerprovince.adapter.VillageAdapter;
import com.example.khmerprovince.model.Commune;
import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.Village;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VillageActivity extends AppCompatActivity {

    private List<Village> villageList = new ArrayList<>();
    private VillageAdapter adapter;
    private RecyclerView recyclerView;
    private String commune,disid,proid,comid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village);
        recyclerView = findViewById(R.id.recyclerViewVillage);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        commune = getIntent().getStringExtra(ConstantField.COM_NAME);
        disid = getIntent().getStringExtra(ConstantField.DISID);
        proid = getIntent().getStringExtra(ConstantField.PROID);
        comid = getIntent().getStringExtra(ConstantField.COMID);
        Log.e("zzzzzzzz",commune+" / "+disid+" / "+proid+" / "+comid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(commune);
        getAllVillage();
    }

    private void getAllVillage()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Village").child(proid).child(disid).child(comid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                villageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Village village = snapshot.getValue(Village.class);
                    villageList.add(village);
                }
                adapter = new VillageAdapter(villageList,VillageActivity.this);
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
