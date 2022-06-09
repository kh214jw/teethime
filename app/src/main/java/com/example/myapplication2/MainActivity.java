package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //화면 변환 버튼
    private Button button_Home;//홈버튼
    private Button button_Tbushing;//양치여부확인 html버튼
    private Button button_Calendar; //달력 일정 버튼
    private Button button_point;
    private Button button_Calendar_under;//홈화면에서 일정 관리버튼
    private Button button_under_teethbrushing;//홈화면에서 하단의 양치 인증하는 버튼
    private Button buttontest;
    //홈화면에서의 기능
    private Button button_Timeinput;
    private TextView textView_time;
    private TimePickerDialog.OnTimeSetListener callbackMethod;
    private Button button_Timesave;

    //데이터베이스에 저장할 리스트 값 = 날짜, 식사 종류 (스피너), 시간
    //입력한 날짜 저장
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int meal_num = 0; //몇번째 인지
    private String meal_date; //사용자가 저장버튼을 누른 날
    private String meal_kind; //사용자가 입력한 식사 종류//스피너 값 데이터 저장
    private TextView textView_spinner;
    private String meal_time; //사용자가 입력한 시간 데이터 저장
    private String toString; //정보를 한 문장으로 표시 database에 한줄로 출력


    private int num;
    //firebase 연동할  때 필요한 import
    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        arrayList에 넣기
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        u_arritems = new ArrayList<User_meal>(); //arraylist를 불러옴
//        u_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,u_arritems); //adapter에 저장
//
//        listview 활용
//        m_listview.setAdapter(m_dapter);
//        읽어오기
//        readUser();



        //상단 홈버튼을 눌렀을 때
        button_Home = (Button) findViewById(R.id.button_Home);
        button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
                Intent intent = new Intent(MainActivity.this, activity_calendar.class);
                startActivity(intent);

            }
        });

        //마이페이지로 이동


        //유의점
        button_point = (Button) findViewById(R.id.button_point);
        button_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Note.class);
                startActivity(intent);
            }
        });

        //하단의 일정관리하는 캘린더로 이동
        button_Calendar_under = (Button) findViewById(R.id.button_Calendar_under);
        button_Calendar_under.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_calendar.class);
                startActivity(intent);
            }
        });

        //하단의 치아 양치 확인 버튼누르면 html로 변환
        button_under_teethbrushing = (Button) findViewById(R.id.button_under_teethbrushing);
        button_under_teethbrushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://629b7864c31fa2084547fa12--gleeful-gnome-73dcfc.netlify.app/"));
                startActivity(intent);
            }
        });

        //데이터불러오기 test 버튼
        buttontest = (Button) findViewById(R.id.buttontest);
        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, test_activity.class);
                startActivity(intent);
            }
        });


        //사용자가 입력한 날짜


        //스피너 드롭다운
        final ArrayList<String> meal_list = new ArrayList<>();
        meal_list.add("아침");
        meal_list.add("점심");
        meal_list.add("저녁");
        meal_list.add("간식");

        Spinner spinner_meal = findViewById(R.id.spinner_Meal);//스피너 객체생성
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meal_list);//배열 어뎁터 생성
        arrayAdapter.setDropDownViewResource( //배열어뎁터 설정
                android.R.layout.simple_spinner_dropdown_item);
        spinner_meal.setAdapter(arrayAdapter);//설정 어뎁터 스피너에 세팅
        spinner_meal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meal_kind = spinner_meal.getItemAtPosition(position).toString(); //meal_kind에 사용자가 입력한 값을 저장
                //Toast.makeText(MainActivity.this, "값 : "+spinner_meal.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //시간 설정
        this.InitializeView();
        this.InitializeListener();



        textView_time = (TextView) findViewById(R.id.textView_time);
        //저장하기 버튼
        button_Timesave = (Button) findViewById(R.id.button_Timesave);
        button_Timesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meal_date = getTime().toString();

                toString = "- kind : " + meal_kind + "  time : "+ textView_time.getText().toString();
                //databaseReference.child("Input").child(meal_date).setValue(toString);
                addInput(meal_num, getTime().toString(), meal_kind, textView_time.getText().toString());
                //databaseReference.push().setValue(arrayList);//User_meal arraylist에 저장?
                Toast.makeText(getApplicationContext(),"저장완료!",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //사용자가 입력한 시간
    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    //시간 설정하는 코드
    private void InitializeView() {
        callbackMethod = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView_time.setText(hourOfDay + "시" + minute + "분");
                meal_time = textView_time.getText().toString();//시간을 string으로 변환
            }
        };
    }

    private void InitializeListener() {
        textView_time = (TextView) findViewById(R.id.textView_time);
    }

    public void OnClickHandler(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod, 8, 10, true);
        dialog.show();
    }

    public void addInput(int num, String date, String kind, String time) {
        //User_meal UserMeal = new User_meal(num,date, kind, time);
        toString = "- kind : " + meal_kind + " time : " + textView_time.getText().toString();
        if (time != null && time != "") { //만약에 시간이 아무것도 없거나 null값이 아니면 firebase에 저장
            databaseReference.child("Input").child(date).child(String.valueOf(num)).setValue(toString);
        }
        meal_num++;
    }

}