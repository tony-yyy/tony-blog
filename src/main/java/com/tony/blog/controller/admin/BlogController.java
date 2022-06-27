package com.tony.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tony.blog.pojo.Blog;
import com.tony.blog.pojo.EditorJson;
import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.service.BlogService;
import io.swagger.models.auth.In;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admin", produces = "application/json;charset=utf-8")
public class BlogController {
    Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    BlogService blogService;

    @RequestMapping(value = "/blogs/uploadMarkDownImage", method = RequestMethod.POST)
    public EditorJson uploadFile(@RequestParam(value = "editormd-image-file", required = false)
                                             MultipartFile attach){
        EditorJson editorJson = new EditorJson();
        try {
            String realFileName = attach.getOriginalFilename();
            if (realFileName.length() > 230) {
                new FileUploadException();
            }
            String saveFileName = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) +"_"+ UUID.randomUUID().toString()
                    .replace("-","").substring(0, 8) +"_"+ realFileName;
            File filePath = new File("D:\\cloudDisk\\markdown\\");
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            // 保存文件
            File savePath = new File(filePath.getAbsolutePath() + File.separator + saveFileName);
            logger.info(savePath.getAbsolutePath());
            attach.transferTo(savePath); //核心上传方法
            // 下面response返回的json格式是editor.md所限制的，规范输出就OK
            editorJson.setSuccess(1);
            editorJson.setUrl("/cloudDisk/markdown/" + saveFileName);
            editorJson.setMessage(realFileName + "上传成功！");
        } catch (Exception e) {
            editorJson.setSuccess(0);
            editorJson.setMessage("图片上传失败！");
        }
        return editorJson;
    }

    @RequestMapping("/blog/save")
    public ResultInfo saveBlog(Blog blog){
        boolean save = blogService.save(blog);
        return new ResultInfo<Long>(blog.getId(), !save, !save?"保存失败":null);
    }

    @RequestMapping("/blog/save/{blogId}")
    public ResultInfo updateBlog(Blog blog, @PathVariable("blogId") Long blogId){
        blog.setId(blogId);
        boolean update = blogService.updateById(blog);
        return new ResultInfo(blog.getId(), !update, !update?"保存失败":null);
    }


    @RequestMapping("/blogs/{page}")
    public Page<Blog> getBlogsOnCondition(
              @PathVariable(value = "page") Integer page
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
            , @RequestParam(value = "title", required = false) String title
            , @RequestParam(value = "typeId", required = false) Integer typeId){
        return blogService.getBlogsOnCondition(page, pageSize,  title, typeId, false, false);
    }

    @RequestMapping("/blog/detail/{blogId}")
    public ResultInfo blogDetail(@PathVariable("blogId") Integer blogId){
        ResultInfo<Blog> resultInfo = new ResultInfo<>();
        Blog blog = blogService.getById(blogId);
        if (blog != null){
            resultInfo.setData(blog);
            resultInfo.setError(false);
        }else {
            resultInfo.setError(true);
            resultInfo.setErrorInfo("id 不存在！");
        }
        return resultInfo;
    }

    @RequestMapping("/blog/delete/{blogId}")
    public ResultInfo deleteBlog(@PathVariable("blogId") Long blogId){
        boolean remove = blogService.removeById(blogId);
        return new ResultInfo(!remove, !remove?"删除失败":null);
    }
}
