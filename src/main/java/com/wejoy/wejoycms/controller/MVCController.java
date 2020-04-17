package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.entity.TArticle;
import com.wejoy.wejoycms.entity.TBanner;
import com.wejoy.wejoycms.entity.TFriendlyLink;
import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MVCController {
    @Autowired
    SubjectService subjectService;

    @Autowired
    BannerService bannerService;

    @Autowired
    ArticleService articleService;

    @Autowired
    TemplateService templateService;

    @Autowired
    FriendlyLinkService friendlyLinkService;

    @Autowired
    UniquePageService uniquePageService;

    @Value("${picturepath.nginx-url}")
    String nginxUrl;

    @RequestMapping("/subject/{code}")
    String frontController(Model model, HttpSession httpSession, @PathVariable("code") String code){
        //获取模板路径
        String path = templateService.getPathByCode(code);
        setCommenModel(model, code);
        httpSession.setAttribute("nginxUrl",nginxUrl);
        return path;
    }

    @RequestMapping("/article/{id}")
    String articleFrontController(Model model,HttpSession httpSession,@PathVariable("id") Long id){
        //获取模板路径
        String path = templateService.getPathByArticleId(id);
        setArticleModel(model, id);
        httpSession.setAttribute("nginxUrl",nginxUrl);
        return path;
    }

    @RequestMapping("/list/{id}")
    String listController(Model model, HttpSession httpSession, @PathVariable("id") Long id){
        //获取模板路径
        String path = templateService.getPathById(subjectService.getSubjectById(id).getTemplateId());
        setCommenModelById(model, id);
        httpSession.setAttribute("nginxUrl",nginxUrl);
        return path;
    }

    @RequestMapping("/login")
    String login(){
        return "pages/security/login.html";
    }

    private void setCommenModel(Model model,String code){
        //获取所有一级栏目
        List<TSubject> parentSubject = subjectService.getAllParentSubjectS();
        //根据栏目code获取子栏目列表及栏目信息
        Map<String, Object> msgAndKid = subjectService.getSubjectSByCode(code);
        //根据链接分类code获取所有链接信息
        List<TFriendlyLink> commonLink = friendlyLinkService.getLinkByCode("CommonLink");
        List<TFriendlyLink> cooperate = friendlyLinkService.getLinkByCode("Cooperate");
        if(code.equals("home")){
            List<TBanner> allBannerS = bannerService.getAllOpenBannerS();
            model.addAttribute("allBannerS",allBannerS);
            //获取指定栏目信息及图片及文章
            List<Long> idList = new ArrayList<>();
            idList.add(new Long(94));
            idList.add(new Long(95));
            idList.add(new Long(96));
            idList.add(new Long(97));
            idList.add(new Long(98));
            idList.add(new Long(99));
            idList.add(new Long(100));
            Map<String,Object> someSubject = uniquePageService.getSomeSubject(idList);
            model.addAttribute("someSubject",someSubject);
        }
        if(code.equals("vision")){
            //获取指定栏目信息及图片及文章
            List<Long> idList = new ArrayList<>();
            idList.add(new Long(88));
            idList.add(new Long(89));
            idList.add(new Long(90));
            idList.add(new Long(91));
            idList.add(new Long(92));
            idList.add(new Long(93));
            Map<String,Object> someSubject = uniquePageService.getSomeSubject(idList);
            model.addAttribute("someSubject",someSubject);
        }
        if(code.equals("overview")){
            //获取指定栏目信息及图片及文章
            List<Long> idList = new ArrayList<>();
            idList.add(new Long(12));
            idList.add(new Long(13));
            idList.add(new Long(14));
            idList.add(new Long(15));
            idList.add(new Long(16));
            idList.add(new Long(17));
            idList.add(new Long(19));
            Map<String,Object> someSubject = uniquePageService.getSomeSubject(idList);
            model.addAttribute("someSubject",someSubject);
            List<TFriendlyLink> incubate = friendlyLinkService.getLinkByCode("Incubate");
            model.addAttribute("incubate",incubate);
        }
        if(code.equals("incubate")){
            List<TFriendlyLink> incubate = friendlyLinkService.getLinkByCode("Incubate");
            model.addAttribute("incubate",incubate);
        }
        model.addAttribute("commonLink",commonLink);
        model.addAttribute("cooperate",cooperate);
        model.addAttribute("msgAndKid",msgAndKid);
        model.addAttribute("parentSubject",parentSubject);
    }

    private void setCommenModelById(Model model,Long id){
        //获取所有一级栏目
        List<TSubject> parentSubject = subjectService.getAllParentSubjectS();
        //根据栏目code获取子栏目列表及栏目信息
        List<TArticle> articleList = articleService.getArticleSBySubjectId(id);
        //todo 获取顶级栏目信息(现在是父级)
        TSubject psubject = subjectService.getSubjectById(subjectService.getSubjectById(id).getPId());
        //根据链接分类code获取所有链接信息
        List<TFriendlyLink> commonLink = friendlyLinkService.getLinkByCode("CommonLink");
        List<TFriendlyLink> cooperate = friendlyLinkService.getLinkByCode("Cooperate");
        model.addAttribute("commonLink",commonLink);
        model.addAttribute("cooperate",cooperate);
        model.addAttribute("articleList",articleList);
        model.addAttribute("psubject",psubject);
        model.addAttribute("parentSubject",parentSubject);
    }

    private void setArticleModel(Model model,Long id){
        //获取所有一级栏目
        List<TSubject> parentSubject = subjectService.getAllParentSubjectS();
        //根据链接分类code获取所有链接信息
        List<TFriendlyLink> commonLink = friendlyLinkService.getLinkByCode("CommonLink");
        List<TFriendlyLink> cooperate = friendlyLinkService.getLinkByCode("Cooperate");
        TArticle article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        model.addAttribute("commonLink",commonLink);
        model.addAttribute("cooperate",cooperate);
        model.addAttribute("parentSubject",parentSubject);
    }

}
