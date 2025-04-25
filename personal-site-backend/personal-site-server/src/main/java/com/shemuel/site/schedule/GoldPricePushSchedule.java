package com.shemuel.site.schedule;

import com.alibaba.fastjson.JSON;
import com.shemuel.site.tools.wx.AccessTokenDTO;
import com.shemuel.site.tools.wx.GoldPriceDataVO;
import com.shemuel.site.tools.wx.GoldRequestVO;
import com.shemuel.site.utils.GoldPriceScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author dengsx
 * @create 2025/04/19
 **/
@Slf4j
@Configuration
@EnableScheduling
public class GoldPricePushSchedule {

    @Value("${wx.appid}")
    private String appid;


    @Value("${wx.secret}")
    private String secret;


    @Value("${wx.openIds}")
    private String openIds;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wx.templateId.gold}")
    private String templateId;

    @Value("${gaode.api.key}")
    private String apiKey;


    /**
     * 工作日的每天下午4点执行（周一到周五 16:00:00）
     */
    @Scheduled(cron = "0 0 17 ? * MON-FRI")
//    @Scheduled(cron = "0 * * * * *")
    public void pushGoldPrice() {
        AccessTokenDTO accessToken = getAccessToken();
        GoldPriceDataVO goldPriceDataVO = GoldPriceScraper.scrape();


        String[] split = openIds.split(",");
        for (String openId : split) {
            GoldRequestVO requestVO = buildDataGold(goldPriceDataVO, openId);
            String json = JSON.toJSONString(requestVO);
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity formEntity = new HttpEntity(json, headers);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken.getAccess_token();
            Object result = restTemplate.postForObject(url, formEntity, Object.class);
            log.info("推送结果: {},{}", openId, result);
        }

    }


    private GoldRequestVO buildDataGold(GoldPriceDataVO param, String openId) {

        return new GoldRequestVO().setTouser(openId).setData(param).setTemplate_id(templateId);
    }


    private AccessTokenDTO getAccessToken() {
        String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", appid, secret);
        return restTemplate.getForObject(url, AccessTokenDTO.class);
    }
}
