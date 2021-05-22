package com.example.begequiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begequiz.databinding.RowLeaderboardsBinding;
import com.google.protobuf.StringValue;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewHolder> {

    Context context;
    ArrayList<User>users;
    User user;

    public LeaderboardsAdapter(Context context, ArrayList<User>users){
        this.context=context;
        this.users=users;
    }


    @NonNull
    @Override
        public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboards,parent,false);

        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        user = users.get(position);

        holder.binding.name.setText(user.getName());
        holder.binding.coins.setText(String.valueOf(user.getCoins()));
        holder.binding.index.setText(String.format("%d",position+1));
    }

    @Override
    public int getItemCount() {

        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        RowLeaderboardsBinding binding;


        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowLeaderboardsBinding.bind(itemView);
        }
    }
}
