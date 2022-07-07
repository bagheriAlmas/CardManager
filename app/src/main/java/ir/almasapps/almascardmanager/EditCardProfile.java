package ir.almasapps.almascardmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class EditCardProfile extends AppCompatActivity {
    private int myID;
    private Card myCard;
    private CreateView createView;
    private EditText txtTitle, txtNumber,txtMoney, txtPassword, txtPassword2, txtCvv;
    private TextView txtExpireDate;
    private ImageView imgBank;
    private Button btnSave;
    private CreditCardEditText tv;
    private String str6Char;
    private String[] strBankInfo;
    private BankAdapter bankAdapter;
    private PersianDatePickerDialog picker;
    private LinearLayout layout_txtExpireDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_profile);

        txtTitle = (EditText) findViewById(R.id.editCardProfile_txtTitle);
        txtNumber = (EditText) findViewById(R.id.editCardProfile_txtNumber);

        txtMoney = (EditText) findViewById(R.id.editCardProfile_txtMoney);
        txtPassword = (EditText) findViewById(R.id.editCardProfile_txtpassword);
        txtPassword2 = (EditText) findViewById(R.id.editCardProfile_txtpassword2);
        txtCvv = (EditText) findViewById(R.id.editCardProfile_txtcvv2);
        txtExpireDate = (TextView) findViewById(R.id.editCardProfile_txtExpireDate);
        imgBank = (ImageView) findViewById(R.id.editCardProfile_imgBank);
        btnSave = (Button) findViewById(R.id.editCardProfile_btnSave);
        layout_txtExpireDate = (LinearLayout)findViewById(R.id.editCardProfile_layout_txtExpireDate);
        myID = getIntent().getIntExtra("ID", 0);
        createView = new CreateView(this);
        bankAdapter = new BankAdapter();
        tv = new CreditCardEditText(txtNumber);

        myCard = createView.selectCardByID(myID);
        str6Char = myCard.getCard_number().substring(0, 6);
        strBankInfo = bankAdapter.getBankArray(str6Char);
        txtTitle.setText(myCard.getCard_title());

        tv = new CreditCardEditText(txtNumber);
        txtNumber.addTextChangedListener(tv);
        txtNumber.setText(myCard.getCard_number());
        txtMoney.setText(Convertor.CardMoney.makeSplitMoney(myCard.getCard_money()));
        txtPassword.setText(myCard.getCard_password());
        txtPassword2.setText(myCard.getCard_password2());
        txtCvv.setText(myCard.getCard_cvv2());
        txtExpireDate.setText(myCard.getCard_expireDate());

        int resID = getResources().getIdentifier(strBankInfo[2], "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);

        layout_txtExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                picker = new PersianDatePickerDialog(EditCardProfile.this)
                        .setPositiveButtonString("انتخاب")
                        .setNegativeButton("بستن")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        //.setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
                        .setMaxYear(1500)
                        //.setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                txtExpireDate.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();

            }
        });


        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i==5){
                    str6Char = charSequence.toString();
                    strBankInfo = bankAdapter.getBankArray(str6Char);

                    String imageName = strBankInfo[2].toString();
                    int resID = getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
                    imgBank.setImageResource(resID);
                    txtTitle.setText("کارت " + strBankInfo[0].toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtMoney.addTextChangedListener(new TextWatcher() {
            int count = 0;
            String num = "";

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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(txtNumber) || txtNumber.length() < 16) {
                    Toast.makeText(getBaseContext(), "شماره کارت صحیح وارد نشده است", Toast.LENGTH_SHORT).show();
                } else {
                    long CardMoney = 0;
                    if(!isEmpty(txtMoney)){
                        CardMoney = Convertor.CardMoney.makeSimpleMoney(txtMoney.getText().toString());
                    }

                    createView.updateCard_all(
                            myCard.getCard_id(),
                            strBankInfo[0], txtTitle.getText().toString(),
                            txtNumber.getText().toString(),Convertor.CardMoney.makeSimpleMoney(CardMoney+""),
                            txtPassword.getText().toString(),
                            txtPassword2.getText().toString(),
                            txtCvv.getText().toString(),
                            txtExpireDate.getText().toString());

                    //Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    EditCardProfile.this.finish();
                }
            }
        });
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() < 4;
    }
}
