#include <jni.h>
#include "AudioEngine.h"
#include <oboe/Oboe.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_example_home_1hackathon_ui_MainActivity_start(JNIEnv *env, jobject thiz) {
    AudioEngine *engine = new(std::nothrow) AudioEngine();
    engine->start();
}