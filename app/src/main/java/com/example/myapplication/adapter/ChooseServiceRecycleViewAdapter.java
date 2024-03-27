package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChooseServiceRecycleViewAdapter extends RecyclerView.Adapter<ChooseServiceRecycleViewAdapter.ServiceViewHolder> {
    private List<Service>list;
    private ItemListener itemListener;

    public ChooseServiceRecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Service> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Service getItem(int pos) {
        return list.get(pos);
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service=list.get(position);
        holder.tvName.setText(service.getName());
        holder.tvPrice.setText(service.getPrice()+"");
        holder.tvDescription.setText(service.getDescription());
//
        String fileImage=service.getFilePath();
        if(fileImage!=null)Picasso.get().load(fileImage).resize(300,300).into(holder.img);
        else holder.img.setImageResource(R.drawable.barber_man);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView tvName,tvPrice,tvDescription;
        private CheckBox checkBox;

        public ServiceViewHolder(@NonNull View view) {
            super(view);
            img=view.findViewById(R.id.imgService);
            tvName=view.findViewById(R.id.tvName);
            tvPrice=view.findViewById(R.id.tvPrice);
            tvDescription=view.findViewById(R.id.tvDescription);
            checkBox=view.findViewById(R.id.cbChoose);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view,int pos);
    }
}
