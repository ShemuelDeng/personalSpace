package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.CertificatesMapper;
import com.shemuel.site.entity.Certificates;
import com.shemuel.site.service.CertificatesService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 证书信息 服务实现类
 */
@Service
@RequiredArgsConstructor
public class CertificatesServiceImpl extends ServiceImpl<CertificatesMapper, Certificates> implements CertificatesService {

    /**
     * 查询证书信息分页列表
     */
    @Override
    public IPage<Certificates> selectPage(Certificates certificates) {
        LambdaQueryWrapper<Certificates> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(certificates.getId() != null, Certificates::getId, certificates.getId());
        wrapper.eq(certificates.getUserId() != null, Certificates::getUserId, certificates.getUserId());
        wrapper.eq(certificates.getCertificateName() != null, Certificates::getCertificateName, certificates.getCertificateName());
        wrapper.eq(certificates.getIssuingOrganization() != null, Certificates::getIssuingOrganization, certificates.getIssuingOrganization());
        wrapper.eq(certificates.getIssueDate() != null, Certificates::getIssueDate, certificates.getIssueDate());
        wrapper.eq(certificates.getExpirationDate() != null, Certificates::getExpirationDate, certificates.getExpirationDate());
        wrapper.eq(certificates.getCredentialId() != null, Certificates::getCredentialId, certificates.getCredentialId());
        wrapper.eq(certificates.getCredentialUrl() != null, Certificates::getCredentialUrl, certificates.getCredentialUrl());
        wrapper.eq(certificates.getSortOrder() != null, Certificates::getSortOrder, certificates.getSortOrder());
        wrapper.eq(certificates.getCreatedAt() != null, Certificates::getCreatedAt, certificates.getCreatedAt());
        wrapper.eq(certificates.getUpdatedAt() != null, Certificates::getUpdatedAt, certificates.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询证书信息列表
     */
    @Override
    public List<Certificates> selectList(Certificates certificates) {
        LambdaQueryWrapper<Certificates> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(certificates.getId() != null, Certificates::getId, certificates.getId());
        wrapper.eq(certificates.getUserId() != null, Certificates::getUserId, certificates.getUserId());
        wrapper.eq(certificates.getCertificateName() != null, Certificates::getCertificateName, certificates.getCertificateName());
        wrapper.eq(certificates.getIssuingOrganization() != null, Certificates::getIssuingOrganization, certificates.getIssuingOrganization());
        wrapper.eq(certificates.getIssueDate() != null, Certificates::getIssueDate, certificates.getIssueDate());
        wrapper.eq(certificates.getExpirationDate() != null, Certificates::getExpirationDate, certificates.getExpirationDate());
        wrapper.eq(certificates.getCredentialId() != null, Certificates::getCredentialId, certificates.getCredentialId());
        wrapper.eq(certificates.getCredentialUrl() != null, Certificates::getCredentialUrl, certificates.getCredentialUrl());
        wrapper.eq(certificates.getSortOrder() != null, Certificates::getSortOrder, certificates.getSortOrder());
        wrapper.eq(certificates.getCreatedAt() != null, Certificates::getCreatedAt, certificates.getCreatedAt());
        wrapper.eq(certificates.getUpdatedAt() != null, Certificates::getUpdatedAt, certificates.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增证书信息
     */
    @Override
    public boolean insert(Certificates certificates) {
        return save(certificates);
    }

    /**
     * 修改证书信息
     */
    @Override
    public boolean update(Certificates certificates) {
        return updateById(certificates);
    }

    /**
     * 批量删除证书信息
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
