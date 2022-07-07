package ir.almasapps.almascardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.DB.View.CreateView;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    CreateView createView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        createView = new CreateView(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                int userNum = createView.getUserCount();
                if (userNum == 0) {
                    intent = new Intent(getBaseContext(), ir.almasapps.almascardmanager.Register.class);
                    startActivity(intent);
                    finish();
                } else if (userNum == 1) {
                    intent = new Intent(getBaseContext(), ir.almasapps.almascardmanager.LoginActivity.class);
                    intent.putExtra("From","Splash");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "خطا در پایگاه داده", Toast.LENGTH_LONG).show();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
