package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UpdateDeleteBarberActivity;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.model.Account;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChooseBarberRecycleViewAdapter extends RecyclerView.Adapter<ChooseBarberRecycleViewAdapter.BarberViewHolder> {
    private List<Account> list;
    private Context context;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ChooseBarberRecycleViewAdapter(Context context) {
        list=new ArrayList<>();
        this.context = context;
    }

    public void setList(List<Account> list) {
        this.list = list;
        for(Account account: list){
            System.out.println(account.getAccount());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_barber, parent, false);
        return new BarberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BarberViewHolder holder, int position) {
        Account barber = list.get(position);
        if(barber==null)return;
        AccountDataSource accountDataSource = new AccountDataSource(context);
        String fileImage=accountDataSource.getFilePictureForCategory(barber.getId());
        holder.barberName.setText(barber.getName());
        if(fileImage!=null)Picasso.get().load(fileImage).resize(300,300).into(holder.imgBarber);
        else holder.imgBarber.setImageResource(R.drawable.barber_man);

        holder.imgBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDeleteBarberActivity.class);
                // Pass the Account to the next activity
                intent.putExtra("account", barber);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class BarberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgBarber;
        private TextView barberName;

        public BarberViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarber = itemView.findViewById(R.id.imgBarber);
            barberName = itemView.findViewById(R.id.tvBarberName);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemCLick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemCLick(View view, int pos);
    }
}
