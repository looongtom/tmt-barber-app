package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceRecycleViewAdapter extends RecyclerView.Adapter<ServiceRecycleViewAdapter.ServiceViewHolder> {
    private List<Service>list;
    private ItemListener itemListener;

    public ServiceRecycleViewAdapter() {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service=list.get(position);
        holder.tvName.setText(service.getName());
        holder.tvPrice.setText(service.getPrice()+"");
        holder.tvDescription.setText(service.getDescription());
        holder.img.setImageResource(R.drawable.barber_man);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView tvName,tvPrice,tvDescription;
        public ServiceViewHolder(@NonNull View view) {
            super(view);
            img=view.findViewById(R.id.imgService);
            tvName=view.findViewById(R.id.tvNameService);
            tvPrice=view.findViewById(R.id.tvPriceService);
            tvDescription=view.findViewById(R.id.tvDescriptionService);
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
