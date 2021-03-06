package com.example.home_hackathon.network.di

import com.example.home_hackathon.pb.AppServiceGrpcKt.AppServiceCoroutineStub
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import org.koin.dsl.module

private const val HOST = "35.194.96.19"
private const val PORT = 8080

val networkModule = module {
    single<Channel> {
        ManagedChannelBuilder
            .forAddress(HOST, PORT)
            .usePlaintext()
            .build()
    }

    single {
        AppServiceCoroutineStub(get())
    }
}
