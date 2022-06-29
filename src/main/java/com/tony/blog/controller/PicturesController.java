package com.tony.blog.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.blog.pojo.Pictures;
import com.tony.blog.pojo.PicturesDates;
import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.pojo.User;
import com.tony.blog.service.PicturesService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@RestController
@RequestMapping(value = "/pictures", produces = "application/json;charset=UTF-8")
public class PicturesController {
    public final static String diskDir = "D:\\cloudDisk\\files\\";
//    public final static String diskDir = "/usr/cloudDisk/files/"; // linux

    @Autowired
    PicturesService picturesService;
//    @Autowired
//    UserService userService;


    /**
     * 所有日期图片
     * @param session
     * @return
     */
    @RequestMapping("/picturesDates")
    @ResponseBody()
    public List<PicturesDates> picturesDates(HttpSession session){
        Long uid = (Long) session.getAttribute("USER_ID");
        System.out.println(uid);
        List<PicturesDates> picturesDates = picturesService.getPicturesDates(0);
        return picturesDates;
    }
    @RequestMapping("/picturesDates/public")
    @ResponseBody()
    public List<PicturesDates> publicPicturesDates(){
        List<PicturesDates> picturesDates = picturesService.getPicturesDates(1);
        return picturesDates;
    }



    /**
     * 返回的是json
     * @param date
     * @param session
     * @return
     * @throws ParseException
     */
    // 注意date：yyyy-MM-dd
    @RequestMapping("/getFilesByDate/{date}")
    @ResponseBody
    public List<Pictures> getFilesByDate(@PathVariable("date") String date, HttpSession session) throws ParseException {
        Long uid = (Long) session.getAttribute("USER_ID");
        System.out.println(uid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("isPublic", 0);
        map.put("date", date);
        List<Pictures> filesByDay = picturesService.getFilesByDay(map);
        System.out.println(filesByDay);
        return filesByDay;
    }

    /**
     * 返回的是json
     * @param date
     * @param
     * @return
     * @throws ParseException
     */
    // 注意date：yyyy-MM-dd
    @RequestMapping("/getFilesByDate/public/{date}")
    @ResponseBody
    public List<Pictures> getPublicFilesByDate(@PathVariable("date") String date) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isPublic", 1);
        map.put("date", date);
        List<Pictures> filesByDay = picturesService.getFilesByDay(map);
        System.out.println(filesByDay);
        return filesByDay;
    }

/*    @RequestMapping(value = "/deleteFiles",  produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result deleteFiles(Model model, HttpServletRequest request, HttpSession session) throws JsonProcessingException {
        Integer uid = (Integer) session.getAttribute("USER_ID");

        // 获取所有要加入回收站的文件id
        String files = request.getParameter("files");
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(files, ArrayList.class);
        // 加入回收站
        myFileService.setFilesAsGarbage(list, uid);
        return new Result(true, null, null);
    }*/

    @RequestMapping(value = "/deleteFilesCompletely",  produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultInfo deleteFilesCompletely(HttpServletRequest request, HttpSession session) throws JsonProcessingException {
        // 获取所有要加入回收站的文件id
        String files = request.getParameter("files");
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(files, ArrayList.class);
        for (Object obj : list) {
            Integer fileId = Integer.valueOf(String.valueOf(obj));
            Pictures delfile = picturesService.findFileById(fileId);
            //创建删除文件对象
            File file = new File(diskDir, delfile.getSavefilename());
            //进行删除
            if (file.exists())file.delete();
            picturesService.deleteFileById(delfile);
        }
        return new ResultInfo(true, null, null);
    }

    /**
     * 文件上传
     * @param files
     * @param session
     * @return
     */
    @PostMapping("/uploadfiles")
    @ResponseBody
    public ResultInfo uploadFiles(@RequestParam("files") List<MultipartFile> files, HttpSession session) throws IOException {
        Long uid = (Long) session.getAttribute("USER_ID");
        //准备数据
        float useSpace = 0;
        //循环遍历所有文件
        for (MultipartFile file : files) {
            //获取文件原始名称
            String realFileName = file.getOriginalFilename();
            //获取文件后缀名
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            //生成新的文件名称
            String saveFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ UUID.randomUUID().toString().replace("-","") +"_"+ realFileName;
            //设置文件上传路径
            String path = diskDir;
//            /cloudDisk/file/
            //获取文件大小(单位kb)
            float size = (((float)file.getSize())/1024);
            //获取文件类型
            String type = file.getContentType();
            //创建文件对象目录
            File filepath = new File(path);
            if (!filepath.exists()) {
                filepath.mkdirs();
            }
            //上传文件
            file.transferTo(new File(path+saveFileName)); //核心上传方法
            //将文件信息保存数据库
            Pictures pictures = new Pictures();
            pictures.setRealfilename(realFileName);
            pictures.setSavefilename(saveFileName);
            pictures.setExt(ext);
            pictures.setDir("/cloudDisk/file/");
            pictures.setSize(size);
            pictures.setUserid(uid);
            pictures.setType(type);
            try {
                picturesService.uploadFiles(pictures);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResultInfo(true, null, null);
    }

    /*
    @RequestMapping("/fileDetail/{fileId}")
    public String fileDetail(@PathVariable("fileId") Integer fileId, Model model, HttpSession session){
        Integer uid = (Integer) session.getAttribute("USER_ID");
//        System.out.println(fileId);
        Pictures file = picturesService.findFileById(fileId, uid);
        Integer lastFileId = picturesService.getLastFileId(file); // 上一张图id，在当前id之前都是新的
        Integer nextFileId = picturesService.getNextFileId(file); // 下一张图id，在当前id之后都是旧的

*//*        System.out.println("cur:" + fileId);
        System.out.println("lastFileId:" + lastFileId);
        System.out.println("nextFileId:" + nextFileId);
        System.out.println();*//*
        LastNextFileIdBean lastNextFileIdBean = new LastNextFileIdBean(lastFileId, nextFileId);
        model.addAttribute("lastNextFileIdBean", lastNextFileIdBean);
        model.addAttribute("file", file);
        return "imageDetail";
    }*/

    @GetMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable("fileId") Integer fileId, HttpServletResponse response, HttpSession session) throws Exception {
        //获取文件信息
        Pictures myfile = picturesService.findFileById(fileId);
        //获取文件的路径
        String path = diskDir;
        //获取文件输入流
        FileInputStream inputStream = new FileInputStream(new File(path,myfile.getSavefilename()));
        //附件下载（不设置为默认在线打开）
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(myfile.getRealfilename(),"utf-8"));
        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(inputStream,outputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        //修改文件的下载次数
        picturesService.setDownLoadCounts(fileId);
    }

    @RequestMapping(value = "/downloadFiles",  produces = "application/json;charset=UTF-8")
    public void downloadFiles(int[] fileIds, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        //生成日期对象
        Date current_date = new Date();
        //设置日期格式化样式为：yyyy-MM-dd
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String zipfilename = simpleDateFormat.format(current_date);
        //设置清除缓存
        response.reset();
        // 不同类型的文件对应不同的MIME类型
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(zipfilename,"utf-8") + ".zip");

        // ZipOutputStream类：完成文件或文件夹的压缩
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        byte[] buf = new byte[1024];

        for (Integer id : fileIds) {
            //获取文件信息
            Pictures myfile = picturesService.findFileById(id);
            //获取文件的路径
            String path = diskDir;
            //创建文件对象
            File file = new File(path,myfile.getSavefilename());

            //创建输入流
            FileInputStream inputStream = new FileInputStream(file);
            // 给列表中的文件单独命名
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, len);
            }
            zipOutputStream.closeEntry();
            inputStream.close();
            //修改文件的下载次数
            picturesService.setDownLoadCounts(id);
        }
        zipOutputStream.close();
    }

    @RequestMapping("/deleteFileByIdCompletely/{fileId}")
    public String deleteFileByIdCompletely(@PathVariable("fileId") Integer fileId
            , HttpServletRequest request
            , HttpSession session) throws JsonProcessingException {
        Pictures delfile = picturesService.findFileById(fileId);
        //创建删除文件对象
        File file = new File(diskDir, delfile.getSavefilename());
        //进行删除
        if (file.exists())file.delete();
        picturesService.deleteFileById(delfile);
        return "redirect:/";
    }

    /**
     * 图像公开/取消
     * @param publish
     * @param fileIds
     * @return
     */
    @RequestMapping("/publish/{publish}")
    public ResultInfo publishPic(@PathVariable("publish") Boolean publish, Integer[] fileIds){
        boolean update = picturesService.setPublishedByIds(fileIds, publish);
        return new ResultInfo(publish?"公开成功！":"取消公开成功！",!update, !update?"更新失败":"");
    }
}

