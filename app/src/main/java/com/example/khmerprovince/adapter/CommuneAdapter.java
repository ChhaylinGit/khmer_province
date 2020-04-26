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

import com.example.khmerprovince.R;
import com.example.khmerprovince.activity.VillageActivity;
import com.example.khmerprovince.model.Commune;
import com.example.khmerprovince.model.ConstantField;

import java.util.List;

public class CommuneAdapter extends RecyclerView.Adapter<CommuneAdapter.CommuneViewHolder> {

    private List<Commune> communeList;
    private Context context;

    public CommuneAdapter(List<Commune> commune,Context context){
        this.communeList = commune;
        this.context = context;
    }

    @NonNull
    @Override
    public CommuneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout_commune,viewGroup,false);
        return new CommuneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommuneViewHolder holder, int i) {
        Commune commune = communeList.get(i);
        holder.tvCommuneName.setText(commune.getCommune());
        holder.tvCommuneNo.setText(i+1+".");
        holder.tvNumberOfVillage.setText("ភូមិ:"+commune.getVillage());
    }

    @Override
    public int getItemCount() {
        return communeList.size();
    }

    class CommuneViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCommuneName;
        private TextView tvCommuneNo;
        private TextView tvNumberOfVillage;
        private ImageView imgNext;
        public CommuneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommuneName = itemView.findViewById(R.id.tvCommuneName);
            tvCommuneNo = itemView.findViewById(R.id.tvCommuneNo);
            tvNumberOfVillage = itemView.findViewById(R.id.tvNumberOfVillage);
            imgNext = itemView.findViewById(R.id.imgNextCommune);
            imgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VillageActivity.class);
                    intent.putExtra(ConstantField.COM_NAME,communeList.get(getAdapterPosition()).getCommune());
                    intent.putExtra(ConstantField.DISID,communeList.get(getAdapterPosition()).getDisid());
                    intent.putExtra(ConstantField.COMID,communeList.get(getAdapterPosition()).getComid());
                    intent.putExtra(ConstantField.PROID,communeList.get(getAdapterPosition()).getProid());
                    context.startActivity(intent);
                }
            });
        }
    }

}
