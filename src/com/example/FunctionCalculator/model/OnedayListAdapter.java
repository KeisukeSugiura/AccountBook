package com.example.FunctionCalculator.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.FunctionCalculator.R;
import com.example.FunctionCalculator.activity.DayAccountFragment;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class OnedayListAdapter extends BaseAdapter {

    ArrayList<DayAccountModel> mAccount;
    LayoutInflater mInflater;
    Activity mActivity;

    private class HoldViewItem {
        TextView price;
        TextView type;
        Button delete;
        DayAccountModel model;
    }

    public OnedayListAdapter(Context context, ArrayList<DayAccountModel> pricies) {
        mInflater = LayoutInflater.from(context);
        mAccount = pricies;
        mActivity = (Activity)context;
    }

    @Override
    public int getCount() {
        return mAccount.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        HoldViewItem holder;
        if (view == null) {
            holder = new HoldViewItem();
            view = mInflater.inflate(R.layout.layout_account_list_item, null);

            holder.price = (TextView) view.findViewById(R.id.account_list_item_price);
            holder.type = (TextView) view.findViewById(R.id.account_list_item_type);
            holder.delete = (Button) view.findViewById(R.id.account_list_item_button_delete);
            holder.delete.setOnClickListener(new OnDeleteClickListener());
            view.setTag(holder);
        } else {
            holder = (HoldViewItem) view.getTag();

        }

        holder.price.setText(String.valueOf(mAccount.get(i).getPrice()));
        holder.type.setText(String.valueOf(mAccount.get(i).getType()));
        holder.delete.setTag(mAccount.get(i));
        return view;
    }


    /**
     * アカウントデータを追加する
     *
     * @param model
     */
    public void addAccount(DayAccountModel model) {
        mAccount.add(model);
    }

    public void deleteAccounrt(DayAccountModel model){
        mAccount.remove(model);
    }



    private void deleteItem(DayAccountModel model){
       // DayAccountFragment activity = (DayAccountFragment) getFragmentManager()
         //       .findFragmentByTag("pager");

        DayAccountFragment activity = (DayAccountFragment) mActivity.getFragmentManager().findFragmentByTag("DayAccount");
       Log.d("delete","sendsend");
        if (activity instanceof DeleteListener) {
            DeleteListener listener = (DeleteListener) activity;
            listener.deleteAccount(model);
        }
    }


    /**
     * デリートボタンのクリックリスナー
     */
    private class OnDeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //TODO  処理記述
            DayAccountModel model =(DayAccountModel)view.getTag();
            deleteItem(model);
        }
    }

    public interface DeleteListener{
        public void deleteAccount(DayAccountModel model);
    }

}
