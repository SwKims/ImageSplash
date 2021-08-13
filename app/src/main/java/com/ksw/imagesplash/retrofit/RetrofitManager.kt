package com.ksw.imagesplash.retrofit

import com.google.gson.JsonElement
import com.ksw.imagesplash.util.API
import com.ksw.imagesplash.util.RESPONSE_STATE
import retrofit2.Call
import retrofit2.Response

/**
 * Created by KSW on 2021-08-13
 */

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // http 콜 생성
    // 레트로핏 인터페이스 가져오기
    private val retrofit: RetrofitInter? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInter::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit) {

        val term: String = searchTerm.let {
            it
        } ?: ""

        val call: Call<JsonElement> = retrofit?.searchPhotos(searchTerm = term).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, t.toString())
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                completion(RESPONSE_STATE.OKAY, response.body().toString())
            }

        })

    }


}