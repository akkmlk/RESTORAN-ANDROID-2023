package com.latihan.tokokelontong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.HolderProfile> {

    Context context;
    List<ProfileModel> profile;

    public AdapterProfile(Context context, List<ProfileModel> profile) {
        this.context = context;
        this.profile = profile;
    }

    @NonNull
    @Override
    public AdapterProfile.HolderProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_profile, parent,false);
        return new HolderProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProfile.HolderProfile holder, int position) {
        holder.tvName.setText(profile.get(position).getName());
        holder.tvUsername.setText(profile.get(position).getUsername());
        holder.tvAdderess.setText(profile.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return profile.size();
    }

    public class HolderProfile extends RecyclerView.ViewHolder {

        TextView tvName, tvUsername, tvAdderess;

        public HolderProfile(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAdderess = itemView.findViewById(R.id.tvAlamat);

        }
    }
}
