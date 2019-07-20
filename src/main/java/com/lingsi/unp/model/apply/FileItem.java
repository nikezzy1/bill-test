package com.lingsi.unp.model.apply;


public class FileItem{

    Integer itemNo;
    String itemName;
    String fileData;

    public FileItem(int itemNo, String itemName, String fileData){
        this.itemNo = itemNo;
        this.itemName=itemName;
        this.fileData=fileData;
    }
    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}