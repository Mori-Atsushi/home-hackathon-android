#include <jni.h>
#include "AudioEngine.h"
#include <oboe/Oboe.h>

extern "C" {
JNIEXPORT jlong JNICALL
Java_com_example_home_1hackathon_audio_AudioEngine_start(JNIEnv *env, jobject thiz) {
    AudioEngine *engine = new AudioEngine();
    engine->start();
    return reinterpret_cast<jlong>(engine);
}
JNIEXPORT void JNICALL
Java_com_example_home_1hackathon_audio_AudioEngine_setToneOn(
        JNIEnv *env, jobject thiz,
        jlong engine_handle, jint key,
        jboolean is_tone_on) {
    AudioEngine *engine = reinterpret_cast<AudioEngine *>(engine_handle);
    engine->tap(key, is_tone_on);
}
}