#include "SoundGenerator.h"

SoundGenerator::SoundGenerator(int32_t sampleRate) : mOscillators(
        std::make_unique<Oscillator[]>(kKeyNum)
) {
    double baseFrequency = 440.0;
    constexpr float amplitude = 2.0;

    // Set up the oscillators
    for (int i = 0; i < kKeyNum; ++i) {
        int32_t key = i + kStartKey;
        double frequency = baseFrequency * pow(2, (key - kBaseKey) / 12.0);
        mOscillators[i].setFrequency(frequency);
        mOscillators[i].setSampleRate(sampleRate);
        mOscillators[i].setAmplitude(amplitude);
    }
}

void SoundGenerator::renderAudio(float *audioData, int32_t numFrames) {
    std::fill_n(mBuffer.get(), kSharedBufferSize, 0);
    std::fill_n(audioData, numFrames, 0);
    for (int i = 0; i < kKeyNum; ++i) {
        mOscillators[i].renderAudio(mBuffer.get(), numFrames);
        for (int j = 0; j < numFrames; ++j) {
            audioData[j] += mBuffer[j];
        }
    }
}

void SoundGenerator::tap(int32_t key, bool isOn) {
    mOscillators[key - kStartKey].setWaveOn(isOn);
}