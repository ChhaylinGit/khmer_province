package com.example.khmerprovince.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.khmerprovince.R;
import com.example.khmerprovince.activity.CommuneActivity;
import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.District;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewholder> {

    private List<District> districtList;
    private Context context;

    public DistrictAdapter(List<District> districtList,Context context)
    {
        this.districtList = districtList;
        this.context = context;
    }

    @NonNull
    @Override
    public DistrictViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_district,viewGroup,false);
        return new DistrictViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewholder holder, int i) {
        District district = districtList.get(i);
        String type = district.getDistype() == 1 ? "ឃុំ:" : "សង្កាត់:";
        holder.tvDistrict.setText(district.getDistrict());
        holder.tvDistrictNo.setText(i+1+".");
        holder.tvNumberOfDistrict.setText(type+district.getCommune());
        holder.tvNumberOfVillage.setText("ភូមិ:"+district.getVillage());
    }

    @Override
    public int getItemCount() {
        return districtList.size();
    }

    class DistrictViewholder extends RecyclerView.ViewHolder{
        private TextView tvDistrict ;
        private TextView tvDistrictNo;
        private TextView tvNumberOfDistrict;
        private TextView tvNumberOfVillage;
        private ImageView img;
        public DistrictViewholder(@NonNull View itemView) {
            super(itemView);
            tvDistrict = itemView.findViewById(R.id.tvDistrictName);
            tvDistrictNo= itemView.findViewById(R.id.tvDistrictNo);
            tvNumberOfDistrict = itemView.findViewById(R.id.tvNumberOfDistrict);
            tvNumberOfVillage = itemView.findViewById(R.id.tvNumberOfVillage);
            img = itemView.findViewById(R.id.imgNext);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommuneActivity.class);
                    intent.putExtra(ConstantField.DIS_NAME,districtList.get(getAdapterPosition()).getDistrict());
                    intent.putExtra(ConstantField.PROID,districtList.get(getAdapterPosition()).getProid());
                    intent.putExtra(ConstantField.DISID,districtList.get(getAdapterPosition()).getDisid());
                    context.startActivity(intent);
                }
            });
        }
    }
}
