package com.tony.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastNextFileIdBean {
    private Integer lastFileId;
    private Integer nextFileId;
}
