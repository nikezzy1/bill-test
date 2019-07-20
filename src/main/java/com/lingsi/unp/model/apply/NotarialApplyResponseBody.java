package com.lingsi.unp.model.apply;


import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotarialApplyResponseBody {
    @ApiModelProperty(value="申请流水号,系统唯一",required=true)
    String applySerialno;


    @ApiModelProperty(value="文件列表",required=true)
    HashMap<String, List<FileItem>> fileList;

    public String getApplySerialno() {
        return applySerialno;
    }

    public void setApplySerialno(String applySerialno) {
        this.applySerialno = applySerialno;
    }

    public HashMap<String, List<FileItem>> getFileList() {
        return fileList;
    }

    public void setFileList(HashMap<String, List<FileItem>> fileList) {
        this.fileList = fileList;
    }
}
