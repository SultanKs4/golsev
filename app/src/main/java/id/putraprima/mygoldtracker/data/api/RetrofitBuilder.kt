package id.putraprima.mygoldtracker.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    @Volatile
    private var BUILDER: Retrofit? = null
    private const val BASE_URL = "https://www.tokopedia.com/emas/api/gold/"

    fun build(): Retrofit {
        return BUILDER ?: synchronized(this) {
            val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            BUILDER = builder
            builder
        }
    }
}