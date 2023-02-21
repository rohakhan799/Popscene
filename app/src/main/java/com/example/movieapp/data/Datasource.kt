package com.example.movieapp.data

import com.example.movieapp.database.MovieDetails

public class Datasource {

    public var mainList: List<String> = listOf<String>(
        "Watch a movie with the easiest way",
        "Watch on any device", "Watch the movie offline"
    )
    public var sublist: List<String> = listOf<String>(
        "Go ahead, explore favourite genre and watch the movie, chill and enjoy.",
        "Stream on your phone, tablet, laptop or smart tv in one package",
        "Download and enjoy. Save your data, watch offline on a unusual locations in the world"
    )

    fun loadMovieList():List<MovieDetails>{
        return listOf<MovieDetails>(
            MovieDetails(1,"The Lost City","IMDB 6.1/10","2h 54m","Action","A reclusive romance novelist on a book tour with her cover model gets swept up in a kidnapping attempt that lands them both in a cutthroat jungle adventure.","Matthew Broderick, Neve Campbell, Andy Dick","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3Tf8vXykYhzHdT0BtsYTp570JGQ.jpg"),
            MovieDetails(2,"Men (2022)","IMDB 6.3/10","1h 54m","Drama","A young woman goes on a solo vacation to the English countryside following the death of her ex-husband.","Nathan Lane, Ernie Sabella, Julie K","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ifHHYafnINVlRYQV8cVJFkd8Uuv.jpg"),
            MovieDetails(3,"The Bob's Burger Movie (2019)","IMDB 5.1/10","3h 54m","Comedy","The Belchers try to save the restaurant from closing as a sinkhole forms in front of it","Ernie Sabella, Julie K","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pSfwXxP94xktZKn3UaeVe6VdFZl.jpg"),
            MovieDetails(4,"Scream (2022)","IMDB 9.1/10","2h 12m","Comedy, Action","A reclusive romance novelist on a book tour with her cover model gets swept up in a kidnapping attempt that lands them both in a cutthroat jungle adventure.","Jeremy Irons, James Earl Jones","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voZdN8XKo1j9e5A44shYdxXKilE.jpg"),
            MovieDetails(5,"The Lion King2","IMDB 3.1/10","1h 4m","Adventure,Action","Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.","Donald Glover, Beyonce,Seth Rogen","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3Tf8vXykYhzHdT0BtsYTp570JGQ.jpg"),
            MovieDetails(6,"The Lion King2 (1998)","IMDB 3.1/10","3h 4m","Comedy, Action","Simba's daughter is the key to a resolution of a bitter feud.","Jeremy Irons,Nathan Lane","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/31vliI2mopLlh5kUoWpJZ19cF8y.jpg"),
            MovieDetails(7,"The Lion King3 (2004)","IMDB 4.1/10","1h 24m","Comedy,Drama","After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.","Nathan Lane,Matthew Broderick","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/a8hoEwfXv3mQWArB4HO6mowmZoe.jpg"),
            MovieDetails(8,"The Lion King (2019)","IMDB 5.1/10","2h 54m","Drama","Timon the meerkat and Pumbaa the warthog retell the story of The Lion King (1994) from their own perspective.","Nathan Lane, Ernie Sabella","https://www.themoviedb.org/t/p/original/neMZH82Stu91d3iqvLdNQfqPPyl.jpg"),
            MovieDetails(9,"The Lion King (1987)","IMDB 8.1/10","2h 34m","Action","After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.","Beyonce,Seth Rogen,Jeremy Irons","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9YlbfXh4kpnFpclA7CA2j1TTZew.jpg"),

        )
    }
    fun loadMovieListTrend():List<MovieDetails>{
        return listOf<MovieDetails>(
            MovieDetails(1,"The Lost City","IMDB 6.1/10","2h 54m","Action","A reclusive romance novelist on a book tour with her cover model gets swept up in a kidnapping attempt that lands them both in a cutthroat jungle adventure.","Matthew Broderick, Neve Campbell, Andy Dick","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3Tf8vXykYhzHdT0BtsYTp570JGQ.jpg"),
            MovieDetails(2,"Men (2022)","IMDB 6.3/10","1h 54m","Drama","A young woman goes on a solo vacation to the English countryside following the death of her ex-husband.","Nathan Lane, Ernie Sabella, Julie K","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ifHHYafnINVlRYQV8cVJFkd8Uuv.jpg"),
            MovieDetails(3,"The Bob's Burger Movie (2019)","IMDB 5.1/10","3h 54m","Comedy","The Belchers try to save the restaurant from closing as a sinkhole forms in front of it","Ernie Sabella, Julie K","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pSfwXxP94xktZKn3UaeVe6VdFZl.jpg"),
            MovieDetails(4,"Scream (2022)","IMDB 9.1/10","2h 12m","Comedy, Action","A reclusive romance novelist on a book tour with her cover model gets swept up in a kidnapping attempt that lands them both in a cutthroat jungle adventure.","Jeremy Irons, James Earl Jones","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voZdN8XKo1j9e5A44shYdxXKilE.jpg"),
            MovieDetails(5,"The Lion King2","IMDB 3.1/10","1h 4m","Adventure,Action","Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.","Donald Glover, Beyonce,Seth Rogen","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3Tf8vXykYhzHdT0BtsYTp570JGQ.jpg"),
            MovieDetails(6,"The Lion King2 (1998)","IMDB 3.1/10","3h 4m","Comedy, Action","Simba's daughter is the key to a resolution of a bitter feud.","Jeremy Irons,Nathan Lane","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/31vliI2mopLlh5kUoWpJZ19cF8y.jpg"),
            MovieDetails(7,"The Lion King3 (2004)","IMDB 4.1/10","1h 24m","Comedy,Drama","After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.","Nathan Lane,Matthew Broderick","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/a8hoEwfXv3mQWArB4HO6mowmZoe.jpg"),
            MovieDetails(8,"The Lion King (2019)","IMDB 5.1/10","2h 54m","Drama","Timon the meerkat and Pumbaa the warthog retell the story of The Lion King (1994) from their own perspective.","Nathan Lane, Ernie Sabella","https://www.themoviedb.org/t/p/original/neMZH82Stu91d3iqvLdNQfqPPyl.jpg"),
            MovieDetails(9,"The Lion King (1987)","IMDB 8.1/10","2h 34m","Action","After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.","Beyonce,Seth Rogen,Jeremy Irons","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9YlbfXh4kpnFpclA7CA2j1TTZew.jpg"),

            )
    }

}