plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.passcrypter"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.passcrypter"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)

        implementation ("androidx.recyclerview:recyclerview:1.4.0")
        // For control over item selection of both touch and mouse driven selection
        implementation ("androidx.recyclerview:recyclerview-selection:1.2.0")
        implementation("androidx.biometric:biometric:1.2.0-alpha05")
    val room_version = "2.6.1" // Use a recent version of Room



    implementation ("androidx.room:room-runtime:$room_version")
    implementation(libs.support.annotations)
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}