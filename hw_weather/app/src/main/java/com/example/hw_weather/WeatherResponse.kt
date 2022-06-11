package com.example.hw_weather

import com.google.gson.annotations.SerializedName

class WeatherResponse {

    @SerializedName("list")
    var list = ArrayList<List>()

}


class List {
    @SerializedName("main")
    var main: Main? = null

    @SerializedName("weather")
    var weather = ArrayList<Weather>()

    @SerializedName("wind")
    var wind: Wind? = null
}


class Main {
    @SerializedName("temp")
    var temp: Float = 0f

    @SerializedName("humidity")
    var humidity: Float = 0f
}


class Weather {
    @SerializedName("id")
    var id: Float = 0f
}


class Wind {
    @SerializedName("speed")
    var speed: Float = 0f
}