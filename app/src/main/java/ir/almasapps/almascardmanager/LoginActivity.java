package ir.almasapps.almascardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.DB.View.CreateView;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    CreateView createView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createView = new CreateView(this);


        txtUsername = (EditText) findViewById(R.id.username);
        txtPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = createView.checkUser(txtUsername.getText().toString(), txtPassword.getText().toString());
                if (result == 0) {
                    Toast.makeText(getBaseContext(), "کاربری با این مشخصات وجود ندارد", Toast.LENGTH_SHORT).show();
                } else {
                    String location = getIntent().getStringExtra("From");
                    Intent intent ;
                    switch (location) {
                        case "CardActivity":
                            int currentID = getIntent().getIntExtra("ID",0);
                            intent = new Intent(getBaseContext(), ir.almasapps.almascardmanager.CardProfile.class);
                            intent.putExtra("ID",currentID);
                            startActivity(intent);
                            break;
                        default:
                            intent = new Intent(getBaseContext(), ir.almasapps.almascardmanager.MainActivity.class);
                            startActivity(intent);
                            break;
                    }
                    LoginActivity.this.finish();
                }
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}

