package com.elsy.rynder.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;


public class Restaurant implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("address")
    private String address;

    @SerializedName("type")
    private String type;

    @SerializedName("image")
    private String image;

    @SerializedName("is_favourite")
    private boolean isFavorite;

    @SerializedName("avg_price")
    private double averagePrice;

    @SerializedName("phone_numbers")
    private ArrayList<RestaurantPhone> restaurantPhoneNumbers;

    @SerializedName("schedules")
    private ArrayList<Schedule> schedules;

    @SerializedName("menus")
    private ArrayList<Menu> menus;

    @SerializedName("packs")
    private ArrayList<FoodPack> packs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLocationLat() {
        return latitude;
    }

    public void setLocationLat(double latitude) {
        this.latitude = latitude;
    }

    public double getLocationLng() {
        return longitude;
    }

    public void setLocationLng(double longitude) {
        this.longitude = longitude;
    }

    public String getProfileImage() {
        return image;
    }

    public void setProfileImage(String image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public ArrayList<RestaurantPhone> getRestaurantPhoneNumbers() {
        return restaurantPhoneNumbers;
    }

    public void setRestaurantPhoneNumbers(ArrayList<RestaurantPhone> restaurantPhoneNumbers) {
        this.restaurantPhoneNumbers = restaurantPhoneNumbers;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public ArrayList<FoodPack> getPacks() {
        return packs;
    }

    public void setPacks(ArrayList<FoodPack> packs) {
        this.packs = packs;
    }
}
