package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;

public class ListEditor extends AppCompatActivity {

    ListView lvListEditor;

    Bundle bRevice;
    List<String> listEdit = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvListEditor = (ListView)findViewById(R.id.lvListEditor);
        bRevice = getIntent().getExtras();
        listEdit = bRevice.getStringArrayList("ListEditor");
        getSupportActionBar().setTitle("List editor");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listEdit);
        lvListEditor.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        if(item.getItemId()==R.id.MenuHome)
        {
            Intent it = new Intent(ListEditor.this, MainActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
}
