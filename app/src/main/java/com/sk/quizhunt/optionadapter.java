package com.sk.quizhunt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class optionadapter extends BaseAdapter {
    private List<itemAdapterTextbook> mList;
    public optionadapter(List<String> catlist, List<itemAdapterTextbook> mList) {
        this.catlist = catlist;
        this.mList=mList;
    }

    private List<String> catlist;
    @Override
    public int getCount() {
        return catlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        View view1;
        final itemAdapterTextbook itemAdapter = mList.get(i);
        if (view == null)
        {
            view1= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.optionitem,viewGroup,false);
        }
        else
        {
            view1=view;
        }
        ((TextView)  view1.findViewById(R.id.tvTitle)).setText(catlist.get(i));
        ImageView im=view1.findViewById(R.id.ivImage);
        im.setImageResource(itemAdapter.getImage());
    /*    Random random= new Random();
        int color = Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(255));
        view1.setBackgroundColor(color);*/
        view1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(final View view) {


                            Intent intent = new Intent(viewGroup.getContext(), Question.class);
                            intent.putExtra("class", i + 1);
                            viewGroup.getContext().startActivity(intent);

                    }
                });

        return view1;
    }
}
