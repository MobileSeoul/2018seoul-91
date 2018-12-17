package youthshelter.youth.co.kr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.item.ListViewItem;

public class AddressListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList();
    private int pixel = 0;

    public int getPixel(){
        return pixel;
    }

    public int getCount() {
        return this.listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1_title);
        TextView addressTextView = (TextView) convertView.findViewById(R.id.textView2_address);
        TextView roadAddressTextView = (TextView) convertView.findViewById(R.id.textView3_address);

        ListViewItem listViewItem = (ListViewItem) this.listViewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        addressTextView.setText(listViewItem.getAddress());
        roadAddressTextView.setText(listViewItem.getRoadAddress());

        pixel = convertView.getLayoutParams().height;
        //Log.i("@@@@@@@@@@@@@@@@@@@@2",pixel+" :: ? ");

        return convertView;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Object getItem(int position) {
        return this.listViewItemList.get(position);
    }

    public void addItem(String title, String address, String roadAddress, double mapx, double mapy) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        item.setAddress(address);
        item.setRoadAddress(roadAddress);
        item.setMapx(mapx);
        item.setMapy(mapy);
        this.listViewItemList.add(item);
    }

    public void clear() {
        this.listViewItemList.clear();
    }
}
