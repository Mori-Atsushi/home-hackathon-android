#ifndef HOME_HACKATHON_SOUNDGENERATOR_H
#define HOME_HACKATHON_SOUNDGENERATOR_H

#include <oboe/Oboe.h>
#include <memory>
#include <math.h>
#include "Oscillator.h"
#include "IRenderableAudio.h"

class SoundGenerator : public IRenderableAudio {
    static constexpr size_t kSharedBufferSize = 1024;
    static constexpr int32_t kKeyNum = 104;
    static constexpr int32_t kStartKey = 24;
    static constexpr int32_t kBaseKey = 69;
public:
    SoundGenerator(int32_t sampleRate);

    void tap(int32_t key, bool isOn);

    void renderAudio(float *audioData, int32_t numFrames) override;

private:
    std::unique_ptr<Oscillator[]> mOscillators;
    std::unique_ptr<float[]> mBuffer = std::make_unique<float[]>(kSharedBufferSize);
};


#endif //HOME_HACKATHON_SOUNDGENERATOR_H
