#ifndef HOME_HACKATHON_AUDIO_ENGINE_H
#define HOME_HACKATHON_AUDIO_ENGINE_H

#include <oboe/Oboe.h>
#include <memory>
#include "Oscillator.h"
#include "DefaultAudioStreamCallback.h"

class AudioEngine : public IRestartable {
public:
    AudioEngine();

    void start();

    void restart() override;

    void tap(bool isDown);

private:
    std::shared_ptr<Oscillator> mOscillator;
};

#endif //HOME_HACKATHON_AUDIO_ENGINE_H
