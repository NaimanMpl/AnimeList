package fr.zelphix.animelist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.zelphix.animelist.AnimeRepository.Singleton.animesList
import fr.zelphix.animelist.MainActivity
import fr.zelphix.animelist.R
import fr.zelphix.animelist.adapter.AnimeAdapter
import fr.zelphix.animelist.adapter.AnimeItemDecoration

class CollectionFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        // Obtenir le recycler view
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = AnimeAdapter(context, animesList.filter { it.liked }, R.layout.item_vertical_anime)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(AnimeItemDecoration())

        return view
    }

}