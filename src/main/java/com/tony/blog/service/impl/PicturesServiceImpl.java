package com.tony.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.blog.mapper.UserMapper;
import com.tony.blog.pojo.Pictures;
import com.tony.blog.mapper.PicturesMapper;
import com.tony.blog.pojo.PicturesDates;
import com.tony.blog.service.PicturesService;
import com.tony.blog.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Service
public class PicturesServiceImpl extends ServiceImpl<PicturesMapper, Pictures> implements PicturesService {
    @Autowired
    PicturesMapper picturesMapper;


    @Override
    public List<Pictures> getFilesByDay(Map<String, Object> map) {
        return picturesMapper.getFilesByDay(map);
    }

    @Override
    public void setFilesAsGarbage(List list, Long uid) {
        for (Object fileId : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("isRecycle", 1);
            map.put("uid", uid);
            map.put("fileId", Integer.valueOf(String.valueOf(fileId)));
            picturesMapper.setIsRecycle(map);
        }
    }

    @Override
    public void uploadFiles(Pictures pictures) throws ParseException {
        //获取系统当前时间
        //生成日期对象
        Date current_date = new Date();
        //设置日期格式化样式为：yyyy-MM-dd
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadtime = simpleDateFormat.format(current_date);
        //设置上传时间和修改时间
        pictures.setChangetime(simpleDateFormat.parse(uploadtime));
        pictures.setUploadtime(simpleDateFormat.parse(uploadtime));
        //设置是否为图片
        boolean isimg = pictures.getType().startsWith("image");
        if (isimg){
            pictures.setIsimg(1);
        }else{
            pictures.setIsimg(0);
        }
        picturesMapper.uploadFiles(pictures);
    }

    @Override
    public Pictures findFileById(Integer fileId) {
        return picturesMapper.findFileById(fileId);
    }

    @Override
    public void deleteFileById(Pictures pictures) {
        picturesMapper.deleteById(pictures);
    }

    @Override
    public void setDownLoadCounts(Integer fileId) {
        picturesMapper.setDownLoadCounts(fileId);
    }

    @Override
    public List<PicturesDates> getPicturesDates(int isPublic) {
        return picturesMapper.getPicturesDates(isPublic);
    }

    @Override
    public boolean setPublishedByIds(Integer[] fileIds, Boolean publish) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder(Arrays.toString(fileIds));
        sb.setCharAt(0, '(');
        sb.setCharAt(sb.length() - 1, ')');
        map.put("ids", sb.toString());
        map.put("publish", publish?1:0);
        int i = picturesMapper.updatePublishedByIds(map);
        return i > 0? true: false;
    }
}
