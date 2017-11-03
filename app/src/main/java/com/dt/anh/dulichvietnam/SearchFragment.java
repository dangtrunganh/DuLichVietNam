package com.dt.anh.dulichvietnam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

public class SearchFragment extends Fragment {
    private View view;
    private SearchView searchView;

    public SearchFragment() {
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
        view = inflater.inflate(R.layout.fragment_search, container, false);

        initViews();
        return view;
    }

    private void initViews() {
        searchView = (SearchView) view.findViewById(R.id.sv_search_fragment);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }
}
