package com.example.infernoinfosec.recycle_view;

public class model extends PostId{
    String headline,purl,description,postedOn,key;

    public model() {
    }

    public model(String headline, String purl, String description, String postedOn, String key) {
        this.headline = headline;
        this.purl = purl;
        this.description = description;
        this.postedOn = postedOn;
        this.key = key;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
