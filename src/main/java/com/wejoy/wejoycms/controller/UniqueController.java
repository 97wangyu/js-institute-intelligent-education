package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.service.ArticleService;
import com.wejoyclass.core.util.CtrlUtil;
import com.wejoyclass.core.util.RespEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "特殊Controller")
@RequestMapping("/unique")
public class UniqueController {
    @Autowired
    ArticleService articleService;

    @Value("${picturepath.nginx-url}")
    String nginxUrl;

    @ApiOperation("根据栏目id，增长系数，页数返回文章")
    @GetMapping("/getArticleForSubject")
    public RespEntity<List<TArticle>> getArticleForSubject(@RequestParam("id") Long id, @RequestParam("coefficient") Integer coefficient, @RequestParam("page") Integer page){

        return CtrlUtil.exe(r -> {
            r.setVal(articleService.getArticleForSubject(id,coefficient,page));
        });
    }

    @ApiOperation("获取nginx地址")
    @GetMapping("/getNginxUrl")
    public RespEntity<String> getNginxUrl(){
        return CtrlUtil.exe(r ->r.setVal(nginxUrl));
    }
}
