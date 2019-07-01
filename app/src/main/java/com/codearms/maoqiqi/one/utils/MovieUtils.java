package com.codearms.maoqiqi.one.utils;

import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;

import java.util.List;

public class MovieUtils {

    /**
     * 格式化人名
     */
    public static String formatPersonName(List<MovieDetailBean.PersonBean> personBeanList) {
        if (personBeanList != null && personBeanList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < personBeanList.size(); i++) {
                if (i < personBeanList.size() - 1) {
                    stringBuilder.append(personBeanList.get(i).getName()).append(" / ");
                } else {
                    stringBuilder.append(personBeanList.get(i).getName());
                }
            }
            return stringBuilder.toString();
        } else {
            return "佚名";
        }
    }

    /**
     * 格式化电影类型
     */
    public static String formatGenre(List<String> genreList) {
        if (genreList != null && genreList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < genreList.size(); i++) {
                if (i < genreList.size() - 1) {
                    stringBuilder.append(genreList.get(i)).append(" / ");
                } else {
                    stringBuilder.append(genreList.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "不知名类型";
        }
    }

    /**
     * 格式化电影时长
     */
    public static String formatDuration(List<String> durationList) {
        if (durationList != null && durationList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < durationList.size(); i++) {
                if (i < durationList.size() - 1) {
                    stringBuilder.append(durationList.get(i)).append(" / ");
                } else {
                    stringBuilder.append(durationList.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "不知";
        }
    }

    /**
     * 格式化制片国家/地区
     */
    public static String formatCountry(List<String> countryList) {
        if (countryList != null && countryList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < countryList.size(); i++) {
                if (i < countryList.size() - 1) {
                    stringBuilder.append(countryList.get(i)).append(" / ");
                } else {
                    stringBuilder.append(countryList.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "不知";
        }
    }
}