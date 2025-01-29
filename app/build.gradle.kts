plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinKsp)
    id("kotlin-parcelize")

}

android {
    namespace = "com.trainee.project.swiggy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.trainee.project.swiggy"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("com.google.android.gms:play-services-location:21.3.0")

    implementation ("io.github.chaosleung:pinview:1.4.4")

    implementation ("com.hbb20:ccp:2.5.0")


    implementation ("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.2.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    ksp ("androidx.lifecycle:lifecycle-compiler:2.8.7")


    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")



// dependency for loading image from url
    implementation( "com.github.bumptech.glide:glide:4.11.0" )


    annotationProcessor ( "com.github.bumptech.glide:compiler:4.11.0" )

    implementation ("com.google.code.gson:gson:2.8.8")

    implementation ("androidx.appcompat:appcompat:1.6.1")

}