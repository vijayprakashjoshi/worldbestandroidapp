package de.stocard.worldbestandroidapp.ui

interface Navigator {
    fun navigateTo(target: NavigationTarget)
    fun navigateBack()

    sealed class NavigationTarget {
        object PlaceSearch : NavigationTarget()
        data class PlaceDetail(var woeid: Int) : NavigationTarget()
    }
}