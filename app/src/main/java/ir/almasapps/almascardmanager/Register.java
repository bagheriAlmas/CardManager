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

public class Register extends AppCompatActivity {


    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtPassword2;
    private Button btnRegister;

    CreateView createView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createView = new CreateView(this);

       txtUsername = (EditText)findViewById(R.id.register_username);
       txtPassword = (EditText)findViewById(R.id.register_password);
       txtPassword2 = (EditText)findViewById(R.id.register_password_2);
       btnRegister = (Button)findViewById(R.id.register_btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(txtUsername) || isEmpty(txtPassword) || isEmpty(txtPassword2)){
                    Toast.makeText(getBaseContext(),"Fill All",Toast.LENGTH_SHORT).show();
                }else {
                    if(! txtPassword.getText().toString().equals(txtPassword2.getText().toString())){
                        Toast.makeText(getBaseContext(),"Password Error",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        createView.insertUser(txtUsername.getText().toString(),txtPassword.getText().toString());
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
