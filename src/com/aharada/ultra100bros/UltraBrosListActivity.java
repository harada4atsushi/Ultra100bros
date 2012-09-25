package com.aharada.ultra100bros;

import java.util.ArrayList;
import java.util.List;

import mediba.ad.sdk.android.openx.MasAdView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.aharada.ultra100bros.adapter.UltraBrosListAdapter;
import com.aharada.ultra100bros.adapter.UltraBrosListData;

/**
 * ウルトラ兄弟一覧を表示するActivity
 * @author a.harada
 */
public class UltraBrosListActivity extends Activity {

	/**
	 * DBから取得するカラム名の配列
	 */
	private static final String[] COLUMNS = {"_id", "bros_no", "rare_level", "is_transformed", "is_displayed"};

	/**
	 * 広告
	 */
	//private MasAdView mad = null;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.ultra_bros_list);
         
         // 広告表示
         /*
         mad = (MasAdView)findViewById(R.id.adview);
         mad.setAuid(getResources().getString(R.string.au_id));
         mad.start();
         */
         
         // ウルトラ兄弟一覧を作成
         createUltraBrosList();
    }
	
    /**
     * Activity再表示時の処理
     */
    @Override
	protected void onResume() {
		super.onResume();
		//mad.start();
		// ウルトラ兄弟一覧を作成
        //createUltraBrosList();　//スクロール位置を記憶しないため一旦コメントアウト
	}

    /**
     * Activityから離れるときの処理
     */
    @Override
    protected void onPause() {
    	super.onPause();
    	//mad.stop();
    }
    
    /**
	 * TOPボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnTop(View view) {
		// TOPへ戻る
		Intent intent = new Intent(UltraBrosListActivity.this, Ultra100brosActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	/**
	 * DBからウルトラ兄弟を全て取得してListViewにセットする
	 */
	private void createUltraBrosList() {
		// DBからウルトラ兄弟のデータを取得する
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("ultra_bros", COLUMNS, null, null, null, null, "bros_no asc");
  		startManagingCursor(cursor);
		
        List<UltraBrosListData> ultraBrosList = new ArrayList<UltraBrosListData>();
        UltraBrosListData item;
        
        // 取得したデータをListにセットする
        while (cursor.moveToNext()) {
	       	 item = new UltraBrosListData();
	       	 item.setBrosNo(cursor.getString(cursor.getColumnIndex("bros_no")));
	       	 item.setIsTransformed(cursor.getInt(cursor.getColumnIndex("is_transformed")));
	       	 item.setRareLevel(cursor.getInt(cursor.getColumnIndex("rare_level")));
	       	 item.setIsDisplayed(cursor.getInt(cursor.getColumnIndex("is_displayed")));
	         ultraBrosList.add(item);
        }
        cursor.close();
 
        // ListをAdapterにセット
        UltraBrosListAdapter ultraAdapater = new UltraBrosListAdapter(this, 0, ultraBrosList);
        ListView listView = (ListView)findViewById(R.id.ultra_bros_list_view);
        listView.setAdapter(ultraAdapater);
        
        db.close();
	}
	
	/**
	 * Activity破棄時の処理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//db.close();
	}
}
