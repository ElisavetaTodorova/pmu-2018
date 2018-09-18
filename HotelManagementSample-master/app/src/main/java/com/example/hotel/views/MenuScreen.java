package com.example.hotel.views;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;


public class MenuScreen extends ExpandableListActivity {
    //Expandable list is used to display data

    ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
        getExpandableListView().setCacheColorHint(5);

        getExpandableListView().setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(MenuScreen.this, ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        getExpandableListView().setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(MenuScreen.this, ((TextView) v).getText().toString(), Toast.LENGTH_LONG).show();
                String s = ((TextView) v).getText().toString();
                Intent i = new Intent(MenuScreen.this, OrderScreen.class);
                i.putExtra("order", s);
                startActivity(i);
                return false;
            }
        });

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, R.string.app_name);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }


    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        //items are defined here
        private String[] groups = {"Sofia", "Varna", "Plovdiv", "Burgas", "Stara Zagora", "Veliko Tarnovo"};
        private String[][] children = {
                {"Hilton Hotel", "Sheraton", "Andrea di Serdica", "Sense Hotel Sofia", "Les Fleurs Boutique Hotel"},
                {"Swiss-Belhotel Dimyat Varna", "Marina Residence Boutique Hotel", "Varna Palace Hotel", "Panorama Hotel"},
                {"Boris Palace", "Hebros Hotel", "Alafrangite Hotel", "Evmolpia Hotel", "Landmark Creek Hotel & Spa", "Alliance Hotel"},
                {"Primoretz Grand Hotel & SPA", "Hotel Bulgaria Burgas", "Hotel Burgas", "Avenue Hotel", "Hotel Luxor"},
                {"Forum Hotel", "Calista Spa Hotel", "Hotel Vereya", "Park Hotel Stara Zagora"},
                {"Hotel Stambolov", "Yantra Grand Hotels", "Hotel Central", "Hotel Tarnava"},
        };


        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView textView = new TextView(MenuScreen.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);

            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }

}