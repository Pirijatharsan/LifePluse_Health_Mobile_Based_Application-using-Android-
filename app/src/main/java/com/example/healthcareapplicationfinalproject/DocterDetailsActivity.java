package com.example.healthcareapplicationfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class DocterDetailsActivity extends AppCompatActivity {


    TextView tv;
    Button backBtn;
    String[][] docter_details = {};
    HashMap<String, String> item;

//    ArrayList list;
    SimpleAdapter sa;
    Database db;
    ArrayList<HashMap<String, String>> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_details);

        db = new Database(this);

        tv = findViewById(R.id.textViewDDTitle);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);



        backBtn = findViewById(R.id.buttonDDBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DocterDetailsActivity.this, FindDocterActivity.class));
            }
        });



        lst = new ArrayList<>();
        Cursor cursor = db.getDoctorsByCategory(title);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> item = new HashMap<>();
                item.put("line1", cursor.getString(cursor.getColumnIndex("name")));
                item.put("line2", cursor.getString(cursor.getColumnIndex("location")));
                item.put("line3", cursor.getString(cursor.getColumnIndex("experience")));
                item.put("line4", cursor.getString(cursor.getColumnIndex("phone")));
                item.put("line5", cursor.getString(cursor.getColumnIndex("fee")));
                lst.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sa = new SimpleAdapter(this, lst,
                R.layout.docterappoinment,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line11, R.id.line22, R.id.line33, R.id.line44, R.id.line55});
        ListView listView = findViewById(R.id.listViewDD);
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent i1 = new Intent(DocterDetailsActivity.this, BookAppointmentActivity.class);
                HashMap<String, String> selectedDoctor = lst.get(i);
                i1.putExtra("text1", title);
                i1.putExtra("text2", selectedDoctor.get("line1"));
                i1.putExtra("text3", selectedDoctor.get("line2"));
                i1.putExtra("text4", selectedDoctor.get("line3"));
                i1.putExtra("text5", selectedDoctor.get("line5"));
                startActivity(i1);
            }
        });
    }

}