package com.example.elvis.elvisafrifapset8;

import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {
    Cursor cursor;
    RestoDatabase db;

    private int getOrderPrice(Cursor cursor) {
        int orderPrice = 0;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                int amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amount")));
                orderPrice += (price * amount);
            }
        }
        return orderPrice;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ListView orderlist = view.findViewById(R.id.orderlist);

        db = RestoDatabase.getInstance(getContext());
        cursor = db.selectAll();

        RestoAdapter adapter = new RestoAdapter(getContext(), cursor);
        orderlist.setAdapter(adapter);

        Button cancel = view.findViewById(R.id.cancelbut);
        cancel.setOnClickListener(this);

        Button confirm = view.findViewById(R.id.orderbut);
        confirm.setOnClickListener(this);

        int totalPr = getOrderPrice(cursor);
        TextView title = view.findViewById(R.id.orderamount);
        title.setText("Price: €" + totalPr);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.orderbut:
                TextView orderplaced = getView().findViewById(R.id.orderplaced);
                orderplaced.setText("Success!");
                break;
            case R.id.cancelbut:
                db.clear();
                this.dismiss();
                break;
        }
    }
}
