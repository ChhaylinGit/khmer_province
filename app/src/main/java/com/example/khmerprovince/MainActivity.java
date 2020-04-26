package com.example.khmerprovince;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText edtClass,edtKey,edtValue;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        edtClass = findViewById(R.id.edtClass);
        edtKey = findViewById(R.id.edtKey);
        edtValue = findViewById(R.id.edtValue);
        database = FirebaseDatabase.getInstance();
        readUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference(edtClass.getText().toString());
                DatabaseReference child = myRef.child(edtKey.getText().toString());
                child.setValue(edtValue.getText().toString());
            }
        });

    }

    private void readUser()
    {
        DatabaseReference ref = database.getReference("Province");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) map.get("name");
                for (Map.Entry<String,Object> entry: map.entrySet()) {
                    Log.e("myvalue","Key: "+entry.getKey()+" Value: "+entry.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
