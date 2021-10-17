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

import android.os.Bundle;

public class HistoryHeartPulseList extends AppCompatActivity {

    private DatabaseReference heartPulseHistDatabase;
    private ListView hearPulseHistoryList;
    private FirebaseAuth firebaseAuth;

    private ArrayList<String> heartPulseHist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_heart_pulse_list);

        firebaseAuth = FirebaseAuth.getInstance();
        heartPulseHistDatabase = FirebaseDatabase.getInstance().getReference().child("History_Heart_Pulse");
        hearPulseHistoryList = (ListView)findViewById(R.id.lvHeartPulseHistory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,heartPulseHist );

        hearPulseHistoryList.setAdapter(arrayAdapter);

        heartPulseHistDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            Log.e("@@@", "onDataChange: " + data.toString());
                            heartPulseHist.add(data.toString());
                        } catch (ClassCastException cce) {
                            // If the object can’t be casted into HashMap, it means that it is of type String.
                            String mString = String.valueOf(dataMap.get(key));
                            heartPulseHist.add(data.toString());
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