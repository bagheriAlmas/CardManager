package ir.almasapps.almascardmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.Adapters.BankAdapter;
import ir.almasapps.almascardmanager.Adapters.ReportAdapter;
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ReportActivity extends AppCompatActivity {

    TextView txtStartDate, txtEndDate, txtTitle, txtCardNumber;
    ImageView imgBank;
    RecyclerView listView;
    ReportAdapter reportAdapter;
    PersianDatePickerDialog picker;
    int startYear, startMonth, startDay;
    private int myID;
    private String StartDate = "", EndDate = "";
    public String imageName;
    Card myCard;
    BankAdapter bankAdapter;
    CreateView createView;

    RadioButton radioAll, radioReceive, radioSpend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        myID = getIntent().getIntExtra("ID", 0);

        radioAll = (RadioButton) findViewById(R.id.report_radioAll);
        radioReceive = (RadioButton) findViewById(R.id.report_radioReceive);
        radioSpend = (RadioButton) findViewById(R.id.report_radioSpend);
        txtStartDate = (TextView) findViewById(R.id.report_txtStartDate);
        txtEndDate = (TextView) findViewById(R.id.report_txtEndDate);
        txtTitle = (TextView) findViewById(R.id.report_txtTitle);
        txtCardNumber = (TextView) findViewById(R.id.report_txtNumber);
        imgBank = (ImageView)findViewById(R.id.report_imgBank);

        listView = (RecyclerView) findViewById(R.id.report_list);
        reportAdapter = new ReportAdapter(this, myID, 0, StartDate, EndDate);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(reportAdapter);

        createView = new CreateView(this);
        myCard = createView.selectCardByID(myID);
        bankAdapter = new BankAdapter();

        String str6Char = myCard.getCard_number().substring(0, 6);
        String[] myBank = bankAdapter.getBankArray(str6Char);
        imageName = myBank[2].toString();
        int resID = getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);

        txtTitle.setText(myCard.getCard_title().toString());
        txtCardNumber.setText(Convertor.CardNumber.makeSplitNumber(myCard.getCard_number().toString()));


        txtEndDate.setEnabled(false);
        radioAll.setChecked(true);
/*        int type = 0;
        if(radioAll.isChecked())
            type=0;
        if(radioReceive.isChecked())
            type=1;
        if(radioSpend.isChecked())
            type=2;*/

        radioAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportAdapter = new ReportAdapter(getBaseContext(), myID, 0, StartDate, EndDate);
                listView.setAdapter(reportAdapter);

            }
        });
        radioReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportAdapter = new ReportAdapter(getBaseContext(), myID, 1, StartDate, EndDate);
                listView.setAdapter(reportAdapter);

            }
        });
        radioSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportAdapter = new ReportAdapter(getBaseContext(), myID, 2, StartDate, EndDate);
                listView.setAdapter(reportAdapter);

            }
        });


        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = new PersianDatePickerDialog(ReportActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        //.setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
                        //.setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                startYear = persianCalendar.getPersianYear();
                                startMonth = persianCalendar.getPersianMonth();
                                startDay = persianCalendar.getPersianDay();
                                Toast.makeText(getBaseContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                                txtStartDate.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                StartDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();

                                txtEndDate.setEnabled(true);

                                if (txtEndDate.isEnabled()) {
                                    reportAdapter = new ReportAdapter(getBaseContext(), myID, 0, StartDate, EndDate);
                                    listView.setAdapter(reportAdapter);
                                }
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianCalendar initDate = new PersianCalendar();
                initDate.setPersianDate(startYear, startMonth, startDay);

                picker = new PersianDatePickerDialog(ReportActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
                        //.setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                Toast.makeText(getBaseContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                                txtEndDate.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                EndDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                                reportAdapter = new ReportAdapter(getBaseContext(), myID, 0, StartDate, EndDate);
                                listView.setAdapter(reportAdapter);
                                radioAll.setChecked(true);

                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();
            }
        });
    }
}
