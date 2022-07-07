package ir.almasapps.almascardmanager.Adapters;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class BankAdapter {
    public String[][] Banks = new String[][]{
            {"بانک اقتصاد نوین", "627412", "ico_eghtesad_novin"},
            {"بانک انصار", "627381", "ico_ansar"},
            {"بانک ایران زمین", "505785", "ico_iranzamin"},
            {"بانک پارسیان", "622106", "ico_parsian"},
            {"بانک پارسیان", "639194", "ico_parsian"},
            {"بانک پارسیان", "627884", "ico_parsian"},
            {"بانک پاسارگاد", "639347", "ico_pasargad"},
            {"بانک پاسارگاد", "502229", "ico_pasargad"},
            {"بانک آینده", "636214", "ico_ayande"},
            {"بانک تجارت", "627353", "ico_tejarat"},
            {"بانک توسعه تعاون", "502908", "ico_tosee_taavon"},
            {"بانک توسعه صادرات ایران", "627648", "ico_tosee_saderat_iran"},
            {"بانک توسعه صادرات ایران", "207177", "ico_tosee_saderat_iran"},
            {"بانک حکمت ایرانیان", "636949", "ico_hekmat_iranian"},
            {"بانک دی", "502938", "ico_dey"},
            {"بانک رفاه کارگران", "589463", "ico_refah"},
            {"بانک سامان", "621986", "ico_saman"},
            {"بانک سپه", "589210", "ico_sepah"},
            {"بانک سرمایه", "639607", "ico_sarmaye"},
            {"بانک سینا", "639346", "ico_sina"},
            {"بانک شهر", "502806", "ico_shahr"},
            {"بانک صادرات ایران", "603769", "ico_saderat"},
            {"بانک صنعت و معدن", "627961", "ico_sanat_va_madan"},
            {"بانک قرض الحسنه مهر ایران", "606373", "ico_gharzolhasane_mehr_iran"},
            {"بانک قوامین", "639599", "ico_ghavamin"},
            {"بانک کارآفرین", "627488", "ico_karafarin"},
            {"بانک کارآفرین", "502910", "ico_karafarin"},
            {"بانک کشاورزی", "603770", "ico_keshavarzi"},
            {"بانک کشاورزی", "639217", "ico_keshavarzi"},
            {"بانک گردشگری", "505416", "ico_gardeshgari"},
            {"بانک مرکزی", "636795", "ico_bank_markazi"},
            {"بانک مسکن", "628023", "ico_maskan"},
            {"بانک ملت", "610433", "ico_mellat"},
            {"بانک ملت", "991975", "ico_mellat"},
            {"بانک ملی ایران", "603799", "ico_meli"},
            {"بانک مهر اقتصاد", "639370", "ico_mehr_eghtesad"},
            {"پست بانک ایران", "627760", "ico_post_bank"},
            {"موسسه اعتباری توسعه", "628157", "ico_moasese_etebari_tosee"},
            {"موسسه اعتباری کوثر", "505801", "ico_moasese_etebari_kosar"},
    };

    public String getBankName(String strCharacter) {
        String str = strCharacter;
        for (int i = 0; i < Banks.length; i++) {
            if (Banks[i][1].equals(str)) {
                str = Banks[i][0].toString();
            }
        }

        return str;
    }
    public String[] getBankArray(String strCharacter) {
        String[] str = new String[]{"نامشخص","","ico_unknow"};
        for (int i = 0; i < Banks.length; i++) {
            if (Banks[i][1].equals(strCharacter)) {
                str = Banks[i];
            }
        }

        return str;
    }


}
