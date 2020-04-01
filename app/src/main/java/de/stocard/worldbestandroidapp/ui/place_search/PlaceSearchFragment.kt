package de.stocard.worldbestandroidapp.ui.place_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.stocard.worldbestandroidapp.ui.Navigator
import de.stocard.worldbestandroidapp.R
import de.stocard.worldbestandroidapp.ui.util.AfterTextChangedListener

class PlaceSearchFragment(
    private val navigator: Navigator
) : Fragment() {

    lateinit var closeButton: ImageView
    lateinit var searchInput: EditText
    lateinit var searchResultRecyclerView: RecyclerView


    private lateinit var viewModel: PlaceSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PlaceSearchViewModel::class.java]
        viewModel.placesList.observe(this, Observer { places ->
            searchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            searchResultRecyclerView.adapter = PlacesSearchAdapter(places, this::placeSelected)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeButton = view.findViewById(R.id.close)
        searchInput = view.findViewById(R.id.search_input) as EditText
        searchResultRecyclerView = view.findViewById(R.id.search_results)

        closeButton.setOnClickListener { onCloseClicked() }

        searchInput.addTextChangedListener(AfterTextChangedListener { text ->
            viewModel.searchInputChanged(text)
        })
    }

    private fun onCloseClicked() {
        when (searchInput.text.isEmpty()) {
            true -> navigator.navigateBack()
            false -> {
                searchInput.setText("")
                searchInput.requestFocus()
            }
        }
    }

    private fun placeSelected(woeid: Int) {
        navigator.navigateTo(Navigator.NavigationTarget.PlaceDetail(woeid))
    }
}