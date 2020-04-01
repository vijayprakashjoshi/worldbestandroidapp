package de.stocard.worldbestandroidapp.domain

data class WeatherEntry(
    val id: Long,
    val applicable_date: String,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_speed: Float,
    val wind_direction: Float,
    val wind_direction_compass: String,
    val min_temp: Float,
    val max_temp: Float,
    val the_temp: Float,
    val air_pressure: Float,
    val humidity: Float,
    val visibility: Float,
    val predictability: Int
)