package com.example.saatvik.weatherapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button getData;
    ImageView currentLogo;
    TextView quote;
    TextView currentTemp;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    ImageView logo1;
    ImageView logo2;
    ImageView logo3;
    ImageView logo4;
    TextView temp1;
    TextView temp2;
    TextView temp3;
    TextView temp4;
    String zip;
    EditText zipCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getData=findViewById(R.id.id_getWeather);
        getData.setTextColor(Color.rgb(255,255,255));
        currentLogo=findViewById(R.id.id_currentLogo);
        quote=findViewById(R.id.id_quote);
        quote.setTextColor(Color.rgb(249, 203, 17));
        currentTemp=findViewById(R.id.id_currentTemp);

        time1=findViewById(R.id.id_time1);
        time1.setTextColor(Color.rgb(88,151,252));
        time2=findViewById(R.id.id_time2);
        time2.setTextColor(Color.rgb(88,151,252));
        time3=findViewById(R.id.id_time3);
        time3.setTextColor(Color.rgb(88,151,252));
        time4=findViewById(R.id.id_time4);
        time4.setTextColor(Color.rgb(88,151,252));

        logo1=findViewById(R.id.id_logo1);
        logo2=findViewById(R.id.id_logo2);
        logo3=findViewById(R.id.id_logo3);
        logo4=findViewById(R.id.id_logo4);

        temp1=findViewById(R.id.id_temp1);
        temp1.setTextColor(Color.rgb(88,151,252));
        temp2=findViewById(R.id.id_temp2);
        temp2.setTextColor(Color.rgb(88,151,252));
        temp3=findViewById(R.id.id_temp3);
        temp3.setTextColor(Color.rgb(88,151,252));
        temp4=findViewById(R.id.id_temp4);
        temp4.setTextColor(Color.rgb(88,151,252));

        zipCode=findViewById(R.id.id_zipCode);
        zipCode.setBackgroundColor(Color.rgb(88,151,252));
        zipCode.setTextColor(Color.WHITE);


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zip=zipCode.getText().toString();
                new getWeatherData().execute(zip);
            }
        });


    }

    public class getWeatherData extends AsyncTask<String, Void, Void>{
        URL url;
        URLConnection urlConnection;
        String zip="";
        String jsonString;
        InputStreamReader streamReader;


        JSONObject weather;
        JSONArray forecast;
        JSONObject weatherCurrent;
        JSONObject weatherForecast;
        int timeConverter;

        JSONObject currentTempData;
        JSONArray currentWeatherDataArray;
        JSONObject currentWeatherData;
        String currentWeatherLogoId;

        JSONObject tempData;
        JSONArray weatherDataArray;
        JSONObject weatherData;
        String weatherLogoId;
        String timeDate;

        String date;

        @Override
        protected Void doInBackground(String... params) {
            try {

                String newZip = params[0];
                Log.d("TAG",newZip);
                url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=" + newZip + "&APPID=341e9fd33499a75476659ece3fa2251b&units=imperial");
                Log.d("TAG",url.toString());
                urlConnection = url.openConnection();
                streamReader = new InputStreamReader(urlConnection.getInputStream());
            } catch (Exception e){
                e.printStackTrace();
            }

            BufferedReader in = new BufferedReader(streamReader);
            jsonString="";
            String current;
            try {
                while ((current = in.readLine()) != null) {
                    jsonString += current;
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                weather = new JSONObject(jsonString);
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Current Weather Data
            try {
                forecast = new JSONArray(weather.getString("list"));
                weatherCurrent = new JSONObject(forecast.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                currentTempData = new JSONObject(weatherCurrent.getString("main"));
                currentWeatherDataArray = new JSONArray(weatherCurrent.getString("weather"));
                currentWeatherData = new JSONObject(currentWeatherDataArray.getString(0));
                currentWeatherLogoId = currentWeatherData.getString("icon").substring(0,2);

                String temp = currentTempData.getString("temp");
                Double tempDoub = Double.parseDouble(temp);
                double tempDoubRound = Math.rint(tempDoub);
                int tempInt = (int)tempDoubRound;


                currentTemp.setText("The weather is "+currentWeatherData.getString("description")+" and "+tempInt+"° F");
                currentTemp.setTextColor(Color.rgb(249, 203, 17));

                switch (currentWeatherLogoId) {
                    case "01":
                        currentLogo.setImageResource(R.drawable.current_01);
                        int rand=(int)(Math.random()*1);
                        if(rand==0)
                            quote.setText("Looks like a great day for web-slinging!");
                        else
                            quote.setText("Try not to make too many quips today.");
                        break;
                    case "02":
                        currentLogo.setImageResource(R.drawable.cloudy_works);
                        quote.setText("It's a pretty good day to stop evil");
                        break;
                    case "03":
                        currentLogo.setImageResource(R.drawable.current_03);
                        quote.setText("With great clouds comes great responsibility");
                        break;
                    case "04":
                        currentLogo.setImageResource(R.drawable.current_04);
                        quote.setText("Looks like evil is plotting something today");
                        break;
                    case "09":
                        currentLogo.setImageResource(R.drawable.current_09);
                        quote.setText("A little rain never stopped Spidey");
                        break;
                    case "10":
                        currentLogo.setImageResource(R.drawable.current_10);
                        quote.setText("Swinging across today will be faster than taking the bus");
                        break;
                    case "11":
                        currentLogo.setImageResource(R.drawable.current_11);
                        quote.setText("Looks like Electro got a little too excited today");
                        break;
                    case "13":
                        currentLogo.setImageResource(R.drawable.current_13);
                        quote.setText("Justice never takes a snow day off");
                        break;
                    case "50":
                        currentLogo.setImageResource(R.drawable.current_50);
                        quote.setText("The crime in this city is hiding in the mist");
                        break;


                }

                for(int i=1; i<=4; i++)
                    setForecast(forecast,i);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setForecast (JSONArray forecast, int index) throws JSONException {
            weatherForecast = new JSONObject(forecast.getString(index));

            tempData = new JSONObject(weatherForecast.getString("main"));
            weatherDataArray = new JSONArray(weatherForecast.getString("weather"));
            weatherData = new JSONObject(weatherDataArray.getString(0));
            weatherLogoId = weatherData.getString("icon").substring(0,2);
            timeDate = weatherForecast.getString("dt_txt").substring(11,13);
            timeConverter=Integer.parseInt(timeDate);
            timeConverter-=5;
            if(timeConverter<0) {
                timeConverter += 12;
                timeDate=timeConverter+":00 PM";
            }
            else if(timeConverter>12){
                timeConverter-=12;
                timeDate=timeConverter+":00 PM";
            }
            else
                timeDate=timeConverter+":00 AM";



            if(index==1) {
                switch (weatherLogoId) {
                    case "01":
                        logo1.setImageResource(R.drawable.logo_01d);
                        break;
                    case "02":
                        logo1.setImageResource(R.drawable.logo_02d);
                        break;
                    case "03":
                        logo1.setImageResource(R.drawable.logo_03d);
                        break;
                    case "04":
                        logo1.setImageResource(R.drawable.logo_04d);
                        break;
                    case "09":
                        logo1.setImageResource(R.drawable.logo_09d);
                        break;
                    case "10":
                        logo1.setImageResource(R.drawable.logo_10d);
                        break;
                    case "11":
                        logo1.setImageResource(R.drawable.logo_11d);
                        break;
                    case "13":
                        logo1.setImageResource(R.drawable.logo_13d);
                        break;
                    case "50":
                        logo1.setImageResource(R.drawable.logo_50d);
                        break;
                }
            }
            else if(index==2){
                switch (weatherLogoId) {
                    case "01":
                        logo2.setImageResource(R.drawable.logo_01d);
                        break;
                    case "02":
                        logo2.setImageResource(R.drawable.logo_02d);
                        break;
                    case "03":
                        logo2.setImageResource(R.drawable.logo_03d);
                        break;
                    case "04":
                        logo2.setImageResource(R.drawable.logo_04d);
                        break;
                    case "09":
                        logo2.setImageResource(R.drawable.logo_09d);
                        break;
                    case "10":
                        logo2.setImageResource(R.drawable.logo_10d);
                        break;
                    case "11":
                        logo2.setImageResource(R.drawable.logo_11d);
                        break;
                    case "13":
                        logo2.setImageResource(R.drawable.logo_13d);
                        break;
                    case "50":
                        logo2.setImageResource(R.drawable.logo_50d);
                        break;
                }
            }
            else if(index==3){
                switch (weatherLogoId) {
                    case "01":
                        logo3.setImageResource(R.drawable.logo_01d);
                        break;
                    case "02":
                        logo3.setImageResource(R.drawable.logo_02d);
                        break;
                    case "03":
                        logo3.setImageResource(R.drawable.logo_03d);
                        break;
                    case "04":
                        logo3.setImageResource(R.drawable.logo_04d);
                        break;
                    case "09":
                        logo3.setImageResource(R.drawable.logo_09d);
                        break;
                    case "10":
                        logo3.setImageResource(R.drawable.logo_10d);
                        break;
                    case "11":
                        logo3.setImageResource(R.drawable.logo_11d);
                        break;
                    case "13":
                        logo3.setImageResource(R.drawable.logo_13d);
                        break;
                    case "50":
                        logo3.setImageResource(R.drawable.logo_50d);
                        break;
                }

            }
            else if(index==4){
                switch (weatherLogoId) {
                    case "01":
                        logo4.setImageResource(R.drawable.logo_01d);
                        break;
                    case "02":
                        logo4.setImageResource(R.drawable.logo_02d);
                        break;
                    case "03":
                        logo4.setImageResource(R.drawable.logo_03d);
                        break;
                    case "04":
                        logo4.setImageResource(R.drawable.logo_04d);
                        break;
                    case "09":
                        logo4.setImageResource(R.drawable.logo_09d);
                        break;
                    case "10":
                        logo4.setImageResource(R.drawable.logo_10d);
                        break;
                    case "11":
                        logo4.setImageResource(R.drawable.logo_11d);
                        break;
                    case "13":
                        logo4.setImageResource(R.drawable.logo_13d);
                        break;
                    case "50":
                        logo4.setImageResource(R.drawable.logo_50d);
                        break;
                }
            }
            switch(index){
                case 1: time1.setText(timeDate);
                    break;
                case 2: time2.setText(timeDate);
                    break;
                case 3: time3.setText(timeDate);
                    break;
                case 4: time4.setText(timeDate);
                    break;
            }

            String tempMin = tempData.getString("temp_min");
            Double tempDoubMin = Double.parseDouble(tempMin);
            double tempDoubRoundMin = Math.rint(tempDoubMin);
            int tempIntMin = (int)tempDoubRoundMin;

            String tempMax = tempData.getString("temp_max");
            Double tempDoubMax = Double.parseDouble(tempMax);
            double tempDoubRoundMax = Math.rint(tempDoubMax);
            int tempIntMax = (int)tempDoubRoundMax;

            switch(index){
                case 1: temp1.setText(tempIntMin+"°-"+tempIntMax+"°F");
                    break;
                case 2: temp2.setText(tempIntMin+"°-"+tempIntMax+"°F");
                    break;
                case 3: temp3.setText(tempIntMin+"°-"+tempIntMax+"°F");
                    break;
                case 4: temp4.setText(tempIntMin+"°-"+tempIntMax+"°F");
                    break;
            }
        }
    }

}