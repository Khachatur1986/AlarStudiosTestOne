package example.am.alarstudiostestone.models;

import java.io.Serializable;

/**
 * Created by Khach on 31-Jul-18.
 */

public class Data implements Serializable {
    private int ImageResourceId;
    private String id;
    private String lon;
    private String lat;
    private String country;
    private String name;

    public Data() {
    }

    public Data(int imageResourceId, String name) {
        ImageResourceId = imageResourceId;
        this.name = name;
    }

    public int getImageResourceId() {
        return ImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        ImageResourceId = imageResourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
