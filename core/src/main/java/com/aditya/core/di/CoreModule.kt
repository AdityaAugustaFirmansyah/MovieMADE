package com.aditya.core.di

import androidx.room.Room
import com.aditya.core.data.MovieRepository
import com.aditya.core.data.source.local.LocalDataSource
import com.aditya.core.data.source.local.room.DatabaseLocal
import com.aditya.core.data.source.remote.RemoteDataSource
import com.aditya.core.data.source.remote.network.RestApi
import com.aditya.core.domain.repository.IMovieRepository
import com.aditya.core.utils.AppExecutors
import com.aditya.core.BuildConfig
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreModule {
    val databaseModule = module {
        val pharse = SQLiteDatabase.getBytes("made".toCharArray())
        val factory = SupportFactory(pharse)
        factory { get<DatabaseLocal>().movieDao() }
        single { Room.databaseBuilder(androidContext(),
            DatabaseLocal::class.java,
            "movie.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build() }
    }

    val networkModule = module {
        single {
            val hostName = "https://developers.themoviedb.org/"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostName,"sha256/iG3RcySOPXk22XlLdaWhzd63hSWN6gdoJkuezAQ8pF4=")
                .build()
            OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .addInterceptor(Interceptor {
                    var request = it.request()
                    val url = request.url().newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build()
                    request = request.newBuilder().url(url).build()
                    return@Interceptor it.proceed(request)
                }).build()
        }
        single {
            val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            retrofit.create(RestApi::class.java) }
    }

    val repoModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IMovieRepository> { MovieRepository(get(),get(),get()) }
    }
}