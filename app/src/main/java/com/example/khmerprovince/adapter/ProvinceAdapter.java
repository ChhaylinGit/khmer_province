package com.example.khmerprovince.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khmerprovince.activity.DistrictActivity_Tab;
import com.example.khmerprovince.model.ConstantField;
import com.example.khmerprovince.model.Province;
import com.example.khmerprovince.R;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder> {

    private List<Province> provinceList;
    private Context context;

    public ProvinceAdapter(List<Province> provinceList,Context context){
        this.provinceList = provinceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProvinceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout_province,viewGroup,false);
        return new ProvinceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceHolder provinceHolder, int i) {
        Province province = provinceList.get(i);
        provinceHolder.tvProvinceNameKH.setText(province.getProNameKh());

        if(province.getUrl().equals("") || province.getUrl() == null)
        {
            Glide.with(context).load(R.mipmap.no_image).into(provinceHolder.imgProvince);
        }else{ Glide.with(context).load(province.getUrl()).into(provinceHolder.imgProvince);}
    }

    @Override
    public int getItemCount() {
        return provinceList.size();
    }

    class ProvinceHolder extends RecyclerView.ViewHolder{
        private TextView tvProvinceNameKH;
        private ImageView imgProvince;
        public ProvinceHolder(@NonNull View itemView) {
            super(itemView);
            tvProvinceNameKH = itemView.findViewById(R.id.tvProvinceNameKH);
            imgProvince = itemView.findViewById(R.id.imgProvince);
            imgProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DistrictActivity_Tab.class);
                    intent.putExtra(ConstantField.PROID,provinceList.get(getAdapterPosition()).getProid());
                    intent.putExtra(ConstantField.PRO_NAME,provinceList.get(getAdapterPosition()).getProNameKh());
                    intent.putExtra(ConstantField.IMAGE_PROVINCE_DETAIL,provinceList.get(getAdapterPosition()).getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

}
