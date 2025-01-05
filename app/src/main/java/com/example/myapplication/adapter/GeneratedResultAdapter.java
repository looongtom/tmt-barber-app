package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.hairfast.response.GeneratedResult;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneratedResultAdapter extends RecyclerView.Adapter<GeneratedResultAdapter.GeneratedResultViewHolder>{

    private List<GeneratedResult> generatedResults;
    private ItemListener itemListener;
    private Context context;
    private GeneratedResult generatedResult=null;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public GeneratedResultAdapter(List<GeneratedResult> generatedResults,Context context) {
        this.generatedResults = generatedResults;
        this.context = context;
    }

    public void setGeneratedResults(List<GeneratedResult> generatedResults) {
        this.generatedResults = generatedResults;
    }

    public GeneratedResult getItem(int position) {
        return generatedResults.get(position);
    }

    private String convertLongToTime(long time) {
        Date date = new Date(time* 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    @NonNull
    @Override
    public GeneratedResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generate, parent, false);
        return new GeneratedResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneratedResultViewHolder holder, int position) {
        GeneratedResult generatedResult = generatedResults.get(position);
        holder.tvTimestamp.setText(convertLongToTime(generatedResult.getCreatedAt()));
        Glide.with(context)
                .load(generatedResult.getGeneratedImg())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.barber_man) // Placeholder image
                        .error(R.drawable.loading) // Error image in case of loading failure
                )
                .into(holder.resultImageView);
        String fileImage=generatedResult.getGeneratedImg();
        if(fileImage!=null) Picasso.get().load(fileImage).resize(300,300).into(holder.resultImageView);
        else holder.resultImageView.setImageResource(R.drawable.barber_man);

    }

    @Override
    public int getItemCount() {
        return generatedResults.size();
    }


    public class GeneratedResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView resultImageView;
        TextView tvTimestamp;

        public GeneratedResultViewHolder(View view) {
            super(view);
            resultImageView = view.findViewById(R.id.imgResult);
            tvTimestamp = view.findViewById(R.id.tvTimestamp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onCLick(view, getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onCLick(View view, int position);
    }
}
