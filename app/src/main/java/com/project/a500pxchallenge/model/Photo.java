package com.project.a500pxchallenge.model;

import java.util.ArrayList;

public class Photo {
    public String current_page;
    public ArrayList<Item> photos;
    public class Item {
        public String feature;
        public ArrayList<Url> images;
    }
    public class Url {
        public String https_url;
    }

}
