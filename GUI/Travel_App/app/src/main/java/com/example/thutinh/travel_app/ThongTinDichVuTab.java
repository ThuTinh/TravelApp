package com.example.thutinh.travel_app;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ThongTinDichVuTab extends AppCompatActivity {
    private Bundle bl;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TextView lbTenDiaDanh ;
    private  TextView lbContent;
    private ViewFlipper viewFlipper;
    private ImageView tabImg1, tabImg2, tabImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_thong_tin_dich_vu_tab);
        AnhXa();
        LoadTabs();
        bl = getIntent().getExtras();
        getData();
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);



    }

    //cấu hình tab
   private void LoadTabs(){
        final TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        //gọi lệnh setup
        tabHost.setup();
        TabHost.TabSpec spec;
        //tạo tab Thông tin
       spec = tabHost.newTabSpec("Thông tin");
       spec.setContent(R.id.tabThongTin);
       spec.setIndicator("Thông tin");
       tabHost.addTab(spec);
       //rạo tab Dịch vụ
       spec = tabHost.newTabSpec("Dịch vụ");
       spec.setContent(R.id.tabDichVu);
       spec.setIndicator("Dịch vụ");
       tabHost.addTab(spec);
       tabHost.setCurrentTab(0);

    }
    void getData()
    {
        try {
            MoTaChiTiet_class item = (MoTaChiTiet_class) bl.getSerializable("Item");
            if (item.arrHinh.size() == 0) {
                tabImg1.setImageResource(R.drawable.noimg);
                tabImg2.setImageResource(R.drawable.noimg);
                tabImg2.setImageResource(R.drawable.noimg);

                Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
            } else {
                if (item.arrHinh.size() == 1) {
                    Picasso.get().load(item.arrHinh.get(0)).into(tabImg1);
                    Picasso.get().load(item.arrHinh.get(0)).into(tabImg2);
                    Picasso.get().load(item.arrHinh.get(0)).into(tabImg3);
                    //Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
                } else {
                    if (item.arrHinh.size() == 2) {
                        Picasso.get().load(item.arrHinh.get(0)).into(tabImg1);
                        Picasso.get().load(item.arrHinh.get(1)).into(tabImg2);
                        Picasso.get().load(item.arrHinh.get(0)).into(tabImg3);
                       // Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
                    } else {
                        Picasso.get().load(item.arrHinh.get(0)).into(tabImg1);
                        Picasso.get().load(item.arrHinh.get(1)).into(tabImg2);
                        Picasso.get().load(item.arrHinh.get(2)).into(tabImg3);
//                        tabImg2.setImageResource(R.drawable.img_mienbac1);
//                        tabImg1.setImageResource(R.drawable.img_mienbac2);
//                        tabImg3.setImageResource(R.drawable.img_miengtrung1);
                        //Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
                    }
                }
            }
            lbTenDiaDanh.setText(item.getTenDiaDanh());
            lbContent.setText(item.getMoTa());

        }catch (Exception e)
        {
            lbTenDiaDanh.setText("NOTHING TO SHOW");
            lbContent.setText("");
        }

    }

   private void AnhXa()
    {
        lbTenDiaDanh = (TextView)findViewById(R.id.lbTenDiaDanh);
        lbContent = (TextView)findViewById(R.id.lbContent);
        tabImg1 = (ImageView)findViewById(R.id.tabImg1);
        tabImg2 = (ImageView)findViewById(R.id.tabImg2);
        tabImg3 = (ImageView)findViewById(R.id.tabImg3);

    }

}


