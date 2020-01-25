package com.example.test_four;

public class Repositories {
    private String name;
    private String owner;
    private String language;
    private int forks;
    private int starts;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public String getName() {
        return name;
    }

    public int getForks() {
        return forks;
    }

    public int getStarts() {
        return starts;
    }

    public String getLanguage() {
        return language;
    }

    public String getOwner() {
        return owner;
    }
}

