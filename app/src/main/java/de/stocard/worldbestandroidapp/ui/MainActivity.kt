package de.stocard.worldbestandroidapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import de.stocard.worldbestandroidapp.R
import de.stocard.worldbestandroidapp.ui.placeDetails.PlaceDetailFragment
import de.stocard.worldbestandroidapp.ui.place_search.PlaceSearchFragment

class MainActivity : AppCompatActivity(),
    Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(Navigator.NavigationTarget.PlaceSearch)

        val background = resources.getDrawable(R.drawable.background)
        background.alpha = 75
        findViewById<View>(R.id.fragment_container).background = background

    }

    override fun navigateTo(target: Navigator.NavigationTarget) {
        if (target is Navigator.NavigationTarget.PlaceSearch) {
            showFragment(PlaceSearchFragment(this), "place_search")
        }
        if (target is Navigator.NavigationTarget.PlaceDetail) {
            showFragment(PlaceDetailFragment(this, target.woeid), "place_detail")
        }
    }

    override fun navigateBack() {
        val popped = supportFragmentManager.popBackStackImmediate()
        if (!popped) this.finish()
    }


    private fun showFragment(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, fragmentTag)
            .addToBackStack(fragmentTag)
            .commit()
    }

}
