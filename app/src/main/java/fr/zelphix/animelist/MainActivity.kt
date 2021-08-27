package fr.zelphix.animelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.zelphix.animelist.fragments.AddAnimeFragment
import fr.zelphix.animelist.fragments.CollectionFragment
import fr.zelphix.animelist.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // load AnimeRepository
        val animeRepository = AnimeRepository()
        animeRepository.updateData {
            // put the fragment on container (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, AddAnimeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}