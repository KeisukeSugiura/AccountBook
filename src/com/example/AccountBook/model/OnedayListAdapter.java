package com.example.AccountBook.model;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.AccountBook.R;
import com.example.AccountBook.view.SelectIconView;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class OnedayListAdapter extends BaseAdapter {

    ArrayList<DayAccountModel> mAccount;
    LayoutInflater mInflater;
    Activity mActivity;
    Fragment mFragment;

    private class HoldViewItem {
        TextView price;
        ImageView type;
        ImageView delete;
        DayAccountModel model;
    }

    public OnedayListAdapter(Fragment fragment, ArrayList<DayAccountModel> pricies) {
        Context context = fragment.getActivity();
        mInflater = LayoutInflater.from(context);
        mAccount = pricies;
        mActivity = (Activity)context;
        mFragment =fragment;
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
            holder.type = (ImageView) view.findViewById(R.id.account_list_item_type);
            holder.delete = (ImageView) view.findViewById(R.id.account_list_item_button_delete);
            holder.delete.setOnClickListener(new OnDeleteClickListener());
            view.setTag(holder);
        } else {
            holder = (HoldViewItem) view.getTag();

        }

        holder.price.setText(String.valueOf(mAccount.get(i).getPrice())+" 円");
        holder.type.setImageResource(SelectIconView.getDrawableId(mAccount.get(i).getType()));
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


    public void clearAccouunt(){
        mAccount.clear();
    }

    private void deleteItem(DayAccountModel model){
       // DayAccountFragment activity = (DayAccountFragment) getFragmentManager()
         //       .findFragmentByTag("pager");

       Log.d("delete","sendsend");
        if (mFragment instanceof DeleteListener) {
            DeleteListener listener = (DeleteListener) mFragment;
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
