package com.example.myapp;

public class thing {
    private String Price;
    private String Category;
    private String Description;
    private String Thing_title;
    private String Image_url1;
    private String Image_url2;
    private String Image_url3;
    private String Hostel_block;
    private String Item_id;

    public thing() {
    }

    public thing(String price, String category, String description, String image_url1, String thing_title,String image_url2,String image_url3,String hostel_block,String item_id) {
        Price = price;
        Category = category;
        Description = description;
        Image_url1 = image_url1;
        Thing_title=thing_title;
        Image_url2 = image_url2;
        Image_url3 = image_url3;
        Hostel_block = hostel_block;
        Item_id= item_id;
    }

    public String getPrice() {
        return Price;
    }
    public String getItem_id() {
        return Item_id;
    }
    public String getThing_title() {
        return Thing_title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage_url1() {
        return Image_url1;
    }

    public String getImage_url2() {
        return Image_url2;
    }
    public String getImage_url3() {
        return Image_url3;
    }
    public String getHostel_block() {
        return Hostel_block;
    }

    public void setPrice(String price) {
        Price = price;
    }
    public void setThing_title(String thing_title) {
        Thing_title=thing_title;
    }

    public void setCategory(String category) {
        Category = category;
    }
    public void setItem_id(String item_id) {
        Item_id = item_id;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage_url1(String image_url1) {
        Image_url1 = image_url1;
    }

    public void setImage_url2(String image_url2) {
        Image_url2 = image_url2;
    }
    public void setImage_url3(String image_url3) {
        Image_url3 = image_url3;
    }
    public void setHostel_block(String hostel_block) {
        Hostel_block = hostel_block;
    }
}
