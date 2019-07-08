package com.codearms.maoqiqi.one.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.codearms.maoqiqi.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-03 16:02
 */
public class PermissionManager {

    private static final String TAG = PermissionManager.class.getSimpleName();

    private WeakReference<Activity> weakReference;
    private String[] permissions;
    private PermissionCallBack callBack;

    public PermissionManager(Activity activity, String[] permissions, PermissionCallBack callBack) {
        this.weakReference = new WeakReference<>(activity);
        this.permissions = permissions;
        this.callBack = callBack;
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    // 验证权限
    public void checkSelfPermission(Fragment fragment, int requestCode) {
        if (weakReference == null || weakReference.get() == null) return;

        LogUtils.e(TAG, "checkSelfPermission");

        if (isMarshmallow()) {
            // 遍历权限数组
            for (String permission : permissions) {
                // 检查权限
                if (PackageManager.PERMISSION_GRANTED != weakReference.get().checkSelfPermission(permission)) {
                    LogUtils.e(TAG, "permission:" + permission);
                    // 当权限申请被拒绝,并且shouldShowRequestPermissionRationale(permission)返回true,应该显示请求权限的理由
                    if (weakReference.get().shouldShowRequestPermissionRationale(permission)) {
                        LogUtils.e(TAG, "shouldShowRequestPermissionRationale");
                        // 显示提示对话框(授权读取外部存储器来显示设备上的歌曲)
                        if (callBack != null) callBack.onRequestPermissionRationale();
                    } else {
                        LogUtils.e(TAG, "requestPermissions");
                        // 直接请求权限
                        if (fragment == null) {
                            weakReference.get().requestPermissions(permissions, requestCode);
                        } else {
                            fragment.requestPermissions(permissions, requestCode);
                        }
                    }
                    // 只要其中一个没有授权,就结束循环
                    return;
                }
            }
        }
        // 加载数据回调
        if (callBack != null) callBack.onSuccess();
    }

    public void permissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (weakReference == null || weakReference.get() == null) return;

        LogUtils.e(TAG, "permissionsResult");

        for (int i = 0; i < grantResults.length; i++) {
            // 判断权限的结果,如果有被拒绝,就return
            if (PackageManager.PERMISSION_GRANTED != grantResults[i]) {
                if (isMarshmallow() && weakReference.get().shouldShowRequestPermissionRationale(permissions[i])) {
                    if (callBack != null) callBack.onFail();
                } else {
                    // 当权限申请被拒绝,并且shouldShowRequestPermissionRationale(permission)返回false
                    // 在此就表示用户勾选了不再询问,应该提示用户去设置界面打开相关权限
                    // 显示提示对话框(去设置界面授权读取外部存储器来显示设备上的歌曲)
                    if (callBack != null) callBack.onSetting();
                }
                return;
            }
        }
        // 所有权限都通过
        if (callBack != null) callBack.onSuccess();
    }

    public interface PermissionCallBack {

        void onRequestPermissionRationale();

        void onSuccess();

        void onSetting();

        void onFail();
    }
}