package com.example.home_hackathon.repository

import com.example.home_hackathon.pb.App
import com.example.home_hackathon.pb.App.Event
import com.example.home_hackathon.pb.AppServiceGrpcKt.AppServiceCoroutineStub
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val api: AppServiceCoroutineStub
) {
    fun event(input: Flow<Event>): Flow<Event> {
        val request = input.map {
            App.EventRequest.newBuilder()
                .setEvent(it)
                .build()
        }
        val response = api.event(request)
        return response.map { it.event }
    }
}
