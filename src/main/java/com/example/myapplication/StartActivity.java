package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //로그인 버튼을 누를시 로그인 화면으로 이동
       Button login_Button = (Button) findViewById(R.id.loginbtn);
        login_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //회원가입 버튼을 누를시 회원가입 화면으로 이동
        Button SU_Button = (Button) findViewById(R.id.sigupbtn);
        SU_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SUActivity.class);
                startActivity(intent);
            }
        });
    }

}