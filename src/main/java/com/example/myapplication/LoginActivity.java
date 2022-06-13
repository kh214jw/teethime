package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //로그인 버튼
    private Button loginBtn;

    // 이메일과 비밀번호
    private EditText user_id;
    private EditText user_password;
    private EditText user_name;
    private EditText user_email;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 데이터베이스
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //파이어베이스 접근 설정
        firebaseAuth =  FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("teethimeUser");

        //xml아이디 설정
        user_email = findViewById(R.id.login_userid);
        user_password = findViewById(R.id.login_pw);

        //로그인 버튼 설정
        loginBtn = findViewById(R.id.login_loginbtn);

        //로그인 버튼이 눌리면
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString();
                String pwd = user_password.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //로그인 성공
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "로그인 성공 !! ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, UserspaceActivity.class);
                            startActivity(intent);
                            finish();   //현재 엑티비티 파괴
                        }else{
                            Toast.makeText(LoginActivity.this, "잘못된 로그인 정보 입니다...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //취소하기 버튼을 누를시 처음으로 이동
        Button closeButton = (Button) findViewById(R.id.login_closebtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        //취소하기 버튼을 누를시 처음으로 이동
        Button sButton_id = (Button) findViewById(R.id.login_idserch);
        sButton_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ids_User.class);
                startActivity(intent);
            }

        });

        //취소하기 버튼을 누를시 처음으로 이동
        Button sButton_pw = (Button) findViewById(R.id.login_pwserch);
        sButton_pw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pws_User.class);
                startActivity(intent);
            }
        });
    }
}
