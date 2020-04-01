package de.stocard.worldbestandroidapp.ui.place_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.stocard.worldbestandroidapp.R
import de.stocard.worldbestandroidapp.domain.Place

class PlacesSearchAdapter(
    private val places: List<Place>,
    private val onPlaceSelected: (woeid: Int) -> Unit
) : RecyclerView.Adapter<PlacesSearchAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = places.size

    override fun getItemId(position: Int): Long {
        return places[position].woeid.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.name.text = place.title
        holder.woeid.text = "#${place.woeid}"
        holder.itemView.setOnClickListener { onPlaceSelected(place.woeid) }
        // Santa Cruz gets a cool surfer logo
        if (place.woeid == 2488853) {
            holder.icon.visibility = View.VISIBLE
            holder.icon.setImageResource(R.drawable.surfer)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.place_icon)
        val name: TextView = view.findViewById(R.id.place_name)
        val woeid: TextView = view.findViewById(R.id.place_woeid)
    }
}