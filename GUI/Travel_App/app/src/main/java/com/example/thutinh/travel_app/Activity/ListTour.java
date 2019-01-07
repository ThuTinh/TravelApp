package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.ListTourAdapter;
import com.example.thutinh.travel_app.DTO.TourDuLich;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListTour extends AppCompatActivity {

    private Bundle bReceive;
    private RecyclerView rvListTour;
    String tenMien;
    String tenTinh;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Bundle bSend;
    String LoaiDichVu;
    private List<TourDuLich>listTour;
    ListTourAdapter listTourAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tour);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        bReceive = getIntent().getExtras();
        LoaiDichVu = bReceive.getString("LoaiDichVu");
        tenMien = bReceive.getString("TenMien");
        tenTinh = bReceive.getString("TenTinh");
        bSend = new Bundle();
        bSend = bReceive;
        getSupportActionBar().setTitle(tenTinh+">List tour");
        listTour = new ArrayList<>();
        AnhXa();
        getData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listTourAdapter = new ListTourAdapter(listTour, this, tenMien, tenTinh,LoaiDichVu);
        rvListTour.setAdapter(listTourAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListTour.setLayoutManager(layoutManager);
        listTourAdapter.notifyDataSetChanged();

    }
    void AnhXa()
    {
        rvListTour = (RecyclerView)findViewById(R.id.rvListTour);
    }

    void getData()
    {
        try {

            myRef.child("DichVu").child(LoaiDichVu).child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    TourDuLich test = dataSnapshot.getValue(TourDuLich.class);
                    test.setKey(dataSnapshot.getKey());
                    // Toast.makeText(listthongtin.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
                    // Toast.makeText(listthongtin.this, test.listCmt.get(0).getTimeCmt(), Toast.LENGTH_SHORT).show();
                   listTour.add(test);
                  //  homeAdapter.arr.add(test);
                    listTourAdapter.notifyDataSetChanged();
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
            Toast.makeText(ListTour.this, "Lỗi, Thử lại sau ", Toast.LENGTH_SHORT).show();
            Log.d("Hix", ex.toString() + LoaiDichVu);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_back,menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
                Intent it = new Intent(getApplicationContext(),AddTour.class);
                it.putExtras(bSend);
                startActivity(it);
                return  true;
            case  R.id.MenuHome:
                Intent it1 = new Intent(ListTour.this, MainActivity.class);
                startActivity(it1);
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
