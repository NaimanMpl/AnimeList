package fr.zelphix.animelist

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.zelphix.animelist.adapter.AnimeAdapter

class AnimePopup(
    private val adapter: AnimeAdapter,
    private val currentAnime: AnimeModel
    ) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_anime_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button : ImageView) {
        if (currentAnime.liked) {
            button.setImageResource(R.drawable.ic_star)
        } else {
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        val starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)
        starButton.setOnClickListener {
            currentAnime.liked = !currentAnime.liked
            val repo = AnimeRepository()
            repo.updateAnime(currentAnime)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            val repo = AnimeRepository()
            repo.deleteAnime(currentAnime)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener { dismiss() }
    }

    private fun setupComponents() {
        // actualiser l'img
        val animeImage = findViewById<ImageView>(R.id.item_image)
        Glide.with(adapter.context).load(Uri.parse(currentAnime.imageUrl)).into(animeImage)
        findViewById<TextView>(R.id.popup_anime_name).text = currentAnime.name
        findViewById<TextView>(R.id.popup_anime_description_subtitle).text = currentAnime.description
        findViewById<TextView>(R.id.popup_anime_category_subtitle).text = currentAnime.category
        findViewById<TextView>(R.id.popup_anime_status_subtitle).text = currentAnime.status
    }

}