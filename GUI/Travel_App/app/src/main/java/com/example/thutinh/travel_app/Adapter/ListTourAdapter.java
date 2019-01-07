package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thutinh.travel_app.DTO.TourDuLich;
import com.example.thutinh.travel_app.R;
import com.example.thutinh.travel_app.Activity.Tour;

import java.util.ArrayList;
import java.util.List;

public class ListTourAdapter extends RecyclerView.Adapter<ListTourAdapter.ViewHolder> {
   private List<TourDuLich> listTour = new ArrayList<>();

    public ListTourAdapter() {
    }

    public ListTourAdapter(List<TourDuLich> listTour, Context mContext, String tenMien, String tenTinh, String loaiDichVu) {
        this.listTour = listTour;
        this.mContext = mContext;
        this.tenMien = tenMien;
        this.tenTinh = tenTinh;
        this.loaiDichVu = loaiDichVu;
    }

    private Context mContext;
    public String tenMien, tenTinh, loaiDichVu;

    @NonNull
    @Override
    public ListTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.item_tour,viewGroup, false);
        ListTourAdapter.ViewHolder viewHolder = new ListTourAdapter.ViewHolder(listAnhView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListTourAdapter.ViewHolder viewHolder, final int i) {
        TourDuLich item = listTour.get(i);
        TextView lbNoiDung = viewHolder.lbNoiDung;
       lbNoiDung.setText("Thời gian: "+item.getthoiGian());
        TextView lbKhoiHanh= viewHolder.lbDiaDiemKhoiHanh;
        lbKhoiHanh.setText("Khởi hành tại: "+item.getDiaDiemKhoiHanh());
        TextView lbDanhSach = viewHolder.lbListDanhSach;
       lbDanhSach.setText(" Danh sách địa điểm: "+ item.getDsDiaDiem());
        ImageView img = viewHolder.img;
        if(item.arrHinh.size()==0)
            img.setImageResource(R.drawable.noimg);
        else
            Glide.with(mContext).load(item.arrHinh.get(0)).into(img);

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TourDuLich item = listTour.get(i);
                // Toast.makeText(listthongtin.this, item.getTenDiaDanh()+item.getMoTa()+item.arrHinh.size(), Toast.LENGTH_LONG).show();blSend.putSerializable("Item", item);
                Intent it = new Intent(mContext, Tour.class);
                Bundle blSend = new Bundle();
                blSend.putSerializable("Item", item);
                blSend.putString("TenMien", tenMien);
                blSend.putString("TenTinh", tenTinh);
                blSend.putString("LoaiDichVu", loaiDichVu);
                it.putExtras(blSend);
                mContext.startActivity(it);
            }


        });
    }


    @Override
    public int getItemCount() {
        return listTour.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView img;
        TextView lbNoiDung, lbListDanhSach, lbDiaDiemKhoiHanh;
        CardView card ;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgTourAnh);
            lbNoiDung =(TextView)itemView.findViewById(R.id.lbListTourNoiDung);
            lbListDanhSach = (TextView)itemView.findViewById(R.id.lbLisstTourdsDiaDiem);
            lbDiaDiemKhoiHanh = (TextView)itemView.findViewById(R.id.lbListTourKhoiHanh);
            card = (CardView)itemView.findViewById(R.id.cardListTour);
        }
    }
}
