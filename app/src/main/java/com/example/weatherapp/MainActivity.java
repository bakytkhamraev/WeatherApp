package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.data.Book;
import com.example.weatherapp.data.RecyclerViewAdapter;
import com.example.weatherapp.data.internet.RetrofitBuilder;
import com.example.weatherapp.data.pojo.CurrentWeather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvCurrentWeather;
    private TextView humi;
    private TextView pressure;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView sunrise;
    private TextView sunset;
    private TextView wind;
    List<Book> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCurrentWeather = findViewById(R.id.tvCurrentWeather);
        humi = findViewById(R.id.humi);
        pressure = findViewById(R.id.pressure);
        minTemp = findViewById(R.id.min_temp);
        maxTemp = findViewById(R.id.max_temp);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        wind = findViewById(R.id.wind_id);
        loadWeather();

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
        Log.d("ololo", "rv");
        RecyclerView myRV = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstBook);
        myRV.setLayoutManager(new GridLayoutManager(this, 1));
        myRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        myRV.setAdapter(myAdapter);
    }


    private void loadWeather() {
        RetrofitBuilder.getService().getCurrentWeather("Bishkek",
                "4bbf5a1ed98cd9f688ebb3651474cdd2")
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CurrentWeather weather = response.body();
                            tvCurrentWeather.setText(weather.getMain().getTemp().toString() + "C");
                            humi.setText(weather.getMain().getHumidity().toString() + "%");
                            pressure.setText(weather.getMain().getPressure().toString() + "mBar");
                            maxTemp.setText(weather.getMain().getTempMax().toString() + "C");
                            minTemp.setText(weather.getMain().getTempMin().toString() + "C");
                            sunrise.setText(weather.getSys().getSunrise().toString() + "AM");
                            sunset.setText(weather.getSys().getSunset().toString() + "PM");
                            wind.setText(weather.getWind().getDeg().toString() + "km/h");

                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {

                    }
                });
    }

}
