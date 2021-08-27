package fr.zelphix.animelist.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.zelphix.animelist.*

class AnimeAdapter(
    val context: MainActivity,
    private val animeList : List<AnimeModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>(){

    // Boite pour ranger tout les composants à contrôler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // image de la plante
        val animeImage = view.findViewById<ImageView>(R.id.item_image)
        val animeName:TextView? = view.findViewById(R.id.item_name)
        val animeDescription:TextView? = view.findViewById(R.id.item_description)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Recuperer les infos des animes
        val currentAnime = animeList[position]

        val repo = AnimeRepository()

        // utiliser Glide pour recup l'img a partir de son lien
        Glide.with(context).load(Uri.parse(currentAnime.imageUrl)).into(holder.animeImage)

        holder.animeName?.text = currentAnime.name
        holder.animeDescription?.text = currentAnime.description

        if (currentAnime.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }
        holder.starIcon.setOnClickListener {
            currentAnime.liked = !currentAnime.liked
            repo.updateAnime(currentAnime)
        }

        // Interation lors du clic sur un anime
        holder.itemView.setOnClickListener {
            AnimePopup(this, currentAnime).show()
        }
    }

    override fun getItemCount(): Int = animeList.size

}