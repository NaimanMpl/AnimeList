package fr.zelphix.animelist

class AnimeModel(
    val id: String = "anime0",
    val name: String = "One Piece",
    val description: String = "La grande aventure de Monkey D. Luffy",
    val imageUrl: String = "http://graven.yt/plante.jpg",
    val category: String = "Shônen",
    val status: String = "En cours de visionnage",
    var liked: Boolean = false
)