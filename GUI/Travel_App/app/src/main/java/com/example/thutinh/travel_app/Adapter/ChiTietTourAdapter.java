package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thutinh.travel_app.DTO.ChiTietTour;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietTourAdapter extends  RecyclerView.Adapter<ChiTietTourAdapter.ViewHolder>  {
   List<ChiTietTour> listChiTietTour = new ArrayList<>();

    public ChiTietTourAdapter(List<ChiTietTour> listChiTietTour) {
        this.listChiTietTour = listChiTietTour;
    }

    @NonNull
    @Override
    public ChiTietTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.item_chitietlichtrinh,viewGroup, false);
        ChiTietTourAdapter.ViewHolder viewHolder = new ChiTietTourAdapter.ViewHolder(listAnhView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietTourAdapter.ViewHolder viewHolder, int i) {
    TextView lbNgay = viewHolder.lbNgay;
    lbNgay.setText(listChiTietTour.get(i).getNgay());
    TextView lbNoiDung = viewHolder.lbNoiDung;
    lbNoiDung.setText(listChiTietTour.get(i).getNoiDung());
    }

    @Override
    public int getItemCount() {
        return listChiTietTour.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lbNoiDung, lbNgay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbNgay =(TextView) itemView.findViewById(R.id.lbNgay);
            lbNoiDung = (TextView)itemView.findViewById(R.id.lbNoiDungChiTiet);
        }
    }
}
