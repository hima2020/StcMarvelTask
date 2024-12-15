# Minimum CMake version required
cmake_minimum_required(VERSION 3.22.1)
project(Marvel)
# Define the native library
add_library(
        native_lip # Name of the library
        SHARED     # Shared library
        native_lip.cpp) # Source file

# Link Android's log library
find_library(
        log-lib # Variable name for the log library
        log)    # Name of the library

# Link the log library to the native library
target_link_libraries(
        native_lip # Your library
        ${log-lib}) # Android log library