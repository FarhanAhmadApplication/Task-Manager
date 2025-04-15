package com.fayez.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class showtasksdata extends RecyclerView.Adapter<showtasksdata.ViewHolder> {
    Context context;
    ArrayList<String > task_name1,task_details1,starting_date1,ending_date1,task_status1,id1;

    public showtasksdata(Context context, ArrayList<String> task_name1, ArrayList<String> task_details1, ArrayList<String> starting_date1, ArrayList<String> ending_date1, ArrayList<String> task_status1, ArrayList<String> id1) {
        this.context = context;
        this.task_name1 = task_name1;
        this.task_details1 = task_details1;
        this.starting_date1 = starting_date1;
        this.ending_date1 = ending_date1;
        this.task_status1 = task_status1;
        this.id1 = id1;
    }

    @NonNull
    @Override
    public showtasksdata.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.showtasksdata,parent,false);
        ViewHolder v1=new ViewHolder(v);
        return v1;
    }

    @Override
    public void onBindViewHolder(@NonNull showtasksdata.ViewHolder holder, int position) {
        holder.task_name.setText(task_name1.get(position));
        holder.start_date.setText("Starting Date"+starting_date1.get(position));
        holder.end_date.setText("Ending Date:"+ending_date1.get(position));
        holder.task_status.setText(task_status1.get(position));
        if(task_status1.get(position).equals("Task Not Complete")){
            holder.task_status.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.task_status.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }
        else if(task_status1.get(position).equals("Task Complete")){
            holder.task_status.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.task_status.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }
        holder.mainLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,Update_And_Delete_Activity.class);
                i.putExtra("Task_Name",task_name1.get(position));
                i.putExtra("Task_Details",task_details1.get(position));
                i.putExtra("Ending_date",ending_date1.get(position));
                i.putExtra("Id",id1.get(position));
                i.putExtra("Task_Status",task_status1.get(position));
                i.putExtra("Starting_Date",starting_date1.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView task_name,start_date,end_date,task_status;
        LinearLayout mainLayout1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_name=itemView.findViewById(R.id.task_name);
            start_date=itemView.findViewById(R.id.start_date);
            end_date=itemView.findViewById(R.id.end_date);
            task_status=itemView.findViewById(R.id.task_status);
            mainLayout1=itemView.findViewById(R.id.mainLayout1);
        }
    }
}