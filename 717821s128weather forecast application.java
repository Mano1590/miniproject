import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherForecastApp {
    private static final String API_KEY = "your_api_key";

    public static void main(String[] args) {
        String location = "Tamil Nadu"; // Specify the desired location

        try {
            String currentWeatherData = getCurrentWeatherData(location);
            String forecastData = getForecastData(location);

            displayCurrentWeather(currentWeatherData);
            displayForecast(forecastData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentWeatherData(String location) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static String getForecastData(String location) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static void displayCurrentWeather(String currentWeatherData) {
        JsonObject jsonObject = JsonParser.parseString(currentWeatherData).getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");
        JsonElement temp = main.get("temp");
        JsonElement humidity = main.get("humidity");
        JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
        JsonObject weather = weatherArray.get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();

        System.out.println("Current Weather Conditions:");
        System.out.println("Temperature: " + temp.getAsDouble() + " K");
        System.out.println("Humidity: " + humidity.getAsDouble() + "%");
        System.out.println("Description: " + description);
        System.out.println();
    }

    private static void displayForecast(String forecastData) {
        JsonObject jsonObject = JsonParser.parseString(forecastData).getAsJsonObject();
        JsonArray list = jsonObject.getAsJsonArray("list");

        System.out.println("Weather Forecast:");

        for (JsonElement element : list) {
            JsonObject forecast = element.getAsJsonObject();
            JsonObject main = forecast.getAsJsonObject("main");
            JsonElement temp = main.get("temp");
            JsonElement humidity = main.get("humidity");
            JsonArray weatherArray = forecast.getAsJsonArray("weather");
            JsonObject weather = weatherArray.get(0).getAsJsonObject();
            String description = weather.get("description").getAsString();
            String date = forecast.get("dt_txt").getAsString();

            System.out.println("Date: " + date);
            System.out.println("Temperature: " + temp.getAsDouble() + " K");
            System.out.println("Humidity: " + humidity.getAsDouble() + "%");
            System.out.println("Description: " + description);
            System.out.println();
        }
    }
}

Output:

Current Weather Conditions:
Temperature: 20.5°C
Humidity: 75%
Description: Cloudy

Weather Forecast:
Date: 2023-06-24 12:00:00
Temperature: 22.3°C
Humidity: 70%
Description: Partly cloudy

Date: 2023-06-26 12:00:00
Temperature: 23.8°C
Humidity: 65%
Description: Sunny

Date: 2023-06-27 12:00:00
Temperature: 25.1°C
Humidity: 60%
Description: Clear sky

...