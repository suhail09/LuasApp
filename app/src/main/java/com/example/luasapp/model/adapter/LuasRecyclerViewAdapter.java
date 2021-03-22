package com.example.luasapp.model.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.luasapp.R;
import com.example.luasapp.model.pojo.Tram;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LuasRecyclerViewAdapter extends
        RecyclerView.Adapter<LuasRecyclerViewAdapter.ViewHolder> {

    private List<Tram> luasData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public LuasRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        luasData = new ArrayList<>();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.luas_row_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tram trams = luasData.get(position);

        if (position == 0) {
            holder.time.setTextColor(Color.RED);
        } else {
            holder.time.setTextColor(Color.DKGRAY);
        }
        holder.time.setText(trams.getDueMins());
        holder.destination_name.setText(trams.getDestination());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return luasData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dueOn, time, direction, destination, destination_name;
        private ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            dueOn = itemView.findViewById(R.id.due_on);
            time = itemView.findViewById(R.id.time);
            direction = itemView.findViewById(R.id.direction);
            destination = itemView.findViewById(R.id.destination);
            destination_name = itemView.findViewById(R.id.destination_name);
            imageView = itemView.findViewById(R.id.imageView);

            dueOn.setText("Due In (min) :");
            destination.setText("Destination :");
            time.setTextSize(50);
            destination_name.setTextSize(25);
            destination_name.setTextColor(Color.DKGRAY);

            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.AM_PM) == Calendar.AM) {
                direction.setText("Outbound");
                direction.setTypeface(null, Typeface.BOLD);
                imageView.setImageResource(R.drawable.ic_inbound_arrow);
                imageView.setRotation(90);
            } else {
                direction.setText("Inbound");
                direction.setTypeface(null, Typeface.BOLD);
                imageView.setImageResource(R.drawable.ic_inbound_arrow);
                imageView.setRotation(270);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void addItems(Tram items) {

        luasData.add(items);
        notifyDataSetChanged();

    }

    public void reset() {
        luasData.clear();
        notifyDataSetChanged();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}