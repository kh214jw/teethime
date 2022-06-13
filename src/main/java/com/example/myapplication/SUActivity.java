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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SUActivity extends AppCompatActivity {

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 데이터베이스
    private DatabaseReference databaseReference;

    // 이메일과 비밀번호
    private EditText user_id;
    private EditText user_password;
    private EditText user_name;
    private EditText user_email;

    private Button btn_SU;
    private Button btn_close;
    private Button overlap_button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //파이어베이스 접근 설정
        firebaseAuth = FirebaseAuth.getInstance();  //인증객체
        databaseReference = FirebaseDatabase.getInstance().getReference("teethimeUser");

        //xml아이디 설정
        user_id = (EditText) findViewById(R.id.sigup_userid);
        user_password = (EditText) findViewById(R.id.sigup_userpassword);
        user_name = (EditText) findViewById(R.id.signup_username);
        user_email = (EditText) findViewById(R.id.signup_useremail);

        //회원가입 버튼
        btn_SU = (Button) findViewById(R.id.signup_sigupbtn);
        btn_close = (Button) findViewById(R.id.signup_closebtn);

        //아이디 중복 체크 버튼
        overlap_button = findViewById(R.id.overlap_button);

        //회원가입 버튼을 눌렀을 때
        btn_SU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_id.getText().toString();
                String pwd = user_password.getText().toString();
                String name = user_name.getText().toString();
                String email = user_email.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SUActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //회원가입 성공을 했다면
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setId(id);
                            account.setName(name);
                            account.setPassword(pwd);

                            databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SUActivity.this, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SUActivity.this, "회원가입에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        //취소하기 버튼을 누를시 처음으로 이동
        Button closeButton = (Button) findViewById(R.id.signup_closebtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        }
    });

    }
}


