package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class activity_calendar extends AppCompatActivity {

    //화면 변환
    private Button button_Home;
    private Button button_Tbushing;
    private Button button_Calendar;
    private Button button_Mypage;


    // 파이어베이스 연결
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListner;



    //파이어베이스 연결 후 내용 가져오기
    private String date; //클릭한 날짜 가져오기

    private ListView listView;
    List fileList =new ArrayList<>();
    ArrayAdapter adapter;
    static boolean calledAlready= false;


    //달력
    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //달력
        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        save_Btn = findViewById(R.id.save_Btn);
        del_Btn = findViewById(R.id.del_Btn);
        cha_Btn = findViewById(R.id.cha_Btn);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        contextEditText = findViewById(R.id.contextEditText);

        //상단 홈버튼을 눌렀을 때
        button_Home = (Button) findViewById(R.id.button_Home);
        button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_calendar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //상단 양치 여부 확인 html버튼 btsendTb
        button_Tbushing = (Button) findViewById(R.id.button_Tbushing);
        button_Tbushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://629b7864c31fa2084547fa12--gleeful-gnome-73dcfc.netlify.app/"));
                startActivity(intent);
            }
        });

        //상단 일정관리 하는 캘린더로 이동
        button_Calendar = (Button) findViewById(R.id.button_Calendar);
        button_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_calendar.this, activity_calendar.class);
                startActivity(intent);

            }
        });




        //달력 보이게 하는 것
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {//사용자가 클릭한 값을 보여주는 것
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                //diaryTextView.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                if (month+1<10){
                    if (dayOfMonth<10) {
                        diaryTextView.setText(String.format("%d-0%d-0%d", year, month + 1, dayOfMonth));
                    }
                    else{
                        diaryTextView.setText(String.format("%d-0%d-%d", year, month + 1, dayOfMonth));
                    }
                }else{
                    diaryTextView.setText(String.format("%d-%d-s%d", year, month + 1, dayOfMonth));
                }
                date = diaryTextView.toString();
                //만약 date랑 파이어베이스에 있는 child("Input")의 데이터중 date와 같으면 아래를 실행

                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
                System.out.println("***********************"+date.toString());
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() //저장버튼
        {
            @Override
            public void onClick(View view)
            {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

            }
        });
    }

    private void initDatabase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("log");
        mDatabaseReference.child("log").setValue("check");

        mChildEventListner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListner);

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListner);
    }




    public void checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";

        FileInputStream fis;

        try
        {
            fis = openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                }
            });
            if (textView2.getText() == null)
            {
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




}