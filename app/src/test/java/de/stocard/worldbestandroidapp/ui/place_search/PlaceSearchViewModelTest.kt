package de.stocard.worldbestandroidapp.ui.place_search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.stocard.worldbestandroidapp.domain.Place
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PlaceSearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testNoSearchInput() {
        val searchTerm = ""
        val expectedPlaces = emptyList<Place>()

        val viewModel = PlaceSearchViewModel()
        viewModel.searchInputChanged(searchTerm)

        viewModel.placesList.observeForever { }
        Thread.sleep(750)
        val actualPlaces = viewModel.placesList.value

        Assert.assertEquals(expectedPlaces, actualPlaces)
    }

    @Test
    fun testSearchInput() {
        val searchTerm = "London"
        val expectedPlaces = listOf(
            Place(
                title = "London",
                location_type = "City",
                woeid = 44418,
                latt_long = "51.506321,-0.12714"
            )
        )

        val viewModel = PlaceSearchViewModel()
        viewModel.searchInputChanged(searchTerm)

        viewModel.placesList.observeForever { }
        Thread.sleep(750)
        val actualPlaces = viewModel.placesList.value

        Assert.assertEquals(expectedPlaces, actualPlaces)
    }

}