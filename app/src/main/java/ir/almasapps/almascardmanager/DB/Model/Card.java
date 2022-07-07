package ir.almasapps.almascardmanager.DB.Model;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class Card {
    private int card_id;
    private String card_bank;
    private String card_title;
    private String card_number;
    private long card_money;
    private String card_password;
    private String card_password2;
    private String card_cvv2;
    private String card_expireDate;

    public Card() {
    }

    public Card(int card_id) {
        this.card_id = card_id;
    }

    public Card(int card_id, long card_money) {
        this.card_id = card_id;
        this.card_money = card_money;
    }

    public Card(String card_title, String card_number, long card_money) {
        this.card_title = card_title;
        this.card_number = card_number;
        this.card_money = card_money;
    }

    public Card(int card_id, String card_bank, String card_title, String card_number, long card_money) {
        this.card_id = card_id;
        this.card_bank = card_bank;
        this.card_title = card_title;
        this.card_number = card_number;
        this.card_money = card_money;
    }

    public Card(String card_bank, String card_title, String card_number, long card_money, String card_password, String card_password2, String card_cvv2, String card_expireDate) {
        this.card_bank = card_bank;
        this.card_title = card_title;
        this.card_number = card_number;
        this.card_money = card_money;
        this.card_password = card_password;
        this.card_password2 = card_password2;
        this.card_cvv2 = card_cvv2;
        this.card_expireDate = card_expireDate;
    }

    public Card(int card_id, String card_bank, String card_title, String card_number, long card_money, String card_password, String card_password2, String card_cvv2, String card_expireDate) {
        this.card_id = card_id;
        this.card_bank = card_bank;
        this.card_title = card_title;
        this.card_number = card_number;
        this.card_money = card_money;
        this.card_password = card_password;
        this.card_password2 = card_password2;
        this.card_cvv2 = card_cvv2;
        this.card_expireDate = card_expireDate;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getCard_bank() {
        return card_bank;
    }

    public void setCard_bank(String card_bank) {
        this.card_bank = card_bank;
    }

    public String getCard_title() {
        return card_title;
    }

    public void setCard_title(String card_title) {
        this.card_title = card_title;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public long getCard_money() {
        return card_money;
    }

    public void setCard_money(long card_money) {
        this.card_money = card_money;
    }

    public String getCard_password() {
        return card_password;
    }

    public void setCard_password(String card_password) {
        this.card_password = card_password;
    }

    public String getCard_password2() {
        return card_password2;
    }

    public void setCard_password2(String card_password2) {
        this.card_password2 = card_password2;
    }

    public String getCard_cvv2() {
        return card_cvv2;
    }

    public void setCard_cvv2(String card_cvv2) {
        this.card_cvv2 = card_cvv2;
    }

    public String getCard_expireDate() {
        return card_expireDate;
    }

    public void setCard_expireDate(String card_expireDate) {
        this.card_expireDate = card_expireDate;
    }
}
