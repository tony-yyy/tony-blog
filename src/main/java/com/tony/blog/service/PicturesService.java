package com.tony.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.blog.pojo.Pictures;
import com.tony.blog.pojo.PicturesDates;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
public interface PicturesService extends IService<Pictures> {

    List<Pictures> getFilesByDay(Map<String, Object> map);

    void setFilesAsGarbage(List list, Long uid);

    void uploadFiles(Pictures pictures) throws ParseException;

    Pictures findFileById(Integer fileId);

    void deleteFileById(Pictures pictures);

    void setDownLoadCounts(Integer fileId);

    List<PicturesDates> getPicturesDates(int isPublic);

    boolean setPublishedByIds(Integer[] fileIds, Boolean publish);
}
