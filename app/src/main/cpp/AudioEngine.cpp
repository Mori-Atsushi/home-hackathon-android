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
    mOscillator = std::make_shared<Oscillator>();
    mOscillator->setFrequency(880.0);
    mOscillator->setSampleRate(stream->getSampleRate());
    mOscillator->setAmplitude(0.5);
    callback->setSource(mOscillator);
    stream->start();
}

void AudioEngine::start() {
}

void AudioEngine::restart() {
}

void AudioEngine::tap(bool isDown) {
    mOscillator->setWaveOn(isDown);
}