package com.dt.anh.dulichvietnam;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {
    private static final String TAG = MainActivity.class.getName();
    private BottomNavigationView bottomNavigation;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        setStatusBarColor(R.color.colorMainActDark);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
        disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        callFragment(new HomeFragment());
    }

    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to change value of shift mode");
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                callFragment(new HomeFragment());
//                Utils.startFragment(getSupportFragmentManager(), ContentFragment.newInstance("ONE"));
                break;
            case R.id.action_search:
                callFragment(new SearchFragment());
                break;
//            case R.id.action_add:
//                Toast.makeText(this, "Click Add New", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.action_favorite:
                callFragment(new FavoriteFragment());
                break;
            case R.id.action_other:
                callFragment(new OtherFragment());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem itemSearch = menu.findItem(R.id.item_menu_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
//        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Bạn muốn đi đâu?");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_search:
                search();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void search() {
        Toast.makeText(this, "CLICK SEARCH", Toast.LENGTH_SHORT).show();
    }

    private void setStatusBarColor(int color) {
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, color));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
