#include "AudioEngine.h"

void AudioEngine::start() {
    oboe::AudioStreamBuilder builder;

    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);

    oboe::AudioStream *stream = nullptr;
    oboe::Result result = builder.openStream(&stream);
}