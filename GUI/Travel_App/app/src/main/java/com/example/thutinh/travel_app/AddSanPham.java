package com.example.thutinh.travel_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.DTO.ThongTinMonAn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddSanPham extends AppCompatActivity {

    private ImageView img;
    private EditText txtGia, txtTenSanPham;
    private ThongTinMonAn item;
    private TextView lbSoSanPhamDaThem;
    private Button btnChoose, btnAddSanPhamThem;
    private  int request_codeFile=1;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    Bundle bReicve;
    Bundle bSend;
    private List<ThongTinMonAn> listThongTinMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_san_pham);
        item = new ThongTinMonAn();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bReicve = getIntent().getExtras();
        bSend = new Bundle();
        bSend = bReicve;
        listThongTinMonAn = new ArrayList<>();
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request_codeFile);

            }
        });
        btnAddSanPhamThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gia = txtGia.getText().toString().trim();
                String tenSanPham = txtTenSanPham.getText().toString().trim();
                if(gia.length()==0 || tenSanPham.length()==0)
                {
                    Toast.makeText(AddSanPham.this, "Nội dung cũng cấp chưa đầy đủ.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    item.setGia(gia);
                    item.setTenMonAn(tenSanPham);
                    listThongTinMonAn.add(item);
                    txtGia.setText("");
                    txtTenSanPham.setText("");
                    img.setImageResource(R.drawable.noimg);
                    lbSoSanPhamDaThem.setText("Số sản phầm đã thêm: "+ listThongTinMonAn.size());

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
                img.setImageBitmap(bt);
                LoadImg(bt);
//                Uri uri = data.getData();
//                img1.setImageURI(uri);

            } catch (IOException e) {
                Log.d("166", e.toString());
            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    public  void AnhXa()
    {
        img = (ImageView)findViewById(R.id.imgAddSanPham);
        txtGia = (EditText)findViewById(R.id.txtAddSanPhamGia);
        txtTenSanPham = (EditText)findViewById(R.id.txtAddsanPhamTen);
        btnChoose = (Button)findViewById(R.id.btnAddSanPhamChoose);
        btnAddSanPhamThem = (Button)findViewById(R.id.btnAddSanPhamThem);
        lbSoSanPhamDaThem = (TextView)findViewById(R.id.lbSoSanPhamDaThem);

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
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void Luu() {
        try {
            for (int i =0;i<listThongTinMonAn.size(); i++)
            {
                bSend.putSerializable("ItemSanPham"+i, listThongTinMonAn.get(i));
            }
            bSend.putInt("SoLuong",listThongTinMonAn.size() );
            bSend.putString("Check","1");
            Intent it = new Intent(AddSanPham.this, AddAnUong.class);
            it.putExtras(bSend);
            startActivity(it);

        }
        catch (Exception b)
        {
            Toast.makeText(AddSanPham.this, "Lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
        }

    }


    private void LoadImg(Bitmap bm) {
        String tenDiaDanh = txtTenSanPham.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bReicve.getString("TenTinh") + "_" + tenDiaDanh + System.currentTimeMillis() + ".png");
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
                Toast.makeText(AddSanPham.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                item.setHinhMonAn(downloadUrl.toString());

            }
        });
    }
    }
