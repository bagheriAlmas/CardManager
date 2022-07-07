package ir.almasapps.almascardmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.Adapters.BankAdapter;
import ir.almasapps.almascardmanager.Adapters.JobAdapter2;
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;

public class CardActivity extends AppCompatActivity {

    CreateView createView;
    ImageView imgBank;
    public TextView txtTitle, txtCardNumber,txtMoney;
    Button btnReceive_Spend;
    public RecyclerView list;
    BankAdapter bankAdapter;
    public JobAdapter2 JobAdapter2;
    int cardID = 0;
    public Card myCard;
    public String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        bankAdapter = new BankAdapter();
        createView = new CreateView(this);


        imgBank = (ImageView) findViewById(R.id.card_imgBank);
        txtTitle = (TextView) findViewById(R.id.card_txtTitle);
        txtMoney= (TextView) findViewById(R.id.card_txtMoney);

        txtCardNumber = (TextView) findViewById(R.id.card_txtNumber);
        btnReceive_Spend = (Button) findViewById(R.id.card_btnReceive_Spend);
        list = (RecyclerView) findViewById(R.id.listView);

        JobAdapter2 = new JobAdapter2(this, list, cardID);


        btnReceive_Spend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddJob();
            }
        });

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(JobAdapter2);
        //list.setDividerHeight(0);

    }


    public void refreshList() {
        JobAdapter2 = new JobAdapter2(this, list, cardID);
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        list.setAdapter(JobAdapter2);
    }

    public void gotoAddJob() {
        Intent intent = new Intent(getBaseContext(), JobActivity.class);
        intent.putExtra("id", myCard.getCard_id());
        intent.putExtra("title", myCard.getCard_title());
        intent.putExtra("number", myCard.getCard_number());
        intent.putExtra("img", imageName);
        intent.putExtra("LAST_COST",myCard.getCard_money());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getBaseContext());
        menuInflater.inflate(R.menu.application_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                startActivity(new Intent(getBaseContext(), LoginActivity.class).putExtra("ID", myCard.getCard_id()).putExtra("From","CardActivity"));
                break;
            case R.id.menu_editCard:
                startActivity(new Intent(getBaseContext(), EditCard.class).putExtra("ID", myCard.getCard_id()));
                break;
            case R.id.menu_addJob:
                gotoAddJob();
                break;
            case R.id.menu_report:
                startActivity(new Intent(getBaseContext(), ReportActivity.class).putExtra("ID", myCard.getCard_id()));
                break;
            case R.id.menu_delete:


                AlertDialog.Builder alert = new AlertDialog.Builder(CardActivity.this);
                alert.setMessage("آیا مطمئن یه حذف این کارت هستید؟");
                alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createView.deleteCard(myCard.getCard_id());
                        int x = createView.selectJobListByID(myCard.getCard_id(),0).size();
                        //for(int i1 = 0;i1<x;i1++) {
                            createView.deleteJobByCardID(myCard.getCard_id());
                        //}
                        CardActivity.this.finish();

                    }
                });
                alert.setNegativeButton("خیر", null);
                alert.show();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {


/*        //-------
        String[][] banks = bankAdapter.Banks;
        for (int i =0;i<banks.length;i++){
            createView.insertCard(banks[i][0],banks[i][1]+"546464",1000);
        }
        //-------*/
        cardID = getIntent().getIntExtra("ID", 0);
        myCard = createView.selectCardByID(cardID);
        txtTitle.setText(myCard.getCard_title());
        txtCardNumber.setText(Convertor.CardNumber.makeSplitNumber(myCard.getCard_number()));

        String str6Char = myCard.getCard_number().substring(0, 6);
        String[] myBank = bankAdapter.getBankArray(str6Char);
        imageName = myBank[2].toString();
        int resID = getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
        imgBank.setImageResource(resID);


        txtMoney.setText(Convertor.CardMoney.makeSplitMoney(myCard.getCard_money()) + " ریال ");

        refreshList();
        super.onResume();
    }

}
