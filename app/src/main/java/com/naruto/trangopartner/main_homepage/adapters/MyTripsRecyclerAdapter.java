package com.naruto.trangopartner.main_homepage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trangopartner.R;
import com.naruto.trangopartner.main_homepage.data.MyTripsData;

import java.util.List;

public class MyTripsRecyclerAdapter extends RecyclerView.Adapter<MyTripsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<MyTripsData> myTripsDataList;

    public MyTripsRecyclerAdapter(Context context, List<MyTripsData> myTripsDataList) {
        this.context = context;
        this.myTripsDataList = myTripsDataList;
    }

    @NonNull
    @Override
    public MyTripsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_trips_recycler_content, null);
        return new MyTripsRecyclerAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvTravelTime, tvDownload, tvDistance, tvRefNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_trips_date);
            tvTravelTime = itemView.findViewById(R.id.tv_trips_travel_time);
            tvDownload = itemView.findViewById(R.id.tv_trips_download);
            tvDistance = itemView.findViewById(R.id.tv_trips_distance);
            tvRefNum = itemView.findViewById(R.id.tv_trips_ref_num);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyTripsRecyclerAdapter.ViewHolder holder, int position) {
        final MyTripsData data = myTripsDataList.get(position);

        if (data != null) {
            if (data.isDownload()) {
                holder.tvDate.setText(data.getDate());
                holder.tvDistance.setText(data.getDistance());
            } else {
                holder.tvDownload.setVisibility(View.GONE);
                holder.tvDate.setText(data.getDate());
                holder.tvDistance.setText(data.getDistance());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (myTripsDataList != null) {
            return myTripsDataList.size();
        } else {
            return 0;
        }
    }

}
