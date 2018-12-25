package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thutinh.travel_app.DTO.DanhSachAnUong;
import com.example.thutinh.travel_app.DTO.TenTinh_class;
import com.example.thutinh.travel_app.DTO.ThongTinMonAn;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThongTinMonAnAdapter extends RecyclerView.Adapter<ThongTinMonAnAdapter.ViewHolder> {


    private List<ThongTinMonAn> listThongTinMonAn;
    public List<ThongTinMonAn> arr;
    private  Context mContext;

    public ThongTinMonAnAdapter(List<ThongTinMonAn> listThongTinMonAn, Context context) {
        this.listThongTinMonAn = listThongTinMonAn;
        this.arr = new ArrayList<>();
        arr.addAll(listThongTinMonAn);
        mContext = context;


    }


    @NonNull
    @Override
    public ThongTinMonAnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.item_chi_tiet_mon_an,viewGroup, false);
        ThongTinMonAnAdapter.ViewHolder viewHolder = new ThongTinMonAnAdapter.ViewHolder(listAnhView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThongTinMonAnAdapter.ViewHolder viewHolder, final int i) {

        ThongTinMonAn item = listThongTinMonAn.get(i);
        ImageView imgThongTinMonAn = viewHolder.img;
       if(item.getHinhMonAn().length()==0)
       {
           imgThongTinMonAn.setImageResource(R.drawable.noimg);
       }
       else
       {
           Glide.with(mContext).load(item.getHinhMonAn()).into(imgThongTinMonAn);
       }
       TextView lbGia = viewHolder.lbGia;
       lbGia.setText(item.getGia());
       TextView lbTenMonAn = viewHolder.lbTenMonAn;
       lbTenMonAn.setText(item.getTenMonAn());
       final CardView cardView = viewHolder.cardChiTietMonAn;
      cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final PopupMenu popupMenu = new PopupMenu(mContext, cardView);
               popupMenu.getMenuInflater().inflate(R.menu.popup_menu_xoa_anh, popupMenu.getMenu());
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       if(item.getItemId()==R.id.btnXoaAnh);
                       {
                          listThongTinMonAn.remove(i);
                          ThongTinMonAnAdapter.this.notifyDataSetChanged();
                       }
                       return false;
                   }
               });
               popupMenu.show();
           }
       });
    }

    @Override
    public int getItemCount() {
        return listThongTinMonAn.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView lbTenMonAn, lbGia;
        CardView cardChiTietMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgChiTietMonAn);
            lbGia = itemView.findViewById(R.id.lbGia);
            lbTenMonAn = itemView.findViewById(R.id.lbTenMonAn);
            cardChiTietMonAn = itemView.findViewById(R.id.cardChiTietMonAn);

        }
    }

}
