package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;



public class WeatherActivity extends AppCompatActivity {
    public static final String TAG = "Weather Activity";
    public static int action_setting = 1000005;
    public static int action_refresh = 100000;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.weather_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music1);
        mediaPlayer.start();



    }

    public boolean  onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == action_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
// This method is executed in main thread
                    String content = msg.getData().getString("server_response");
                    Toast.makeText(WeatherActivity.this, content, Toast.LENGTH_SHORT).show();
                }
            };
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
// this method is run in a worker thread
                    try {
// wait for 5 seconds to simulate a long network access
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
// Assume that we got our data from server
                    Bundle bundle = new Bundle();
                    bundle.putString("server_response", "some sample json here");
// notify main thread
                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
            t.start();
            return true;
        } else if (item.getItemId() == action_refresh) {
            Intent intent = new Intent(this, PrefActivity.class);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "Pause");

    }
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "Resume");
    }
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "Start");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "Destroy");
    }
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "Stop");
    }
}