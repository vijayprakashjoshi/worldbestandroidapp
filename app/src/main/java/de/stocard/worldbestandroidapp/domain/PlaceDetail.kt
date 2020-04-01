package de.stocard.worldbestandroidapp.domain

data class PlaceDetail(
    var title: String,
    var location_type: String,
    var woeid: Int,
    var latt_long: String,
    var timezone: String,
    var consolidated_weather: List<WeatherEntry>
)