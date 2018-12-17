package youthshelter.youth.co.kr.data;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CultureData implements Serializable {
    private String imageName;
    private String cultureName;
    private String cultureLocation;
    private String culturePlayTime;
    private ArrayList<String> imagePath = new ArrayList<>();
    private String cultureTel;
    private String cultureWebSite;
    private String cultureprogram;
    private String start_date;
    private String end_date;

    public CultureData(String imageName, String cultureName, String cultureLocation, String culturePlayTime, String cultureTel, String cultureWebSite, String cultureprogram, String start_date, String end_date) {
        this.imageName = imageName;
        this.cultureName = cultureName;
        this.cultureLocation = cultureLocation;
        this.culturePlayTime = culturePlayTime;
        this.cultureTel = cultureTel;
        this.cultureWebSite = cultureWebSite;
        this.cultureprogram = cultureprogram;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCultureprogram() {
        return cultureprogram;
    }

    public void setCultureprogram(String cultureprogram) {
        this.cultureprogram = cultureprogram;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCultureName() {
        return cultureName;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
    }

    public String getCultureLocation() {
        return cultureLocation;
    }

    public void setCultureLocation(String cultureLocation) {
        this.cultureLocation = cultureLocation;
    }

    public String getCulturePlayTime() {
        return culturePlayTime;
    }

    public void setCulturePlayTime(String culturePlayTime) {
        this.culturePlayTime = culturePlayTime;
    }

    public ArrayList<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(ArrayList<String> imagePath) {
        this.imagePath = imagePath;
    }

    public String getCultureTel() {
        return cultureTel;
    }

    public void setCultureTel(String cultureTel) {
        this.cultureTel = cultureTel;
    }

    public String getCultureWebSite() {
        return cultureWebSite;
    }

    public void setCultureWebSite(String cultureWebSite) {
        this.cultureWebSite = cultureWebSite;
    }
}
