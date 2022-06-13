package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class UserspaceActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 데이터베이스
    private DatabaseReference databaseReference;

    // 유저 정보
    private TextView user_id;
    private TextView user_name;
    private TextView user_email;

    private Button login_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);


        //로그아웃
        Button logout_btn = findViewById(R.id.logoutbtn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 하기
                firebaseAuth.signOut();
                Toast.makeText(UserspaceActivity.this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                //첫화면으로 이동
                Intent intent = new Intent(UserspaceActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }
}
