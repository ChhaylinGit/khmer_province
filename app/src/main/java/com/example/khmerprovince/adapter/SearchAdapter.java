package com.example.khmerprovince.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khmerprovince.R;
import com.example.khmerprovince.activity.SearchActivity;
import com.example.khmerprovince.model.District;
import com.example.khmerprovince.model.SearchModelClass;
import com.google.android.gms.actions.ItemListIntents;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private ItemListener mListener;
    private String prefix = "";
    private List<SearchModelClass> allVendors, cuisines;

    public SearchAdapter(List<SearchModelClass> cuisines,List<SearchModelClass> allVendors,Context context, ItemListener mListener, String prefix) {
        this.context = context;
        this.mListener = mListener;
        this.prefix = prefix;
        this.allVendors = allVendors;
        this.cuisines = cuisines;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_list_row, viewGroup, false);
        SearchViewHolder myViewHolder = new SearchViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {
        if (prefix.length() > 0)
            holder.setData(allVendors.get(holder.getAdapterPosition()), holder);
        else
            holder.setData(cuisines.get(holder.getAdapterPosition()), holder);
    }

    @Override
    public int getItemCount() {

        if (prefix.length() > 0)
            return allVendors.size();
        else
            return cuisines.size();

    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView icon;
        TextView textView;
        View parent;
        SearchModelClass vendorModel;

         SearchViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView.findViewById(R.id.parentView);
            this.textView = itemView.findViewById(R.id.textView);
            this.icon = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        void setData(SearchModelClass vendorModel,SearchViewHolder holder){
            textView = holder.textView;
            icon = holder.icon;
            this.vendorModel = vendorModel;

            if(prefix.length() > 0){
                if(vendorModel.isCuisine){
                    textView.setText(highlight(prefix,vendorModel.name + "(" +vendorModel.numberOfCuisine + ")"));
                    icon.setImageResource(R.mipmap.ic_local_offer);
                }else{
                    textView.setText(highlight(prefix,vendorModel.name));
                    icon.setImageResource(R.mipmap.ic_local_dining);
                }
            }else{
                if (vendorModel.isCuisine) {
                    textView.setText(vendorModel.name + " (" + vendorModel.numberOfCuisine + ")");
                    icon.setImageResource(R.mipmap.ic_local_offer);
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(vendorModel);
            };
        }
    }

    private static CharSequence highlight(String search, String originalText) {
        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer
                .normalize(originalText, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ENGLISH);

        int start = normalizedText.indexOf(search.toLowerCase(Locale.ENGLISH));
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(originalText);
            while (start >= 0) {
                int spanStart = Math.min(start, originalText.length());
                int spanEnd = Math.min(start + search.length(),
                        originalText.length());

                highlighted.setSpan(new ForegroundColorSpan(Color.BLUE),
                        spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }

    public interface ItemListener {
        void onItemClick(SearchModelClass model);
    }

}
