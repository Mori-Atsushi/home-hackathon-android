#include <jni.h>
#include "AudioEngine.h"
#include <oboe/Oboe.h>

extern "C" {
JNIEXPORT jlong JNICALL
Java_com_example_home_1hackathon_ui_MainActivity_start(JNIEnv *env, jobject thiz) {
    AudioEngine *engine = new AudioEngine();
    engine->start();
    return reinterpret_cast<jlong>(engine);
}

JNIEXPORT void JNICALL
Java_com_example_home_1hackathon_ui_MainActivity_setToneOn(
        JNIEnv *env, jobject thiz,
        jlong engine_handle,
        jboolean is_tone_on) {
    AudioEngine *engine = reinterpret_cast<AudioEngine *>(engine_handle);
    engine->tap(is_tone_on);
}
}