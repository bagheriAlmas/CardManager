package ir.almasapps.almascardmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
 * Created by mahdi on 2/11/2018 AD.
 */

public class JobAdapter2 extends RecyclerView.Adapter<JobAdapter2.CustomHolder> {

    Context context;
    CreateView createView;
    private String txtType;
    RecyclerView listView;
    final List<Job> jobList;
    int myID;
    TextView txtAllMoney;



    public JobAdapter2(Context context, RecyclerView _listView, int ID) {
        this.context = context;
        createView = new CreateView(context);
        listView = _listView;
        jobList = createView.selectJobListByID(ID, 0);
        myID = ID;
        txtAllMoney = (TextView) ((CardActivity) context).findViewById(R.id.card_txtMoney);

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_jobs,parent,false);

        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomHolder holder, final int position) {


        final Job myJob = jobList.get(position);
        holder.txtDate.setText(myJob.getJob_date());

        if (myJob.getJob_description().equals("")) {
            holder.txtDescription.setText("بدون توضیحات...");
        } else {
            holder.txtDescription.setText(myJob.getJob_description());
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
            holder.txtLastCost.setText(SplitMoney.substring(0, SplitMoney.length() - 1));


            holder.txtCost.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.txtCost.setText("-" + Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            holder.imgArrow.setImageResource(R.drawable.arrow_red);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("واریز")) {
            long x1 = myJob.getJob_cost();
            long x2 = myJob.getJob_last_cost();
            long result = x2 + x1;

            holder.txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(result));

            holder.txtCost.setTextColor(context.getResources().getColor(R.color.green));
            holder.txtCost.setText("+" + Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            holder.imgArrow.setImageResource(R.drawable.arrow_green);

            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("ویرایش")) {
            long x1 = myJob.getJob_cost();
            long x2 = jobList.get(position).getJob_last_cost();

            holder.txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(x2));

            holder.txtCost.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.txtCost.setText(Convertor.CardMoney.makeSplitMoney(x1));
            holder.imgArrow.setImageResource(R.mipmap.ic_edit);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }
        //listView.scrollToPosition(position);




    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        TextView txtCost, txtDescription, txtDate, txtLastCost;
        ImageView imgArrow;
        //LinearLayout linearLayoutDelete;

        public CustomHolder(View view) {
            super(view);
            //list_item = inflater.inflate(R.layout.list_item_jobs, null);
            txtCost = (TextView) view.findViewById(R.id.list_item_job_txtCost);
            txtLastCost = (TextView) view.findViewById(R.id.list_item_job_txtLastCost);
            txtDate = (TextView) view.findViewById(R.id.list_item_job_txtDate);
            txtDescription = (TextView) view.findViewById(R.id.list_item_job_txtDescription);
            imgArrow = (ImageView) view.findViewById(R.id.list_item_job_imgArrow);
           // linearLayoutDelete = (LinearLayout)view.findViewById(R.id.list_item_job_imgDelete);
/*
            if (getLayoutPosition() == getItemCount()-1) {
            linearLayoutDelete.setVisibility(View.VISIBLE);
        }*/
/*int x = getAdapterPosition();
            if (x == jobList.size() - 1) {
                alertDialogMaker(getAdapterPosition());
            }*/


/*            linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogMaker(getAdapterPosition());
                }
            });*/
        }
    }

/*
    public void alertDialogMaker(final int position){

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("آیا مطمئن یه حذف آخرین آیتم هستید؟");
        alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createView.deleteJob(jobList.get(position).getJob_id());
                listView.setAdapter(new JobAdapter2(context, listView, myID));
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
    }*/
}
