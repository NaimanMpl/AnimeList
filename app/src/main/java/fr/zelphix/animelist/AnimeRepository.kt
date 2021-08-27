package fr.zelphix.animelist

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.zelphix.animelist.AnimeRepository.Singleton.animesList
import fr.zelphix.animelist.AnimeRepository.Singleton.databaseRef
import fr.zelphix.animelist.AnimeRepository.Singleton.storageReference
import java.net.URI
import java.util.*

class AnimeRepository {

    object Singleton {

        private val BUCKET_URL: String = "gs://anime-list-d995b.appspot.com"

        // Connextion à l'espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Connexion à la référence "animes" de la BDD
        private val database = FirebaseDatabase.getInstance("https://anime-list-d995b-default-rtdb.firebaseio.com/")
        val databaseRef = database.getReference("animes")

        // Création de la liste d'animes
        val animesList = arrayListOf<AnimeModel>()
    }

    fun updateData(callback: () -> Unit) {
        // Importer les données de la BDD pour les donner à la liste d'animes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                animesList.clear()
                // Récolter la liste
                for (ds in snapshot.children) {
                    // Construction de l'objet AnimeModel
                    val anime = ds.getValue(AnimeModel::class.java)
                    if (anime != null) {
                        animesList.add(anime)
                    }
                }
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {} // Au cas ou il ne parvient a rien recuperer

        })
    }

    // fonction pour envoyer des fichers ds la bdd
    fun uploadImage(file: Uri) {
        if (file == null) return
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storageReference.child(fileName)
        val uploadTask = ref.putFile(file)

        // demarrer la tache d'envoi du fichier
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<URI>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            return@Continuation ref.downloadUrl
        })
    }

    // mettre a jour l'objet Anime en BDD
    fun updateAnime(anime: AnimeModel) = databaseRef.child(anime.id).setValue(anime)

    fun deleteAnime(anime : AnimeModel) = databaseRef.child(anime.id).removeValue()

}