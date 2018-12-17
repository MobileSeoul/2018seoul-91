package youthshelter.youth.co.kr.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import youthshelter.youth.co.kr.activity.MainActivity;

public class GeocodeUtil {
    final Geocoder geocoder;

    public static class GeoLocation {

        double latitude;
        double longitude;

        public GeoLocation(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    private LocationManager locManager;
    private Location myLocation;

    public GeocodeUtil(Context context) {
        geocoder = new Geocoder(context, Locale.KOREAN);

    }

  public ArrayList<GeoLocation> getGeoLocationListUsingAddress(String address) {
    ArrayList<GeoLocation> resultList = new ArrayList<>();
    try {
      List<Address> list = geocoder.getFromLocationName(address, 10);

      for (Address addr : list) {
        resultList.add(new GeoLocation(addr.getLatitude(), addr.getLongitude()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return resultList;
  }

  public ArrayList<Address> getAddressListUsingGeolocation(GeoLocation location) {
    ArrayList<Address> resultList = new ArrayList<>();

    try {
      List<Address> list = geocoder.getFromLocation(location.latitude, location.longitude, 10);

      for (Address addr : list) {
        resultList.add(addr);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return resultList;
  }
}