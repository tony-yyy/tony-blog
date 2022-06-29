package com.tony.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tony.blog.pojo.Pictures;
import com.tony.blog.pojo.PicturesDates;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Repository
public interface PicturesMapper extends BaseMapper<Pictures> {

    @Select("        SELECT\n" +
            "            DATE_FORMAT(uploadTime,'%Y-%m-%d 00:00:00') AS  date , COUNT(id) AS count\n" +
            "        FROM\n" +
            "            pictures\n" +
            "        WHERE\n" +
            "            isPublic >= #{isPublic}\n" +
            "            AND isRecycle = 0\n" +
            "        GROUP BY\n" +
            "            date\n" +
            "        order by date desc")
    List<PicturesDates> getPicturesDates(@Param("isPublic") int isPublic);

    @Select("        SELECT *\n" +
            "            FROM pictures\n" +
            "        WHERE " +
            "        DATE_FORMAT(uploadTime,'%Y-%m-%d') = #{map.date}\n" +
            "        AND isRecycle = 0\n" +
            "        AND isPublic >= #{map.isPublic}\n" +
            "        ORDER BY id desc")
    List<Pictures> getFilesByDay(@Param("map") Map<String, Object> map);

    @Update("UPDATE pictures SET isRecycle = #{map.isRecycle} WHERE userId = #{map.uid} AND id = #{map.fileId}")
    void setIsRecycle(Map<String, Object> map);

    @Insert("        INSERT INTO pictures(\n" +
            "            realFileName,\n" +
            "            saveFileName,\n" +
            "            ext, dir, size,\n" +
            "            TYPE, isimg, userId, thumbnail)\n" +
            "        VALUES(\n" +
            "            #{pictures.realfilename},\n" +
            "            #{pictures.savefilename},\n" +
            "            #{pictures.ext}, #{pictures.dir}, #{pictures.size},\n" +
            "            #{pictures.type}, #{pictures.isimg}, #{pictures.userid}, #{pictures.thumbnail}\n" +
            "        )")
    void uploadFiles(@Param("pictures") Pictures pictures);

    @Select("select * from pictures where id = #{fileId}")
    Pictures findFileById(@Param("fileId") Integer fileId);

    @Update("UPDATE pictures SET downLoadTimes = downLoadTimes+1 WHERE id=#{fileId}")
    void setDownLoadCounts(@Param("fileId") Integer fileId);

    @Update("update pictures set isPublic = #{map.publish} where id in ${map.ids}")
    int updatePublishedByIds(@Param("map") Map<String, Object> map);
}
