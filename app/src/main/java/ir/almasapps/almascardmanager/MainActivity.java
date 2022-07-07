package ir.almasapps.almascardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.Adapters.CardAdapter;

public class MainActivity extends AppCompatActivity {
     CardAdapter cardAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getBaseContext(),AddNewCard.class);
                startActivity(intent);
            }
        });

        //--------------------------------------------------------------
        cardAdapter = new CardAdapter(this);
        listView = (ListView)findViewById(R.id.main_list);
        listView.setAdapter(cardAdapter);


    }

    @Override
    protected void onResume() {
        listView.setAdapter(cardAdapter);
        super.onResume();
    }
}
