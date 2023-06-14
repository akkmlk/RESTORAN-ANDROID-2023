package com.latihan.tokokelontong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.HolderMenu> {

    Context context;
    List<MenuModel> listMenu;

    public AdapterMenu(Context context, List<MenuModel> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
    }

    public void setFilteredList (List<MenuModel> filteredList ) {
        this.listMenu = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMenu.HolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new HolderMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenu.HolderMenu holder, int position) {
        holder.tvName.setText(listMenu.get(position).getName());
        holder.tvPrice.setText(String.valueOf(listMenu.get(position).getPrice()));
//        holder.tvQty.setText(String.valueOf(listMenu.get(position).getQty()));
        holder.tvRate.setText(String.valueOf(listMenu.get(position).getRate()));

//        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int qty = listMenu.get(position).getQty();
//                qty--;
//                listMenu.get(position).setQty(qty);
//                notifyDataSetChanged();
//            }
//        });

//        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int qty = listMenu.get(position).getQty();
//                qty++;
//                listMenu.get(position).setQty(qty);
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class HolderMenu extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvRate;
        ImageView btnRemove, btnAdd;

        public HolderMenu(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
//            tvQty = itemView.findViewById(R.id.tvQty);
            tvRate = itemView.findViewById(R.id.tvRate);

            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
