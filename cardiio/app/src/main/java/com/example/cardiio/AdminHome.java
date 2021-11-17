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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

    private static final String HEART_PULSE_CHANNEL_ID = "heartPulse";
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView bodyTemperature;
    private TextView HeartPulse;
    private TextView Spo2;
    private FirebaseDatabase firebaseHeart;
    private FirebaseDatabase firebaseDatabase;
    private ImageButton imbHistoryTemperature;
    private ImageButton imbHistoryHeartPulse;
    private ImageButton imbHistorySpo2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.teal_200))));


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseHeart = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Temperature");
        final DatabaseReference databaseReference1 = firebaseHeart.getReference("Temperature");
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

        NotificationChannel HeartPulseChannel =
                new NotificationChannel(HEART_PULSE_CHANNEL_ID,"HeartPulse", NotificationManager.IMPORTANCE_DEFAULT);
        HeartPulseChannel.setLightColor(Color.GREEN);
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.createNotificationChannel(HeartPulseChannel);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heartPulse = snapshot.child("Heart Pulse").getValue().toString();
                //HeartPulse.setText(heartPulse);

                if(heartPulse.equals("80")){
                    Intent notificationIntent = new Intent(AdminHome.this,AdminHome.class);
                    PendingIntent pi = PendingIntent.getActivity(AdminHome.this,0,notificationIntent,0);

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(AdminHome.this)
                            .setContentTitle("Heart Rate Monitor!!")
                            .setContentText("Your Heart Rate is Normal. Stay Safe Stay Healthy ")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.Heart_Rate_less100)))
                            .setSmallIcon(R.mipmap.ic_launcher_round).setLargeIcon(BitmapFactory.decodeResource(AdminHome.this.getResources(),R.mipmap.ic_launcher_round))
                            .setChannelId(HEART_PULSE_CHANNEL_ID)
                            .setColor(getResources().getColor(R.color.purple_200))
                            .setVibrate(new long[] {0,300,300,300})
                            .setLights(Color.GREEN,1000,5000)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(0,notification.build());


                }else if (heartPulse.equals("100")){
                    Intent notificationIntent = new Intent(AdminHome.this,AdminHome.class);
                    PendingIntent pi = PendingIntent.getActivity(AdminHome.this,0,notificationIntent,0);

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(AdminHome.this)
                            .setContentTitle("Heart Rate Monitor!!")
                            .setContentText("Your Heart Rate is High")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.Heart_Rate_more100)))
                            .setSmallIcon(R.mipmap.ic_launcher_round).setLargeIcon(BitmapFactory.decodeResource(AdminHome.this.getResources(),R.mipmap.ic_launcher_round))
                            .setChannelId(HEART_PULSE_CHANNEL_ID)
                            .setColor(getResources().getColor(R.color.purple_200))
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
                Toast.makeText(AdminHome.this, error.getCode(), Toast.LENGTH_SHORT).show();
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