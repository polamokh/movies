package me.polamokh.movies.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import me.polamokh.movies.BuildConfig
import me.polamokh.movies.domain.DetailedMovie
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/now_playing?api_key=${TMDB_API_KEY}")
    suspend fun getNowPlaying(@Query("page") page: Int): ResponseDTO

    @GET("movie/top_rated?api_key=${TMDB_API_KEY}")
    suspend fun getTopRated(@Query("page") page: Int): ResponseDTO

    @GET("search/movie?api_key=${TMDB_API_KEY}")
    suspend fun search(@Query("query") query: String, @Query("page") page: Int): ResponseDTO

    @GET("movie/{movie_id}?api_key=${TMDB_API_KEY}")
    fun getDetailsAsync(@Path("movie_id") movieId: Int): Deferred<DetailedMovie>

    companion object {
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        private const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY

        fun create(): TMDBService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(TMDB_BASE_URL)
                .build()
                .create(TMDBService::class.java)
        }
    }
}