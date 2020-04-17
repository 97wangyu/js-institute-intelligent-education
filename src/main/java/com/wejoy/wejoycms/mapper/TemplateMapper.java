package com.wejoy.wejoycms.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateMapper {
    String getPathById(Long id);
    //根据模板code获取模板路径
    String getPathByCode(String code);
    //根据文章id获取模板路径
    String getPathByArticleId(Long id);
}
