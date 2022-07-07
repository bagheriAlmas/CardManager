package ir.almasapps.almascardmanager.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.CardActivity;
import ir.almasapps.almascardmanager.DB.Model.Job;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;

/**
 * Created by mahdi on 12/19/2017 AD.
 */

public class JobAdapter extends BaseAdapter {

    Context context;
    CreateView createView;
    TextView txtCost, txtDescription, txtDate, txtLastCost;
    private String txtType;
    ImageView imgArrow;
    View list_item;
    LayoutInflater inflater;
    ListView listView;
    final List<Job> jobList;
    int myID;
    TextView txtAllMoney;
    //LinearLayout linearLayoutDelete;


    public JobAdapter(Context context, ListView _listView, int ID) {
        this.context = context;
        createView = new CreateView(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = _listView;
        jobList = createView.selectJobListByID(ID, 0);
        myID = ID;
    }

    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {



        list_item = inflater.inflate(R.layout.list_item_jobs, null);
        txtCost = (TextView) list_item.findViewById(R.id.list_item_job_txtCost);
        txtLastCost = (TextView) list_item.findViewById(R.id.list_item_job_txtLastCost);
        txtDate = (TextView) list_item.findViewById(R.id.list_item_job_txtDate);
        txtDescription = (TextView) list_item.findViewById(R.id.list_item_job_txtDescription);

        imgArrow = (ImageView) list_item.findViewById(R.id.list_item_job_imgArrow);
        txtAllMoney = (TextView) ((CardActivity) context).findViewById(R.id.card_txtMoney);
        //linearLayoutDelete = (LinearLayout)list_item.findViewById(R.id.list_item_job_imgDelete);

        final Job myJob = jobList.get(position);
        txtDate.setText(myJob.getJob_date());

        if (myJob.getJob_description().equals("")) {
            txtDescription.setText("بدون توضیحات...");
        } else {
            txtDescription.setText(myJob.getJob_description());
        }

        txtType = myJob.getJob_type();
        if (txtType.equals("برداشت")) {
            long x1 = myJob.getJob_cost();
            long x2 = jobList.get(position).getJob_last_cost();
            long result = x2 - x1;


            List<String> myNumbers = Arrays.asList(NumberFormat.getNumberInstance(Locale.US).format(result).split(","));
            int size = myNumbers.size();
            String SplitMoney = "";
            for (int i = 0; i < size; i++) {
                String tmp = myNumbers.get(i).toString();
                SplitMoney += tmp + ",";
            }
            txtLastCost.setText(SplitMoney.substring(0, SplitMoney.length() - 1));


            txtCost.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            txtCost.setText("-" + Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            imgArrow.setImageResource(R.drawable.arrow_red);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("واریز")) {
            long x1 = myJob.getJob_cost();
            long x2 = myJob.getJob_last_cost();
            long result = x2 + x1;

            txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(result));

            txtCost.setTextColor(context.getResources().getColor(R.color.green));
            txtCost.setText("+" + Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            imgArrow.setImageResource(R.drawable.arrow_green);

            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("ویرایش")) {
            long x1 = myJob.getJob_cost();
            long x2 = jobList.get(position).getJob_last_cost();

            txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(x2));

            txtCost.setTextColor(context.getResources().getColor(R.color.yellow));
            txtCost.setText(Convertor.CardMoney.makeSplitMoney(x1));
            imgArrow.setImageResource(R.mipmap.ic_edit);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }


    /*    if (position == jobList.size() - 1) {
            linearLayoutDelete.setVisibility(View.VISIBLE);
        }*/

        list_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (position == jobList.size() - 1) {
                    alertDialogMaker(position);
                }
                return false;
            }
        });

        /*linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogMaker(position);
            }
        });*/

// set creator
        return list_item;
    }

    public void alertDialogMaker(final int position){

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("آیا مطمئن یه حذف آخرین آیتم هستید؟");
        alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createView.deleteJob(jobList.get(position).getJob_id());
                listView.setAdapter(new JobAdapter(context, listView, myID));
                String type = jobList.get(position).getJob_type();

                long money = createView.selectCardByID(Integer.valueOf(jobList.get(position).getJob_cardID())).getCard_money();
                long removeMoney = jobList.get(position).getJob_cost();
                long lastCostForEditJob = jobList.get(position).getJob_last_cost();

                if (type.equals("برداشت")) {
                    money = money + removeMoney;
                } else if (type.equals("واریز")) {
                    money = money - removeMoney;
                } else if (type.equals("ویرایش")) {
                    money = lastCostForEditJob;
                }
                createView.updateMoney(Integer.valueOf(jobList.get(position).getJob_cardID()), money);
                txtAllMoney.setText(Convertor.CardMoney.makeSplitMoney(money) + " ریال ");

            }
        });
        alert.setNegativeButton("خیر", null);
        alert.show();
    }
}
