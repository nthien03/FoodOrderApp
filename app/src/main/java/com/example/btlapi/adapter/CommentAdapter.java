package com.example.btlapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapi.R;
import com.example.btlapi.model.Rate;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHoler>{
    private List<Rate> listComment;

    public CommentAdapter(List<Rate> listComment) {
        this.listComment = listComment;
    }

    @NonNull
    @Override
    public CommentViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new CommentViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHoler holder, int position) {
        Rate rate = listComment.get(position);
        if (rate == null) {
            return;
        }
        String times = rate.getCreateAt().getDate() + "-" + (rate.getCreateAt().getMonth()+ 1) + "-" + (rate.getCreateAt().getYear()+1900);
        holder.tvDateComment.setText(times);
        holder.tvComment.setText(rate.getComment());
        holder.ratingFood.setRating(rate.getValueRate());
    }

    @Override
    public int getItemCount() {
        if (listComment == null){
            return 0;
        }
        else {
            return listComment.size();
        }
    }



    public class CommentViewHoler extends RecyclerView.ViewHolder {
        private TextView tvDateComment, tvComment;
        private RatingBar ratingFood;


        public CommentViewHoler(@NonNull View itemView) {
            super(itemView);
            tvDateComment = itemView.findViewById(R.id.tv_date_comment);
            tvComment = itemView.findViewById(R.id.tv_comment);
            ratingFood = itemView.findViewById(R.id.rate_detail);
        }
    }


}
