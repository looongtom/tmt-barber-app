package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.result.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResultRecycleViewAdapter extends RecyclerView.Adapter<ResultRecycleViewAdapter.ResultViewHolder>{
    private List<Result> list;
    private Context context;

    public ResultRecycleViewAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    public void setList(List<Result> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_result, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Result result = list.get(position);
        if(result == null) return;
        String fileImage = result.getUrl();
        if(fileImage!=null) Picasso.get().load(fileImage).into(holder.img);
        else holder.img.setImageResource(R.drawable.barber_man);

    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.click_image);
        }
    }
}
