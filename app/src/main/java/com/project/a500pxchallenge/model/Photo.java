package com.project.a500pxchallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Photo {
    public String current_page;
    public int total_pages;
    public List<Item> photos;

    public class Item implements Serializable{
        public String name;
        public String description;
        public String feature;
        public ArrayList<Url> images;
    }

    public class Url implements Serializable{
        public String https_url;
    }

}
