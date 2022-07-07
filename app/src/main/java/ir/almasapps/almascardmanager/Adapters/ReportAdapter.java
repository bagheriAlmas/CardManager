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
import ir.almasapps.almascardmanager.DB.Model.Job;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;

/**
 * Created by mahdi on 12/19/2017 AD.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CustomHolder> {

    Context context;
    CreateView createView;
    private String txtType;
    List<Job> jobList;


    public ReportAdapter(Context context, int ID, int ViewType, String startDate, String endDate) {
        this.context = context;
        createView = new CreateView(context);
        //jobList = createView.selectAllJobsByDate(ID,ViewType,startDate,endDate);

        if (!startDate.equals("") && !endDate.equals("")) {
            jobList = createView.selectAllJobsByDate(ID, ViewType, startDate, endDate);

        } else {
            jobList = createView.selectJobListByID(ID, ViewType);
        }
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_jobs, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {


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
            holder.txtCost.setText("-"+Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            holder.imgArrow.setImageResource(R.drawable.arrow_red);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("واریز")) {
            long x1 = myJob.getJob_cost();
            long x2 = myJob.getJob_last_cost();
            long result = x2 + x1;

            holder.txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(result));

            holder.txtCost.setTextColor(context.getResources().getColor(R.color.green));
            holder.txtCost.setText("+"+Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            holder.imgArrow.setImageResource(R.drawable.arrow_green);

            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }else if (txtType.equals("ویرایش")) {
            long x1 = myJob.getJob_cost();
            long x2 = jobList.get(position).getJob_last_cost();

            holder.txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(x2));

            holder.txtCost.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.txtCost.setText(Convertor.CardMoney.makeSplitMoney(x1));
            holder.imgArrow.setImageResource(R.mipmap.ic_edit);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }



        holder.txtCost.setText(Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
//        txtDescription.setText(myJob.getJob_description());
        //txtType.setText(myJob.getJob_type());


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        TextView txtCost, txtDescription, txtDate, txtLastCost;
        ImageView imgArrow;

        public CustomHolder(View view) {
            super(view);

            txtCost = (TextView) view.findViewById(R.id.list_item_job_txtCost);
            txtDate = (TextView) view.findViewById(R.id.list_item_job_txtDate);
            txtLastCost = (TextView) view.findViewById(R.id.list_item_job_txtLastCost);
            txtDescription = (TextView) view.findViewById(R.id.list_item_job_txtDescription);
            imgArrow = (ImageView) view.findViewById(R.id.list_item_job_imgArrow);

        }
    }

    /*

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

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        list_item = inflater.inflate(R.layout.list_item_jobs, null);
        txtCost = (TextView) list_item.findViewById(R.id.list_item_job_txtCost);
        txtDate = (TextView) list_item.findViewById(R.id.list_item_job_txtDate);
        txtLastCost = (TextView) list_item.findViewById(R.id.list_item_job_txtLastCost);
        txtDescription = (TextView) list_item.findViewById(R.id.list_item_job_txtDescription);
        imgArrow = (ImageView)list_item.findViewById(R.id.list_item_job_imgArrow);


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
            txtCost.setText("-"+Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            imgArrow.setImageResource(R.drawable.arrow_red);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        } else if (txtType.equals("واریز")) {
            long x1 = myJob.getJob_cost();
            long x2 = myJob.getJob_last_cost();
            long result = x2 + x1;

            txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(result));

            txtCost.setTextColor(context.getResources().getColor(R.color.green));
            txtCost.setText("+"+Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
            imgArrow.setImageResource(R.drawable.arrow_green);

            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }else if (txtType.equals("ویرایش")) {
            long x1 = myJob.getJob_cost();
            long x2 = jobList.get(position).getJob_last_cost();

            txtLastCost.setText(Convertor.CardMoney.makeSplitMoney(x2));

            txtCost.setTextColor(context.getResources().getColor(R.color.yellow));
            txtCost.setText(Convertor.CardMoney.makeSplitMoney(x1));
            imgArrow.setImageResource(R.mipmap.ic_edit);
            //txtLastCost.setText("موجودی : " + result + " ریال ");

        }



        txtCost.setText(Convertor.CardMoney.makeSplitMoney(myJob.getJob_cost()));
//        txtDescription.setText(myJob.getJob_description());
        //txtType.setText(myJob.getJob_type());


        return list_item;
    }

*/
}
