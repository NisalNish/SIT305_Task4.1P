package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.UpdateTask;
import com.example.myapplication.db.DBHelper;
import com.example.myapplication.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.taskVH>{

    ArrayList<Task> tasks;
    Context context;

    public TaskAdapter(ArrayList<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public taskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_task, parent, false);
        taskVH svh = new taskVH(view);

        return svh;
    }


    @Override
    public void onBindViewHolder(@NonNull taskVH holder, int position) {

        Task t = tasks.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvDescription.setText(t.getDescription());
        holder.tvDate.setText(t.getDate());

        holder.cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, t.getTitle() + "Will be updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, UpdateTask.class);
                intent.putExtra("TASK", t);
                context.startActivity(intent);
            }


        });

        holder.cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, t.getTitle() + "Will be delete", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Conformation");
                builder.setMessage("Are you sure to delete ?");
                builder.setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(context);

                        int result = dbHelper.deleteTask(t.getId());

                        if(result > 0)
                        {
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            tasks.remove(t);
                            notifyDataSetChanged();
                        }else
                        {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                        }



                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();



            }
        });

    }

    @Override
    public int getItemCount()
    {
        return tasks.size();
    }


    class taskVH extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvDate;
        CardView cardUpdate, cardDelete;


        @SuppressLint("WrongViewCast")
        public taskVH(@NonNull View v) {
            super(v);

            tvTitle = v.findViewById(R.id.tvTitle);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvDate = v.findViewById(R.id.tvDate);

            cardDelete = v.findViewById(R.id.cardDelete);
            cardUpdate = v.findViewById(R.id.cardUpdate);

        }
    }


}
