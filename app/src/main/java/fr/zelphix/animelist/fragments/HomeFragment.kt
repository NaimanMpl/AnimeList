package fr.zelphix.animelist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.zelphix.animelist.AnimeModel
import fr.zelphix.animelist.AnimeRepository.Singleton.animesList
import fr.zelphix.animelist.MainActivity
import fr.zelphix.animelist.R
import fr.zelphix.animelist.adapter.AnimeAdapter
import fr.zelphix.animelist.adapter.AnimeItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // recuperer le recycler view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = AnimeAdapter(context, animesList.filter{ !it.liked }, R.layout.item_horizontal_anime)

        // recup le 2ns recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = AnimeAdapter(context, animesList, R.layout.item_vertical_anime)
        verticalRecyclerView.addItemDecoration(AnimeItemDecoration())

        return view
    }

}