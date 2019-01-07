package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.thutinh.travel_app.Adapter.ThemAnhAdapter;
import com.example.thutinh.travel_app.DTO.TourDuLich;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddTour extends AppCompatActivity {

    private EditText txtTourLink, txtTourNoiDung, txtTourdsDiaDiem, txtTourKhoiHanhTai, txtTourThoiGianTu, txtTourThoiGianDen, txtTourMaTour, txtTourSlConLai, txtTourPhone,txtTourFace,txtTourEmail,txtTourGia,txtTourChiTietLichTrinhNgay,txtTourChiTietLichTrinhNoiDung;
    private Button btnTourChoose;

    private RecyclerView rvAnh;
    private TourDuLich tour;
    private  Bundle bRecive;
    private  String Edit = "0";
    private ThemAnhAdapter themAnhAdapter;
    private List<Bitmap> listHinh;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private int request_codeFile = 2;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private  String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_add_tour);
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm tour");
        bRecive = getIntent().getExtras();
        Edit = bRecive.getString("Edit", "0");

        if (Edit.equals("1")) {
            tour = (TourDuLich) bRecive.getSerializable("Item");
            txtTourThoiGianTu.setText(tour.getThoiGianTu());
            txtTourThoiGianDen.setText(tour.getThoiGianDen());
            txtTourSlConLai.setText(tour.getSoLuongCon());
            txtTourFace.setText(tour.getFaceBook());
            txtTourPhone.setText(tour.getPhone());
            txtTourEmail.setText(tour.getEmail());
            txtTourdsDiaDiem.setText(tour.getDsDiaDiem());
            txtTourMaTour.setText(tour.getMaTour());
            txtTourKhoiHanhTai.setText(tour.getDiaDiemKhoiHanh());
            name = tour.getNguoiTao();
            txtTourGia.setText(tour.getGia());
            txtTourNoiDung.setText(tour.getthoiGian());
            txtTourLink.setText(tour.getLink());
            for (int i = 0; i < tour.getArrHinh().size(); i++) {
                try {
                    //   Bitmap bm = getBitmapFromURL(item.arrHinh.get(i));
                    Glide
                            .with(getApplicationContext())
                            .asBitmap()
                            .load(tour.arrHinh.get(i))
                            .into(new SimpleTarget<Bitmap>(100, 100) {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    listHinh.add(resource);
                                }
                            });

                } catch (Exception e) {
                    Toast.makeText(this, "Lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

        } else
            tour = new TourDuLich();

        themAnhAdapter = new ThemAnhAdapter(listHinh, tour.arrHinh);
        rvAnh.setAdapter(themAnhAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAnh.setLayoutManager(layoutManager);

        //themAnhAdapter.notifyDataSetChanged();

        btnTourChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request_codeFile);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_thong_tin, menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void AnhXa() {
        txtTourdsDiaDiem = (EditText) findViewById(R.id.txtTourdsDiaDiem);
        txtTourKhoiHanhTai = (EditText) findViewById(R.id.txtTourKhoiHanhTai);
        txtTourMaTour = (EditText) findViewById(R.id.txtTourMaTour);
        txtTourNoiDung = (EditText) findViewById(R.id.txtTourNoiDung);
        txtTourSlConLai = (EditText) findViewById(R.id.txtTourSlConLai);
        txtTourThoiGianDen = (EditText) findViewById(R.id.txtTourThoiGianDen);
        txtTourThoiGianTu = (EditText) findViewById(R.id.txtTourThoiGianTu);
        rvAnh = (RecyclerView)findViewById(R.id.listTourHinh);
        txtTourEmail = (EditText)findViewById(R.id.txtTourEmail);
        txtTourPhone = (EditText)findViewById(R.id.txtTourPhone);
        txtTourFace = (EditText)findViewById(R.id.txtTourFace);
        txtTourGia = (EditText)findViewById(R.id.txtTourGia);
        btnTourChoose = (Button)findViewById(R.id.btnTourChoose);
        listHinh = new ArrayList<>();

        txtTourLink = (EditText)findViewById(R.id.txtTourLink);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==R.id.save)
           Luu();
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }
        if(item.getItemId()==R.id.MenuHome)
        {
            Intent it = new Intent(AddTour.this, MainActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Luu() {


        if( txtTourNoiDung.toString().trim().length()==0||txtTourMaTour.toString().trim().length()==0||txtTourPhone.toString().trim().length()==0|| txtTourThoiGianTu.toString().trim().length()==0||txtTourThoiGianDen.toString().trim().length()==0||txtTourSlConLai.toString().trim().length()==0||txtTourdsDiaDiem.toString().trim().length()==0)
        {
            Toast.makeText(AddTour.this, "Nội dung chưa đầy đủ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            tour.setEmail(txtTourEmail.getText().toString());
            tour.setGia(txtTourGia.getText().toString());
            tour.setDiaDiemKhoiHanh(txtTourKhoiHanhTai.getText().toString());
            tour.setPhone(txtTourPhone.getText().toString());
            tour.setMaTour(txtTourMaTour.getText().toString());
            tour.setDsDiaDiem(txtTourdsDiaDiem.getText().toString());
            tour.setFaceBook(txtTourFace.getText().toString());
            tour.setSoLuongCon(txtTourSlConLai.getText().toString());
            tour.setThoiGianTu(txtTourThoiGianTu.getText().toString());
            tour.setThoiGianDen(txtTourThoiGianDen.getText().toString());
            tour.arrHinh =new ArrayList<>();
            tour.arrHinh.addAll(themAnhAdapter.arrHinh);
            tour.setNguoiTao(mAuth.getCurrentUser().getEmail());
            tour.setLink(txtTourLink.getText().toString());

            if(Edit.equals("1"))
            {

                //      Toast.makeText(AddTour.this,bRecive.getString("LoaiDichVu") + bRecive.getString("TenMien")+ bRecive.getString("TenTinh")+tour.getKey()+"", Toast.LENGTH_SHORT).show();
                myRef.child("DichVu").child(bRecive.getString("LoaiDichVu")).child(bRecive.getString("TenMien")).child(bRecive.getString("TenTinh")).child(tour.getKey()).setValue(tour);
                Toast.makeText(AddTour.this, "Edit Succces", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddTour.this, ListTour.class);
                it.putExtras(bRecive);
                startActivity(it);

            }
            else
            {

                myRef.child("DichVu").child(bRecive.getString("LoaiDichVu")).child(bRecive.getString("TenMien")).child(bRecive.getString("TenTinh")).push().setValue(tour);
                Toast.makeText(AddTour.this, "Add Succces", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddTour.this, ListTour.class);
                it.putExtras(bRecive);
                // finish();
                startActivity(it);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_codeFile && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                Bitmap bt = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                listHinh.add(bt);
                LoadImg(bt);


            } catch (IOException e) {
                Log.d("166", e.toString());
            }
            themAnhAdapter.notifyDataSetChanged();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void LoadImg(Bitmap bm) {

        String nameHome = txtTourMaTour.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bRecive.getString("TenTinh") + "_" + nameHome + System.currentTimeMillis() + ".png");
        //img.setDrawingCacheEnabled(true);
        // img.buildDrawingCache();
        // Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        Bitmap bitmap = bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddTour.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                themAnhAdapter.arrHinh.add(downloadUrl.toString());
                // Toast.makeText(AddTour.this, "link "+ themAnhAdapter.arrHinh.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
       