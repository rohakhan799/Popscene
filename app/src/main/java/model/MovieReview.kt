package model

import java.util.*

class MovieReview {
    var authorId=Int
    var authorName=String
    var reviewContent=String
    var reviewHead=String
    var id=String
    var url= String
    val likeCount=Int
    val dislikeCount=Int
    lateinit var reviewDate: Calendar

}
