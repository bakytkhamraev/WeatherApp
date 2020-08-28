package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.data.Book;
import com.example.weatherapp.data.RecyclerViewAdapter;
import com.example.weatherapp.data.internet.RetrofitBuilder;
import com.example.weatherapp.data.pojo.CurrentWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private TextView daytime;
    private TextView sunset;
    private TextView day;
    private ImageView imageView;
    private TextView sunny;
    private TextView wind;
    private List<Book> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadWeather();
        listBook();
        rv();
    }

    private void initView() {
        tvCurrentWeather = findViewById(R.id.tvCurrentWeather);
        humi = findViewById(R.id.humi);
        pressure = findViewById(R.id.pressure);
        minTemp = findViewById(R.id.min_temp);
        maxTemp = findViewById(R.id.max_temp);
        imageView = findViewById(R.id.weather_img);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        sunny = findViewById(R.id.sunny);
        daytime = findViewById(R.id.daytime);
        wind = findViewById(R.id.wind_id);
        day = findViewById(R.id.day);
    }

    private void listBook() {
        lstBook = new ArrayList<>();
        lstBook.add(new Book("Mon, 21", "35°C  26°C", R.drawable.sunrise));
        lstBook.add(new Book("Tue, 22", "35°C  26°C", R.drawable.sunset));
        lstBook.add(new Book("Mon, 23", "35°C  26°C", R.drawable.barometer));
        lstBook.add(new Book("Tue, 24", "35°C  26°C", R.drawable.humidity));
        lstBook.add(new Book("Tue, 25", "35°C  26°C", R.drawable.wind));
        lstBook.add(new Book("Mon, 26", "35°C  26°C", R.drawable.sunny));
        lstBook.add(new Book("Mon, 27", "35°C  26°C", R.drawable.clock));
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
                            sunny.setText(weather.getWeather().get(0).getMain());
                            //иконка погоды
                            Glide.with((MainActivity.this)).load("http://openweathermap.org/img/wn/" + weather.
                                    getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageView);
                            tvCurrentWeather.setText(weather.getMain().getTempMin().toString() + "C");
                            humi.setText(weather.getMain().getHumidity().toString() + "%");
                            pressure.setText(weather.getMain().getPressure().toString() + "mBar");
                            maxTemp.setText(weather.getMain().getTempMax().toString() + "C");
                            minTemp.setText(weather.getMain().getTempMin().toString() + "C");
                            wind.setText(weather.getWind().getDeg().toString() + "km/h");

                            //day
                            String currentDate = new SimpleDateFormat("EEEE-dd-MM-yyyy-HH:mm", Locale.getDefault()).format(new Date());
                            day.setText(currentDate);

                            //sunrise
                            long am = Long.valueOf(weather.getSys().getSunrise()) * 1000;
                            Date sunrise1 = new java.util.Date(am);
                            String sunr = new SimpleDateFormat(" hh:mma").format(sunrise1);
                            sunrise.setText(sunr);

                            //sunset
                            long pm = Long.valueOf(weather.getSys().getSunset()) * 1000;
                            Date sunset1 = new java.util.Date(pm);
                            String suns = new SimpleDateFormat(" hh:mma").format(sunset1);
                            sunset.setText(suns);

                            //daytime
                            int dayTimeMin = weather.getSys().getSunset() - weather.getSys().getSunrise();
                            long pmam = dayTimeMin * 1000;
                            Date dayTimeInNormal = new java.util.Date(pmam);
                            String dayT = new SimpleDateFormat(" h:mma").format(dayTimeInNormal);
                            daytime.setText(dayT);

                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {

                    }
                });
    }

}
