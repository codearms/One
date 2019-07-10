// 注意,FragmentPagerAdapter是一次性将所有页卡都加载完毕,没有销毁的。
// 而FragmentStatePagerAdapter并不是一次性将页卡都加载完毕,而是默认每次加载进三个页卡,当前页卡被滑动消失就会被销毁。
// 这是它们的区别。因此如果页卡较多,建议采用FragmentStatePagerAdapter适配器。



