package com.oaso.movie_series_rappi.di

import android.app.Application
import androidx.room.Room
import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovieDataBase
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovieDataBase
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.model.server.TheMovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application) = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application): PopularMovieDataBase = Room.databaseBuilder(
        app,
        PopularMovieDataBase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun databaseRatedProvider(app: Application): RatedMovieDataBase = Room.databaseBuilder(
        app,
        RatedMovieDataBase::class.java,
        "rated-movie-db"
    ).build()

    @Singleton
    @Provides
    fun providesOkHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesService(retrofit: Retrofit): TheMovieDbService =
        retrofit.create(TheMovieDbService::class.java)


    @Singleton
    @Provides
    fun providesRepository(theMovieDbService: TheMovieDbService, db: PopularMovieDataBase, ratedDb : RatedMovieDataBase) =
        MoviesRepository(theMovieDbService, db, ratedDb)
}