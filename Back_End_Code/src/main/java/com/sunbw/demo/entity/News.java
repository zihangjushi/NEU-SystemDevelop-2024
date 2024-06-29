package com.sunbw.demo.entity;


public class News {
    private Integer newsId;
    private String title;
    private String image;
    private String content;
    private String author;
    private String introduction;
    private String companyName;
    private String createTime;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public News(Integer newsId, String title, String image, String content, String author, String introduction, String companyName, String createTime) {
        this.newsId = newsId;
        this.title = title;
        this.image = image;
        this.content = content;
        this.author = author;
        this.introduction = introduction;
        this.companyName = companyName;
        this.createTime = createTime;
    }

    public News(){

    }
}
