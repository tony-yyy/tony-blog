package com.tony.blog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// 月图片数量
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicturesDates {
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
   private Date date;
   private int count;
   private List<Pictures> pics;
}
