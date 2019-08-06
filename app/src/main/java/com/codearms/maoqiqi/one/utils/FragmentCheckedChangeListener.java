package com.codearms.maoqiqi.one.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

/**
 * 主页面
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 10:20
 */
public abstract class FragmentCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

    private final FragmentManager fm;
    private final int[] buttonIds;
    private final int resId;
    private final String[] tags;
    private final Fragment[] fragments;

    private int position;
    private Fragment previousFragment;

    /**
     * @param fm        FragmentManager
     * @param buttonIds Array of button resource ids
     * @param resId     View id
     */
    public FragmentCheckedChangeListener(FragmentManager fm, int[] buttonIds, int resId) {
        this.fm = fm;
        this.buttonIds = buttonIds;
        this.resId = resId;
        this.tags = new String[buttonIds.length];
        this.fragments = new Fragment[buttonIds.length];
        for (int i = 0; i < buttonIds.length; i++) {
            tags[i] = "TAG_" + i;
        }
    }

    public void findFragmentByTag() {
        for (int i = 0; i < buttonIds.length; i++) {
            fragments[i] = fm.findFragmentByTag(tags[i]);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 得到对应的fragment
        Fragment to = getFragment(checkedId);
        // 替换
        switchFragment(previousFragment, to);
    }

    private Fragment getFragment(int checkedId) {
        for (int i = 0; i < buttonIds.length; i++) {
            if (checkedId == buttonIds[i]) {
                position = i;
                break;
            }
        }
        return fragments[position] == null ? fragments[position] = newFragment(position) : fragments[position];
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (to != null && from != to) {// from != to 才切换
            previousFragment = to;
            FragmentTransaction ft = fm.beginTransaction();

            // from隐藏
            if (from != null) ft.hide(from);

            if (!to.isAdded()) {
                // 没有被添加,添加to
                ft.add(resId, to, tags[position]).commit();
            } else {
                // 已经被添加,显示to
                ft.show(to).commit();
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public String[] getTags() {
        return tags;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    protected abstract Fragment newFragment(int position);
}