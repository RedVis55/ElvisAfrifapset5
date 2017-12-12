package com.example.elvis.elvisafrifapset8;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by ELV on 03-Dec-17.
 */

public class RestoAdapter extends ResourceCursorAdapter {


    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_rest, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dishes = view.findViewById(R.id.dishes);

        dishes.setText(cursor.getString(cursor.getColumnIndex("dish")));
    }
}

