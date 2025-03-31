package com.shemuel.site.db;

import com.alibaba.fastjson.JSON;
import com.shemuel.site.config.VelocityInitializer;
import com.shemuel.site.entity.GenTable;
import com.shemuel.site.mapper.GenTableMapper;
import com.shemuel.site.service.GenTableService;
import com.shemuel.site.utils.VelocityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 简历
 *
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-14-15:54
 * @Description:
 */
@SpringBootTest
@ActiveProfiles("dev")
public class MainGenerator {

    @Autowired
    private GenTableMapper tableMapper;
    @Autowired
    private GenTableService tableService;

    @Test
    public void generate() {
        doGenerate("article_sync_record","third_party_platform");
    }

    private void doGenerate(String... tableNames) {

        if (tableNames == null || tableNames.length == 0){
            tableNames =  tableMapper.selectDbTableList(new GenTable()).stream().map(GenTable::getTableName).toArray(String[]::new);
        }


        VelocityInitializer.initVelocity();
        String[] tables = tableNames;
        tableService.importGenTable(tables);

        for (String table : tables) {
            GenTable genTable = tableMapper.selectGenTableByName(table);

            Map<String, String> map = tableService.previewCode(genTable.getTableId());


            // 在 testMbg() 方法内补充以下代码
            map.forEach((fileName, content) -> {

                if (!fileName.contains("vue") && !fileName.contains("api")) {

                    String basePath = Paths.get("D:\\code\\company\\personalSpace\\personal-site-backend\\personal-site-server\\src\\main\\java")
                            .toAbsolutePath().toString();

                    String basePath2 = Paths.get("D:\\code\\company\\personalSpace\\personal-site-backend\\personal-site-server\\src\\main\\resources")
                            .toAbsolutePath().toString();

                    try {
                        String fileName1 = getFileName(fileName, genTable);
                        Path outputPath;
                        if (fileName1.contains(".xml")) {
                            // 构建完整保存路径
                            outputPath = Paths.get(basePath2, fileName1);
                        } else {
                            // 构建完整保存路径
                            outputPath = Paths.get(basePath, fileName1);
                        }

                        // 创建目录（如果不存在）
                        Files.createDirectories(outputPath.getParent());

                        // 写入文件（使用UTF-8编码）
                        Files.write(outputPath, content.getBytes(StandardCharsets.UTF_8));

                        System.out.println("Generated: " + outputPath);
                    } catch (IOException e) {
                        System.err.println("Error writing file: " + fileName);
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private String getFileName(String fileName, GenTable table) {
        String className = VelocityUtil.getClassName(table.getTableName());
        String packageName = VelocityUtil.packageName.replace(".", "/");

        if (fileName.contains("entity")) {
            return packageName + "/entity/" + className + ".java";
        } else if (fileName.contains("mapper.java")) {
            return packageName + "/mapper/" + className + "Mapper.java";
        } else if (fileName.contains("service.java")) {
            return packageName + "/service/" + className + "Service.java";
        } else if (fileName.contains("serviceImpl")) {
            return packageName + "/service/impl/" + className + "ServiceImpl.java";
        } else if (fileName.contains("controller")) {
            return packageName + "/controller/" + className + "Controller.java";
        } else if (fileName.contains("mapper.xml")) {
            return "mapper/" + className + "Mapper.xml";
        } else if (fileName.contains("vue")) {
            return "vue/" + className.toLowerCase() + "/index.vue";
        }
        return null;
    }
}
