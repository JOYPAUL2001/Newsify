package com.example.newsify.api

import com.example.newsify.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        //This code defines a class named RetrofitInstance that likely helps create a Retrofit client for making network requests in an Android or Java application. Let's break it down:
        //
        //companion object: This is a special type of object declaration in Kotlin that allows you to define functions and properties that can be accessed directly from the class name, without needing to instantiate the class itself.
        //
        //private val retrofit by lazy { ... }: This line declares a private property named retrofit of type Retrofit. The by lazy keyword is used to initialize the property only when it's accessed for the first time. This improves performance as the Retrofit object creation can be expensive.
        //
        //Inside the curly braces ({}), the code defines how the retrofit object is created:
        //
        //HttpLoggingInterceptor: An instance of HttpLoggingInterceptor is created. This interceptor logs HTTP request and response information to the console, which is helpful for debugging network calls.
        //Logging Level: The logging level is set to BODY using setLevel(HttpLoggingInterceptor.Level.BODY). This will log the entire request and response body content.
        //OkHttpClient: An OkHttpClient builder is used to create a custom HTTP client. The addInterceptor(logging) line adds the previously created logging interceptor to the client. This ensures all requests and responses are logged.
        //Retrofit Builder: A Retrofit.Builder is used to configure the Retrofit client.
        //baseUrl(BASE_URL): Sets the base URL for all API requests. BASE_URL is likely defined elsewhere and holds the actual URL of your web service.
        //addConverterFactory(GsonConverterFactory.create()): This line adds a converter factory that tells Retrofit how to convert between JSON data and Java objects. Here, GsonConverterFactory is used, indicating Gson library will be used for this conversion.
        //client(client): This sets the custom OkHttpClient created earlier with logging enabled.
        //Build Retrofit: Finally, the build() method is called on the Retrofit.Builder to create the actual Retrofit object.
        //In summary, this code snippet creates a Retrofit client with logging enabled and Gson configured for JSON conversion. This client can then be used to make network requests to your web service in a clean and type-safe manner.

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}