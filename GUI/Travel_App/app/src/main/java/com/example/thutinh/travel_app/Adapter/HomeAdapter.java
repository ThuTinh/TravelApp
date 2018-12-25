package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thutinh.travel_app.ChiTietHome;
import com.example.thutinh.travel_app.DTO.Home;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.DTO.TenTinh_class;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends  RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Home> listHome;
    private  String loaiDichVu;

    public HomeAdapter(List<Home> listHome,  Context mContext, String tenTinh, String tenMien, String loaiDichVu) {
        this.listHome = listHome;
        arr = new ArrayList<>();
        this.arr.addAll(listHome);
        this.mContext = mContext;
        this.tenTinh = tenTinh;
        this.tenMien = tenMien;
        this.loaiDichVu = loaiDichVu;
    }

    public HomeAdapter() {

    }


    public ArrayList<Home> arr;
    private Context mContext;
    private  String tenTinh, tenMien;
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.item_home,viewGroup, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(listAnhView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder viewHolder, int i) {
        final Home item = listHome.get(i);
        ImageView imgHome = viewHolder.imgHome;
        if(item.arrHinh.size()==0)
            imgHome.setImageResource(R.drawable.noimg);
        else
        {
            Glide.with(mContext).load(item.arrHinh.get(0)).into(imgHome);

        }
        TextView lbNoiO = viewHolder.lbnoiO;
        lbNoiO.setText(item.getNameHome());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bSend = new Bundle();
                bSend.putSerializable("Item",item);
                bSend.putString("TenTinh",tenTinh);
                bSend.putString("TenMien", tenMien);
                bSend.putString("LoaiDichVu", loaiDichVu);
                Intent it = new Intent(mContext, ChiTietHome.class);
                it.putExtras(bSend);
                mContext.startActivity(it);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listHome.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHome;
        TextView lbnoiO;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imgHome = itemView.findViewById(R.id.imgHome);
            lbnoiO = itemView.findViewById(R.id.lbNoio);
            cardView = itemView.findViewById(R.id.card);

        }
    }

    public  void filter (String text)
    {

        try
        {
            text = text.toLowerCase(Locale.getDefault());
            listHome.clear();
            if(text.length()==0)
                listHome.addAll(arr);
            else
            {
                for(Home home : arr)
                {
                    if(home.getNameHome().toLowerCase(Locale.getDefault()).contains(text))
                    {
                        listHome.add(home);
                    }
                }

            }
            this.notifyDataSetChanged();
        }catch (Exception e)
        {
            Log.d("hix",e.toString());
        }

    }
}
