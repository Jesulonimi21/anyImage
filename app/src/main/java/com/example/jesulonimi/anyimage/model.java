package com.example.jesulonimi.anyimage;

public class model {
   private String imageUrl;
private String cName;
private  int likes;

    public model() {
    }

    public model(String imageUrl, int likes, String cName) {
        this.imageUrl = imageUrl;
        this.cName=cName;
        this.likes=likes;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
