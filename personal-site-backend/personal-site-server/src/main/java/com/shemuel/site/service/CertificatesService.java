package com.shemuel.site.service;

import com.shemuel.site.entity.Certificates;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 证书信息 服务接口
 */
public interface CertificatesService extends IService<Certificates> {
    /**
     * 查询证书信息分页列表
     */
    IPage<Certificates> selectPage(Certificates certificates);

    /**
     * 查询证书信息列表
     */
    List<Certificates> selectList(Certificates certificates);

    /**
     * 新增证书信息
     */
    boolean insert(Certificates certificates);

    /**
     * 修改证书信息
     */
    boolean update(Certificates certificates);

    /**
     * 批量删除证书信息
     */
    boolean deleteByIds(List<Integer> ids);
}
