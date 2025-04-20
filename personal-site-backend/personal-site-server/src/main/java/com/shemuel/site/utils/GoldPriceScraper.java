package com.shemuel.site.utils;

import com.shemuel.site.tools.wx.GoldPriceDataVO;
import com.shemuel.site.tools.wx.PropertyVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class GoldPriceScraper {
    public static GoldPriceDataVO scrape()  {

        try {
            GoldPriceDataVO result = new GoldPriceDataVO();
            result.setDate(PropertyVO.init(DateUtil.getDateWithWeek(),null));
            Document doc = Jsoup.connect("https://www.jinjia.com.cn/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            Elements goldPriceItems = doc.select("div.bg:has(h1:contains(今日金价查询表)) .list ul li");
            Elements goldPriceItems1 = doc.select("div.bg:has(h2:contains(金店今日金价)) .list ul li");

            for (int i = 1; i < goldPriceItems.size(); i++) { // 跳过表头
                Element li = goldPriceItems.get(i);
                String name = li.select(".name").text();
                String price = li.select(".new span").text();
                String change = li.select(".rise span").text();
                String open = li.select(".open").text();
                String prevClose = li.select(".prec").text();
                String updateTime = li.select(".time").text();
                if (Objects.equals("今日金价", name)){
                    result.setBasePrice(PropertyVO.init(price,null));
                }

            }

            for (int i = 1; i < goldPriceItems1.size(); i++) { // 跳过表头
                Element li = goldPriceItems1.get(i);
                String name = li.select(".name").text();
                String price = li.select(".new span").text();

                switch (name){
                    case "周大福":
                        result.setZhoudafu(PropertyVO.init(price,null));
                        break;
                    case "老凤祥":
                        result.setLaofengxiang(PropertyVO.init(price,null));
                        break;
                    case "周六福":
                        result.setZhouliufu(PropertyVO.init(price,null));
                        break;
                    case "周生生":
                        result.setZhoushengsheng(PropertyVO.init(price,null));
                        break;
                        case "六福珠宝":
                        result.setLiufuzhubao(PropertyVO.init(price,null));
                        break;
                    case "老庙":
                        result.setLaomiaohuangjin(PropertyVO.init(price,null));
                        break;
                }
            }


            return result;
        } catch (IOException e) {
            e.printStackTrace();
           return null;
        }

    }

}