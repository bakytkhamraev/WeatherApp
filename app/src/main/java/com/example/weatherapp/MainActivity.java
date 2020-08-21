package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weatherapp.data.Book;
import com.example.weatherapp.data.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstBook = new ArrayList<>();
        lstBook.add(new Book("Mon, 21", "35°C  26°C", R.drawable.sunrise));
        lstBook.add(new Book("Tue, 22", "35°C  26°C", R.drawable.sunset));
        lstBook.add(new Book("Wed, 22", "35°C  26°C", R.drawable.wind));
        lstBook.add(new Book("Wed, 22", "35°C  26°C", R.drawable.sunny));
        lstBook.add(new Book("Wed, 22", "35°C  26°C", R.drawable.sunny));
        lstBook.add(new Book("Wed, 22", "35°C  26°C", R.drawable.sunny));
        lstBook.add(new Book("Wed, 22", "35°C  26°C", R.drawable.sunny));
        rv();

    }

    private void rv() {
        RecyclerView myRV = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstBook);
        myRV.setLayoutManager(new GridLayoutManager(this, 1));
        myRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        myRV.setAdapter(myAdapter);
    }

}
