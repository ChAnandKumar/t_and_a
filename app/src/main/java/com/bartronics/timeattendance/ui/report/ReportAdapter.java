package com.bartronics.timeattendance.ui.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.model.ReportModel;

import java.util.List;

/**
 * Created by anand.chandaliya on 17-02-2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private List<ReportModel> reportList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place,inDate, inTime, outTime;

        public MyViewHolder(View view) {
            super(view);
            place = (TextView) view.findViewById(R.id.place_txt);
            inDate = (TextView) view.findViewById(R.id.date_txt);
            inTime = (TextView) view.findViewById(R.id.in_txt);
            outTime = (TextView) view.findViewById(R.id.out_txt);
        }
    }


    public ReportAdapter(List<ReportModel> reportList) {
        this.reportList = reportList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReportModel report = reportList.get(position);
        holder.place.setText(report.getEmpLocation());
        holder.inDate.setText(report.getEmpInDate());
        holder.inTime.setText(report.getEmpInTime());
        holder.outTime.setText(report.getEmpOutTime());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}