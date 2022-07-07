package ir.almasapps.almascardmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.Adapters.BankAdapter;
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;
import me.grantland.widget.AutofitHelper;

public class CardProfile extends AppCompatActivity {

    private int myID;
    private Card myCard;
    private CreateView createView;
    private TextView tvTitle,tvNumber,tvMoney,tvPassword,tvPassword2,tvCvv,tvExpireDate;
    private ImageView imgBank;
    private Button btnEdit;

    private BankAdapter bankAdapter;

/*    private int card_id;
    private String card_bank;
    private String card_title;
    private String card_number;
    private String card_password;
    private String card_password2;
    private String card_cvv2;
    private String card_expireDate;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_profile);

        tvTitle = (TextView)findViewById(R.id.cardProfile_lblTitle);
        tvNumber = (TextView)findViewById(R.id.cardProfile_lblNumber);
        tvMoney = (TextView)findViewById(R.id.cardProfile_lblMoney);
        tvPassword = (TextView)findViewById(R.id.cardProfile_lblpassword);
        tvPassword2 = (TextView)findViewById(R.id.cardProfile_lblpassword2);
        tvCvv = (TextView)findViewById(R.id.cardProfile_lblcvv2);
        tvExpireDate = (TextView)findViewById(R.id.cardProfile_lblExpireDate);
        imgBank = (ImageView)findViewById(R.id.cardProfile_imgBank);
        btnEdit = (Button)findViewById(R.id.cardProfile_btnEdit);

        AutofitHelper.create(tvMoney);


        initialize();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),EditCardProfile.class);
                intent.putExtra("ID",myID);
                startActivity(intent);
            }
        });

    }

    public void initialize(){

        myID = getIntent().getIntExtra("ID",0);
        createView = new CreateView(this);
        bankAdapter = new BankAdapter();
        myCard =createView.selectCardByID(myID);
        String str6Char = myCard.getCard_number().substring(0,6);
        String []BankInfo = bankAdapter.getBankArray(str6Char);
        tvTitle.setText(myCard.getCard_title());
        tvNumber.setText(Convertor.CardNumber.makeSplitNumber(myCard.getCard_number()));
        tvMoney.setText(Convertor.CardMoney.makeSplitMoney(myCard.getCard_money()));
        tvPassword.setText(myCard.getCard_password());
        if(tvPassword.getText().toString().equals(""))
            tvPassword.setText("تخصیص داده نشده");
        tvPassword2.setText(myCard.getCard_password2());
        if(tvPassword2.getText().toString().equals(""))
            tvPassword2.setText("تخصیص داده نشده");
        tvCvv.setText(myCard.getCard_cvv2());
        if(tvCvv.getText().toString().equals(""))
            tvCvv.setText("تخصیص داده نشده");
        tvExpireDate.setText(myCard.getCard_expireDate());
        if(tvExpireDate.getText().toString().equals(""))
            tvExpireDate.setText("تخصیص داده نشده");

        int resID = getResources().getIdentifier(BankInfo[2], "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);

    }

    @Override
    protected void onResume() {
       initialize();
        super.onResume();
    }
}
