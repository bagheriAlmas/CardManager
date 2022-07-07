package ir.almasapps.almascardmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.Adapters.BankAdapter;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;
import ir.almasapps.almascardmanager.Helper.CreditCardEditText;

public class AddNewCard extends AppCompatActivity {

    EditText txtNumber, txtName,txtMoney;
    ImageView imgBank;
    Button btnAdd;
    BankAdapter bankAdapter;
    CreateView createView;
    private CreditCardEditText tv;
    private String[] strBankInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        txtNumber = (EditText) findViewById(R.id.addCard_txtNumber);
        txtMoney = (EditText) findViewById(R.id.addCard_txtMoney);

        txtName = (EditText) findViewById(R.id.addCard_txtTitle);
        imgBank = (ImageView) findViewById(R.id.addCard_imgBank);
        btnAdd = (Button) findViewById(R.id.addCard_btnAdd);
        bankAdapter = new BankAdapter();
        createView = new CreateView(this);

        tv = new CreditCardEditText(txtNumber);
        txtNumber.addTextChangedListener(tv);

        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i==5){
                    String str6Char = charSequence.toString();
                    strBankInfo = bankAdapter.getBankArray(str6Char);

                    String imageName = strBankInfo[2].toString();
                    int resID = getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
                    imgBank.setImageResource(resID);

                    txtName.setText("کارت " + strBankInfo[0]);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        txtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtMoney.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    txtMoney.setText(formattedString);
                    txtMoney.setSelection(txtMoney.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                txtMoney.addTextChangedListener(this);
            }

        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = txtNumber.length();
                if (isEmpty(txtNumber) || txtNumber.length()<16) {
                    Toast.makeText(getBaseContext(), "شماره کارت صحیح وارد نشده است", Toast.LENGTH_SHORT).show();
                }
                else {
                    long CardMoney = 0;
                    if(!isEmpty(txtMoney)){
                        CardMoney = Convertor.CardMoney.makeSimpleMoney(txtMoney.getText().toString());

                    }
                    createView.insertCard(txtName.getText().toString(),txtNumber.getText().toString(),CardMoney);
                    AddNewCard.this.finish();
                }
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() < 4;
    }

}
