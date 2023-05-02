class Config {

    static applicationId = 'com.tongsr.eyepetizer'
    static namespace = 'com.tongsr.eyepetizer'
    static appName = 'Eyepetizer'

    static compileSdkVersion = 33
    static minSdkVersion = 24
    static targetSdkVersion = 33
    static versionCode = 1000
    static versionName = '1.0.0.0'

    // libs 的版本号
    static routerVersion = "1.1.2"
    static retrofitVersion = "2.9.0"
    static coroutinesVersion = "1.6.4"
    static androidxVersion = "2.6.1"
    static navVersion = "2.5.3"
    static roomVersion = "2.5.0"
    static mojitoVersion = "1.8.7"
    static pictureSelectorVersion = "v3.10.9"
    static coilVersion = "2.3.0"
    static paging3version = "3.1.1"

    static modules = [
            /*Don't delete this line*/
            /*Generated by "module_config.json"*/
            feature_launcher_app       : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/launcher/app"),
            feature_eyepetizer_app     : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/eyepetizer/app"),
            feature_main_pkg           : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/main/pkg"),
            feature_main_export        : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/main/export"),
            feature_home_pkg           : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/home/pkg"),
            feature_home_export        : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/home/export"),
            feature_square_pkg         : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/square/pkg"),
            feature_square_export      : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/square/export"),
            feature_found_pkg          : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/found/pkg"),
            feature_found_export       : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/found/export"),
            feature_user_pkg           : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/user/pkg"),
            feature_user_export        : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/user/export"),
            feature_test_app           : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/test/app"),
            feature_test_pkg           : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/test/pkg"),
            feature_test_export        : new ModuleConfig(isApply: true , useLocal: true , localPath: "./feature/test/export"),
            lib_core                   : new ModuleConfig(isApply: true , useLocal: true , localPath: "./lib/core"),
            lib_base                   : new ModuleConfig(isApply: true , useLocal: true , localPath: "./lib/base"),
            lib_common                 : new ModuleConfig(isApply: true , useLocal: true , localPath: "./lib/common"),
            lib_data                   : new ModuleConfig(isApply: true , useLocal: true , localPath: "./lib/data"),
            lib_router                 : new ModuleConfig(isApply: true , useLocal: true , localPath: "./lib/router"),
            /*Don't delete this line*/
    ]

    static plugins = [

    ]

    static libs = [
            androidxCore            : new LibConfig(path: "androidx.core:core-ktx:1.10.0"),
            activityKtx             : new LibConfig(path: "androidx.activity:activity-ktx:1.6.0"),
            fragmentKtx             : new LibConfig(path: "androidx.fragment:fragment-ktx:1.5.4"),
            lifecycleRuntimeKtx     : new LibConfig(path: "androidx.lifecycle:lifecycle-runtime-ktx:$androidxVersion"),
            lifecycleViewModelKtx   : new LibConfig(path: "androidx.lifecycle:lifecycle-viewmodel-ktx:$androidxVersion"),
            lifecycleLivedataKtx    : new LibConfig(path: "androidx.lifecycle:lifecycle-livedata-ktx:$androidxVersion"),
            lifecycleLivedataCodeKtx: new LibConfig(path: "androidx.lifecycle:lifecycle-livedata-core-ktx:$androidxVersion"),
            androidxAppcompat       : new LibConfig(path: "androidx.appcompat:appcompat:1.6.1"),
            // material风格UI
            androidxMaterial        : new LibConfig(path: "com.google.android.material:material:1.8.0"),
            constraintLayout        : new LibConfig(path: "androidx.constraintlayout:constraintlayout:2.1.4"),
            // 路由框架
            routerApi               : new LibConfig(path: "cn.therouter:router:$routerVersion"),
            routerCompiler          : new LibConfig(path: "cn.therouter:apt:$routerVersion"),
            // 网络
            retrofit                : new LibConfig(path: "com.squareup.retrofit2:retrofit:$retrofitVersion"),
            okhttp                  : new LibConfig(path: "com.squareup.okhttp3:okhttp:4.10.0"),
            retrofitGsonConverter   : new LibConfig(path: "com.squareup.retrofit2:converter-gson:$retrofitVersion"),
            retrofitMoshiConverter  : new LibConfig(path: "com.squareup.retrofit2:converter-moshi:2.9.0"),
            // 启动框架。TheRouter 有类似的功能
            startup                 : new LibConfig(path: "androidx.startup:startup-runtime:1.1.1"),
            // 存储数据
            datastore               : new LibConfig(path: "androidx.datastore:datastore-preferences:1.0.0"),
            // 分页
            paging3                 : new LibConfig(path: "androidx.paging:paging-runtime:$paging3version"),
            paging3ktx              : new LibConfig(path: "androidx.paging:paging-runtime-ktx:$paging3version"),
            // 导航栏
            navigation              : new LibConfig(path: "androidx.navigation:navigation-fragment-ktx:$navVersion"),
            navigationKtx           : new LibConfig(path: "androidx.navigation:navigation-ui-ktx:$navVersion"),
            // 有ConcatAdapter
            recyclerview            : new LibConfig(path: "androidx.recyclerview:recyclerview:1.3.0"),
            // 图片加载
            coil                    : new LibConfig(path: "io.coil-kt:coil:$coilVersion"),
            coilGif                 : new LibConfig(path: "io.coil-kt:coil-gif:$coilVersion"),
            coilSvg                 : new LibConfig(path: "io.coil-kt:coil-svg:$coilVersion"),
            coilVideo               : new LibConfig(path: "io.coil-kt:coil-video:$coilVersion"),
            // 协程
            coreCoroutines          : new LibConfig(path: "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"),
            androidCoroutines       : new LibConfig(path: "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"),
            gson                    : new LibConfig(path: "com.google.code.gson:gson:2.10.1"),
            // 代替 Gson，性能比 Gson 高
            moshi                   : new LibConfig(path: "com.squareup.moshi:moshi-kotlin:1.14.0"),
            // 工具集，已用源码的形式引入
            utils                   : new LibConfig(path: "com.blankj:utilcodex:1.31.1"),
            // 委托方式实现 ViewBinding
            viewBindingUtils        : new LibConfig(path: "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6"),
            // 用于线上的日志
            timber                  : new LibConfig(path: "com.jakewharton.timber:timber:5.0.1"),
            // RecyclerView
            brvah                   : new LibConfig(path: "io.github.cymchad:BaseRecyclerViewAdapterHelper:4.0.0-beta04"),
            // 数据库
            room                    : new LibConfig(path: "androidx.room:room-runtime:$roomVersion"),
            roomCompiler            : new LibConfig(path: "androidx.room:room-compiler:$roomVersion"),
            roomKtx                 : new LibConfig(path: "androidx.room:room-ktx:$roomVersion"),
            roomPaging              : new LibConfig(path: "androidx.room:room-paging:$roomVersion"),
            // 图片选择
            pictureSelector         : new LibConfig(path: "io.github.lucksiege:pictureselector:$pictureSelectorVersion"),
            pictureSelectorCompress : new LibConfig(path: "io.github.lucksiege:compress:$pictureSelectorVersion"),
            pictureSelectorUcrop    : new LibConfig(path: "io.github.lucksiege:ucrop:$pictureSelectorVersion"),
            pictureSelectorCamerax  : new LibConfig(path: "io.github.lucksiege:camerax:$pictureSelectorVersion"),
            // 查看大图
            mojito                  : new LibConfig(path: "com.github.mikaelzero.mojito:mojito:$mojitoVersion"),
            // support long image and gif with Sketch
            sketchImageViewLoader   : new LibConfig(path: "com.github.mikaelzero.mojito:SketchImageViewLoader:$mojitoVersion"),
            // 依赖注入
            hilt                    : new LibConfig(path: "com.google.dagger:hilt-android:2.44"),
            hiltCompiler            : new LibConfig(path: "com.google.dagger:hilt-compiler:2.44"),
            // mvi 架构
            mavericks               : new LibConfig(path: "com.airbnb.android:mavericks:3.0.2"),
            // leakcanary;debugImplementation引用
            leakcanary              : new LibConfig(path: "com.squareup.leakcanary:leakcanary-android:2.10"),
            // 异常上报
            bugly                   : new LibConfig(path: "com.tencent.bugly:crashreport:latest.release"),
    ]

}