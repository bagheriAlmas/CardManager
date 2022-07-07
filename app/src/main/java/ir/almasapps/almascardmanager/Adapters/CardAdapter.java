package ir.almasapps.almascardmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import ir.almasapps.almasalmascardmanager.R;
import ir.almasapps.almascardmanager.CardActivity;
import ir.almasapps.almascardmanager.DB.Model.Card;
import ir.almasapps.almascardmanager.DB.View.CreateView;
import ir.almasapps.almascardmanager.Helper.Convertor;

/**
 * Created by mahdi on 12/17/2017 AD.
 */

public class CardAdapter extends BaseAdapter {
    Context context;
    CreateView createView;
    LayoutInflater inflater;
    TextView txtTitle, txtNumber;
    CircularImageView imgBank;
    View list_item;
    BankAdapter bankAdapter;

    public CardAdapter(Context context) {
        this.context = context;
        createView = new CreateView(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bankAdapter = new BankAdapter();
    }

    @Override
    public int getCount() {
        return createView.getCardCount();
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        final List<Card> cardList = createView.selectAllCards();

        list_item = inflater.inflate(R.layout.list_item, null);
        txtNumber = (TextView) list_item.findViewById(R.id.list_item_txtNumber);
        txtTitle = (TextView) list_item.findViewById(R.id.list_item_txtTitle);
        imgBank = (CircularImageView) list_item.findViewById(R.id.list_item_imgBank);

        final Card myCard = cardList.get(position);
        txtTitle.setText(myCard.getCard_title());

        txtNumber.setText(Convertor.CardNumber.makeSplitNumber(myCard.getCard_number())+"");

        String BankNum6 = myCard.getCard_number().subSequence(0, 6).toString();
        String[] myBank = bankAdapter.getBankArray(BankNum6);


        final String imageName = myBank[2].toString();
        int resID = context.getResources().getIdentifier(imageName, "drawable", "ir.almasapps.cardmanager");
// Set Border
        imgBank.setBorderColor(context.getResources().getColor(R.color.colorPrimary));
        imgBank.setBorderWidth(7);
// Add Shadow with default param
        imgBank.addShadow();
// or with custom param
        imgBank.setShadowRadius(1);
        imgBank.setShadowColor(Color.LTGRAY);
        imgBank.setImageResource(resID);



/*
        String imageName = hotelList.get(position).getHotel_picture();
        List<String> imageArray = Arrays.asList(imageName.split(","));
        int resID = context.getResources().getIdentifier(imageArray.get(0), "drawable", "com.example.mahdi.hotel");
        imgHotel.setImageResource(resID);
*/

        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardActivity.class);
                intent.putExtra("ID", myCard.getCard_id());
                context.startActivity(intent);
            }
        });
        return list_item;
    }
}
