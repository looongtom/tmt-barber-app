package com.example.myapplication.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.category.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.ServiceViewHolder> {
    private List<Category>list;
    private Context context;

    public CategoryRecycleViewAdapter(Context context) {
        this.context= context;
        list = new ArrayList<>();
    }


    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Category getItem(int pos) {
        return list.get(pos);
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Category category=list.get(position);
        holder.tvName.setText(category.getName());

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new ServiceRecycleViewAdapter(category.getListServicing(),context));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private RecyclerView recyclerView;
        public ServiceViewHolder(@NonNull View view) {
            super(view);
            tvName=view.findViewById(R.id.tvNameCategory);
            recyclerView=view.findViewById(R.id.recyleService);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface ItemListener{
    }
}
