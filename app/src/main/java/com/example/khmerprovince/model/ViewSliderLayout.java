package com.example.khmerprovince.model;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.khmerprovince.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewSliderLayout {

    private SliderLayout slider;
    private List<Slider> sliderList = new ArrayList<>();
    private Context context;

    public void showSlider(View view, final Context context)
    {
        slider = view.findViewById(R.id.slider);
        this.context = context;
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Slider");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sliderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Slider slider = snapshot.getValue(Slider.class);
                    sliderList.add(slider);

                }
                HashMap<String, String> file_maps = new HashMap<>();
                for (Slider sl : sliderList) {
                    file_maps.put(sl.getTitle(),sl.image);
                }
                for (String name : file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(context);
                    textSliderView.description(name).image(file_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra", name);
                    slider.addSlider(textSliderView);
                }
                slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
                slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                slider.setCustomAnimation(new DescriptionAnimation());
                slider.setDuration(4000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
