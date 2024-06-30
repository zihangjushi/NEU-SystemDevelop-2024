package com.NEUSystemDevelop2024.controller;


import com.NEUSystemDevelop2024.biz.NewsBiz;
import com.NEUSystemDevelop2024.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsBiz biz;


    @PostMapping("/add")
    public Map addNews(@RequestBody Map<String, Object> request) {

        String title = (String) request.get("title");
        String image = (String) request.get("imageUrl");
        String content = (String) request.get("content");
        String author = (String) request.get("author");
        String introduction = (String) request.get("summary");
        String companyName = (String) request.get("tenant");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = now.format(formatter);

        News news = new News(null, title, image, content, author, introduction, companyName, createTime);
        Map res = new HashMap();
        try {
            int result = biz.addNews(news);
            if (result > 0) {
                res.put("isOk", true);
            } else {
                res.put("isOk", false);
            }
        } catch (Exception e) {
            res.put("isOk",false);
        }

        return res;
    }

    @RequestMapping("/getnews")
    public Map getNews() {

        List<News> news = biz.listNews();

        Map map = new HashMap();
        map.put("isOk",true);
        map.put("news",news);


        return map;


    }


    @RequestMapping("/mynews")
    public Map getMyNews(@RequestParam String companyName) {


//        String companyName = (String)res.get("companyName");
        List<News> news = biz.findNewsBycompanyName(companyName);

        Map map = new HashMap();
        map.put("isOk",true);
        map.put("news",news);


        return map;


    }

    @RequestMapping("/main")
    public Map getNewsByPage(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "10") int pageSize) {
        return biz.findNewsByPage(pageNum, pageSize);
    }

    @RequestMapping("/delete")
    public Map deleteNews(@RequestBody Map request) {
        Map res = new HashMap();

        List<Integer> ids = (List<Integer>) request.get("ids");
        int deletedCount = biz.deleteNewsByIds(ids);

        res.put("isOk",true);
        return res;
    }

    @RequestMapping("/getEditNew")
    public Map getNew(@RequestParam("newsId") Integer newsId) {
        Map res = new HashMap();


        News editNew = biz.findNewsById(newsId);

        res.put("isOk",true);
        res.put("editNew",editNew);

        return res;
    }

    @RequestMapping("/edit")
    public Map editNews(@RequestBody Map request) {

        String title = (String) request.get("title");
        String image = (String) request.get("imageUrl");
        String content = (String) request.get("content");
        String author = (String) request.get("author");
        String introduction = (String) request.get("summary");
        String companyName = (String) request.get("tenant");

        int newsId = (int) request.get("newsId");

        News news = new News(newsId, title, image, content, author, introduction, companyName, null);
        Map res = new HashMap();
        try {
            int result = biz.updateNews(news);
            if (result > 0) {
                res.put("isOk", true);
            } else {
                res.put("isOk", false);
            }
        } catch (Exception e) {
            res.put("isOk",false);
        }
        return res;
    }


}
