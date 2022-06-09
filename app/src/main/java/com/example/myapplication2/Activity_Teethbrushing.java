package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Teethbrushing extends AppCompatActivity {
    private Button button_Home;
    private Button button_Tbushing;
    private Button button_Calendar;
    private Button button_Mypage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teethbrushing);

        //상단 홈버튼을 눌렀을 때
        button_Home = (Button) findViewById(R.id.button_Home);
        button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Teethbrushing.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //상단 양치 여부 확인 html버튼 btsendTb
        button_Tbushing = (Button) findViewById(R.id.button_Tbushing);
        button_Tbushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Teethbrushing.this, Activity_Teethbrushing.class);
                startActivity(intent);
            }
        });

        //상단 일정관리 하는 캘린더로 이동
        button_Calendar = (Button) findViewById(R.id.button_Calendar);
        button_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Teethbrushing.this, activity_calendar.class);
                startActivity(intent);

            }
        });

        //마이페이지로 이동

    }
}