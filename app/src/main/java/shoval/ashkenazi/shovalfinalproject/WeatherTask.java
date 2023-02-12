package shoval.ashkenazi.shovalfinalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherTask extends AsyncTask<String, Void, String> {
    String CITY = "Jerusalem";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String[] args) {
        String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + "08a1251d0d3f7b7f55a6e0c5d547ced1");
        return response;
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject main = jsonObj.getJSONObject("main");
            JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
            JSONObject sys = jsonObj.getJSONObject("sys");
            // CALL VALUE IN API :
            String city_name = jsonObj.getString("name");
            String countryname = sys.getString("country");
            Long updatedAt = jsonObj.getLong("dt");
            String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
            String temperature = main.getString("temp");
            String cast = weather.getString("description");
            String humi_dity = main.getString("humidity");
            String temp_min = main.getString("temp_min");
            String temp_max = main.getString("temp_max");
            Long rise = sys.getLong("sunrise");
            String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
            Long set = sys.getLong("sunset");
            String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));

            Log.e("temp","" +temperature + "°C");
// SET ALL VALUES IN TEXTBOX :
// etSearch.setHint("" + temperature + "°C");
//                city.setText(city_name);
//                country.setText(countryname);
//                time.setText(updatedAtText);
//                temp.setText(temperature + "°C");
//                forecast.setText(cast);
//                humidity.setText(humi_dity);
//                min_temp.setText(temp_min);
//                max_temp.setText(temp_max);
//                sunrises.setText(sunrise);
//                sunsets.setText(sunset);
        } catch (Exception e) {
            Toast.makeText(MainActivity.navView.getContext(), "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
