package com.mugoori.memoapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.mugoori.memoapp2.adapter.MemoAdapter;
import com.mugoori.memoapp2.data.DatabaseHandler;
import com.mugoori.memoapp2.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
//    ImageView imgSearch;
    ImageView imgDelete;
    Button btnAdd;

    RecyclerView recyclerView;

    MemoAdapter adapter;

    ArrayList<Memo> memoList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
//        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MemoCreateActivity.class);
                startActivity(intent);

            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 유저가 입력한 키워드 뽑기
                String keyword = editSearch.getText().toString().trim();

                // 2글자 이상 입력했을대만 검색이 되도록 한다
                if (keyword.length() < 2 && keyword.length() > 0 ){
                    return;
                }

                // 디비에서 가져오기
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.SearchMemo(keyword);
                db.close();

                // 화면에 보여주기
                adapter = new MemoAdapter(MainActivity.this,memoList);
                recyclerView.setAdapter(adapter);

            }
        });



//        imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String keyword = editSearch.getText().toString().trim();
//                if (keyword.isEmpty()){
//                    return;
//                }
//
//                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
//                memoList = db.SearchMemo(keyword);
//                db.close();
//                adapter = new MemoAdapter(MainActivity.this,memoList);
//                recyclerView.setAdapter(adapter);
//
//
//
//
//            }
//        });
//
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.getAllMemos();
                adapter = new MemoAdapter(MainActivity.this,memoList);
                recyclerView.setAdapter(adapter);


            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemos();

        adapter = new MemoAdapter(MainActivity.this,memoList);
        recyclerView.setAdapter(adapter);


        db.close();

    }
}