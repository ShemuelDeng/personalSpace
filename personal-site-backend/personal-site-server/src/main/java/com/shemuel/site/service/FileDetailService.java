package com.shemuel.site.service;

import com.shemuel.site.entity.FileDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文件记录表 服务接口
 */
public interface FileDetailService extends IService<FileDetail> {
    /**
     * 查询文件记录表分页列表
     */
    IPage<FileDetail> selectPage(FileDetail fileDetail);

    /**
     * 查询文件记录表列表
     */
    List<FileDetail> selectList(FileDetail fileDetail);

    /**
     * 新增文件记录表
     */
    boolean insert(FileDetail fileDetail);

    /**
     * 修改文件记录表
     */
    boolean update(FileDetail fileDetail);

    /**
     * 批量删除文件记录表
     */
    boolean deleteByIds(List<String> ids);
}
