/*
package youthshelter.youth.co.kr.data;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class YouthCenter implements Serializable {
    private String imageName;
    private String centerName;
    private String centerLocation;
    private String centerPlayTime;
    private int like;
    private ArrayList<String> imagePath = new ArrayList<>();
    private String centerTel;
    private String centerWebSite;

    public YouthCenter(String imageName, String centerName, String centerLocation, String centerPlayTime, int like) {
        this.imageName = imageName;
        this.centerName = centerName;
        this.centerLocation = centerLocation;
        this.centerPlayTime = centerPlayTime;
        this.like = like;
    }
}
*/

package youthshelter.youth.co.kr.data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YouthCenter implements Serializable {
    private String address;
    private String bonus;
    private int count;
    private String format;
    private String homepage;
    private String image;
    private String introduction;
    private int like;
    private String name;
    private String phone;
    private String saturday;
    private String sunday;
    private String weekday;
    private double latitude;
    private double longitude;

    @Exclude
    private double distance;
    @Exclude
    private boolean isCalcDistance = false;
}


