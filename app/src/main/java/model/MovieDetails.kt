package model

import java.util.*

class MovieDetails {
    val movieId=Int
    var movieTitle=String
    lateinit var releaseYear:Calendar
    val movieRating=Int
    var movieTime=String
    var voteCount=Int
    lateinit var imageList: List<Int>
    lateinit var movieCast:List<String>
    var movieDesc=String
    var moviePlot=String
    val downloadMov=Boolean
    val playMovie=Boolean
}