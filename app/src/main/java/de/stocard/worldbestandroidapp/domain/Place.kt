package de.stocard.worldbestandroidapp.domain

data class Place(
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String
)