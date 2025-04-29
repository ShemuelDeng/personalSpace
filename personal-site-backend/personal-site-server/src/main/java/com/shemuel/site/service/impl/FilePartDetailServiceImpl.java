package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.FilePartDetailMapper;
import com.shemuel.site.entity.FilePartDetail;
import com.shemuel.site.service.FilePartDetailService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文件分片信息表，仅在手动分片上传时使用 服务实现类
 */
@Service
@RequiredArgsConstructor
public class FilePartDetailServiceImpl extends ServiceImpl<FilePartDetailMapper, FilePartDetail> implements FilePartDetailService {

    /**
     * 查询文件分片信息表，仅在手动分片上传时使用分页列表
     */
    @Override
    public IPage<FilePartDetail> selectPage(FilePartDetail filePartDetail) {
        LambdaQueryWrapper<FilePartDetail> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(filePartDetail.getId() != null, FilePartDetail::getId, filePartDetail.getId());
        wrapper.eq(filePartDetail.getPlatform() != null, FilePartDetail::getPlatform, filePartDetail.getPlatform());
        wrapper.eq(filePartDetail.getUploadId() != null, FilePartDetail::getUploadId, filePartDetail.getUploadId());
        wrapper.eq(filePartDetail.getETag() != null, FilePartDetail::getETag, filePartDetail.getETag());
        wrapper.eq(filePartDetail.getPartNumber() != null, FilePartDetail::getPartNumber, filePartDetail.getPartNumber());
        wrapper.eq(filePartDetail.getPartSize() != null, FilePartDetail::getPartSize, filePartDetail.getPartSize());
        wrapper.eq(filePartDetail.getHashInfo() != null, FilePartDetail::getHashInfo, filePartDetail.getHashInfo());
        wrapper.eq(filePartDetail.getCreateTime() != null, FilePartDetail::getCreateTime, filePartDetail.getCreateTime());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文件分片信息表，仅在手动分片上传时使用列表
     */
    @Override
    public List<FilePartDetail> selectList(FilePartDetail filePartDetail) {
        LambdaQueryWrapper<FilePartDetail> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(filePartDetail.getId() != null, FilePartDetail::getId, filePartDetail.getId());
        wrapper.eq(filePartDetail.getPlatform() != null, FilePartDetail::getPlatform, filePartDetail.getPlatform());
        wrapper.eq(filePartDetail.getUploadId() != null, FilePartDetail::getUploadId, filePartDetail.getUploadId());
        wrapper.eq(filePartDetail.getETag() != null, FilePartDetail::getETag, filePartDetail.getETag());
        wrapper.eq(filePartDetail.getPartNumber() != null, FilePartDetail::getPartNumber, filePartDetail.getPartNumber());
        wrapper.eq(filePartDetail.getPartSize() != null, FilePartDetail::getPartSize, filePartDetail.getPartSize());
        wrapper.eq(filePartDetail.getHashInfo() != null, FilePartDetail::getHashInfo, filePartDetail.getHashInfo());
        wrapper.eq(filePartDetail.getCreateTime() != null, FilePartDetail::getCreateTime, filePartDetail.getCreateTime());
        return list(wrapper);
    }

    /**
     * 新增文件分片信息表，仅在手动分片上传时使用
     */
    @Override
    public boolean insert(FilePartDetail filePartDetail) {
        return save(filePartDetail);
    }

    /**
     * 修改文件分片信息表，仅在手动分片上传时使用
     */
    @Override
    public boolean update(FilePartDetail filePartDetail) {
        return updateById(filePartDetail);
    }

    /**
     * 批量删除文件分片信息表，仅在手动分片上传时使用
     */
    @Override
    public boolean deleteByIds(List<String> ids) {
        return removeByIds(ids);
    }
}
