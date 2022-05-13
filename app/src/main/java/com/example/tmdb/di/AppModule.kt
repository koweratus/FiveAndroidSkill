package com.example.tmdb.di

import android.app.Application
import androidx.room.Room
import com.example.tmdb.data.local.TmdbDatabase
import com.example.tmdb.remote.TMDBApi
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.repository.TvRepository
import com.example.tmdb.utils.Constants.BASE_URL
import com.example.tmdb.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(htttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(htttpLoggingInterceptor)
            .callTimeout(
                15, TimeUnit.SECONDS
            )
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun providesTmdbApi(okHttpClient: OkHttpClient): TMDBApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    fun provideTmdbDatabase(application: Application): TmdbDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            TmdbDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build() // The reason we can construct a database for the repo
    }

    @Singleton
    @Provides
    fun provideTmdbDao(db: TmdbDatabase) = db.getTmdbDao() // The reason we can implement a Dao for the database

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(api: TMDBApi) = MoviesRepository(api)

    @Provides
    @Singleton
    fun provideTvSeriesRepository(api: TMDBApi) = TvRepository(api)
}
