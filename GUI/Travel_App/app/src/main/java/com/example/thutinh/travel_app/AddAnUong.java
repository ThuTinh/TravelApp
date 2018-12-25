package com.example.thutinh.travel_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thutinh.travel_app.Adapter.ThongTinMonAnAdapter;
import com.example.thutinh.travel_app.DTO.DanhSachAnUong;
import com.example.thutinh.travel_app.DTO.Home;
import com.example.thutinh.travel_app.DTO.ThongTinMonAn;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAnUong extends AppCompatActivity {

    private EditText txtAnUongTenNhaCungCap,txtAnUongPhone,txtAnUongEmail,txtAnUongFacebook,txtAnUongViTri, txtAddAnUongNoiDung;
    private ImageView imgAnUong,imgAddSanPham;
    private Button btnAddAnUongChoose;
    private RecyclerView rvDanhMucCacSanPham;
    Bundle bRceive, bSend;
    int request_codeFile = 1, request_them=2;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DanhSachAnUong item;
    private List<ThongTinMonAn> listMonAn;
    private ThongTinMonAnAdapter thongTinMonAnAdapter ;
    PlaceAutocompleteFragment placeAutocomplete;
    private EditText txtAddsanPhamTen,txtAddSanPhamGia;
    private  Button btnAddSanPhamChoose,btnAddSanPhamThem;
    ImageView imgSanPham ;
    private  ThongTinMonAn thongTinMonAn;
    String Edit = "0";
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myRef = database.getReference();
        setContentView(R.layout.activity_add_an_uong);
        bRceive = getIntent().getExtras();
        Edit = bRceive.getString("Edit","0");
        AnhXa();
        listMonAn = new ArrayList<>();
        thongTinMonAn = new ThongTinMonAn();
        bSend = new Bundle();
        bSend = bRceive;
        item = new DanhSachAnUong();
        getSupportActionBar().setTitle("Thêm ẩm thực");
        if(Edit.equals("1"))
        {
            item = (DanhSachAnUong) bRceive.getSerializable("Item");
            txtAnUongTenNhaCungCap.setText(item.getTennhaCungCap());
            txtAnUongPhone.setText(item.getPhone());
            txtAnUongEmail.setText(item.getEmail());
            txtAnUongFacebook.setText(item.getFace());
            txtAnUongViTri.setText(item.getDiaDiem());
            txtAddAnUongNoiDung.setText(item.getNoiDung());
            Glide.with(AddAnUong.this).load(item.getHinh()).into(imgAnUong);
            listMonAn = item.getListMonAn();

        }
       // thongTinMonAn = (ThongTinMonAn) bRceive.getSerializable("ItemSanPham");

           thongTinMonAnAdapter = new ThongTinMonAnAdapter(listMonAn, AddAnUong.this);
           rvDanhMucCacSanPham.setLayoutManager(new GridLayoutManager(this, 2));
           rvDanhMucCacSanPham.setAdapter(thongTinMonAnAdapter);
           thongTinMonAnAdapter.notifyDataSetChanged();

         btnAddAnUongChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, request_codeFile);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), request_codeFile);
            }
        });

       btnAddSanPhamChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, request_them);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), request_them);

            }
        });
        btnAddSanPhamThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtAddsanPhamTen.getText().toString().trim().length()==0||txtAddSanPhamGia.getText().toString().trim().length()==0)
                {
                    Toast.makeText(AddAnUong.this, "Thông tin chưa đầy đủ", Toast.LENGTH_SHORT).toString();
                }
                else
                {
                    thongTinMonAn.setTenMonAn(txtAddsanPhamTen.getText().toString());
                    thongTinMonAn.setGia(txtAddSanPhamGia.getText().toString());
                    listMonAn.add(thongTinMonAn);
                    thongTinMonAnAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_codeFile && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                Bitmap bt = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgAnUong.setImageBitmap(bt);
                LoadImg(bt);
            } catch (IOException e) {
                Log.d("166", e.toString());
            }
            super.onActivityResult(requestCode, resultCode, data);

        }else
        {
            if(requestCode==request_them && resultCode==RESULT_OK )
            {
                Uri uri = data.getData();
                try {
                    Bitmap bt = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgAddSanPham.setImageBitmap(bt);
                    LoadImgSanPham(bt);
//                Uri uri = data.getData();
//                img1.setImageURI(uri);

                } catch (IOException e) {
                    Log.d("166", e.toString());
                }

            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_thong_tin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==R.id.save)
        {
            Luu();
        }
        if(item.getItemId()== android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    void  AnhXa()
    {
        txtAnUongEmail = (EditText)findViewById(R.id.txtAnUongEmail);
        txtAnUongFacebook = (EditText)findViewById(R.id.txtAnUongFacebook);
        txtAnUongPhone = (EditText)findViewById(R.id.txtAnUongPhone);
        txtAnUongTenNhaCungCap = (EditText)findViewById(R.id.txtAnUongTenNhaCungCap);
        txtAnUongViTri = (EditText)findViewById(R.id.txtAnUongViTri);
        imgAnUong = (ImageView)findViewById(R.id.imgAnUong);
        btnAddAnUongChoose = (Button)findViewById(R.id.btnAddAnUongChoose);
        //btnAddAnUongThem = (Button)findViewById(R.id.btnAddAnUongThem);
        rvDanhMucCacSanPham = (RecyclerView)findViewById(R.id.rvDanhMucCacSanPham);
        txtAddAnUongNoiDung = (EditText)findViewById(R.id.txtAddAnUongNoiDung);
        linearLayout = (LinearLayout)findViewById(R.id.linerlayout);
        placeAutocomplete = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.txtAutoComplacete);
        txtAddsanPhamTen = (EditText)findViewById(R.id.txtAddsanPhamTen);
        txtAddSanPhamGia = (EditText)findViewById(R.id.txtAddSanPhamGia);
         btnAddSanPhamChoose = (Button)findViewById(R.id.btnAddSanPhamChoose);
         btnAddSanPhamThem = (Button)findViewById(R.id.btnAddSanPhamThem);
        imgAddSanPham = (ImageView)findViewById(R.id.imgAddSanPham);


    }

    private void Luu() {
        String tenNhaCungCap, face, email, phone, viTri, noiDung;
        tenNhaCungCap = txtAnUongTenNhaCungCap.getText().toString().trim();
        face = txtAnUongFacebook.getText().toString().trim();
        email = txtAnUongEmail.getText().toString().trim();
        viTri = txtAnUongViTri.getText().toString().trim();
        noiDung = txtAddAnUongNoiDung.getText().toString().trim();
        phone = txtAnUongPhone.getText().toString().trim();

       if(tenNhaCungCap.length()==0||face.length()==0||email.length()==0||viTri.length()==0 ||phone.length()==0)

            Toast.makeText(AddAnUong.this, "Nội dung cũng cấp chưa đầy đủ.", Toast.LENGTH_SHORT).show();
        else
        {
            item.setDiaDiem(viTri);
            item.setEmail(email);
            item.setFace(face);
            item.setTennhaCungCap(tenNhaCungCap);
            item.setNguoiTao(mAuth.getCurrentUser().getEmail());
            item.setPhone(phone);
            item.setListMonAn(listMonAn);
            item.setNoiDung(noiDung);
            Intent it = new Intent(AddAnUong.this, ListAnUong.class);
            it.putExtras(bSend);
            startActivity(it);
            if (Edit.equals("1")) {
                myRef.child("DichVu").child(bRceive.getString("LoaiDichVu")).child(bRceive.getString("TenMien")).child(bRceive.getString("TenTinh")).child(item.getKey()).setValue(item);
                Toast.makeText(AddAnUong.this, "cập nhập thành công", Toast.LENGTH_SHORT).show();
                it.putExtras(bSend);
                startActivity(it);

            } else {

                myRef.child("DichVu").child(bRceive.getString("LoaiDichVu")).child(bRceive.getString("TenMien")).child(bRceive.getString("TenTinh")).push().setValue(item);
                Toast.makeText(AddAnUong.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                it.putExtras(bSend);
                // finish();
                startActivity(it);
            }
        }

    }

    private void LoadImg(Bitmap bm) {
        String tenDiaDanh = txtAnUongTenNhaCungCap.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bRceive.getString("TenTinh") + "_" + tenDiaDanh + System.currentTimeMillis() + ".png");
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
                Toast.makeText(AddAnUong.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                item.setHinh(downloadUrl.toString());
                Log.d("Hinh", item.getHinh());

            }
        });
    }

    private void LoadImgSanPham(Bitmap bm) {
        String tenDiaDanh = txtAnUongTenNhaCungCap.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bRceive.getString("TenTinh") + "_" + tenDiaDanh + System.currentTimeMillis() + ".png");
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
                Toast.makeText(AddAnUong.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
               thongTinMonAn.setHinhMonAn(downloadUrl.toString());

            }
        });
    }
}
