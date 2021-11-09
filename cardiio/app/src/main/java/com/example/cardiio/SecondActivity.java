package com.example.cardiio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView bodyTemperature;
    private TextView HeartPulse;
    private FirebaseDatabase firebaseDatabase;
    private ImageButton imbHistoryTemperature;
    private ImageButton imbHistoryHeartPulse;
    private TextView Spo2;
    private ImageButton imbHistorySpo2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intentBackgroundService = new Intent(this, PushNotification.class);
        startService(intentBackgroundService);

        firebaseAuth = FirebaseAuth.getInstance();
        bodyTemperature = (TextView)findViewById(R.id.tvBodyTemperature);
        HeartPulse = (TextView)findViewById(R.id.tvHeartPulse);
        Spo2 = (TextView)findViewById(R.id.tvSpo2);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Temperature");

        logout = (Button)findViewById(R.id.btnLogout);

        imbHistoryTemperature = (ImageButton)findViewById(R.id.IBHistoryTemperature);
        imbHistoryHeartPulse = (ImageButton)findViewById(R.id.IBHistoryHeartRate);
        imbHistorySpo2 = (ImageButton)findViewById(R.id.IBHistorySPO2);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Logout();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.child("Temperature").getValue().toString();
                bodyTemperature.setText("Body Temperature : " + temperature);

                String heartPulse = dataSnapshot.child("Heart Pulse").getValue().toString();
                HeartPulse.setText("Heart Pulse : " + heartPulse);

                String spo2 = dataSnapshot.child("SPO2").getValue().toString();
                Spo2.setText(("SPO2 :" + spo2));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        imbHistoryTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,HistoryTemperatureList.class));
            }
        });

        imbHistoryHeartPulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,HistoryHeartPulseList.class));
            }
        });

        imbHistorySpo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,HistorySpo2List.class));
            }
        });

    }



    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
                break;
            }
            case R.id.profileMenu:
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;

            case R.id.AboutUsMenu:
                startActivity(new Intent(SecondActivity.this, AboutUs.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}