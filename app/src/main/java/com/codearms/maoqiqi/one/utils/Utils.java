package com.codearms.maoqiqi.one.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.view.View;

public class Utils {

    public static void setBackground(View view, Bitmap bitmap) {
        Palette.from(bitmap).generate(palette -> {
            // 1.活力颜色
            Palette.Swatch a = palette.getVibrantSwatch();
            // 2.亮的活力颜色
            Palette.Swatch b = palette.getLightVibrantSwatch();
            // 3.暗的活力颜色
            Palette.Swatch c = palette.getDarkVibrantSwatch();
            // 4.柔色
            Palette.Swatch d = palette.getMutedSwatch();
            // 5.亮的柔色
            Palette.Swatch e = palette.getLightMutedSwatch();
            // 6.暗的柔色
            Palette.Swatch f = palette.getDarkMutedSwatch();

            if (f != null) {
                f.getRgb(); // rgb颜色
                f.getTitleTextColor();// 文本颜色
            }

            int one = 0;
            int two = 0;

            if (f != null) {
                one = f.getRgb();
            } else {
                if (d != null) {
                    one = d.getRgb();
                } else {
                    if (e != null) {
                        one = e.getRgb();
                    } else {
                        if (a != null) {
                            one = a.getRgb();
                        }
                    }
                }
            }

            if (b != null) {
                two = b.getRgb();
            } else {
                if (a != null) {
                    two = a.getRgb();
                } else {
                    if (c != null) {
                        two = c.getRgb();
                    } else {
                        if (d != null) {
                            two = d.getRgb();
                        }
                    }
                }
            }

            if (one == 0 || two == 0) return;
            view.setBackgroundDrawable(getDrawable(one, two));
        });
    }

    private static Drawable getDrawable(int one, int two) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{one, two});
        // 设置形状
        drawable.setShape(GradientDrawable.RECTANGLE);
        // 设置渐变方式
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }

    public static int getLightColor(Bitmap bitmap) {
        int color = 0;
        Palette palette = Palette.from(bitmap).generate();
        // 1.活力颜色
        Palette.Swatch a = palette.getVibrantSwatch();
        // 2.亮的活力颜色
        Palette.Swatch b = palette.getLightVibrantSwatch();
        // 3.暗的活力颜色
        Palette.Swatch c = palette.getDarkVibrantSwatch();
        // 4.柔色
        Palette.Swatch d = palette.getMutedSwatch();

        if (b != null) {
            color = b.getRgb();
        } else {
            if (a != null) {
                color = a.getRgb();
            } else {
                if (c != null) {
                    color = c.getRgb();
                } else {
                    if (d != null) {
                        color = d.getRgb();
                    }
                }
            }
        }
        return color;
    }
}