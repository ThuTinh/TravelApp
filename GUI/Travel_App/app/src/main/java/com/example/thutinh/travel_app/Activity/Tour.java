package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.CommentAdapter;
import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.DTO.TourDuLich;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class Tour extends AppCompatActivity {

    private TextView lbTourThoiGian,lbTourLink,lbTourPhone,lbTourFace,lbTourCmt,lbTourEmail,lbTourTGTu,lbTourTGDen,lbTourGia,lbTourDiaDiemDen,lbTourMaTour,lbTourSoLuongConLai,lbKhoiHanhTai;
    private Button btnTourSave;
    private EditText txtTourCmt;
    private FlipperLayout flipTour;
    private  Bundle bRecive;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private  String name;
    private  String tenMien;
    private String tenTinh;
    private  String loaiDichVu;
    private TourDuLich tour;
    private RecyclerView rvTourCmt;
    private CommentAdapter commentAdapter;
    private  Comment_class cmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = user.getDisplayName();
        setContentView(R.layout.activity_tour);
        bRecive = getIntent().getExtras();
        tour = new TourDuLich();
        tenMien = bRecive.getString("TenMien");
        tenTinh = bRecive.getString("TenTinh");
        loaiDichVu = bRecive.getString("LoaiDichVu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AnhXa();
        GetData();
        lbTourPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = lbTourPhone.getText().toString();
                if(!TextUtils.isEmpty(phone))
                {
                    String dial = "tel:" + phone;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }
                else
                {
                    Toast.makeText(Tour.this, "Số điện thoại không phù hợp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lbTourLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(lbTourLink.getText().toString()));
                startActivity(it);
            }
        });


            commentAdapter = new CommentAdapter(tour.getListFeedBack());
            rvTourCmt.setAdapter(commentAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvTourCmt.setLayoutManager(layoutManager1);
            lbTourCmt.setText("Bình Luận ("+tour.getListFeedBack().size()+")");
            btnTourSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMT();
            }
        });
    }

    private void CMT() {

        cmt = new Comment_class();

        if(txtTourCmt.getText().toString().trim().length()==0)
        {
            Toast.makeText(Tour.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cmt.setName(name);
            cmt.setCmt(txtTourCmt.getText().toString());
            //Lay gio he thong
            Date thoiGian = new Date();
            //Khai bao dinh dang ngay thang
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
            //parse ngay thang sang dinh dang va chuyen thanh string.
            String showTime = dinhDangThoiGian.format(thoiGian.getTime());
            cmt.setTimeCmt(showTime);
            tour.listFeedBack.add(cmt);
            myRef.child("DichVu").child(loaiDichVu).child(tenMien).child(tenTinh).child(tour.getKey()).setValue(tour);
            commentAdapter.notifyDataSetChanged();
            txtTourCmt.setText("");
        }
    }

    void AnhXa()
    {

        lbKhoiHanhTai = (TextView)findViewById(R.id.lbKhoiHanhTai);
        lbTourDiaDiemDen = (TextView)findViewById(R.id.lbTourDiaDiemDen);
        lbTourEmail = (TextView)findViewById(R.id.lbTourEmail);
        lbTourFace = (TextView)findViewById(R.id.lbTourFace);
        lbTourPhone = (TextView)findViewById(R.id.lbTourPhone);
        lbTourMaTour = (TextView)findViewById(R.id.lbTourMaTour);
        lbTourGia = (TextView)findViewById(R.id.lbTourGia);
        lbTourTGDen = (TextView)findViewById(R.id.lbTourTGDen);
        lbTourTGTu = (TextView)findViewById(R.id.lbTourTGTu);
        flipTour = (FlipperLayout)findViewById(R.id.flipTour);
        lbTourSoLuongConLai = (TextView)findViewById(R.id.lbTourSoLuongConLai);
        rvTourCmt = (RecyclerView)findViewById(R.id.rvTourCmt);
        txtTourCmt = (EditText)findViewById(R.id.txtTourCmt);
        btnTourSave = (Button)findViewById(R.id.btnTourSave);
        lbTourCmt = (TextView)findViewById(R.id.lbTourCmt);
        lbTourLink = (TextView)findViewById(R.id.lbTourLink);
        lbTourThoiGian = (TextView)findViewById(R.id.lbTourThoiGian);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tour.getNguoiTao().equals(user.getEmail())) {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.update)
        {
            Intent it = new Intent(Tour.this, AddTour.class);
            Bundle bSend = new Bundle(bRecive);
            bSend.putString("Edit","1");
            it.putExtras(bSend);
            startActivity(it);
        }

        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        if(item.getItemId()==R.id.MenuHome)
        {
            Intent it = new Intent(Tour.this, MainActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    public void  GetData()
    {
       tour = (TourDuLich) bRecive.getSerializable("Item");
        if (tour.arrHinh.size() == 0) {
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageDrawable(R.drawable.noimg).setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            flipTour.addFlipperView(view);
            // Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < tour.arrHinh.size(); i++) {
                FlipperView view = new FlipperView(getBaseContext());
                view.setImageUrl(tour.arrHinh.get(i)).setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                flipTour.addFlipperView(view);
            }
        }
        lbTourTGTu.setText(tour.getThoiGianTu());
        lbTourTGDen.setText(tour.getThoiGianDen());
        lbTourGia.setText(tour.getGia());
        lbTourMaTour.setText(tour.getMaTour());
        lbTourFace.setText(tour.getFaceBook());
        lbTourEmail.setText(tour.getEmail());
        lbTourPhone.setText(tour.getPhone());
        lbTourDiaDiemDen.setText(tour.getDsDiaDiem());
        lbKhoiHanhTai.setText(tour.getDiaDiemKhoiHanh());
        lbTourThoiGian.setText(tour.getthoiGian());
        lbTourSoLuongConLai.setText(tour.getSoLuongCon());
        lbTourLink.setText(tour.getLink());
    }
}
