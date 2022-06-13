package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Pws_User extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwserch);


        //취소하기 버튼을 누를시 처음으로 이동
        Button sButton_pw = (Button) findViewById(R.id.login_closebtn);
        sButton_pw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pws_User.class);
                startActivity(intent);
            }
        });

    }

}
