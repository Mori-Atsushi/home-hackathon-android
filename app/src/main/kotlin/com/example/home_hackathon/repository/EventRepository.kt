package com.example.home_hackathon.repository

import android.util.Log
import com.example.home_hackathon.pb.App
import com.example.home_hackathon.pb.AppServiceGrpcKt.AppServiceCoroutineStub

class EventRepository(
    private val api: AppServiceCoroutineStub
) {
    suspend fun ping() {
        val request = App.PingRequest.newBuilder().build()
        val response = api.ping(request)
        Log.d("TAG", response.toString())
    }
}