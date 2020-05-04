#ifndef HOME_HACKATHON_AUDIO_ENGINE_H
#define HOME_HACKATHON_AUDIO_ENGINE_H

#include <oboe/Oboe.h>
#include <memory>
#include <math.h>
#include "SoundGenerator.h"
#include "DefaultAudioStreamCallback.h"

class AudioEngine : public IRestartable {
public:
    AudioEngine();

    void start();

    void restart() override;

    void tap(int32_t key, bool isDown);

private:
    std::shared_ptr<SoundGenerator> mSoundGenerator;
};

#endif //HOME_HACKATHON_AUDIO_ENGINE_H
