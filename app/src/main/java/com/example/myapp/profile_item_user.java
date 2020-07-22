package com.example.myapp;

public class profile_item_user {
    private String Price1;
    private String Thing_title1;
    private String Thumbnail1;
    private int Item_id;

    public profile_item_user() {
    }

    public profile_item_user(String price1, String thing_title1, String thumbnail1,int item_id) {
        Price1 = price1;
        Thing_title1 = thing_title1;
        Thumbnail1 = thumbnail1;
        Item_id= item_id;

    }

    public String getPrice1() {
        return Price1;
    }

    public String getThing_title1() {
        return Thing_title1;
    }

    public String getThumbnail1() {
        return Thumbnail1;
    }

    public int getItem_id() {
        return Item_id;
    }

    public void setPrice1(String price1) {
        Price1 = price1;
    }

    public void setThing_title1(String thing_title1) {
        Thing_title1 = thing_title1;
    }

    public void setThumbnail1(String thumbnail1) {
        Thumbnail1 = thumbnail1;
    }

    public void setItem_id(int item_id){
        Item_id = item_id;
    }
}
