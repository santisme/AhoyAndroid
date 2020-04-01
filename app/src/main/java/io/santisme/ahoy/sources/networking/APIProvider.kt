package io.santisme.ahoy.sources.networking

import io.santisme.ahoy.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIProvider {

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl("https://${BuildConfig.DiscourseDomain}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val original = chain.request()

                val updatedRequest = original.newBuilder()
                    .header(name = "Api-Key", value = BuildConfig.DiscourseApiKey)
                    .method(method = original.method, body = original.body)
                    .build()

                return chain.proceed(updatedRequest)

            }

        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }
}
