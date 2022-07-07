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
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;
import ir.almasapps.almascardmanager.Helper.CreditCardEditText;
import ir.almasapps.almascardmanager.utils.PersianCalendar;

public class EditCard extends AppCompatActivity {

    EditText txtNumber, txtName, txtMoney;
    ImageView imgBank;
    Button btnEdit;
    Card myCard;
    CreateView createView;
    BankAdapter bankAdapter;
    private CreditCardEditText tv;
    private String[] strBankInfo;
    private String str6Char;
    String lastCost;
    PersianCalendar persianCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        txtNumber = (EditText) findViewById(R.id.editCard_txtNumber);
        txtMoney = (EditText) findViewById(R.id.editCard_txtMoney);
        txtName = (EditText) findViewById(R.id.editCard_txtName);
        imgBank = (ImageView) findViewById(R.id.editCard_imgBank);
        btnEdit = (Button) findViewById(R.id.editCard_btnEdit);

        createView = new CreateView(this);
        bankAdapter = new BankAdapter();

        int currentID = getIntent().getIntExtra("ID", 0);
        myCard = createView.selectCardByID(currentID);

        tv = new CreditCardEditText(txtNumber);
        txtNumber.addTextChangedListener(tv);
        txtNumber.setText(myCard.getCard_number());

        txtMoney.setText(Convertor.CardMoney.makeSplitMoney(myCard.getCard_money()));

        lastCost = txtMoney.getText().toString();

        persianCalendar = new PersianCalendar();
        final String CurrentDate = persianCalendar.getPersianShortDate();


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

        txtName.setText(myCard.getCard_title());

        str6Char = myCard.getCard_number().substring(0, 6);
        strBankInfo = bankAdapter.getBankArray(str6Char);

        String[] bankInfo = bankAdapter.getBankArray(str6Char);
        int resID = getResources().getIdentifier(bankInfo[2], "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);

        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i == 5) {
                    str6Char = charSequence.toString();
                    strBankInfo = bankAdapter.getBankArray(str6Char);

                    String imageName = strBankInfo[2].toString();
                    int resID = getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
                    imgBank.setImageResource(resID);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isEmpty(txtNumber) || txtNumber.length() < 16) {
                        Toast.makeText(getBaseContext(), "شماره کارت صحیح وارد نشده است", Toast.LENGTH_SHORT).show();
                    } else {

                        long CardMoney = 0;
                        if (!isEmpty(txtMoney)) {
                            CardMoney = Convertor.CardMoney.makeSimpleMoney(txtMoney.getText().toString());
                        }

                        createView.updateCard(myCard.getCard_id(), strBankInfo[0], txtName.getText().toString(), txtNumber.getText().toString(), CardMoney);
                        createView.insertJob(String.valueOf(myCard.getCard_id()),"ویرایش",Convertor.CardMoney.makeSimpleMoney(CardMoney+""),Convertor.CardMoney.makeSimpleMoney(lastCost),"ویرایش شده",CurrentDate);

                        //Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        EditCard.this.finish();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),"خطا در ورود اطلاعات \n ورودی های خود را چک کنید",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() < 4;
    }
}
