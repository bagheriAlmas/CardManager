package ir.almasapps.almascardmanager.DB.View;

import android.content.Context;

import java.util.List;

import ir.almasapps.almascardmanager.DB.Controller.MyDB;
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.Model.Job;
import ir.almasapps.almascardmanager.DB.Model.User;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class CreateView {
    Context context;
    MyDB myDB;

    public CreateView(Context context) {
        this.context = context;
        myDB = new MyDB(context);
    }

    public long insertUser(String Username, String Password) {
        return myDB.insertUser(new User(Username, Password));
    }

    public User selectUserByID(int ID) {
        return myDB.selectUserByID(new User(ID));
    }

    public int updateUser(int ID, String Username, String Password) {
        return myDB.updateUser(new User(ID, Username, Password));
    }

    public int checkUser(String Username, String Password) {
        return myDB.checkUser(new User(Username, Password));
    }

    public int getUserCount() {
        return myDB.getUserCount();
    }

//-------------------------------------------------------------------------------------------------------------

    public long insertCard(String Title, String Number,long Money) {
        return myDB.insertCard(new Card(Title, Number,Money));
    }

    public List<Card> selectAllCards() {
        return myDB.selectAllCards();
    }

    public Card selectCardByID(int ID) {
        return myDB.selectCardByID(ID);
    }

    public int updateCard(int ID, String Bank, String Title, String Number,long Money) {
        return myDB.updateCardByID(new Card(ID, Bank, Title, Number,Money));
    }


    public int updateCard_all(int ID, String Bank, String Title, String Number,long Money,String Password,String Password2,String cvv2,String ExpireDate) {
        return myDB.updateCardByID_All(new Card(ID, Bank, Title, Number,Money,Password,Password2,cvv2,ExpireDate));
    }

    public int updateMoney(int ID,long Money){
        return myDB.updateMoney(new Card(ID,Money));
    }

    public int deleteCard (int ID){
        return myDB.deleteCardByID(ID);
    }

    public int getCardCount() {
        return myDB.getCardCount();
    }
//-------------------------------------------------------------------------------------------------------------

    public long insertJob(String cardID, String type, long cost,long lastCost, String description, String date) {
    /*
        private int job_id;
    private String job_cardID;
    private String job_type;
    private String job_cost;
    private String job_description;
    private String job_time;
    private String job_date;

     */
        return myDB.insertJob(new Job(cardID, type, cost,lastCost, description, date));
    }

    public List<Job> selectAllJobsByDate(int Id,int Type,String StartDate,String EndDate) {
        return myDB.selectAllJobsByDateAndType(Id,Type,StartDate,EndDate);
    }


    public int updateJobByID(int ID,String Type,long Cost,long LastCost,String Description,String Date){
        return myDB.updateJob(new Job(ID,Type,Cost,LastCost,Description,Date));
    }

    public int getJobCount() {
        return myDB.getJobCount();
    }

    public int deleteJob(int ID) {
        return myDB.deleteJob(new Job(ID));
    }

    public int deleteJobByCardID(int CardID) {
        return myDB.deleteJobByCardID(CardID);
    }

    public Job selectJobByID(int ID) {
        return myDB.selectJobByID(new Job(ID));
    }

    public List<Job> selectJobListByID(int ID, int viewType) {
        return myDB.selectJobListByID(ID,viewType);
    }
}