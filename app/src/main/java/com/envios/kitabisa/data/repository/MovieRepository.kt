package com.envios.kitabisa.data.repository

import com.envios.kitabisa.BuildConfig
import com.envios.kitabisa.data.local.dao.FavoriteDao
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.data.remote.MovieAPI
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.data.remote.model.MovieDetail
import com.envios.kitabisa.data.remote.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieRepository(private val movieAPI: MovieAPI, val favoriteDao: FavoriteDao) {

     suspend fun getPopularMovies( page:String): List<Movie?>? {
        val response = movieAPI.getPopularMovieAsync(BuildConfig.API_KEY, "en-US", page).await()
         return response.body()?.results

    }

     suspend fun getTopRatedMovie( page:String): List<Movie?>? {
        val response = movieAPI.getTopRatedMovieAsync(BuildConfig.API_KEY, "en-US", page).await()
        return response.body()?.results
    }

    suspend fun getNowPlayingMovie( page:String): List<Movie?>? {
        val response = movieAPI.getNowPlayingMovieAsync(BuildConfig.API_KEY, "en-US", page).await()
        return response.body()?.results
    }

    suspend fun getMovieReviews( movie_id:String): List<Review?>? {
        val response = movieAPI.getReviewAsync(movie_id, BuildConfig.API_KEY).await()
        return response.body()?.results
    }

    suspend fun getMovieDetail( movie_id:String): MovieDetail?? {
        val response = movieAPI.getMovieDetailAsync(movie_id, BuildConfig.API_KEY).await()
        return response.body()
    }

    suspend fun insertFavoriteMovie(movieDetail: MovieDetail?) {

        withContext(Dispatchers.IO) {
            favoriteDao.insert(
                Favorite(
                    id = movieDetail?.id,
                    adult = movieDetail?.adult,
                    backdropPath = movieDetail?.backdropPath,
                    originalLanguage = movieDetail?.originalLanguage,
                    originalTitle = movieDetail?.originalTitle,
                    overview = movieDetail?.overview,
                    popularity = movieDetail?.popularity,
                    posterPath = movieDetail?.posterPath,
                    releaseDate = movieDetail?.releaseDate,
                    title = movieDetail?.title,
                    video = movieDetail?.video

                )
            )
        }

    }

    fun getAllUsersFromLocal(): List<Favorite?> {
        return favoriteDao.getAllFavorites()
    }

    fun checkIsFavorite(id:Int): Favorite? {
        return favoriteDao.checkIsFavoriteMovie(id)
    }

    suspend fun deleteFavoriteMovie(id:Int) {
        withContext(Dispatchers.IO) {
            favoriteDao.delete(id)
        }
    }
}