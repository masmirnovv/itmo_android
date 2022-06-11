package com.example.hw_weather

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var weatherVal: ArrayList<Int>
    private lateinit var weatherIconVal: ArrayList<Int>
    private var windVal: Int = 0
    private var humidVal: Int = 0
    private var dayOfWeekVal: String = ""

    private lateinit var weatherTextView: ArrayList<TextView>
    private lateinit var weatherIcons: ArrayList<ImageView>
    private lateinit var windTextView: TextView
    private lateinit var humidTextView: TextView
    private lateinit var dayOfWeekTextView: ArrayList<TextView>



    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(WeatherService::class.java)
    private var call: Call<WeatherResponse>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValues()
        initViews()
        update()
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
        call = null
    }




    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(WEATHER_VAL_KEY, weatherVal)
        outState.putIntegerArrayList(WEATHER_ICONS_KEY, weatherIconVal)
        outState.putInt(WIND_KEY, windVal)
        outState.putInt(HUMID_KEY, humidVal)
        outState.putString(DAY_OF_WEEK_KEY, dayOfWeekVal)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        weatherVal = savedInstanceState.getIntegerArrayList(WEATHER_VAL_KEY) as ArrayList<Int>
        weatherIconVal = savedInstanceState.getIntegerArrayList(WEATHER_ICONS_KEY) as ArrayList<Int>
        windVal = savedInstanceState.getInt(WIND_KEY)
        humidVal = savedInstanceState.getInt(HUMID_KEY)
        dayOfWeekVal = savedInstanceState.getString(DAY_OF_WEEK_KEY)!!
        updateViews()
    }


    private fun initValues() {
        weatherVal = ArrayList()
        weatherVal.addAll(Array(6) {ABSOLUTE_ZERO.toInt()})
        weatherIconVal = ArrayList()
        weatherIconVal.addAll(Array(6) {SUNNY})
        windVal = 0
        humidVal = 0
        dayOfWeekVal = MON
    }

    private fun initViews() {
        weatherTextView = arrayListOf(R.id.currentValue, R.id.monTemp, R.id.tueTemp, R.id.wedTemp,
            R.id.thuTemp, R.id.friTemp).map { findViewById<TextView>(it) } as ArrayList<TextView>
        weatherIcons = arrayListOf(R.id.currentIc, R.id.monIc, R.id.tueIc, R.id.wedIc,
            R.id.thuIc, R.id.friIc).map { findViewById<ImageView>(it) } as ArrayList<ImageView>
        windTextView = findViewById(R.id.windValue)
        humidTextView = findViewById(R.id.humidValue)
        dayOfWeekTextView = arrayListOf(R.id.monTxt, R.id.tueTxt, R.id.wedTxt, R.id.thuTxt,
            R.id.friTxt).map { findViewById<TextView>(it) } as ArrayList<TextView>
    }

    private fun update() {

        call = service.getCurrentWeatherData(CITY, APP_ID)
        call!!.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    weatherVal = arrayListOf(weatherResponse.list[0].main!!.temp.toCelsius())
                    weatherIconVal = arrayListOf(weatherResponse.list[0].weather[0].id.toIconID())
                    windVal = weatherResponse.list[0].wind!!.speed.toInt()
                    humidVal = weatherResponse.list[0].main!!.humidity.toInt()
                    dayOfWeekVal = getDayOfWeek()

                    for (i in Array(5) {it * 8}) {
                        val list = weatherResponse.list[i]
                        weatherVal.add(list.main!!.temp.toCelsius())
                        weatherIconVal.add(list.weather[0].id.toIconID())
                    }

                    updateViews()

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(
                    baseContext,
                    "Error while networking: ${t.message}",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateViews() {
        for (i in 0 until weatherVal.size)
            weatherTextView[i].text = weatherVal[i].degrees()
        for (i in 0 until weatherIconVal.size)
            weatherIcons[i].setImageResource(ICONS[weatherIconVal[i]])
        windTextView.text = windVal.wind()
        humidTextView.text = humidVal.humidity()
        var day = dayOfWeekVal
        for (i in 0..4) {
            dayOfWeekTextView[i].text = day
            day = nextDayOfWeek(day)
        }
    }



    private fun Float.toIconID() : Int {
        // https://openweathermap.org/weather-conditions
        return when ((this / 100).toInt()) {
            2, 3, 5 -> RAIN
            6 -> SNOW
            7, 8 -> if (this == 800f) SUNNY else CLOUDS
            else -> SUNNY
        }
    }



    private fun getDayOfWeek() : String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        return SimpleDateFormat("EE", Locale.ENGLISH).format(date.time)
    }

    private fun nextDayOfWeek(cur : String) : String {
        return when (cur) {
            MON -> TUE
            TUE -> WED
            WED -> THU
            THU -> FRI
            FRI -> SAT
            SAT -> SUN
            SUN -> MON
            else -> "ERR"
        }
    }



    private fun Int.degrees() = "$this°C"
    private fun Int.wind() = "$this m/s"
    private fun Int.humidity() = "$this%"

    private fun Float.toCelsius() = (this + ABSOLUTE_ZERO).toInt()



    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"
        const val CITY = "Saint Petersburg, RU"
        const val APP_ID = "5bd3d63bdca9c76af9c3ecbd425b470a"

        const val ABSOLUTE_ZERO = -273.15f

        const val SUNNY = 0
        const val CLOUDS = 1
        const val RAIN = 2
        const val SNOW = 3

        val ICONS = arrayOf(R.drawable.ic_sunny, R.drawable.ic_cloud, R.drawable.ic_rain,
            R.drawable.ic_snow)

        const val WEATHER_VAL_KEY = "WEATHER_VAL"
        const val WEATHER_ICONS_KEY = "WEATHER_ICON"
        const val WIND_KEY = "WIND"
        const val HUMID_KEY = "HUMIDITY"
        const val DAY_OF_WEEK_KEY = "DAY_OF_WEEK"

        const val MON = "Mon"
        const val TUE = "Tue"
        const val WED = "Wed"
        const val THU = "Thu"
        const val FRI = "Fri"
        const val SAT = "Sat"
        const val SUN = "Sun"
    }

}