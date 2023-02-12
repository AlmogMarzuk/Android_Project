package shoval.ashkenazi.shovalfinalproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import shoval.ashkenazi.shovalfinalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView navView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoval.ashkenazi.shovalfinalproject.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_register, R.id.navigation_login, R.id.navigation_add_post, R.id.navigation_fid, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.navigation_edit_post || navDestination.getId() == R.id.navigation_login || navDestination.getId() == R.id.navigation_register)
                hideBottomNav();
            else
                showBottomNav();
        });

        // new WeatherTask().execute();
    }

    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
    public static void showBottomNav() {
        navView.setVisibility(View.VISIBLE);
    }

    public static void hideBottomNav() {
        navView.setVisibility(View.GONE);
    }
}