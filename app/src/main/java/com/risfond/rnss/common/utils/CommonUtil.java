package com.risfond.rnss.common.utils;

import android.app.Activity;
import android.content.Context;

import com.risfond.rnss.entry.City;
import com.risfond.rnss.entry.Province;
import com.risfond.rnss.home.call.activity.CallActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Abbott on 2017/7/18.
 */

public class CommonUtil {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static void call(final Context context, final String phone) {
        DialogUtil.getInstance().showCall(context, new DialogUtil.PressCallBack() {
            @Override
            public void onPressButton(int buttonIndex) {
                if (buttonIndex == DialogUtil.BUTTON_OK) {
                    CallActivity.startAction(context, phone);
                } else if (buttonIndex == DialogUtil.BUTTON_CANCEL) {
                    CallUtil.call(context, phone);
                }
            }
        });
    }

    /**
     * 根据资源名称在mipmap中获取资源ID
     *
     * @param context
     * @param imageName
     * @return
     */
    public int getResourceFromMipmap(Context context, String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        return resId;
    }

    /**
     * 防止点击过快
     *
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 高级搜索地点
     *
     * @param context
     * @return
     */
    public static List<Province> createProvince(Context context) {
        List<Province> provinces = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(context.getAssets().open("province.xml"));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("province");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element lan = (Element) nodeList.item(i);

                List<City> cities = new ArrayList<>();
                Province province = new Province();

                String str1[] = lan.getAttribute("name").split(",");
                province.setId(str1[0]);
                province.setName(str1[1]);
                province.setPinyin(str1[2]);

                NodeList nodeList2 = lan.getElementsByTagName("city");
                for (int j = 0; j < nodeList2.getLength(); j++) {
                    City city = new City();
                    String str2[] = nodeList2.item(j).getTextContent().split(",");
                    city.setId(str2[0]);
                    city.setName(str2[1]);
                    city.setPinyin(str2[2]);
                    cities.add(city);
                }
                province.setCities(cities);

                provinces.add(province);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return provinces;
    }

    /**
     * 注册省市
     *
     * @param context
     * @return
     */
    public static List<Province> createRegisterProvince(Context context) {
        List<Province> provinces = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(context.getAssets().open("regist_province.xml"));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("province");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element lan = (Element) nodeList.item(i);

                List<City> cities = new ArrayList<>();
                Province province = new Province();

                String str1[] = lan.getAttribute("name").split(",");
                province.setId(str1[0]);
                province.setName(str1[1]);
                province.setPinyin(str1[2]);

                NodeList nodeList2 = lan.getElementsByTagName("city");
                for (int j = 0; j < nodeList2.getLength(); j++) {
                    City city = new City();
                    String str2[] = nodeList2.item(j).getTextContent().split(",");
                    city.setId(str2[0]);
                    city.setName(str2[1]);
                    city.setPinyin(str2[2]);
                    cities.add(city);
                }
                province.setCities(cities);

                provinces.add(province);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return provinces;
    }


}
