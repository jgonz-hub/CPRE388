plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.cameraapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cameraapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


        // CameraX core library using the camera2 implementation

        // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation ("androidx.camera:camera-core:1.4.0-alpha02")
    implementation ("androidx.camera:camera-camera2:1.4.0-alpha02")
        // If you want to additionally use the CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha02")
        // If you want to additionally use the CameraX VideoCapture library
    implementation ("androidx.camera:camera-video:1.4.0-alpha02")
        // If you want to additionally use the CameraX View class
    implementation ("androidx.camera:camera-view:1.4.0-alpha02")
        // If you want to additionally add CameraX ML Kit Vision Integration
    implementation ("androidx.camera:camera-mlkit-vision:1.4.0-alpha02")
        // If you want to additionally use the CameraX Extensions library
    implementation ("androidx.camera:camera-extensions:1.4.0-alpha02")
    implementation ("com.google.mlkit:image-labeling:17.0.7")
    implementation ("com.google.android.gms:play-services-mlkit-image-labeling:16.0.8")
    implementation ("com.google.mlkit:object-detection:16.2.0")
    implementation ("com.google.mlkit:common:16.2.0")



}