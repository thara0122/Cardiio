package com.example.cardiio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class AdminHome extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView bodyTemperature;
    private TextView HeartPulse;
    private TextView Spo2;
    private FirebaseDatabase firebaseDatabase;
    private ImageButton imbHistoryTemperature;
    private ImageButton imbHistoryHeartPulse;
    private ImageButton imbHistorySpo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.teal_200))));


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Temperature");
        bodyTemperature = (TextView)findViewById(R.id.tvBodyTemperature);
        HeartPulse = (TextView)findViewById(R.id.tvHeartPulse);
        Spo2 = (TextView)findViewById(R.id.tvSpo2);

        imbHistoryTemperature = (ImageButton)findViewById(R.id.IBHistoryTemperature);
        imbHistoryHeartPulse = (ImageButton)findViewById(R.id.IBHistoryHeartRate);
        imbHistorySpo2 = (ImageButton)findViewById(R.id.IBHistorySPO2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.child("Temperature").getValue().toString();
                bodyTemperature.setText("Body Temperature : " + temperature);

                String heartPulse = dataSnapshot.child("Heart Pulse").getValue().toString();
                HeartPulse.setText("Heart Pulse : " + heartPulse);

                String spo2 = dataSnapshot.child("SPO2").getValue().toString();
                Spo2.setText("SPO2 :" + spo2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminHome.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        imbHistoryTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,HistoryTemperatureList.class));
            }
        });

        imbHistoryHeartPulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,HistoryHeartPulseList.class));
            }
        });

        imbHistorySpo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,HistorySpo2List.class));
            }
        });

    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(AdminHome.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:
                startActivity(new Intent(AdminHome.this,ProfileActivity.class));
                break;

            case R.id.ListOfUsersMenu:
                startActivity(new Intent(AdminHome.this,UserList.class));
                break;

            case R.id.AboutUsMenu:
                startActivity(new Intent(AdminHome.this,AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}