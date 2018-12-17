package youthshelter.youth.co.kr.task;

import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import youthshelter.youth.co.kr.adapter.AddressListViewAdapter;

public class AddressAddTask extends AsyncTask<Object, String, Object[]> {
    private char[] buffer = new char[60000];
    private int pixel = 0;
    private int count = 0;

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(Object[] values) {
        AddressListViewAdapter addressListViewAdapter = (AddressListViewAdapter)values[0];
        addressListViewAdapter.notifyDataSetChanged();
        pixel = addressListViewAdapter.getPixel();

        //Log.i("@@@@@@@@@@@###@@@",pixel+" :: ? "+ count);
        ((ListView) values[2]).getLayoutParams().height = (int) ((((float) ((DisplayMetrics) values[1]).densityDpi) / 160.0f) * ((float)90.5)*count);
        ((ListView) values[2]).requestLayout();
    }

    protected Object[] doInBackground(Object... params) {
        AddressAddTask addressAddTask = this;
        try {
            String query = String.format("query=%s&display=30", new Object[]{URLEncoder.encode(((EditText) params[0]).getText().toString(), "UTF-8")});
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://openapi.naver.com/v1/search/local.json");
            stringBuilder.append("?");
            stringBuilder.append(query);
            HttpsURLConnection conn = (HttpsURLConnection) new URL(stringBuilder.toString()).openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Naver-Client-Id", "ILqZQkHkEjOBbNUnSQsS");
            conn.setRequestProperty("X-Naver-Client-Secret", "bWxAwq4Oy3");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            InputStream inputStream = conn.getInputStream();
            String returnString = convertInputStreamToString(inputStream);
            inputStream.close();
            JSONArray items = new JSONObject(returnString).getJSONArray("items");
            AddressListViewAdapter addressListViewAdapter = (AddressListViewAdapter)params[1];
            int i = 0;
            while (i < items.length()) {
                JSONObject item = items.getJSONObject(i);
                String query2 = query;
                Log.i("######!@#!@$!@#!@",item.getString("mapx")+","+item.getString("mapy"));
                addressListViewAdapter.addItem(removeHtml(item.getString("title")), item.getString("address"), item.getString("roadAddress"), item.getDouble("mapx"), item.getDouble("mapy"));
                i++;
                query = query2;
            }
            count = addressListViewAdapter.getCount();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return new Object[]{params[1],params[2], params[3]};
    }

    public String convertInputStreamToString(InputStream stream) throws IOException, UnsupportedEncodingException {
        new InputStreamReader(stream, "UTF-8").read(this.buffer);
        return new String(this.buffer);
    }

    private String removeHtml(String html) {
        return html.replaceAll("<(.*?)\\>", "").replaceAll("<(.*?)\\\n", "").replaceFirst("(.*?)\\>", "").replaceAll("&nbsp;", " ").replaceAll("&amp;", " ");
    }
}