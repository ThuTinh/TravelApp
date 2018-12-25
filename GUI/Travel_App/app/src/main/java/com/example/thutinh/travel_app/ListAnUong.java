package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.HomeAdapter;
import com.example.thutinh.travel_app.Adapter.ListAnUongAdapter;
import com.example.thutinh.travel_app.DTO.DanhSachAnUong;
import com.example.thutinh.travel_app.DTO.Home;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListAnUong extends AppCompatActivity {

    private Bundle bReceive;
    private RecyclerView rvListAnUOng;
    String tenMien;
    String tenTinh;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Bundle bSend;
    String LoaiDichVu;
    List<DanhSachAnUong>listAnUong;
    ListAnUongAdapter listAnUongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_an_uong);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        bReceive = getIntent().getExtras();
        LoaiDichVu = bReceive.getString("LoaiDichVu");
        tenMien = bReceive.getString("TenMien");
        tenTinh = bReceive.getString("TenTinh");
        bSend = new Bundle();
        bSend = bReceive;
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tenTinh+">List ẩm thực");
        listAnUong = new ArrayList<>();
        AnhXa();

        GetDaTa();
        listAnUongAdapter = new ListAnUongAdapter(ListAnUong.this,listAnUong,tenTinh, tenMien ,LoaiDichVu);
        rvListAnUOng.setHasFixedSize(true);
            rvListAnUOng.setLayoutManager(new GridLayoutManager(this, 2));
            rvListAnUOng.setAdapter(listAnUongAdapter);
            listAnUongAdapter.notifyDataSetChanged();


    }

    private void GetDaTa() {

            try {

                myRef.child("DichVu").child(LoaiDichVu).child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        DanhSachAnUong test = dataSnapshot.getValue(DanhSachAnUong.class);
                        test.setKey(dataSnapshot.getKey());
                        // Toast.makeText(listthongtin.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
                        // Toast.makeText(listthongtin.this, test.listCmt.get(0).getTimeCmt(), Toast.LENGTH_SHORT).show();
                        listAnUong.add(test);
                        listAnUongAdapter.arr.add(test);
                       listAnUongAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }catch (Exception ex)
            {
                Toast.makeText(ListAnUong.this, "Error ", Toast.LENGTH_SHORT).show();
                Log.d("Hix", ex.toString() + LoaiDichVu);

            }

    }

    void AnhXa()
    {
        rvListAnUOng = (RecyclerView)findViewById(R.id.rvListAnUong);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.create_back,menu);
        android.support.v7.widget.SearchView searchView =(android.support.v7.widget.SearchView) menu.findItem(R.id.seachTinh).getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listAnUongAdapter.filter(s.trim());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                listAnUongAdapter.filter(s.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent t = new Intent(getApplicationContext(), ListDichVu.class);
                t.putExtras(bSend);
                startActivity(t);
                return true;
            case R.id.cb_create:
                Intent it = new Intent(getApplicationContext(),AddAnUong.class);
                it.putExtras(bSend);
                startActivity(it);
                return  true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
