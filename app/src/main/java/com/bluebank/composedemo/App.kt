package com.bluebank.composedemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initCoil()
    }

    // 生产级全局Coil配置：缓存、默认图、错误图
    private fun initCoil() {
        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_cache"))
                    .maxSizeBytes(1024 * 1024 * 100) // 100M
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}