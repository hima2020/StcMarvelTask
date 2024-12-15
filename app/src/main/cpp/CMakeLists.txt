# Specify the minimum CMake version required
cmake_minimum_required(VERSION 3.22.1)

# Define the project name
project(Marvel)

# Define the native library (name must be consistent across CMake, Gradle, and System.loadLibrary())
add_library(
        native_lib # Name of the library (not native_lip)
        SHARED
        nativelip.cpp # Correct file name
)

# Locate the Android log library
find_library(
        log-lib
        log
)

# Link the Android log library to the native library
target_link_libraries(
        native_lib # Your library (must match System.loadLibrary("native_lib"))
        ${log-lib} # Link Android log library
)
