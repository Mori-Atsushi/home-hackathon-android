package com.example.home_hackathon.repository.mapper

import com.example.home_hackathon.model.Sound
import com.example.home_hackathon.pb.App

fun Sound.toProto(): App.Sound {
    return App.Sound.newBuilder()
        .setSoundID(soundID)
        .setIsDown(isDown)
        .build()
}

fun App.Sound.toModel(): Sound {
    return Sound(
        soundID = soundID,
        isDown = isDown
    )
}
