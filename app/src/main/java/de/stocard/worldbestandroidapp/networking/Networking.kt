package de.stocard.worldbestandroidapp.networking

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.stocard.worldbestandroidapp.domain.Place
import de.stocard.worldbestandroidapp.domain.PlaceDetail
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resumeWithException

object Networking {

    private val client by lazy { OkHttpClient.Builder().build() }

    suspend fun searchPlace(searchString: String): List<Place> {
        val request = Request.Builder()
            .url("https://www.metaweather.com/api/location/search/?query=" + searchString)
            .build()
        return client.newCall(request).await().let { response ->
            when (val code = response.code()) {
                in 200..299 -> parseSearchResultData(
                    response.body()!!
                )
                else -> throw NullPointerException("unexpected response code $code")
            }
        }
    }

    suspend fun getWeather(woeid: Int): PlaceDetail {
        Log.e("Networking", "getWeather")
        val request = Request.Builder()
            .url("https://www.metaweather.com/api/location/" + woeid)
            .build()
        return client.newCall(request).await().let { response ->
            Log.e("Networking", "getWeather response ${response.code()}")
            when (val code = response.code()) {
                in 200..299 -> parseWeatherData(
                    response.body()!!
                )
                else -> throw NullPointerException("unexpected response code $code")
            }
        }
    }

    private fun parseSearchResultData(body: ResponseBody): List<Place> {
        val type = object : TypeToken<List<Place>>() {}.type
        return Gson().fromJson(body.string(), type)
    }

    private fun parseWeatherData(body: ResponseBody): PlaceDetail {
        return Gson().fromJson(body.string(), PlaceDetail::class.java)
    }

    private suspend fun Call.await(): Response {
        return suspendCancellableCoroutine { continuation ->
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response) { response.close() }
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }
            })
            continuation.invokeOnCancellation { cancel() }
        }
    }
}