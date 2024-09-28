package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.service.Servicing;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChooseServiceRecycleViewAdapterV2 extends RecyclerView.Adapter<ChooseServiceRecycleViewAdapterV2.ServiceViewHolder> {
    private List<Servicing>list;
    private ItemListener itemListener;

    public ChooseServiceRecycleViewAdapterV2(List<Servicing> listService) {
        list = listService;
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
        private Button btnChoose;
        public ServiceViewHolder(@NonNull View view) {
            super(view);
            img=view.findViewById(R.id.imgService);
            tvName=view.findViewById(R.id.tvName);
            tvPrice=view.findViewById(R.id.tvPrice);
            tvDescription=view.findViewById(R.id.tvDescription);
            btnChoose=view.findViewById(R.id.cbChoose);
            btnChoose.setOnClickListener(this);
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
