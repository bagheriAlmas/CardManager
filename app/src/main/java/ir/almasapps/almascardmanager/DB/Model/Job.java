package ir.almasapps.almascardmanager.DB.Model;


/**
 * Created by mahdi on 12/19/2017 AD.
 */

public class Job {
    private int job_id;
    private String job_cardID;
    private String job_type;
    private long job_cost;
    private long job_last_cost;
    private String job_description;
    private String job_date;



    public Job() {
    }

    public Job(int job_id) {
        this.job_id = job_id;
    }

    public Job(int job_id, String job_type, long job_cost, long job_last_cost, String job_description, String job_date) {
        this.job_id = job_id;
        this.job_type = job_type;
        this.job_cost = job_cost;
        this.job_last_cost = job_last_cost;
        this.job_description = job_description;
        this.job_date = job_date;
    }

    public Job(String job_cardID, String job_type, long job_cost, long job_last_cost, String job_description, String job_date) {
        this.job_cardID = job_cardID;
        this.job_type = job_type;
        this.job_cost = job_cost;
        this.job_last_cost = job_last_cost;
        this.job_description = job_description;
        this.job_date = job_date;
    }

    public Job(int job_id, String job_cardID, String job_type, long job_cost,long job_last_cost, String job_description, String job_date) {
        this.job_id = job_id;
        this.job_cardID = job_cardID;
        this.job_type = job_type;
        this.job_cost = job_cost;
        this.job_last_cost = job_last_cost;
        this.job_description = job_description;
        this.job_date = job_date;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getJob_cardID() {
        return job_cardID;
    }

    public void setJob_cardID(String job_cardID) {
        this.job_cardID = job_cardID;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public long getJob_cost() {
        return job_cost;
    }

    public void setJob_last_cost(long job_last_cost) {
        this.job_last_cost = job_last_cost;
    }

    public long getJob_last_cost() {
        return job_last_cost;
    }

    public void setJob_cost(long job_cost) {
        this.job_cost = job_cost;
    }


    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }
}
