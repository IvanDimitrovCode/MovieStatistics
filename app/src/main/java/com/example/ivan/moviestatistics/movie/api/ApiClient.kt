package com.example.ivan.moviestatistics.movie.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiClient {
    companion object {
        private const val baseUrl = "https://api.trakt.tv"
        const val API_KEY = "019a13b1881ae971f91295efc7fdecfa48b32c2a69fe6dd03180ff59289452b8"
        private var retrofit: ApiInterface? = null

        fun getClient(): ApiInterface? {
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor())
                    .build()

                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)
            }
            return retrofit
        }
    }

    private class AuthorizationInterceptor : Interceptor {
        val apiVersion = "2"
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val builder = original.newBuilder()
            val queryUrl = original.url().newBuilder()
                .addQueryParameter("extended", "full,images")
                .build()
            builder.url(queryUrl)
                .addHeader("Accept", "application/json")
                .addHeader("trakt-api-version", apiVersion)
                .addHeader("trakt-api-key", API_KEY)
                .method(original.method(), original.body())
                .build()

            return chain.proceed(builder.build())
        }
    }
}