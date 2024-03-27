package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.TimeSlot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseTimeSlotRecycleViewAdapter extends RecyclerView.Adapter<ChooseTimeSlotRecycleViewAdapter.TimeSlotViewHolder> {

    private List<TimeSlot> list;
    private Context context;
    private ItemListener itemListener;
    private Account account=null;
    private Set<Integer>listIdService=new HashSet<>();
    public TimeSlot getItem(int pos) {
        return list.get(pos);
    }
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public ChooseTimeSlotRecycleViewAdapter(Context context) {
        list=new ArrayList<>();
        this.context = context;
    }
    public void setList(List<TimeSlot> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_choose_timeslot, null);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        TimeSlot timeSlot = list.get(position);
        if(timeSlot==null)return;
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(context);
        holder.txtTimeSlot.setText(timeSlot.getTimeStart());
        holder.txtStatus.setText(timeSlot.getStatus());

    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTimeSlot;
        private TextView txtStatus;
        private CardView cardView;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
            txtStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemCLick(v,getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemCLick(View view, int pos);
    }

}
