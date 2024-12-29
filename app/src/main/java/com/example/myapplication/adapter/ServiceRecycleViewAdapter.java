package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.service.Servicing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiceRecycleViewAdapter extends RecyclerView.Adapter<ServiceRecycleViewAdapter.ServiceViewHolder> {
    private List<Servicing>list;
    private ItemListener itemListener;
    private Context context;

    public ServiceRecycleViewAdapter(List<Servicing> listService,Context context) {
        list = listService;
        this.context = context;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Servicing service=list.get(position);
        holder.tvName.setText(service.getName());
        holder.tvPrice.setText(service.getPrice()+"");
        Glide.with(context)
                .load(service.getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.barber_man) // Placeholder image
                        .error(R.drawable.error_loading) // Error image in case of loading failure
                )
                .into(holder.img);
        String des=service.getDescription();
        if(des.length()>11)des=des.substring(0,11)+"...";
        holder.tvDescription.setText(des);
//
        String fileImage=service.getUrl();
        if(fileImage!=null)Picasso.get().load(fileImage).resize(300,300).into(holder.img);
        else holder.img.setImageResource(R.drawable.barber_man);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), service.toString(), Toast.LENGTH_SHORT).show();
                //add logic to display service details
            }
        });
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
                Toast.makeText(view.getContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view,int pos);
    }
}
