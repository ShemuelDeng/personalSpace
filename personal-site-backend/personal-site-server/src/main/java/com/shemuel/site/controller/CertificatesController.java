package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Certificates;
import com.shemuel.site.service.CertificatesService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 证书信息 控制器
 */
@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Tag(name = "证书信息管理")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @GetMapping("/list")
    @Operation(summary = "获取证书信息列表")
    public RestResult<IPage<Certificates>> list(Certificates certificates) {
        return RestResult.success(certificatesService.selectPage(certificates));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取证书信息详情")
    public RestResult<Certificates> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(certificatesService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加证书信息")
    public RestResult<Object> add(@RequestBody Certificates certificates) {
        return RestResult.success(certificatesService.insert(certificates));
    }

    @PutMapping("/update")
    @Operation(summary = "修改证书信息")
    public RestResult<Object> edit(@RequestBody Certificates certificates) {
        return RestResult.success(certificatesService.update(certificates));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除证书信息")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(certificatesService.deleteByIds(ids));
    }
}
