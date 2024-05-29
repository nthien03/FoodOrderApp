package com.example.btlapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlapi.R;
import com.example.btlapi.model.Food;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context context;
    private List<Food> imagesFoods;
    private String urlAPI = "http://192.168.1.100:3333/api/images/";
    public Food getItem(int position) {
        if (position < 0 || position >= imagesFoods.size()) {
            return null;
        }
        return imagesFoods.get(position);
    }
    public ReviewAdapter(Context context, List<Food> imagesFoods) {
        this.context = context;
        this.imagesFoods = imagesFoods;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
    Food imagesFood = imagesFoods.get(position);
    if(imagesFood != null){
        if(imagesFood.getImages()!=null){
            Glide.with(context).load(urlAPI + imagesFood.getImages()).into(holder.images);

        }
    }
    }

    @Override
    public int getItemCount() {
        return imagesFoods.size();
    }
    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        EditText editTextReview;
        TextView textView;
        ImageView images;
        RadioGroup radioGroup;
        RadioButton start1, start2, start3, start4, start5;
        CardView cardView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.review_container);
            editTextReview = itemView.findViewById(R.id.editTextReview);
            images = itemView.findViewById(R.id.images);
            radioGroup = itemView.findViewById(R.id.radio_group);
            textView = itemView.findViewById(R.id.textView);
            start1 = itemView.findViewById(R.id.star1);
            start2 = itemView.findViewById(R.id.star2);
            start3 = itemView.findViewById(R.id.star3);
            start4 = itemView.findViewById(R.id.star4);
            start5 = itemView.findViewById(R.id.star5);

        }
    }

}
