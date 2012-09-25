package com.aharada.ultra100bros.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.aharada.ultra100bros.R;
import com.aharada.ultra100bros.UltraBrosListHorizontalActivity;

/**
 * ウルトラ兄弟一覧画面のAdapterクラス
 * @author a_harada
 */
public class UltraBrosListAdapter extends ArrayAdapter<UltraBrosListData> {

	/**
	 * LayoutInflate
	 */
	private LayoutInflater _layoutInflater;

	/**
	 * ウルトラ兄弟番号
	 */
	//private String brosNo;
	
	/**
	 * コンストラクタ
	 * @param context
	 * @param textViewResourceId
	 * @param dataList
	 */
	public UltraBrosListAdapter(Context context, int textViewResourceId, List<UltraBrosListData> dataList) {
		 super(context, textViewResourceId, dataList);
		 _layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	 /**
	  * ListView一行ごとの処理
	  * @param position 処理中の行数
	  * @param convertView 処理中の行View
	  * @param parent 親
  	  */
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 // 特定の行(position)のデータを得る
		 UltraBrosListData item = (UltraBrosListData)getItem(position);
	 
		 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		 //if (null == convertView) {
		 convertView = _layoutInflater.inflate(R.layout.ultra_bros_row, null);
		 //}
	 
		 Resources res = parent.getResources();
         int resourceId;
         Bitmap bitmapImg;

         // newマーク
         // 変身済みかつ未閲覧の場合
         if (item.getIsTransformed() == 1 && item.getIsDisplayed() == 0) {
	         ImageView imgNewMark = (ImageView)convertView.findViewById(R.id.new_mark);
	         resourceId = res.getIdentifier("new_mark", "drawable", R.class.getPackage().getName());
	         bitmapImg = BitmapFactory.decodeResource(parent.getResources(), resourceId);
	         imgNewMark.setImageBitmap(bitmapImg);
         }
         
         // no
         final String brosNo = item.getBrosNo();
		 ImageView imgBrosNo = (ImageView)convertView.findViewById(R.id.bros_no);
		 resourceId = res.getIdentifier("no" + brosNo, "drawable", R.class.getPackage().getName());
		 bitmapImg = BitmapFactory.decodeResource(parent.getResources(), resourceId);
		 imgBrosNo.setImageBitmap(bitmapImg);
		 
		 // 名前
		 String imageName;
		 ImageView imgBrosName = (ImageView)convertView.findViewById(R.id.bros_name);
		 
		 // 一旦OnClickを無効にする
         convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {}
         });
         
		 // レアレベル4かつ変身していない場合
    	 if (item.getRareLevel() == 4 && item.getIsTransformed() == 0) {
    		 imageName = "ultra_name_unkown";
         // 未変身
    	 } else if (item.getIsTransformed() == 0) {
    		 imageName = "ultra_name_" + brosNo + "b";
    	 // 変身済
    	 } else {
        	 imageName = "ultra_name_" + brosNo;
        	 // 行選択時の処理
	         convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), UltraBrosListHorizontalActivity.class);
					intent.putExtra("bros_no", brosNo);
					v.getContext().startActivity(intent);
				}
	         });
    	 }
         resourceId = res.getIdentifier(imageName, "drawable", R.class.getPackage().getName());
         bitmapImg = BitmapFactory.decodeResource(parent.getResources(),resourceId);
         imgBrosName.setImageBitmap(bitmapImg);
         
		 return convertView;
	 }
}
