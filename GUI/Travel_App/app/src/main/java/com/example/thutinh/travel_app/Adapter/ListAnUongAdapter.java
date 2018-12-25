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
import com.example.thutinh.travel_app.ChiTietMonAn;
import com.example.thutinh.travel_app.DTO.DanhSachAnUong;
import com.example.thutinh.travel_app.DTO.ThongTinMonAn;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAnUongAdapter extends RecyclerView.Adapter<ListAnUongAdapter.ViewHolder>{

   private Context mContext;
   private String tenTinh;
   private  String tenMien;
   private  String loaiDichVu;
   private List<DanhSachAnUong> listAnUong;
   public List<DanhSachAnUong> arr;

    public ListAnUongAdapter(Context mContext, List<DanhSachAnUong> listAnUong,String tenTinh, String tenMien, String loaiDichVu ) {
        this.mContext = mContext;
        this.tenTinh = tenTinh;
        this.tenMien = tenMien;
        this.loaiDichVu = loaiDichVu;
        this.listAnUong = listAnUong;
        this.arr = new ArrayList<>();
        arr.addAll(listAnUong);
        this.loaiDichVu = loaiDichVu;
    }

    public ListAnUongAdapter() {
    }

    @NonNull
    @Override
    public ListAnUongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.item_list_an_uong,viewGroup, false);
        ListAnUongAdapter.ViewHolder viewHolder = new ListAnUongAdapter.ViewHolder(listAnhView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAnUongAdapter.ViewHolder viewHolder, int i) {
        final DanhSachAnUong item = listAnUong.get(i);
        ImageView imgHome = viewHolder.imgListAnUong;
        if(item.getHinh().length()==0)
            imgHome.setImageResource(R.drawable.noimg);
        else
        {
            Glide.with(mContext).load(item.getHinh()).into(imgHome);

        }
        TextView  lbName = viewHolder.lbNameListAnUOng;
        lbName.setText(item.getTennhaCungCap());
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bSend = new Bundle();
                bSend.putSerializable("Item",item);
                bSend.putString("TenTinh",tenTinh);
                bSend.putString("TenMien", tenMien);
                bSend.putString("LoaiDichVu", loaiDichVu);
                Intent it = new Intent(mContext, ChiTietMonAn.class);
                it.putExtras(bSend);
                mContext.startActivity(it);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAnUong.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        ImageView imgListAnUong;
        TextView lbNameListAnUOng;
        CardView card ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgListAnUong = itemView.findViewById(R.id.imgListAnUong);
            lbNameListAnUOng = itemView.findViewById(R.id.lbNameListAnUong);
            card = itemView.findViewById(R.id.cardListAnUOng);

        }
    }

    public  void filter (String text)
    {

        try
        {
            text = text.toLowerCase(Locale.getDefault());
            listAnUong.clear();
            if(text.length()==0)
                listAnUong.addAll(arr);
            else
            {
                for(DanhSachAnUong ds : arr)
                {
                    if(ds.getTennhaCungCap().toLowerCase(Locale.getDefault()).contains(text))
                    {
                        listAnUong.add(ds);
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
