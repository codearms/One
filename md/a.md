// 注意,FragmentPagerAdapter是一次性将所有页卡都加载完毕,没有销毁的。
// 而FragmentStatePagerAdapter并不是一次性将页卡都加载完毕,而是默认每次加载进三个页卡,当前页卡被滑动消失就会被销毁。
// 这是它们的区别。因此如果页卡较多,建议采用FragmentStatePagerAdapter适配器。



项目完全开源，如果你觉得不错，请帮忙star一下吧，或者点击右上角的分享，分享给你的朋友，这是你对我最大的支持，非常感谢！

 android:textIsSelectable="true"

 getSupportActionBar().setDisplayHomeAsUpEnabled(true);//不添加这句华为手机会出现标题显示不完整的问题


if (isQQClientAvailable()) {
     String url = "mqqwpa://im/chat?chat_type=wpa&uin=503233512";
     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
 } else
     Toasty.error(this, "当前设备未安装QQ").show();


/**
 * 判断qq是否可用
 */
public static boolean isQQClientAvailable() {
    final PackageManager packageManager = Utils.getContext().getPackageManager();
    List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
    if (packageInfo != null) {
        for (int i = 0; i < packageInfo.size(); i++) {
            String pn = packageInfo.get(i).packageName;
            if (pn.equals("com.tencent.mobileqq")) {
                return true;
            }
        }
    }
    return false;
}


谢谢，您没有安装支付宝客户端


// 保存用户按返回键的时间
    private long mExitTime = 0;



    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Snackbar.make(mDrawerLayout, R.string.exit_toast, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


 // 如果当前获取的数据数目没有全局设定的每次获取的条数，说明已经没有更多数据


// 华为底部导航栏适配
if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
    AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
}



private void enableJavascript() {
    this.webview.getSettings().setJavaScriptEnabled(true);
    this.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
}


private void enableCaching() {
    this.webview.getSettings().setAppCachePath(getFilesDir() + getPackageName() + "/cache");
    this.webview.getSettings().setAppCacheEnabled(true);
    this.webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
}


private void enableAdjust() {
    this.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    this.webview.getSettings().setLoadWithOverviewMode(true);
}


private void zoomedOut() {
    this.webview.getSettings().setLoadWithOverviewMode(true);
    this.webview.getSettings().setUseWideViewPort(true);
    this.webview.getSettings().setSupportZoom(true);
}


ViewCompat.setElevation(tab_layout, ViewUtils.dp2px(getContext(), 4));



  getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头



// ===
mIvDownload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        downloadPicture(0);
    }
});
RxPermissions rxPermissions = new RxPermissions(this);
RxView.clicks(mIvDownload)
        .compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        .subscribe(new Consumer<com.tbruyelle.rxpermissions2.Permission>() {
            @Override
            public void accept(@NonNull com.tbruyelle.rxpermissions2.Permission permission) throws Exception {
                if (permission.granted) {
                    downloadPicture(0);
                } else if (permission.shouldShowRequestPermissionRationale) {

                } else {
                    SnackBarUtils.makeShort(mViewPager,getString(R.string.no_permisstion_write)).info();
                }
            }
        });


//

class MyViewPager extends PagerAdapter {
        Context context;

        public MyViewPager(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            PhotoView photoView = new PhotoView(context);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            photoView.setLayoutParams(layoutParams);

            //setUpPhotoViewAttacher(photoView);
            ImageLoader.displayImage(photoView, mUrlList.get(position));
            container.addView(photoView);

            return photoView;
        }

        @Override
        public int getCount() {
            return mUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
//


  ActivityCompat.finishAfterTransition(this);


//
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse(mWbContent.getUrl()));
if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(intent);
} else {
    Toast.makeText(WebActivity.this, R.string.toast_open_fail, Toast.LENGTH_SHORT).show();
}


//
妹子还没有准备好


OkHttpClient client = new OkHttpClient();
client.setReadTimeout(21, TimeUnit.SECONDS);
RestAdapter restAdapter = new RestAdapter.Builder()
        .setClient(new OkClient(client))
        .setEndpoint(MainFactory.HOST)
        .setConverter(new GsonConverter(gson))
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .build();
mService = restAdapter.create(GuDong.class);

//
<color name="primary">#FFEB3B</color>
<color name="primary_dark">#FBC02D</color>

//
//        ###### 注解框架 [butterknife](https://github.com/JakeWharton/butterknife)
//        ###### Json解析 [Gson](https://github.com/google/gson)
//        ###### 网络框架 [retrofit](https://github.com/square/retrofit)  [okhttp](https://github.com/square/okhttp)
//        ###### 打印日志框架 [klog](https://github.com/ZhaoKaiQiang/KLog)
//        ###### 图片加载 [glide](https://github.com/bumptech/glide)
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


请作者喝一杯咖啡 (￣▽￣)~*


protected int pageIndex;
protected int pageCount = Constants.PAGE_COUNT;


   StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

获取存储权限失败，请前往设置页面打开存储权限