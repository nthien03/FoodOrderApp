package com.example.btlapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.btlapi.R;
import com.example.btlapi.fragment.CartFragment;
import com.example.btlapi.fragment.MenuFragment;
import com.example.btlapi.manager.TableManager;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation ahBottomNavigation;
    private ImageView imageView;
    private TextView textViewNumberTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        createBottomNav();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main,new MenuFragment(),"menuFragment");
        fragmentTransaction.commit();

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            int numberTable = bundle.getInt("numberTable");
            textViewNumberTable.setText("Bàn " + numberTable);
        }*/
        textViewNumberTable.setText("Bàn " + TableManager.getInstance().getNumberTable().getNumberTable());



    }

    private void createBottomNav() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_menu, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_cart, R.color.white);

        // Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);

        ahBottomNavigation.setAccentColor(Color.parseColor("#00CD00"));
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Xác định Fragment tương ứng với vị trí của tab
                Fragment fragment = null;
                String tag = null;

                switch (position) {
                    case 0:
                        fragment = new MenuFragment();
                        tag = "menuFragment";
                        break;
                    case 1:
                        fragment = new CartFragment();
                        tag = "cartFragment";
                        break;
                    default:
                        break;
                }

                // Nếu fragment không null, thì thay thế fragment hiện tại trong activity
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment, tag).commit();
                }

                // Trả về true để chỉ ra rằng bạn đã xử lý sự kiện chọn tab
                return true;
            }
        });
    }

    private void initWidgets() {
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_nav);
        imageView = (ImageView) findViewById(R.id.img_food);
        textViewNumberTable  = (TextView) findViewById(R.id.tv_numberTable);
    }
}