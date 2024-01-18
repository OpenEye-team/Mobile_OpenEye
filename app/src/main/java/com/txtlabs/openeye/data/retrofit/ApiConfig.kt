package com.txtlabs.openeye.data.retrofit

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.orhanobut.hawk.Hawk
import com.txtlabs.openeye.data.response.LoginResponse
import com.txtlabs.openeye.utils.SharedPrefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object{
        fun getApiService(): ApiService {
            //Hawk.init().build()


            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val headerInterceptor = Interceptor { chain ->
                val request = chain.request()
                val loginData : LoginResponse? = Hawk.get(SharedPrefs.KEY_LOGIN)
                val newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer ${loginData?.loginToken ?: ""}")
                    .build()

                Log.d("api_key",loginData?.loginToken ?: "")

                chain.proceed(newRequest)
            }

            // Create a Gson instance
            val gson = Gson()

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://4qvfv2f9-5001.asse.devtunnels.ms")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiServiceWithoutToken(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://4qvfv2f9-5000.asse.devtunnels.ms")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}