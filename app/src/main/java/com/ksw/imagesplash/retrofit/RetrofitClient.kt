package com.ksw.imagesplash.retrofit

import android.util.Log
import com.ksw.imagesplash.util.API
import com.ksw.imagesplash.util.Constants.TAG
import com.ksw.imagesplash.util.isJsonArray
import com.ksw.imagesplash.util.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * Created by KSW on 2021-08-13
 */

// 싱글톤 메모리에 하나만 사용
object RetrofitClient {

    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "getClient: getClient()")


        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그 찍기위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(TAG, "log: called / message : $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }

        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        client.addInterceptor(loggingInterceptor)

        // 기본 파라미터 인터셉터 설정

        val baseParameterInterceptor: Interceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "intercept: called()")
                // 오리지날 리퀘스트
                val originalRequest = chain.request()

                // 쿼리 파라미터 추가
                val addedUrl =
                    originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID)
                        .build()
                val finalRequest =
                    originalRequest.newBuilder()
                        .url(addedUrl)
                        .method(originalRequest.method, originalRequest.body)
                        .build()

                return chain.proceed(finalRequest)
            }

        })

        // 위에서 설정한 기본파라미터 인터셉터를 okhttp 클라이언트에 추가
        client.addInterceptor(baseParameterInterceptor)

        // 커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        if (retrofitClient == null) {

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .client(client.build())
                .build()
        }

        return retrofitClient
    }

}