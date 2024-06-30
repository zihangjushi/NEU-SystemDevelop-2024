package com.NEUSystemDevelop2024.biz;


import com.NEUSystemDevelop2024.entity.News;
import com.NEUSystemDevelop2024.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsBiz {
    @Autowired
    public NewsMapper biz;

    public void setBiz(NewsMapper biz) {
        this.biz = biz;
    }

    public int addNews(News news) {
        return biz.insertNews(news);
    }

    public int updateNews(News news) {
        return biz.updateNews(news);
    }

    public List<News> listNews() {
        return biz.listNews();
    }

    public int countNews(){
        return biz.countNews();
    }

    public Map findNewsByPage(int pageNum, int pageSize){
        int total = biz.countNews();
        int startIndex = (pageNum - 1) * pageSize;

        Map map = new HashMap();
        List<News> newsList = biz.findNewsByPage(startIndex,pageSize);
        map.put("newsList",newsList);
        map.put("total",total);
        map.put("pageSize",pageSize);
        map.put("pageNum",pageNum);

        return map;
    }

    public int deleteNewsByIds(List<Integer> ids) {
        return biz.deleteNewsByIds(ids);
    }

    public News findNewsById(int id) {
        return biz.findNewsById(id);
    }

    public List<News> findNewsBycompanyName(String companyName) {
        return biz.listNewsByCompanyName(companyName);
    }


    public String getCompanynameByid(int id){
        return biz.selectCompanynameByid(id);
    }
}
