package com.example.cardiio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class MyAdapter extends FirebaseRecyclerAdapter<Users,MyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Users model) {
        holder.userName.setText(model.getUserName());
        holder.userEmail.setText(model.getUserEmail());
        holder.userAge.setText(model.getUserAge());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView userAge,userEmail,userName;

        @SuppressLint("CutPasteId")
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            userAge = (TextView)itemView.findViewById(R.id.tvAge);
            userEmail = (TextView)itemView.findViewById(R.id.tvEmail);
            userName = (TextView)itemView.findViewById(R.id.tvName);


        }
    }
}