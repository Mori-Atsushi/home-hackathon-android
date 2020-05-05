package com.example.home_hackathon.repository.mapper

import com.example.home_hackathon.model.Event
import com.example.home_hackathon.pb.App

fun App.SoundEvent.toModel(): Event.SoundEvent {
    return Event.SoundEvent(
        sound = sound.toModel(),
        userID = userID
    )
}

fun App.UserEvent.toModel(): Event.UserEvent {
    return Event.UserEvent(
        userIDs = userIDsList
    )
}