# One

本软件使用 MVP 架构，OkHttp + Retrofit + RxJava + Glide 框架实现，界面遵循Google Material Design。


## 目录

* [Download](#Download)
* [Usage](#Usage)
* [Screenshot](#Screenshot)
* [About](#About)
* [License](#License)


## 开发环境

- JDK8
- Android Studio

## 项目搭建
1. 创建项目
2. 添加对Java 8 的支持（参考[Android Studio对于Java8特性的支持][1]）
3. 添加依赖库，依赖库详细说明见下
4. 插入版权信息（[Android Studio 配置 Copyright 插入版权信息][2]）

## 依赖库
1. [BottomNavigation：底部导航栏](https://github.com/Ashok-Varma/BottomNavigation)
2. [okhttp3 系列：网络请求]()
3. [retrofit2 系列：REST安卓客户端请求库]()
4. [gson：]()
5. [rxjava：响应式编程框架]()
6. [rxandroid：]()
7. [picasso：图片加载]()
8. []()

    implementation 'com.squareup.okio:okio:1.17.2'
    implementation "com.squareup.okhttp3:okhttp:3.14.2"
    implementation 'com.squareup.okhttp3:logging-interceptor:3.14.2'
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'


* Android广告图片轮播控件[https://github.com/youth5201314/banner]
* Android TabLayout依赖库[https://github.com/H07000223/FlycoTabLayout]
* 一个Java序列化/反序列化库，用于将Java对象转换为JSON并返回(https://github.com/google/gson)
* Android图片加载和缓存库(https://github.com/bumptech/glide)
* 功能强大且灵活的RecyclerAdapter(https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* 通过各种触摸手势实现支持缩放的ImageView(https://github.com/chrisbanes/PhotoView)
* Android智能下拉刷新框架(https://github.com/scwang90/SmartRefreshLayout)

//
//        ###### 网络框架 [retrofit](https://github.com/square/retrofit)  [okhttp](https://github.com/square/okhttp)
//        ###### 打印日志框架 [klog](https://github.com/ZhaoKaiQiang/KLog)
//        ###### 刷新框架 [SwipeToLoadLayout](https://github.com/Aspsine/SwipeToLoadLayout)
//        ###### 解析Html [jsoup](https://github.com/jhy/jsoup)
//        ###### 权限管理库 [AndPermission](https://github.com/yanzhenjie/AndPermission)
//        ###### 提示框  [material-dialogs](https://github.com/afollestad/material-dialogs)  [Android-SVProgressHUD](https://github.com/saiwu-bigkoo/Android-SVProgressHUD)
//        ###### RecycleView分割线 [RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
//        ###### ViewPager的标题控件 [smarttablayout](https://github.com/ogaclejapan/SmartTabLayout)
//        ###### 广告轮播控件 [SwitcherView](https://github.com/maning0303/SwitcherView)
//        ###### 收藏按钮 [ThumbUp](https://github.com/ldoublem/ThumbUp)
//        ###### 模糊控件 [Blurry](https://github.com/wasabeef/Blurry)
//        ###### 网络请求监控 [chuck](https://github.com/jgilfelt/chuck)
//        ###### 表格控件 [scrollablepanel](https://github.com/Kelin-Hong/ScrollablePanel)
//        ###### 可以展开的文字 [expandableTextView](https://github.com/Manabu-GT/ExpandableTextView)
//        ###### 自定义日历控件 [MNCalendar](https://github.com/maning0303/MNCalendar)
//        ###### 日志监听 [MNCrashMonitor](https://github.com/maning0303/MNCrashMonitor)
//        ###### 图片缩放 [PhotoView](https://github.com/chrisbanes/PhotoView)
//        ###### APK升级安装 [MNUpdateAPK](https://github.com/maning0303/MNUpdateAPK)
//        ###### 夜间模式 [MNChangeSkin](https://github.com/maning0303/MNChangeSkin)
//        ###### 图片浏览 [MNImageBrowser](https://github.com/maning0303/MNImageBrowser)
//        ###### 汉字转拼音 [TinyPinyin](https://github.com/promeG/TinyPinyin)
//        ###### 快速跳跃分组的侧边栏控件 [WaveSideBar](https://github.com/Solartisan/WaveSideBar)
//        ###### 背景可以移动的View [KenBurnsView](https://github.com/flavioarfaria/KenBurnsView)
//        ###### 圆形图片 [CircleImageView](https://github.com/hdodenhof/CircleImageView)
//        ###### 滚轮选择器 [Android-PickerView](https://github.com/Bigkoo/Android-PickerView)
//        ###### 图片选择器 [PictureSelector](https://github.com/LuckSiege/PictureSelector)

## 主要技术点
1. NavigationView 搭配 DrawerLayout 的具体使用
2. TabLayout 和 ViewPager 结合的具体使用
3. SwipeRefreshLayout 结合 RecyclerView下拉刷新
4. picasso图片框架
5. CoordinatorLayout、AppBarLayout、CollapsingToolbarLayout、Toolbar、NestedScrollView实现伸缩折叠效果
6. android 5.0 转场动画
7. 高仿豆瓣图书、影视详情

## 参考项目
1. [Google官方出品的MVP架构项目，含有多个不同的架构分支。](https://github.com/googlesamples/android-architecture)
2. [纯净知乎日报：最纯净的知乎日报客户端。运行快，无广告，无社交，省流量。](https://github.com/laucherish/PureZhihuD)
3. [云阅：一款基于网易云音乐UI，使用Gank.Io及豆瓣api开发的符合Google Material Design的Android客户端。项目采取的是MVVM-DataBinding架构开发，现主要包括：干货区、电影区和书籍区三个子模块。](https://github.com/youlookwhat/CloudReader)
4. []()

## 博客
1. [Retrofit — Getting Started and Creating an Android Client](https://futurestud.io/tutorials/retrofit-getting-started-and-android-client)
2.

## ScreenShot

* 导航模块

    <img src="/screenshot/Screenshot_0_01.png" width="200px" />
    <img src="/screenshot/Screenshot_0_02.png" width="200px" />
    <img src="/screenshot/Screenshot_0_03.png" width="200px" />
    <img src="/screenshot/Screenshot_0_04.png" width="200px" />
    <img src="/screenshot/Screenshot_0_05.png" width="200px" />
    <img src="/screenshot/Screenshot_0_06.png" width="200px" />
    <img src="/screenshot/Screenshot_0_07.png" width="200px" />
    <img src="/screenshot/Screenshot_0_08.png" width="200px" />
    <img src="/screenshot/Screenshot_0_09.png" width="200px" />
    <img src="/screenshot/Screenshot_0_10.png" width="200px" />
    <img src="/screenshot/Screenshot_0_11.png" width="200px" />
    <img src="/screenshot/Screenshot_0_12.png" width="200px" />
    <img src="/screenshot/Screenshot_0_13.png" width="200px" />
    <img src="/screenshot/Screenshot_0_14.png" width="200px" />

* 首页模块

    <img src="/screenshot/Screenshot_1_01.png" width="200px" />
    <img src="/screenshot/Screenshot_1_02.png" width="200px" />
    <img src="/screenshot/Screenshot_1_03.png" width="200px" />
    <img src="/screenshot/Screenshot_1_04.png" width="200px" />
    <img src="/screenshot/Screenshot_1_05.png" width="200px" />
    <img src="/screenshot/Screenshot_1_06.png" width="200px" />
    <img src="/screenshot/Screenshot_1_07.png" width="200px" />
    <img src="/screenshot/Screenshot_1_08.png" width="200px" />
    <img src="/screenshot/Screenshot_1_09.png" width="200px" />
    <img src="/screenshot/Screenshot_1_10.png" width="200px" />
    <img src="/screenshot/Screenshot_1_11.png" width="200px" />
    <img src="/screenshot/Screenshot_1_12.png" width="200px" />
    <img src="/screenshot/Screenshot_1_13.png" width="200px" />


* 新闻模块

	<img src="/screenshot/Screenshot_2_01.png" width="200px" />
    <img src="/screenshot/Screenshot_2_02.png" width="200px" />
    <img src="/screenshot/Screenshot_2_03.png" width="200px" />
    <img src="/screenshot/Screenshot_2_04.png" width="200px" />

* 图书模块

	<img src="/screenshot/Screenshot_3_01.png" width="200px" />
    <img src="/screenshot/Screenshot_3_02.png" width="200px" />
    <img src="/screenshot/Screenshot_3_03.png" width="200px" />
    <img src="/screenshot/Screenshot_3_04.png" width="200px" />

* 音乐模块

    <img src="/screenshot/Screenshot_4_01.png" width="200px" />
    <img src="/screenshot/Screenshot_4_02.png" width="200px" />
    <img src="/screenshot/Screenshot_4_03.png" width="200px" />
    <img src="/screenshot/Screenshot_4_04.png" width="200px" />
    <img src="/screenshot/Screenshot_4_05.png" width="200px" />
    <img src="/screenshot/Screenshot_4_06.png" width="200px" />
    <img src="/screenshot/Screenshot_4_07.png" width="200px" />

* 影视模块

	<img src="/screenshot/Screenshot_5_01.png" width="200px" />
    <img src="/screenshot/Screenshot_5_02.png" width="200px" />
    <img src="/screenshot/Screenshot_5_03.png" width="200px" />
    <img src="/screenshot/Screenshot_5_04.png" width="200px" />


## About

* **作者**：March
* **邮箱**：fengqi.mao.march@gmail.com
* **头条**：https://toutiao.io/u/425956/subjects
* **简书**：https://www.jianshu.com/u/02f2491c607d
* **掘金**：https://juejin.im/user/5b484473e51d45199940e2ae
* **CSDN**：http://blog.csdn.net/u011810138
* **SegmentFault**：https://segmentfault.com/u/maoqiqi
* **StackOverFlow**：https://stackoverflow.com/users/8223522


## License

```
   Copyright 2019 maoqiqi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

[1]: http://blog.csdn.net/u011810138/article/details/79026468
[2]: http://blog.csdn.net/u011810138/article/details/79033110