package com.example.cardiio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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



    private static final String HEART_PULSE_CHANNEL_ID = "heartPulse";
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView bodyTemperature;
    private TextView HeartPulse;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseDatabase firebaseTemp;
    private ImageButton imbHistoryTemperature;
    private ImageButton imbHistoryHeartPulse;
    private TextView Spo2;
    private ImageButton imbHistorySpo2;



    @RequiresApi(api = Build.VERSION_CODES.O)
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
        firebaseTemp = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Temperature");
        final DatabaseReference databaseReference1 = firebaseTemp.getReference("Temperature");

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

        NotificationChannel HeartPulseChannel =
                new NotificationChannel(HEART_PULSE_CHANNEL_ID,"HeartPulse",NotificationManager.IMPORTANCE_DEFAULT);
        HeartPulseChannel.setLightColor(Color.GREEN);
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.createNotificationChannel(HeartPulseChannel);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heartPulse = snapshot.child("Heart Pulse").getValue().toString();
                //HeartPulse.setText(heartPulse);

                if(heartPulse.equals("80")){
                    Intent notificationIntent = new Intent(SecondActivity.this,SecondActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(SecondActivity.this,0,notificationIntent,0);

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(SecondActivity.this)
                            .setContentTitle("Heart Rate Monitor!!")
                            .setContentText("Your Heart Rate is High")
                            .setSmallIcon(android.R.drawable.stat_notify_error)
                            .setChannelId(HEART_PULSE_CHANNEL_ID)
                            .setColor(getResources().getColor(R.color.black))
                            .setVibrate(new long[] {0,300,300,300})
                            .setLights(Color.GREEN,1000,5000)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(0,notification.build());


                }else if (heartPulse.equals("100")){
                    Intent notificationIntent = new Intent(SecondActivity.this,SecondActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(SecondActivity.this,0,notificationIntent,0);

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(SecondActivity.this)
                            .setContentTitle("Heart Rate Monitor!!")
                            .setContentText("Your Heart Rate is Morattu High")
                            .setSmallIcon(android.R.drawable.stat_notify_error)
                            .setChannelId(HEART_PULSE_CHANNEL_ID)
                            .setColor(getResources().getColor(R.color.black))
                            .setVibrate(new long[] {0,300,300,300})
                            .setLights(Color.WHITE,1000,5000)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0,notification.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SecondActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
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