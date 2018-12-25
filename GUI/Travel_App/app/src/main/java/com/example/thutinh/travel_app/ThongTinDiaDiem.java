package com.example.thutinh.travel_app;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.CommentAdapter;
import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class ThongTinDiaDiem extends AppCompatActivity {

    private Bundle bl;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TextView lbTenDiaDanh ;
    private  TextView lbContent;
    private ImageView imgThaTim;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String tenMien = "";
    private String tenTinh = "";
    static Boolean isLike = false;
    private String email;
    private MoTaChiTiet_class item;
    private RecyclerView rvComment;
    private List<Comment_class> listCmt;
    private CommentAdapter adapter;
    private Button btnComment;
    private EditText txtComment;
    private  String name;
    private  Comment_class cmt;
    private  TextView lbCmt, lbThongTinDiaDanhViTri, lbNameEdit;
  //  private  ImageView imgEditMoTaChiTiet;
    private FlipperLayout flipAnhThongTin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        email = user.getEmail();
        // name = mAuth.getCurrentUser().getDisplayName();
        name = user.getDisplayName();

        for (UserInfo userInfo : user.getProviderData()) {
            if (name == null && userInfo.getDisplayName() != null) {
                name = userInfo.getDisplayName();
            }
        }
        // name = user.getDisplayName();
        cmt = new Comment_class();
        myRef = database.getReference();
        setContentView(R.layout.activity_thong_tin_dia_diem);
        AnhXa();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bl = getIntent().getExtras();
        tenTinh = bl.getString("TenTinh");
        tenMien = bl.getString("TenMien");
        getSupportActionBar().setTitle(tenTinh);

        getData();
        //  myRef.child(tenMien).child(tenTinh).child(item.getKey()).child("listCmt").push().setValue(new Comment_class("df","dfg","rt"));
        XetYeuThich();



        imgThaTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (isLike) {
                        isLike = false;
                        imgThaTim.setImageResource(R.drawable.timtrang);
                        item.dsYeuThich.remove(email);
                        if(item.dsYeuThich.size()<=0)
                        {
                            item.setSoLuotThich(0);
                        }
                        else
                        {
                            item.setSoLuotThich(item.dsYeuThich.size());

                        }

                    } else {

                        item.dsYeuThich.add(email);
                        item.setSoLuotThich(item.dsYeuThich.size());
                        isLike = true;
                        imgThaTim.setImageResource(R.drawable.tymdo);
                    }

                    try {
                        // Toast.makeText(ThongTinDichVuTab.this, tenMien, Toast.LENGTH_SHORT).show();
                        //myRef.child(tenMien).child(tenTinh).child(item.getKey()).child("soLuotThich").setValue(item.soLuotThich);
                        myRef.child("ThongTin").child(tenMien).child(tenTinh).child(item.getKey()).setValue(item);
                    }
                    catch (Exception r)
                    {
                        Log.d("metquadi", r.toString());
                    }

                }
                catch (Exception ex)
                {

                }
            }
        });

        lbThongTinDiaDanhViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vitri = lbThongTinDiaDanhViTri.getText().toString();
                List<Address> listAddress = null;
                Geocoder geocoder = new Geocoder(ThongTinDiaDiem.this);
                try {
                    listAddress =  geocoder.getFromLocationName(vitri, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = listAddress.get(0);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">?q=<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">(" + lbTenDiaDanh.getText().toString() + ")"));
                startActivity(intent);
            }
        });


        if(item.getListCmt()!=null)
        {
            adapter = new CommentAdapter(item.getListCmt());
            rvComment.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvComment.setLayoutManager(layoutManager);
            lbCmt.setText("Bình Luận ("+item.listCmt.size()+")");
        }

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    CMT();

                }catch (Exception x)
                {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_list_edit, menu);
            getMenuInflater().inflate(R.menu.menu_update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void CMT() {

        if (txtComment.getText().toString().trim().length() == 0)
            Toast.makeText(ThongTinDiaDiem.this, "Nội dung rỗng", Toast.LENGTH_SHORT).show();
        else {

            cmt.setName(name);

            cmt.setCmt(txtComment.getText().toString());
            //Lay gio he thong
            Date thoiGian = new Date();

            //Khai bao dinh dang ngay thang
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");

            //parse ngay thang sang dinh dang va chuyen thanh string.
            String showTime = dinhDangThoiGian.format(thoiGian.getTime());
            cmt.setTimeCmt(showTime);
            item.listCmt.add(cmt);
            myRef.child("ThongTin").child(tenMien).child(tenTinh).child(item.getKey()).setValue(item);

            adapter.notifyDataSetChanged();
            txtComment.setText("");
            lbCmt.setText("Bình luận"+listCmt.size());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem t) {
        Intent it ;
        switch (t.getItemId())
        {

            case android.R.id.home:
                 it = new Intent(ThongTinDiaDiem.this, listthongtin.class);
                Bundle b = new Bundle();
                b.putString("TenMien", tenMien);
                b.putString("TenTinh", tenTinh);
                it.putExtras(b);
                startActivity(it);
                return true;
            case R.id.update:
                 it = new Intent(ThongTinDiaDiem.this, AddThongTinDiaDanh.class);
                Bundle bun = new Bundle(bl);
                bun.putString("Edit","1");
                it.putExtras(bun);
                startActivity(it);
                return  true;
            case R.id.ShowListEditor:
                it = new Intent(this, ListEditor.class);
                Bundle bl = new Bundle();
                bl.putStringArrayList("ListEditor",  item.getListEdit());
                it.putExtras(bl);
                startActivity(it);

            default:break;
        }
        return super.onOptionsItemSelected(t);
    }

    private void XetYeuThich()
    {
        if(item.dsYeuThich.contains(email))
            imgThaTim.setImageResource(R.drawable.tymdo);

    }



    void getData() {
        item = (MoTaChiTiet_class) bl.getSerializable("Item");
        try {


            //flipDichVu.setVisibility(View.VISIBLE);
            if (item.arrHinh.size() == 0) {
                FlipperView view = new FlipperView(getBaseContext());
                view.setImageDrawable(R.drawable.noimg).setImageScaleType(ImageView.ScaleType.CENTER);
                flipAnhThongTin.addFlipperView(view);
                // Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < item.arrHinh.size(); i++) {
                    FlipperView view = new FlipperView(getBaseContext());
                    view.setImageUrl(item.arrHinh.get(i)).setDescription("Du lịch việt nam").setImageScaleType(ImageView.ScaleType.FIT_XY);
                    flipAnhThongTin.addFlipperView(view);
                }
            }
            lbTenDiaDanh.setText(item.getTenDiaDanh());
            lbNameEdit.setText(item.getListEdit().get(item.getListEdit().size()-1));
            lbThongTinDiaDanhViTri.setText(item.getViTri());
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
        imgThaTim = (ImageView)findViewById(R.id.imgThaTim);
        btnComment = (Button)findViewById(R.id.btnComment);
        txtComment = (EditText)findViewById(R.id.txtComment);
        rvComment = (RecyclerView)findViewById(R.id.rvCmt);
        lbCmt = (TextView)findViewById(R.id.lbCmt);
        lbThongTinDiaDanhViTri = (TextView)findViewById(R.id.lbThongTinDiaDanhViTri);
        lbNameEdit =(TextView)findViewById(R.id.lbNameEdit);
       // imgEditMoTaChiTiet = (ImageView)findViewById(R.id.imgEditMoTaChiTiet);

        flipAnhThongTin = (FlipperLayout)findViewById(R.id.flipAnhThongTin);
        // flipDichVu = (FlipperLayout)findViewById(R.id.flipDichVu);

    }
}
