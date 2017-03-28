package com.charles.coolweather.util;

import android.text.TextUtils;

import com.charles.coolweather.model.City;
import com.charles.coolweather.model.CoolWeatherDB;
import com.charles.coolweather.model.County;
import com.charles.coolweather.model.Province;

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     * @param coolWeatherDB 数据库相关操作
     * @param response 服务器返回的数据
     */
    public synchronized static boolean handleProvincesResponse(
            CoolWeatherDB coolWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(","); // 服务器返回格式“代号|城市,代号|城市”
            if (allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Providence表
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     * @param coolWeatherDB 数据库相关操作
     * @param response 服务器返回的数据
     * @param provinceId 所在省份id
     */
    public static boolean handleCitiesResponse(
            CoolWeatherDB coolWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // 将解析出来的数据存储到City表
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     * @param coolWeatherDB 数据库相关操作
     * @param response 服务器返回的数据
     * @param cityId 所在城市id
     */
    public static boolean handleCountiesResponse(
            CoolWeatherDB coolWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // 将解析出来的数据存储到County表
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
