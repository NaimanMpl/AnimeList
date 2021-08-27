package fr.zelphix.animelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.zelphix.animelist.fragments.AddAnimeFragment
import fr.zelphix.animelist.fragments.CollectionFragment
import fr.zelphix.animelist.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_anime_page -> {
                    loadFragment(AddAnimeFragment(this), R.string.add_anime_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        loadFragment(HomeFragment(this), R.string.home_page_title)
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        // load AnimeRepository
        val animeRepository = AnimeRepository()

        // actualiser le titre
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)


        animeRepository.updateData {
            // put the fragment on container (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}