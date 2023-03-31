# Farmer Vision 👨‍🌾👁 - Google Solution Challenge 2023
This project aims to protect farmer's crops from damage and destruction by keeping a look out for any intruding cattle or livestock. This project is a collaboration among the members of <a href="https://www.linkedin.com/company/developer-student-clubs-bbdnitm1/" target="_blank" rel="noreferrer">GDSC BBDITM</a>: <a href="https://github.com/harisj58/" target="_blank" rel="noreferrer">Haris Javed</a>, <a href="https://github.com/abdullahnizami77" target="_blank" rel="noreferrer">Abdullah Nizami</a> and Anurag Pathak.

## Project Details

### Tech Stack:
Android Studio with Kotlin, Firebase, TensorFlow
<p align="left">
<a href="https://developer.android.com/studio" target="_blank" rel="noreferrer"> <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Android_Studio_Icon_3.6.svg/512px-Android_Studio_Icon_3.6.svg.png?20210301045217" alt="android_studio" width="40" height="40"/> </a><a href="https://kotlinlang.org/" target="_blank" rel="noreferrer"> <img src="https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin_Icon.png" alt="kotlin" width="35" height="40"/> </a><a href="https://firebase.google.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/firebase/firebase-icon.svg" alt="firebase" width="40" height="40"/> </a><a href="https://www.tensorflow.org/" target="_blank" rel="noreferrer"> <img src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/google-tensorflow-icon.svg" alt="tensorflow" width="40" height="40"/> </a></p>

### Description:

* In recent times, the unchecked growth of stray cattle roaming about is causing menace for people. In India, especially in the state of Uttar Pradesh, there are about 1.18 million of them as per a census in 2019. Often times, these stray cattle wander into designated farmlands, wreaking havoc on the sown crops. In turn, this results in a catastrophic loss for the farmers, many of whom are dependent on the next harvest itself to make a living.

* Keeping this in mind, we the members of the GDSC BBDITM have come up with a solution Farmer Vision. Farmer Vision is an Android application, that runs on one of two devices - a camera device and a client device. The camera device collects video feed from the back camera and is intended to be placed in such a way to obtain maximum coverage of the farm area. This footage is processed by our own TensorFlow Lite model, to detect any stray animal in sight.

* The status of all camera devices is updated and stored using the Firebase Realtime Database, thereby making sure that all events are monitored in real time. Another device can be used as a client device using which the farmer can see the status of all cameras in real time. Using this information, he can take appropriate action such as removing the detected cattle and reporting to the concerned authorities about it.

* To begin, the farmer has to sign in with his Google account on one or more camera devices. On camera devices, after sign in, simply select "Camera Device" to configure the device as a camera. Then, he must sign in on another device, acting as a "Client Device", with the same Google account. With this, the set up is complete and he can now monitor his fields from home.

This application should be run on a physical Android device.

## Build the demo using Android Studio

### Prerequisites

*   The **[Android Studio](https://developer.android.com/studio/index.html)**
    IDE.

*   A physical Android device with a minimum OS version of SDK 24 (Android 7.0 -
    Nougat) with developer mode enabled. The process of enabling developer mode
    may vary by device. This device will also need to have the latest version of
    Google Play Services installed.

### Building

*   Open Android Studio. From the Welcome screen, select Open an existing
    Android Studio project.

*   From the Open File or Project window that appears, navigate to the repository
    directory. Click OK.

*   If it asks you to do a Gradle Sync, click OK.

*   With your Android device connected to your computer and developer mode
    enabled, click on the green Run arrow in Android Studio.
