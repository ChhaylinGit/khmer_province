package com.example.khmerprovince.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khmerprovince.R;
import com.example.khmerprovince.model.Village;

import java.util.List;

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.VillageViewHolder>  {

    private List<Village> villageList;
    private Context context;

    public VillageAdapter(List<Village> villageList,Context context)
    {
        this.villageList = villageList;
        this.context = context;
    }


    @NonNull
    @Override
    public VillageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout_village,viewGroup,false);
        return new VillageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VillageViewHolder holder, int i) {
        Village village = villageList.get(i);
        holder.tvVillage.setText("ភូមិ"+village.getVillage());
        holder.tvVillageNo.setText(i+1+".");
    }

    @Override
    public int getItemCount() {
        return villageList.size();
    }

    class VillageViewHolder extends RecyclerView.ViewHolder{
        private TextView tvVillage;
        private TextView tvVillageNo;
        public VillageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVillage = itemView.findViewById(R.id.tvVillageName);
            tvVillageNo = itemView.findViewById(R.id.tvVillageNo);
        }
    }

}
