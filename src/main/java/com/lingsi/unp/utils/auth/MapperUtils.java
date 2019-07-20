package com.lingsi.unp.utils.auth;

import com.lingsi.unp.mapper.auth.AppMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 好像不行啊
 */
@Component
public class MapperUtils {
    @Autowired
    private static AppMenuMapper menuMapper;

    public static MapperUtils mapperUtils;

    @PostConstruct
    public void init(){
        mapperUtils=this;
        mapperUtils.menuMapper=this.menuMapper;
    }
    public static MapperUtils getMapperUtils(){
        return mapperUtils;
    }
}
