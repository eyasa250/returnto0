package com.example.returnto0;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.returnto0.databinding.ActivityButtombarBinding;

public class buttombar extends AppCompatActivity {

    private ActivityButtombarBinding binding;

    @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtombarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_room, R.id.navigation_notifications )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_buttombar);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}

