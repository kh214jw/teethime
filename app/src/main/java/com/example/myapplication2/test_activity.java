package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class test_activity extends AppCompatActivity {
    MainActivity main;
    private Button button_Home;
    private Button button_Tbushing;
    private Button button_Calendar;
    private Button button_point;

    private Button search_btn;
    private TextView read_Text;
    private Button bt_read;

    private String date;

    private TextView txtResult;
    private TextView tvSelect;

    private EditText write_edit;
    private ListView listView;
    private SearchAdapter adapter;
    private List<String> list;
    private ArrayList<String> arraylist;

    private int len;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        String[] searchlist = new String[10];//최대 10개만 가져옴
        //final TextView tvSelect = findViewById(R.id.tvSelect);
        ListView listView = findViewById(R.id.listView);

        list = new ArrayList<>();

        //검색어 값 가져오기
        write_edit = (EditText) findViewById(R.id.write_edit);
        
        write_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i1, int i2, int i3) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.clear();
                String text = write_edit.getText().toString();
                myRef.child("Input").child(text).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error", task.getException());
                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            String array = String.valueOf(task.getResult().getValue());
                            //결과값
                            //[null, - kind : 아침 time : 8시10분, - kind : 점심 time : 12시55분, - kind : 저녁 time : 6시45분, - kind : 간식 time : 18시40분]
                            String array1 = array.replace("[", "");
                            String array2 = array1.replace("]", "");
                            Log.d("firebase", array2);
                            String array3 = array2.replace("null, ", "");
                            String[] logList = array3.split(", ");
                            for (int i = 0; i < logList.length; i++) {
                                Log.d("firebase", logList[i]);
                                /*결과값
                                - kind : 아침 time : 8시10분
                                - kind : 점심 time : 12시55분
                                - kind : 저녁 time : 6시45분
                                - kind : 간식 time : 18시40분*/
                                if (Objects.equals(logList[i], "null")){
                                    continue;
                                }
                                list.add(logList[i]);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(adapter);


                        }
                        //listView.setOnItemClickListener(this);
                        //search(text);
                    }
                });
            }
        });
        



        //상단 홈버튼을 눌렀을 때
        button_Home = (Button) findViewById(R.id.button_Home);
        button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test_activity.this, MainActivity.class);
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
                Intent intent = new Intent(test_activity.this, activity_calendar.class);
                startActivity(intent);

            }
        });

        //마이페이지로 이동


        //유의점
        button_point = (Button) findViewById(R.id.button_point);
        button_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test_activity.this, Activity_Note.class);
                startActivity(intent);
            }
        });

    }
}