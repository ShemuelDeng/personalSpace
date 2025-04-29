package com.shemuel.site.service;

import com.shemuel.site.entity.FilePartDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文件分片信息表，仅在手动分片上传时使用 服务接口
 */
public interface FilePartDetailService extends IService<FilePartDetail> {
    /**
     * 查询文件分片信息表，仅在手动分片上传时使用分页列表
     */
    IPage<FilePartDetail> selectPage(FilePartDetail filePartDetail);

    /**
     * 查询文件分片信息表，仅在手动分片上传时使用列表
     */
    List<FilePartDetail> selectList(FilePartDetail filePartDetail);

    /**
     * 新增文件分片信息表，仅在手动分片上传时使用
     */
    boolean insert(FilePartDetail filePartDetail);

    /**
     * 修改文件分片信息表，仅在手动分片上传时使用
     */
    boolean update(FilePartDetail filePartDetail);

    /**
     * 批量删除文件分片信息表，仅在手动分片上传时使用
     */
    boolean deleteByIds(List<String> ids);
}
