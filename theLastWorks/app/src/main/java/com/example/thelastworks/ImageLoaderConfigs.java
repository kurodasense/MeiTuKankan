package com.example.thelastworks;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderConfigs {
    public static ImageLoaderConfiguration getImageLoaderConfiguration(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                //内在缓存额外选项， 最大的宽度，高度
                //.memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                //.diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                //线程池配置
                //.taskExecutor()
                //.taskExecutorForCachedImages()
                //.threadPoolSize(3) // default  线程池内加载的数量
                //.threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                //任务处理优先级 Fist In Fist Out
                //.tasksProcessingOrder(QueueProcessingType.FIFO) // default
                //内存中不缓存一张图片的多个尺寸大小
                //.denyCacheImageMultipleSizesInMemory()
                //内在缓存策略
                //.memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                //内存缓存大小
                //.memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                //内在缓存大小：占用百分比
                .memoryCacheSizePercentage(13) // default
                //磁盘缓存策略
                //.diskCache(new LruDiskCache()) // default 可以自定义缓存路径
                //磁盘缓存大小
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                //.diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                //.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                //.imageDownloader(new BaseImageDownloader(context)) // default
                //(new BaseImageDecoder(false)) // default
                //加载具体图片时的一些配置
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build();
        return configuration;
    }
    public static DisplayImageOptions getDefaultDisplayImageOptions(Context context) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                //是否缓存
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //RGB 565   r红色占5   g绿色占6   b蓝色占5  -> 2字节
                //alpha
                //ARGB 4444    4 4 4 4  -> 2字节
                //ARGB 8888    -> 4字节
                //10 * 10 用rgb565 -> 10*10*2
                .bitmapConfig(Bitmap.Config.RGB_565)
                //加载时、加载错误时展示什么内容
                .showImageOnLoading(R.drawable.c_loading)
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                //加载效果
                //ctrl + p
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
        //ctrl + h
        //BitmapDisplayer;
        return displayImageOptions;
    }

}
