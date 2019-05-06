package com.example.giyeon.blizzard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.custom.CustomTypefaceSpan;
import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.example.giyeon.blizzard.user.view.frag.ExplanationFragment;
import com.example.giyeon.blizzard.user.view.frag.MainQuizeFragment;
import com.example.giyeon.blizzard.user.view.frag.MainEggManageFragment;
import com.example.giyeon.blizzard.user.view.frag.ShopFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public interface OnBackPressedListener {
        void onBack();
    }

    private OnBackPressedListener mBackListener;

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }

    Context context;
    /** Header Menu **/
    LinearLayout navHeaderContainer;

    View navHeaderView;
    CustomDialog customDialog;

    /** Fragment Setting**/
    FragmentManager manager;



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        setTitle("");




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setSystem();

        //myMethod
        setStatusBar();
        CommonController.getInstance().setFont();
        setBGM();

        //fragment Setting
        manager = setManager();

        manager.beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(mBackListener !=null) {
                mBackListener.onBack();
            } else {
                if(SimpleData.getInstance().backPressedTime == 0) {
                    Toast.makeText(context, "한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                    SimpleData.getInstance().backPressedTime = System.currentTimeMillis() - SimpleData.getInstance().backPressedTime;
                } else {
                    int seconds = (int) (System.currentTimeMillis() - SimpleData.getInstance().backPressedTime);

                    if(seconds > 2000) {
                        Toast.makeText(context, "한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                        SimpleData.getInstance().backPressedTime = 0;
                    } else {
                        super.onBackPressed();
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.mainAdventure){
            manager.beginTransaction().replace(R.id.content_main, new MainQuizeFragment()).commit();

        } else if (id == R.id.userInfo) {
            Toast.makeText(context, " friens fragment 미구현", Toast.LENGTH_SHORT).show(); // must modification
        } else if( id == R.id.explanation) {
            manager.beginTransaction().replace(R.id.content_main, new ExplanationFragment()).commit();
        } else if( id == R.id.shop) {
            manager.beginTransaction().replace(R.id.content_main, new ShopFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private FragmentManager setManager() {
        return getSupportFragmentManager();
    }


    private void setStatusBar() {
        View view = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.WHITE);
            } else if(Build.VERSION.SDK_INT >= 21 ) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
    }
    private void setBGM() {
        CommonController.backgroundSound.stop();
        CommonController.backgroundSound = MediaPlayer.create(context, R.raw.main_song);
        CommonController.backgroundSound.setLooping(true);
        CommonController.backgroundSound.start();
    }
    private void setSystem() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        for(int i = 0 ; i< m.size() ; i++) {
            MenuItem mi = m.getItem(i);

            SubMenu subMenu = mi.getSubMenu();
            if(subMenu != null && subMenu.size() >0) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);

                    applyFontToMenuItem(subMenuItem, false);

                }
            }
            applyFontToMenuItem(mi, true);
        }
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderView = navigationView.getHeaderView(0);

        navHeaderContainer = (LinearLayout)navHeaderView.findViewById(R.id.navigation_header_container);

        navHeaderContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });



    }


    private void applyFontToMenuItem(MenuItem mi, boolean ko) {
        Typeface font;
        if(ko) {
            font = ResourcesCompat.getFont(context,  R.font.pfstardust);
        } else {
            font = ResourcesCompat.getFont(context,  R.font.shylock_nbp);
        }

        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }



}

