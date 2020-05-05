package com.example.home_hackathon.repository

import com.example.home_hackathon.model.Event
import com.example.home_hackathon.model.Sound
import com.example.home_hackathon.pb.App
import com.example.home_hackathon.pb.AppServiceGrpcKt.AppServiceCoroutineStub
import com.example.home_hackathon.repository.mapper.toModel
import com.example.home_hackathon.repository.mapper.toProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class EventRepository(
    private val api: AppServiceCoroutineStub
) {
    fun event(input: Flow<Sound>): Flow<Event> {
        val request = input.map {
            App.EventRequest.newBuilder()
                .setSound(it.toProto())
                .build()
        }
        val response = api.event(request)
        return response.mapNotNull {
            when (it.eventOneofCase) {
                App.EventResponse.EventOneofCase.SOUNDEVENT -> it.soundEvent.toModel()
                App.EventResponse.EventOneofCase.USEREVENT -> it.userEvent.toModel()
                else -> null
            }
        }
    }
}
