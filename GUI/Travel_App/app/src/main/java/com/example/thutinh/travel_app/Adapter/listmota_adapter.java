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
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.R;
import com.example.thutinh.travel_app.ThongTinDiaDiem;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class listmota_adapter extends RecyclerView.Adapter<listmota_adapter.ViewHolder> {

//    private Context context;

    public listmota_adapter(List<MoTaChiTiet_class> listThongtin,Context mContext, String tenMien, String tenTinh) {
        this.listThongtin = listThongtin;
        this.arr  = new ArrayList<>();
        arr.addAll(listThongtin);
        this.mContext = mContext;
        this.tenMien = tenMien;
        this.tenTinh = tenTinh;
    }

    //    private int layout;
    private List<MoTaChiTiet_class> listThongtin;
    public   ArrayList<MoTaChiTiet_class> arr;
    public Context mContext;
    public String tenMien, tenTinh;



    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtDiaDanh, txtThongTin, txtSoLuotThich;
        ImageView img, ImgYeuThich;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txtDiaDanh = (TextView) itemView.findViewById(R.id.txttieude);
             txtThongTin = (TextView) itemView.findViewById(R.id.txtthongTin);
             txtSoLuotThich=(TextView)itemView.findViewById(R.id.lbSoLuotThich);
             img = (ImageView) itemView.findViewById(R.id.img);
           ImgYeuThich=(ImageView)itemView.findViewById(R.id.imgYeuTich);
           parent = (CardView) itemView.findViewById(R.id.card);
//           itemView.setOnClickListener(this);
//           itemView.setOnLongClickListener(this);



        }
//        public  void setItemClickListener(ItemClickListener itemClickListener)
//        {
//         this.itemClickListener = itemClickListener;
//        }
//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),false);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), true);
//
//            return true;
//        }
    }
    public  void filter (String text)
    {
        Log.d("222", arr.size()+"");
        try
        {
            text = text.toLowerCase(Locale.getDefault());
           listThongtin.clear();
            if(text.length()==0)
               listThongtin.addAll(arr);
            else
            {
                for(MoTaChiTiet_class mota: arr)
                {
                    if(mota.getTenDiaDanh().toLowerCase(Locale.getDefault()).contains(text))
                    {
                       listThongtin.add(mota);
                    }
                }
            }
            this.notifyDataSetChanged();
        }catch (Exception e)
        {
            Log.d("hix",e.toString());
        }
    }

    @NonNull
    @Override
    public listmota_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.motachitiet,viewGroup, false);
        listmota_adapter.ViewHolder viewHolder = new listmota_adapter.ViewHolder(listAnhView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull listmota_adapter.ViewHolder viewHolder, final int i) {

        MoTaChiTiet_class item = listThongtin.get(i);
        TextView txtDiaDanh = viewHolder.txtDiaDanh;
        txtDiaDanh.setText(item.getTenDiaDanh());
        TextView txtThongTin = viewHolder.txtThongTin;
        txtThongTin.setText(item.getMoTa());
        TextView txtSoLuotThich = viewHolder.txtSoLuotThich;
        txtSoLuotThich.setText( item.getSoLuotThich()+"");
        ImageView img = viewHolder.img;
        if(item.arrHinh.size()==0)
            img.setImageResource(R.drawable.noimg);
        else
            Glide.with(mContext).load(item.arrHinh.get(0)).into(img);

       viewHolder.parent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MoTaChiTiet_class item = listThongtin.get(i);
                // Toast.makeText(listthongtin.this, item.getTenDiaDanh()+item.getMoTa()+item.arrHinh.size(), Toast.LENGTH_LONG).show();blSend.putSerializable("Item", item);
                Intent it = new Intent(mContext, ThongTinDiaDiem.class);
               Bundle blSend = new Bundle();
                blSend.putSerializable("Item", item);
                blSend.putString("TenMien", tenMien);
                blSend.putString("TenTinh", tenTinh);
                it.putExtras(blSend);
                mContext.startActivity(it);
                }


        });
           }


    @Override
    public int getItemCount() {
        return listThongtin.size();
    }
}
