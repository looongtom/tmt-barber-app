package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.category.Category;
import com.example.myapplication.model.service.Servicing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseCategoryRecycleViewAdapter extends RecyclerView.Adapter<ChooseCategoryRecycleViewAdapter.CategortViewHolder> {
    private List<Category>list;
    private Context context;
    private ItemListener listener;
    private Set<Integer> chosenList = new HashSet<>();

    public ChooseCategoryRecycleViewAdapter(Context context, ItemListener listener) {
        this.context= context;
        this.listener = listener;
        list = new ArrayList<>();
    }

    public Set<Integer> getChosenList() {
        return chosenList;
    }

    public void setChosenList(Set<Integer> chosenList) {
        this.chosenList = chosenList;
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
    public CategortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategortViewHolder holder, int position) {
        Category category=list.get(position);
        holder.tvName.setText(category.getName());

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseServiceRecycleViewAdapterV2 serviceAdapter =new ChooseServiceRecycleViewAdapterV2(category.getListServicing(), new ChooseServiceRecycleViewAdapterV2.ItemListener() {
            @Override
            public void onItemClick(int id) {
                listener.onItemClick(id);
            }
        });
        serviceAdapter.setChosenList(getChosenList());
        holder.recyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CategortViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RecyclerView recyclerView;
        public CategortViewHolder(@NonNull View view) {
            super(view);
            tvName=view.findViewById(R.id.tvNameCategory);
            recyclerView=view.findViewById(R.id.recyleService);

        }


    }
    public interface ItemListener {
        void onItemClick(int id);
    }
}
