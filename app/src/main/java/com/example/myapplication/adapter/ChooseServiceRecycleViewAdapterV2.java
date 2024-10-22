package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.service.Servicing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseServiceRecycleViewAdapterV2 extends RecyclerView.Adapter<ChooseServiceRecycleViewAdapterV2.ServiceViewHolder> {
    private List<Servicing>list;
    private ItemListener listener;
    private Boolean isChoose=true;
    private Set<Integer>chosenList=new HashSet<>();

    public ChooseServiceRecycleViewAdapterV2(List<Servicing> listService, ItemListener listener) {
        list = listService;
        this.listener = listener;
    }

    public void setChosenList(Set<Integer> chosenList) {
        this.chosenList = chosenList;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }

    public void setList(List<Servicing> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Servicing getItem(int pos) {
        return list.get(pos);
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_service_v2, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Servicing service=list.get(position);
        holder.tvName.setText(service.getName());
        holder.tvPrice.setText(service.getPrice()+"");
        String des=service.getDescription();
        if(des.length()>11)des=des.substring(0,11)+"...";
        holder.tvDescription.setText(des);
//
        String fileImage=service.getUrl();
        if(fileImage!=null)Picasso.get().load(fileImage).resize(300,300).into(holder.img);
        else holder.img.setImageResource(R.drawable.barber_man);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "Long click "+service.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        holder.btnChoose.setOnCheckedChangeListener(null);
        holder.btnChoose.setOnCheckedChangeListener((compoundButton, b) -> {
            listener.onItemClick(service.getId());
        });

        if (chosenList.contains(service.getId())) {
            holder.btnChoose.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView tvName,tvPrice,tvDescription;
        private CheckBox btnChoose;
        public ServiceViewHolder(@NonNull View view) {
            super(view);
            img=view.findViewById(R.id.imgService);
            tvName=view.findViewById(R.id.tvName);
            tvPrice=view.findViewById(R.id.tvPrice);
            tvDescription=view.findViewById(R.id.tvDescription);
            btnChoose=view.findViewById(R.id.cbChoose);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface ItemListener{
        void onItemClick(int id);
    }
}
