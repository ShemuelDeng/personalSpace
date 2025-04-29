package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.FileDetailMapper;
import com.shemuel.site.entity.FileDetail;
import com.shemuel.site.service.FileDetailService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文件记录表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class FileDetailServiceImpl extends ServiceImpl<FileDetailMapper, FileDetail> implements FileDetailService {

    /**
     * 查询文件记录表分页列表
     */
    @Override
    public IPage<FileDetail> selectPage(FileDetail fileDetail) {
        LambdaQueryWrapper<FileDetail> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(fileDetail.getId() != null, FileDetail::getId, fileDetail.getId());
        wrapper.eq(fileDetail.getUrl() != null, FileDetail::getUrl, fileDetail.getUrl());
        wrapper.eq(fileDetail.getSize() != null, FileDetail::getSize, fileDetail.getSize());
        wrapper.eq(fileDetail.getFilename() != null, FileDetail::getFilename, fileDetail.getFilename());
        wrapper.eq(fileDetail.getOriginalFilename() != null, FileDetail::getOriginalFilename, fileDetail.getOriginalFilename());
        wrapper.eq(fileDetail.getBasePath() != null, FileDetail::getBasePath, fileDetail.getBasePath());
        wrapper.eq(fileDetail.getPath() != null, FileDetail::getPath, fileDetail.getPath());
        wrapper.eq(fileDetail.getExt() != null, FileDetail::getExt, fileDetail.getExt());
        wrapper.eq(fileDetail.getContentType() != null, FileDetail::getContentType, fileDetail.getContentType());
        wrapper.eq(fileDetail.getPlatform() != null, FileDetail::getPlatform, fileDetail.getPlatform());
        wrapper.eq(fileDetail.getThUrl() != null, FileDetail::getThUrl, fileDetail.getThUrl());
        wrapper.eq(fileDetail.getThFilename() != null, FileDetail::getThFilename, fileDetail.getThFilename());
        wrapper.eq(fileDetail.getThSize() != null, FileDetail::getThSize, fileDetail.getThSize());
        wrapper.eq(fileDetail.getThContentType() != null, FileDetail::getThContentType, fileDetail.getThContentType());
        wrapper.eq(fileDetail.getObjectId() != null, FileDetail::getObjectId, fileDetail.getObjectId());
        wrapper.eq(fileDetail.getObjectType() != null, FileDetail::getObjectType, fileDetail.getObjectType());
        wrapper.eq(fileDetail.getMetadata() != null, FileDetail::getMetadata, fileDetail.getMetadata());
        wrapper.eq(fileDetail.getUserMetadata() != null, FileDetail::getUserMetadata, fileDetail.getUserMetadata());
        wrapper.eq(fileDetail.getThMetadata() != null, FileDetail::getThMetadata, fileDetail.getThMetadata());
        wrapper.eq(fileDetail.getThUserMetadata() != null, FileDetail::getThUserMetadata, fileDetail.getThUserMetadata());
        wrapper.eq(fileDetail.getAttr() != null, FileDetail::getAttr, fileDetail.getAttr());
        wrapper.eq(fileDetail.getFileAcl() != null, FileDetail::getFileAcl, fileDetail.getFileAcl());
        wrapper.eq(fileDetail.getThFileAcl() != null, FileDetail::getThFileAcl, fileDetail.getThFileAcl());
        wrapper.eq(fileDetail.getHashInfo() != null, FileDetail::getHashInfo, fileDetail.getHashInfo());
        wrapper.eq(fileDetail.getUploadId() != null, FileDetail::getUploadId, fileDetail.getUploadId());
        wrapper.eq(fileDetail.getUploadStatus() != null, FileDetail::getUploadStatus, fileDetail.getUploadStatus());
        wrapper.eq(fileDetail.getCreateTime() != null, FileDetail::getCreateTime, fileDetail.getCreateTime());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文件记录表列表
     */
    @Override
    public List<FileDetail> selectList(FileDetail fileDetail) {
        LambdaQueryWrapper<FileDetail> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(fileDetail.getId() != null, FileDetail::getId, fileDetail.getId());
        wrapper.eq(fileDetail.getUrl() != null, FileDetail::getUrl, fileDetail.getUrl());
        wrapper.eq(fileDetail.getSize() != null, FileDetail::getSize, fileDetail.getSize());
        wrapper.eq(fileDetail.getFilename() != null, FileDetail::getFilename, fileDetail.getFilename());
        wrapper.eq(fileDetail.getOriginalFilename() != null, FileDetail::getOriginalFilename, fileDetail.getOriginalFilename());
        wrapper.eq(fileDetail.getBasePath() != null, FileDetail::getBasePath, fileDetail.getBasePath());
        wrapper.eq(fileDetail.getPath() != null, FileDetail::getPath, fileDetail.getPath());
        wrapper.eq(fileDetail.getExt() != null, FileDetail::getExt, fileDetail.getExt());
        wrapper.eq(fileDetail.getContentType() != null, FileDetail::getContentType, fileDetail.getContentType());
        wrapper.eq(fileDetail.getPlatform() != null, FileDetail::getPlatform, fileDetail.getPlatform());
        wrapper.eq(fileDetail.getThUrl() != null, FileDetail::getThUrl, fileDetail.getThUrl());
        wrapper.eq(fileDetail.getThFilename() != null, FileDetail::getThFilename, fileDetail.getThFilename());
        wrapper.eq(fileDetail.getThSize() != null, FileDetail::getThSize, fileDetail.getThSize());
        wrapper.eq(fileDetail.getThContentType() != null, FileDetail::getThContentType, fileDetail.getThContentType());
        wrapper.eq(fileDetail.getObjectId() != null, FileDetail::getObjectId, fileDetail.getObjectId());
        wrapper.eq(fileDetail.getObjectType() != null, FileDetail::getObjectType, fileDetail.getObjectType());
        wrapper.eq(fileDetail.getMetadata() != null, FileDetail::getMetadata, fileDetail.getMetadata());
        wrapper.eq(fileDetail.getUserMetadata() != null, FileDetail::getUserMetadata, fileDetail.getUserMetadata());
        wrapper.eq(fileDetail.getThMetadata() != null, FileDetail::getThMetadata, fileDetail.getThMetadata());
        wrapper.eq(fileDetail.getThUserMetadata() != null, FileDetail::getThUserMetadata, fileDetail.getThUserMetadata());
        wrapper.eq(fileDetail.getAttr() != null, FileDetail::getAttr, fileDetail.getAttr());
        wrapper.eq(fileDetail.getFileAcl() != null, FileDetail::getFileAcl, fileDetail.getFileAcl());
        wrapper.eq(fileDetail.getThFileAcl() != null, FileDetail::getThFileAcl, fileDetail.getThFileAcl());
        wrapper.eq(fileDetail.getHashInfo() != null, FileDetail::getHashInfo, fileDetail.getHashInfo());
        wrapper.eq(fileDetail.getUploadId() != null, FileDetail::getUploadId, fileDetail.getUploadId());
        wrapper.eq(fileDetail.getUploadStatus() != null, FileDetail::getUploadStatus, fileDetail.getUploadStatus());
        wrapper.eq(fileDetail.getCreateTime() != null, FileDetail::getCreateTime, fileDetail.getCreateTime());
        return list(wrapper);
    }

    /**
     * 新增文件记录表
     */
    @Override
    public boolean insert(FileDetail fileDetail) {
        return save(fileDetail);
    }

    /**
     * 修改文件记录表
     */
    @Override
    public boolean update(FileDetail fileDetail) {
        return updateById(fileDetail);
    }

    /**
     * 批量删除文件记录表
     */
    @Override
    public boolean deleteByIds(List<String> ids) {
        return removeByIds(ids);
    }
}
