package id.putraprima.mygoldtracker.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface GoldService {
    @GET("price")
    suspend fun getPriceToday(): Response<ResponseBody>

    @GET("price/history?format=month&limit=12")
    suspend fun getPriceHistory(): Response<ResponseBody>
}