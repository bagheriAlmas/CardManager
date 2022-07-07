package ir.almasapps.almascardmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;
import ir.almasapps.almascardmanager.utils.PersianCalendar;

public class JobActivity extends AppCompatActivity {

    ImageView imgBank;
    TextView txtTitle,txtNumber;
    Spinner spinner;
    EditText txtCost,txtDescription;
    Button btnSave;
    PersianCalendar persianCalendar;
    CreateView createView;

    private long intentLastCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        imgBank = (ImageView)findViewById(R.id.job_imgBank);
        txtTitle = (TextView)findViewById(R.id.job_txtTitle);
        txtNumber = (TextView)findViewById(R.id.job_txtNumber);
        spinner = (Spinner)findViewById(R.id.job_spinnerType);
        txtCost = (EditText)findViewById(R.id.job_cost);
        txtDescription = (EditText)findViewById(R.id.job_description);
        btnSave = (Button)findViewById(R.id.job_btnAdd);

        persianCalendar = new PersianCalendar();
        createView = new CreateView(this);


        String[] strKind = new String[]{"برداشت","واریز"};
        spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.list_item_spinner, R.id.list_item_spinner_txt,strKind));


        final int intentCardID = getIntent().getIntExtra("id",0);
        String intentTitle = getIntent().getStringExtra("title");
        String intentNumber = getIntent().getStringExtra("number");
        String intentimg = getIntent().getStringExtra("img");
        intentLastCost = getIntent().getLongExtra("LAST_COST",0);

        txtTitle.setText(intentTitle);
        txtNumber.setText(Convertor.CardNumber.makeSplitNumber(intentNumber));
        int resID = getResources().getIdentifier(intentimg, "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);

        final String CurrentDate = persianCalendar.getPersianShortDate();



        txtCost.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtCost.removeTextChangedListener(this);

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
                    txtCost.setText(formattedString);
                    txtCost.setSelection(txtCost.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                txtCost.addTextChangedListener(this);
            }

        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    createView.insertJob(intentCardID+"",
                            spinner.getSelectedItem().toString(),
                            Convertor.CardMoney.makeSimpleMoney(txtCost.getText().toString()),
                            Convertor.CardMoney.makeSimpleMoney(String.valueOf(intentLastCost)),
                            txtDescription.getText().toString(),CurrentDate);
                    long cost = Convertor.CardMoney.makeSimpleMoney(txtCost.getText().toString());
                    long money = createView.selectCardByID(intentCardID).getCard_money();
                    switch (spinner.getSelectedItem().toString()){
                        case "واریز":
                            createView.updateMoney(intentCardID, ((int) money) + ((int) cost));
                            break;
                        case "برداشت" :
                            createView.updateMoney(intentCardID,((int) money) - ((int) cost));
                            break;
                    }
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),"لطفا مقادیر صحیح وارد کنید",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
