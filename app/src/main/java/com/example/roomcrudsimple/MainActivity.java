package com.example.roomcrudsimple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomcrudsimple.database.StudentDatabase;
import com.example.roomcrudsimple.my_interface.IClickItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edt_id, edt_fullname, edt_birthday, edt_average;
    private Button btnAdd, btnUpdate, btnDelete, btnSearch;
    private RecyclerView rcv;
    private StudentAdapter studentAdapter;
    private List<Student> mListStudent;
    StudentDatabase db;

    String strId, strName, strBithday;
    int strAverage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        db = Room.databaseBuilder(this, StudentDatabase.class, "a.db").allowMainThreadQueries().build();

        studentAdapter = new StudentAdapter(new IClickItem() {
            @Override
            public void onClickItem(Student student) {
                onBindItemToEdt(student);
            }
        });
        mListStudent = new ArrayList<>();
        loadData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(studentAdapter);

        btnAdd.setOnClickListener(v -> {
            insert();
        });

        btnUpdate.setOnClickListener(v -> {
            update();
        });

        btnDelete.setOnClickListener(v -> {
            delete();
        });

        btnSearch.setOnClickListener(v -> {
            loadData();
        });
    }

    private void initUi(){
        edt_id = findViewById(R.id.edt_id);
        edt_fullname = findViewById(R.id.edt_name);
        edt_birthday = findViewById(R.id.edt_birthday);
        edt_average = findViewById(R.id.edt_average);
        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnSearch = findViewById(R.id.btn_list);
        rcv = findViewById(R.id.recyclerview);
    }

    private void loadData(){
        mListStudent = db.studentDAO().getListStudent();
        studentAdapter.setData(mListStudent);
    }

    private void insert(){
        getData();
            if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strName) || TextUtils.isEmpty(strBithday)){
                Toast.makeText(this, "Please enter full information", Toast.LENGTH_SHORT).show();
            }

        Student student = new Student(strId, strName, strBithday, strAverage);
        if(checkExistId(student)){
            update();
            return;
        }
        db.studentDAO().insert(student);
        Toast.makeText(this, "Insert successfully", Toast.LENGTH_SHORT).show();

        edt_id.setText("");
        edt_fullname.setText("");
        edt_birthday.setText("");
        edt_average.setText("");
        loadData();
    }

    private void getData(){
        strId = edt_id.getText().toString().trim();
        strName = edt_fullname.getText().toString().trim();
        strBithday = edt_birthday.getText().toString().trim();
        strAverage = Integer.parseInt(edt_average.getText().toString().trim());

    }
    private void update(){
        getData();
        if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strName) || TextUtils.isEmpty(strBithday)){
            Toast.makeText(this, "Please enter full information", Toast.LENGTH_SHORT).show();
        }
        Student student = new Student(strId, strName, strBithday, strAverage);
        db.studentDAO().update(student);
        Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
        /*edt_id.setText("");
        edt_fullname.setText("");
        edt_birthday.setText("");
        edt_average.setText("");*/
        loadData();
    }

    private void delete(){
        getData();
        Student student = new Student(strId, strName, strBithday, strAverage);
        new AlertDialog.Builder(this).setTitle("Delete").setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.studentDAO().delete(student);
                        Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                }).setNegativeButton("No", null).show();
    }

    private boolean checkExistId(Student student){
        List<Student> list = db.studentDAO().checkId(student.getId());
        return list != null && !list.isEmpty();
    }

    private void onBindItemToEdt(Student student){
        edt_id.setText(student.getId());
        edt_fullname.setText(student.getFullName());
        edt_birthday.setText(student.getBirthday());
        edt_average.setText(String.valueOf(student.getAverage()));
    }
}