plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "pcs.technicaltest.robisaputra"
    compileSdk = 35

    defaultConfig {
        applicationId = "pcs.technicaltest.robisaputra"
        minSdk = 24
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":networks"))
    implementation(project(":BaseApplication"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.hilt.android)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.multidex)
    implementation(libs.github.bumptech.glide)
    implementation(libs.facebook.shimmer)
    implementation(libs.io.reactivex.rxjava)
    implementation(libs.io.reactivex.rxandroid)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.adapter.rxjava3)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.splashscreen)
    implementation(libs.jakewharton.threetenabp)

    ksp(libs.google.dagger.compiler)
    ksp(libs.google.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.android)
    testImplementation(libs.io.mockk)
    testImplementation(libs.androidx.test)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

hilt {
    enableAggregatingTask = false
}