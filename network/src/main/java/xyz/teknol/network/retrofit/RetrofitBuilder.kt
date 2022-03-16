package xyz.teknol.network.retrofit

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.teknol.network.adapter.NetworkResponseAdapterFactory

class RetrofitBuilder(val context: Context) {

    companion object {
        private const val BASE_URL = "http://15.206.152.5:8080/"
        private const val cacheSize = (5 * 1024 * 1024).toLong()
    }

    lateinit var okHttpClient: OkHttpClient

    private fun getRetrofit(): Retrofit {
        return if (::okHttpClient.isInitialized) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } else
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun enableCache(cacheSize: Long): RetrofitBuilder {
        val myCache = Cache(context.cacheDir, cacheSize)
        createClient(myCache)
        return this
    }

    fun enableCache(): RetrofitBuilder {
        val myCache = Cache(context.cacheDir, cacheSize)
        createClient(myCache)
        return this
    }

    private fun createClient(myCache: Cache) {
        okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request =
                    if (isInternetAvailable(context))
                        request.newBuilder()
                            .addHeader(
                                "Cache-Control",
                                "public, max-age=${60}"
                            )
                            .build()
                    else
                        request.newBuilder()
                            .addHeader(
                                "Cache-Control",
                                "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}"
                            )
                            .build()

                chain.proceed(request)
            }
            .build()
    }

    fun getApiService(): ApiService = getRetrofit().create(ApiService::class.java)

}