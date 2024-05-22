package com.meteo.models;

public class UserCity {
    private int id;
    private int userId;
    private String name;
    private String placeId;
    private String lat;
    private String lng;

    // Constructor
    public UserCity(int id, int userId, String name, String placeId, String lat, String lng) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }
    
    public String getLat() {
        return lat;
    }
    
    public String getLng() {
        return lng;
    }
}
