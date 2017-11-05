package com.dt.anh.dulichvietnam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.dt.anh.dulichvietnam.adapter.PlaceAdapter;
import com.dt.anh.dulichvietnam.adapter.ProvinceAdapter;
import com.dt.anh.dulichvietnam.model.Place;
import com.dt.anh.dulichvietnam.model.Province;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private TabHost mTabHost;
    private View view;

    private RecyclerView mRecyclerViewMonth, //Month là theo tỉnh
            mRecyclerViewDay, //Day (trong ngày) là theo địa danh (Place)
            mRecyclerViewBeach, //Beach là theo địa danh
            mRecyclerMountain; //Mountain là theo địa danh
    private ArrayList<Province> arrayProvince;
    private ArrayList<Place> arrayPlaceBeach, arrayPlaceDay, arrayPlaceMountain;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapterPlaceDay, mAdapterProvinceMonth, mAdapterBeach, mAdapterMountain;

    private DatabaseReference mData;
    private TextView tvProvinceMonth, tvBeach, tvOneDay, tvMountain;

    private LinearLayout lnlNorthArea, lnlMidArea, lnlSouthArea;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        initDataDay();
        initDataMonth();
        initDataBeach();
        initDataMountain();
        loadTab();
        return view;
    }

    private void initViews() {
        mTabHost = (TabHost) view.findViewById(R.id.tabhost);
        mRecyclerViewMonth = (RecyclerView) view.findViewById(R.id.rv_salient_point_month);
        mRecyclerViewMonth.setHasFixedSize(true);
        mRecyclerViewDay = (RecyclerView) view.findViewById(R.id.rv_salient_point_one_day);
        mRecyclerViewDay.setHasFixedSize(true);
        mRecyclerViewBeach = (RecyclerView) view.findViewById(R.id.rv_salient_point_beach);
        mRecyclerViewBeach.setHasFixedSize(true);
        mRecyclerMountain = (RecyclerView) view.findViewById(R.id.rv_salient_point_mountain);
        mRecyclerMountain.setHasFixedSize(true);

        tvProvinceMonth = (TextView) view.findViewById(R.id.tv_salient_all_province_month);
        tvBeach = (TextView) view.findViewById(R.id.tv_salient_all_place_beach);
        tvOneDay = (TextView) view.findViewById(R.id.tv_salient_all_place_one_day);
        tvMountain = (TextView) view.findViewById(R.id.tv_salient_all_place_mountain);

        lnlNorthArea = (LinearLayout) view.findViewById(R.id.lnl_north_area);
        lnlMidArea = (LinearLayout) view.findViewById(R.id.lnl_mid_area);
        lnlSouthArea = (LinearLayout) view.findViewById(R.id.lnl_south_area);

        lnlNorthArea.setOnClickListener(this);
        lnlMidArea.setOnClickListener(this);
        lnlSouthArea.setOnClickListener(this);

        tvProvinceMonth.setOnClickListener(this);
        tvBeach.setOnClickListener(this);
        tvOneDay.setOnClickListener(this);
        tvMountain.setOnClickListener(this);
//        mRecyclerViewMonth.setLayoutManager(layoutManager);
//        mRecyclerViewBeach.setLayoutManager(layoutManager);
//        mRecyclerMountain.setLayoutManager(layoutManager);
    }

    private void initDataBeach() {
        arrayPlaceBeach = new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        link.add("abc");
        Place placeTmp = new Place("Hạ Long Bay1", link, 5, "ahihi du lịch Nha Trang vui lắm.");
        arrayPlaceBeach.add(placeTmp);
        placeTmp = new Place("Nha Trang Bay2", link, 5, "ahihi du lịch Nha Trang vui lắm.");
        arrayPlaceBeach.add(placeTmp);
        arrayPlaceBeach.add(placeTmp);
        arrayPlaceBeach.add(placeTmp);
        arrayPlaceBeach.add(placeTmp);
        arrayPlaceBeach.add(placeTmp);
        arrayPlaceBeach.add(placeTmp);

        mAdapterBeach = new PlaceAdapter(getActivity().getBaseContext(), arrayPlaceBeach, false);
        mRecyclerViewBeach.setAdapter(mAdapterBeach);

        layoutManager
                = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewBeach.setLayoutManager(layoutManager);
    }

    private void initDataMountain() {
        arrayPlaceMountain = new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        link.add("abc");
        Place placeTmp = new Place("Núi Bài Thơ1", link, 5, "ahihi du lịch núi vui lắm.");
        arrayPlaceMountain.add(placeTmp);
        placeTmp = new Place("Núi Bài Thơ2", link, 5, "ahihi du lịch núi vui lắm.");
        arrayPlaceMountain.add(placeTmp);
        arrayPlaceMountain.add(placeTmp);
        arrayPlaceMountain.add(placeTmp);
        arrayPlaceMountain.add(placeTmp);
        arrayPlaceMountain.add(placeTmp);
        arrayPlaceMountain.add(placeTmp);

        mAdapterMountain = new PlaceAdapter(getActivity().getBaseContext(), arrayPlaceMountain, false);
        mRecyclerMountain.setAdapter(mAdapterMountain);

        layoutManager
                = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerMountain.setLayoutManager(layoutManager);
    }

    private void initDataMonth() {
        arrayProvince = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference();

        //===============================
        //set dữ liệu vào adapter
        mAdapterProvinceMonth = new ProvinceAdapter(getActivity().getBaseContext(), arrayProvince);
        mRecyclerViewMonth.setAdapter(mAdapterProvinceMonth);

        layoutManager
                = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewMonth.setLayoutManager(layoutManager);
        mData.child("Province").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Province province = dataSnapshot.getValue(Province.class);
                arrayProvince.add(province);
                mAdapterProvinceMonth.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void initDataDay() {
        arrayPlaceDay = new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        link.add("abc");
        Place placeTmp = new Place("Hạ Long Bay1", link, 5, "ahihi du lịch hạ long vui lắm.");
        arrayPlaceDay.add(placeTmp);
        placeTmp = new Place("Hạ Long Bay2", link, 5, "ahihi du lịch hạ long vui lắm.");
        arrayPlaceDay.add(placeTmp);
        arrayPlaceDay.add(placeTmp);
        arrayPlaceDay.add(placeTmp);
        arrayPlaceDay.add(placeTmp);
        arrayPlaceDay.add(placeTmp);
        arrayPlaceDay.add(placeTmp);

        mAdapterPlaceDay = new PlaceAdapter(getActivity().getBaseContext(), arrayPlaceDay, false);
        mRecyclerViewDay.setAdapter(mAdapterPlaceDay);

        layoutManager
                = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewDay.setLayoutManager(layoutManager);
//        mRecyclerViewDay.addItemDecoration(new DividerItemDecoration(mRecyclerViewDay.getContext(),
//                DividerItemDecoration.HORIZONTAL));
//        mRecyclerViewDay.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadTab() {
        mTabHost.setup();

        TabHost.TabSpec spec;

        //Tạo tab 1:
        spec = mTabHost.newTabSpec("T1");
        spec.setContent(R.id.salient_point);
        spec.setIndicator("Nổi bật");
        mTabHost.addTab(spec);

        //Tạo tab 2:
        spec = mTabHost.newTabSpec("T2");
        spec.setContent(R.id.place);
        spec.setIndicator("Địa danh");
        mTabHost.addTab(spec);

        //Thiết lập tab mặc định được chọn ban đầu là 0
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String s = "Tab tag = " + tabId + "; index = " +
                        mTabHost.getCurrentTab();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_salient_all_province_month:
//                startActivity(new Intent(getActivity().getBaseContext(), ListPlaceActivity.class));
                break;
            case R.id.tv_salient_all_place_beach:
                startActivity(new Intent(getActivity().getBaseContext(), ListPlaceActivity.class));
                break;
            case R.id.tv_salient_all_place_one_day:
                startActivity(new Intent(getActivity().getBaseContext(), ListPlaceActivity.class));
                break;
            case R.id.tv_salient_all_place_mountain:
                startActivity(new Intent(getActivity().getBaseContext(), ListPlaceActivity.class));
                break;
            case R.id.lnl_north_area:
                startActivity(new Intent(getActivity().getBaseContext(), DetailAreaActivity.class));
                break;
            case R.id.lnl_mid_area:
                startActivity(new Intent(getActivity().getBaseContext(), DetailAreaActivity.class));
                break;
            case R.id.lnl_south_area:
                startActivity(new Intent(getActivity().getBaseContext(), DetailAreaActivity.class));
                break;
            default:
                break;
        }
    }
}
