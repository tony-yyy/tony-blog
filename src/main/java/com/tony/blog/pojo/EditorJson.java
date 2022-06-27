package com.tony.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorJson {
    /**
     * editormd上传图片返回的json数据
     */
    private int success;  // 后端是否处理成功 1成功 0失败
    private String message; // 提示信息
    private String url; // 成功后的url地址（图片存储在服务器的地址，相对路径）
}
