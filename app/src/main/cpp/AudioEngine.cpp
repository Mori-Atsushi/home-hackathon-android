#include "AudioEngine.h"

AudioEngine::AudioEngine() {
    oboe::AudioStreamBuilder builder;
    DefaultAudioStreamCallback *callback = new DefaultAudioStreamCallback(*this);

    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setChannelCount(oboe::ChannelCount::Mono);
    builder.setCallback(callback);

    oboe::AudioStream *stream = nullptr;
    builder.openStream(&stream);
    mSoundGenerator = std::make_shared<SoundGenerator>(stream->getSampleRate());
    callback->setSource(mSoundGenerator);
    stream->start();
}

void AudioEngine::start() {
}

void AudioEngine::restart() {
}

void AudioEngine::tap(int32_t key, bool isDown) {
    mSoundGenerator->tap(key, isDown);
}