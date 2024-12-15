#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_NativeLib_getPublicKey(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF("082f6426bdd8e2645731fbb226271bd6");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_NativeLib_getPrivateKey(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF("99f753cb33c0a32fd95e4cd9e7fce40b8a243d59");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_NativeLib_getBaseUrl(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF("https://gateway.marvel.com/v1/public/");
}
