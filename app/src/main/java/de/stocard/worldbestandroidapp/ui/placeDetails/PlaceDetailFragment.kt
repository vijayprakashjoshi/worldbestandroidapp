package de.stocard.worldbestandroidapp.ui.placeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.stocard.worldbestandroidapp.ui.Navigator
import de.stocard.worldbestandroidapp.R
import de.stocard.worldbestandroidapp.domain.PlaceDetail

class PlaceDetailFragment(
    private val navigator: Navigator,
    private val woeid: Int
) : Fragment() {

    private lateinit var viewModel: PlaceDetailViewModel

    private val closeView: ImageView by lazy { requireView().findViewById<ImageView>(R.id.close) }
    private val placeTitleView: TextView by lazy { requireView().findViewById<TextView>(R.id.place_title) }
    private val placeWoeidView: TextView by lazy { requireView().findViewById<TextView>(R.id.place_woeid) }
    private val placeLatView: TextView by lazy { requireView().findViewById<TextView>(R.id.place_lat) }
    private val placeLngView: TextView by lazy { requireView().findViewById<TextView>(R.id.place_lng) }
    private val placeTempView: TextView by lazy { requireView().findViewById<TextView>(R.id.place_temp) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PlaceDetailViewModel::class.java]
        viewModel.init(woeid)
        viewModel.subscribe(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    fun setUiState(placeDetail: PlaceDetail) {

        val lat = placeDetail.latt_long.takeWhile { isNumberOrDigit(it) }
        val lng = placeDetail.latt_long.reversed().takeWhile { isNumberOrDigit(it) }.reversed()
        val current = placeDetail.consolidated_weather.first()
        val temperature = "${current.the_temp} °C [${current.min_temp} °C - ${current.max_temp} °C]"

        closeView.setOnClickListener { navigator.navigateBack() }

        placeTitleView.text = placeDetail.title
        placeWoeidView.text = "#${placeDetail.woeid}"
        placeLatView.text = lat
        placeLngView.text = lng
        placeTempView.text = temperature
    }

    private fun isNumberOrDigit(char: Char): Boolean = when (char) {
        in '0'..'9', '.' -> true
        else -> false
    }
}