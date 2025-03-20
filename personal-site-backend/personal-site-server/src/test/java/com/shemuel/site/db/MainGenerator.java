package com.shemuel.site.db;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.shemuel.site.config.VelocityInitializer;
import com.shemuel.site.entity.GenTable;
import com.shemuel.site.mapper.GenTableMapper;
import com.shemuel.site.service.GenTableService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 简历
 * @Author: dengshaoxiang
 * @Date: 2025-03-14-15:54
 * @Description:
 */
@SpringBootTest
@ActiveProfiles("dev")
@MapperScan("com.shemuel.site.mapper") // 确保与实际mapper包路径一致
public class MainGenerator {
    @Autowired
    private GenTableService tableService;

    @Test
    public void testMbg(){

        // 1.全局配置
//        FastAutoGenerator.create("jdbc:mysql://172.16.6.69:3306/vv_nubility?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&useSSL=false", "root", "Vvlife@2019")
//                .globalConfig(builder -> builder
//                        .author("邓绍祥")
//                        .outputDir("D:\\code\\company\\personalSpace\\personal-site-backend\\personal-site-server\\src\\main\\java")
//                        .commentDate("yyyy-MM-dd")
//                )
//                .packageConfig(builder -> builder
//                        .parent("com.shemuel.site")
//                        .entity("entity")
//                        .mapper("mapper")
//                        .service("service")
//                        .controller("controller") // 设置 Controller 包名
//                        .serviceImpl("service.impl")
//                        .xml("mapper.xml")
//                )
//                .strategyConfig(builder -> {
//                    builder
//                            .entityBuilder()
//                            .enableLombok() // 启用 Lombok
//                            .enableTableFieldAnnotation() // 启用字段注解
//                            .controllerBuilder()
//                            .enableRestStyle(); // 启用 REST 风格
//                })
//                .templateConfig(builder -> {
//                    builder.disable(TemplateType.ENTITY, TemplateType.SERVICE, TemplateType.SERVICE_IMPL, TemplateType.CONTROLLER) // 禁用默认模板
//                            .entity("/templates/entity.java.vm")
//                            .service("/templates/service.java.vm")
//                            .serviceImpl("/templates/serviceImpl.java.vm")
//                            .controller("/templates/controller.java.vm");
//                })
//
//                .templateEngine(new VelocityTemplateEngine())
//                .execute();

        // 查询数据
        // List<GenTable> list = tableMapper.selectDbTableList(new GenTable());
        // tableService.importGenTable(Arrays.asList("user_profile").toArray(new String[1]));
        VelocityInitializer.initVelocity();
        Map<String, String> map = tableService.previewCode(23L);
        System.out.println(JSON.toJSONString(map));

        map.forEach((k, v) -> {

        });

    }
}
