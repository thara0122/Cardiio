package com.example.cardiio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryTemperatureList extends AppCompatActivity {

    private DatabaseReference tempHistDatabase;
    private ListView temperatureHistoryList;
    private FirebaseAuth firebaseAuth;

    private ArrayList<String> tempHist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_temperature_list);

        firebaseAuth = FirebaseAuth.getInstance();
        tempHistDatabase = FirebaseDatabase.getInstance().getReference().child("History_Temperature/");
        temperatureHistoryList = (ListView)findViewById(R.id.lvTemperatureHistory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempHist);

        temperatureHistoryList.setAdapter(arrayAdapter);

        tempHistDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            Log.e("@@@", "onDataChange: " + data.toString());
                            tempHist.add(data.toString());
                        } catch (ClassCastException cce) {
                            // If the object can???t be casted into HashMap, it means that it is of type String.
                            String mString = String.valueOf(dataMap.get(key));
                            tempHist.add(data.toString());
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}