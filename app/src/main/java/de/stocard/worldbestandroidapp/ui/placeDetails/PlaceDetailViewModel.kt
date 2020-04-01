package de.stocard.worldbestandroidapp.ui.placeDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.stocard.worldbestandroidapp.networking.Networking
import de.stocard.worldbestandroidapp.domain.PlaceDetail
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceDetailViewModel : ViewModel() {

    private val woeidLiveData = MutableLiveData<Int>()
    private val state = Transformations.switchMap(woeidLiveData) { woeid: Int ->
        val result = MutableLiveData<PlaceDetail>()
        GlobalScope.launch { result.postValue(Networking.getWeather(woeid)) }
        result
    }

    fun init(woeid: Int) {
        this.woeidLiveData.value = woeid
    }

    fun subscribe(ui: PlaceDetailFragment) {
        state.observe(ui, Observer { ui.setUiState(it) })
    }
}