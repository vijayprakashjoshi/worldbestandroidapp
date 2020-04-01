package de.stocard.worldbestandroidapp.ui.place_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.stocard.worldbestandroidapp.networking.Networking
import de.stocard.worldbestandroidapp.domain.Place
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceSearchViewModel : ViewModel() {

    val placesList: MutableLiveData<List<Place>> = MutableLiveData()

    fun searchInputChanged(searchString: String) {
        if (searchString.isEmpty()) {
            placesList.value = emptyList()
            return
        }
        GlobalScope.launch {
            val places = Networking.searchPlace(searchString)
            placesList.postValue(places)
        }
    }
}