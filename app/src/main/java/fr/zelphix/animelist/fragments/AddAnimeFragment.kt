package fr.zelphix.animelist.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.zelphix.animelist.AnimeModel
import fr.zelphix.animelist.AnimeRepository
import fr.zelphix.animelist.AnimeRepository.Singleton.downloadUri
import fr.zelphix.animelist.MainActivity
import fr.zelphix.animelist.R
import java.util.*

class AddAnimeFragment(
    private val context : MainActivity
) : Fragment() {

    private var file : Uri? = null
    private var uploadedImage : ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_anime, container, false)

        uploadedImage = view.findViewById(R.id.preview_image)

        // Récuperer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        pickupImageButton.setOnClickListener { pickupImage() }

        // recuperer le btn confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        val repo = AnimeRepository()
        repo.uploadImage(file!!) {
            val animeName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val animeDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val category = view.findViewById<Spinner>(R.id.category_spinner).selectedItem.toString()
            val status = view.findViewById<Spinner>(R.id.status_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            val anime = AnimeModel(
                UUID.randomUUID().toString(),
                animeName,
                animeDescription,
                downloadImageUrl.toString(),
                category,
                status
            )

            repo.insertAnime(anime)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) return
            val file = data.data
            uploadedImage?.setImageURI(file)
        }
    }

}