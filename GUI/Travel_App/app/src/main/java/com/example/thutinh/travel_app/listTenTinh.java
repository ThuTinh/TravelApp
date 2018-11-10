package com.example.thutinh.travel_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.listTenTinhAdapter;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.DTO.TenTinh_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class listTenTinh extends AppCompatActivity {
   listTenTinhAdapter adapter;
   ListView listViewTenTinh;
   String tenMien="";
    private FirebaseAuth mAuth;
   List<TenTinh_class> dsTenTinh;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ten_tinh);
        mAuth = FirebaseAuth.getInstance();
        listViewTenTinh = (ListView)findViewById(R.id.listTenTinh);
        //Nhận dữ liệu từ màn hình chính
       Render();

       listViewTenTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if(mAuth.getCurrentUser()==null)
               {
                   AlertDialog.Builder dialog = new AlertDialog.Builder(listTenTinh.this);
                   dialog.setTitle("Thông báo");
                   dialog.setMessage("Hãy đăng nhập để thực hiện tác vụ");
                   dialog.setCancelable(false);
                   dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   });

                   AlertDialog alertDialog = dialog.create();
                   alertDialog.show();

               }
               else {
                   String tenTinh;
                   tenTinh = dsTenTinh.get(position).getTenTinh().toString();
                   Intent it = new Intent(listTenTinh.this, listthongtin.class);
                   Bundle bl = new Bundle();
                   bl.putString("TenMien", tenMien);
                   bl.putString("TenTinh", tenTinh);
                   it.putExtras(bl);
                   startActivity(it);
               }


//               database = FirebaseDatabase.getInstance();
//               myRef = database.getReference();
//
//               myRef.child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
//                   @Override
//                   public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                       MoTaChiTiet_class test = dataSnapshot.getValue(MoTaChiTiet_class.class);
//                       Toast.makeText(listTenTinh.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
//
//                   }
//
//                   @Override
//                   public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                   }
//
//                   @Override
//                   public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                   }
//
//                   @Override
//                   public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                   }
//               });
//

           }
       });

    }

    void Render()
    {
        Intent it = getIntent();
        Bundle bundle = it.getBundleExtra("DuLieu");
        // tenMien = getIntent().getExtras().getString("TenMien");
        // tenMien = it.getStringExtra("TenMien");
        tenMien = bundle.getString("TenMien");

        // Log.d("")

        dsTenTinh = new ArrayList<TenTinh_class>();
        if(tenMien.equals("MienBac"))
        {
            dsTenTinh.add(new TenTinh_class("Lào Cai", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class("Yên Bái", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class("Điện Biên", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Hoà Bình", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Lai Châu", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Sơn La", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Hà Giang", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Cao Bằng", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Bắc Kạn", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Lạng Sơn", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class("Tuyên Quang", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Thái Nguyên", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Phú Thọ", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Bắc Giang", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Quảng Ninh", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Bắc Ninh", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Hà Nam", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Hà Nội", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Hải Dương", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Hải Phòng", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Yên Bái", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Nam Định", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Ninh Bình", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Thái Bình", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class("Vĩnh Phúc", R.drawable.chuyen_trang));

        }
        if(tenMien.equals("MienTrung"))
        {
            //  dsTenTinh = new ArrayList<TenTinh_class>();
            dsTenTinh.add(new TenTinh_class("Thanh Hoá", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Nghệ An", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class("Hà Tĩnh", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class(  "Quảng Bình", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Quảng Trị", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class( "Thừa Thiên-Huế", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Đà Nẵng", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class( "Quảng Nam", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Quảng Ngãi", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Bình Định", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Phú Yên", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Khánh Hoà", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class("Ninh Thuận", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class( "Bình Thuận", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Kon Tum", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class( "Gia Lai", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class("Đắc Lắc", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class("Vĩnh Phúc", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class("Vĩnh Phúc", R.drawable.chuyen_trang));



        }
        if(tenMien.equals("MienNam")) {
            // dsTenTinh = new ArrayList<TenTinh_class>();
            dsTenTinh.add(new TenTinh_class( "Bình Phước", R.drawable.chuyen_trang));
            dsTenTinh.add(new TenTinh_class("Tây Ninh", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class( "Đồng Nai", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Bình Dương", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Thành phố Hồ Chí Minh", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class(  "Bà Rịa - Vũng Tàu", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class("Long An", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class("Đồng Tháp", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class( "An Giang", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class( "Tiền Giang", R.drawable.chuyen_trang));
            dsTenTinh.add( new TenTinh_class( "Kiên Giang", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class( "Bến Tre", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class(   "Vĩnh Long", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class( "Thành phố Cần Thơ", R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class(  "Hậu Giang", R.drawable.chuyen_trang));
            dsTenTinh.add(  new TenTinh_class( "Sóc Trăng",  R.drawable.chuyen_trang));
            dsTenTinh.add(    new TenTinh_class( "Bạc Liêu", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class( "Cà Mau", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class(  "Ninh Thuận", R.drawable.chuyen_trang));
            dsTenTinh.add(   new TenTinh_class(  "Bình Thuận" ,R.drawable.chuyen_trang));

        }
        adapter = new listTenTinhAdapter(listTenTinh.this,R.layout.itemtentinh, dsTenTinh);
        listViewTenTinh.setAdapter(adapter);


    }






}
