package com.example.thutinh.travel_app;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
