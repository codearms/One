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