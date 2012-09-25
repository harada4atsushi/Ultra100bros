package com.aharada.ultra100bros;

import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * ウルトラ兄弟を表示するActivity
 * @author a_harada
 */
public class UltraDisplayActivity extends Activity {
	
	/**
	 * DBから取得するカラム名の配列
	 */
	private static final String[] SELECT_COLUMNS = {"_id", "bros_no", "rare_level"};
	
	/**
	 * DBから取得するWhere条件
	 */
	private static final String WHERE = "rare_level = ?";
	
	/**
	 * 広告
	 */
	//private MasAdView mad = null;
	
	/**
	 * DatabaseHelper
	 */
	private DatabaseHelper dbHelper;
	
	/**
	 * 更新可能DB
	 */
    private SQLiteDatabase db;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.ultra_display);
    	
        // 広告表示
        /*
        mad.setAuid(getResources().getString(R.string.au_id));
        mad.start();
         */
        
    	// DB初期化
    	dbHelper = new DatabaseHelper(this);
    	db = dbHelper.getWritableDatabase();

    	// ウルトラ兄弟を決定する
    	selectUltraBros();

        // 効果音を挿入
    	MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.ultra_sound_voice);
        mMediaPlayer.start();
        
    }

	/**
	 * 兄弟一覧ボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnBrosList(View view) {
		// 画面遷移する
		Intent intent = new Intent(UltraDisplayActivity.this, UltraBrosListActivity.class);
		startActivity(intent);
	}

	/**
	 * 戻るボタン押下時の処理
	 * @param view View
	 */
	public void onClickBackButton(View view) {
		finish();
	}
	
	/**
	 * 表示されるウルトラ兄弟を判定する
	 */
	private void selectUltraBros() {
		Resources res = getResources();
        Random rnd = new Random();
        int ran = rnd.nextInt(100);
        int resourceId = 0; 
        int rareLevel = 1;
        
        // 乱数によってレアレベル判定
        if (ran <= 5) {
        	rareLevel = 4;
        } else if (ran <= 20) {
        	rareLevel = 3;
        } else if (ran <= 50) {
        	rareLevel = 2;
        } else if (ran <= 100) {
        	rareLevel = 1;
        }
        
        // DBからウルトラ兄弟のデータを取得する
		Cursor cursor = db.query("ultra_bros", SELECT_COLUMNS, WHERE,
				new String[]{String.valueOf(rareLevel)}, null, null, "random()");
  		startManagingCursor(cursor);
		
        String brosNo = null;
        if (cursor.moveToNext()) {
        	brosNo = cursor.getString(cursor.getColumnIndex("bros_no"));
        }
        // 兄弟番号画像をセットする
        resourceId = res.getIdentifier("no" + brosNo, "drawable", R.class.getPackage().getName());
        ImageView imgBrosNo = (ImageView)findViewById(R.id.ultra_bros_no);
        imgBrosNo.setImageDrawable(res.getDrawable(resourceId));

        // ウルトラ兄弟名画像をセットする
        resourceId = res.getIdentifier("ultra_name_" + brosNo, "drawable", R.class.getPackage().getName());
        ImageView imgBrosName = (ImageView)findViewById(R.id.ultra_bros_name);
        imgBrosName.setImageDrawable(res.getDrawable(resourceId));

        // ウルトラ兄弟本体画像をセットする
        resourceId = res.getIdentifier("no" + brosNo + "_ultrabros", "drawable", R.class.getPackage().getName());
        ImageView imgUltraBody = (ImageView)findViewById(R.id.ultra_bros_body);
        imgUltraBody.setImageDrawable(res.getDrawable(resourceId));

        // ウルトラ兄弟説明テキスト画像をセットする
        resourceId = res.getIdentifier("ultra_text_" + brosNo, "drawable", R.class.getPackage().getName());
        ImageView imgUltraText = (ImageView)findViewById(R.id.ultra_bros_text);
        imgUltraText.setImageDrawable(res.getDrawable(resourceId));
        
        // ウルトラ兄弟レアレベル画像をセットする
        resourceId = res.getIdentifier("rare_level" + rareLevel, "drawable", R.class.getPackage().getName());
        ImageView imgRareLevel = (ImageView)findViewById(R.id.rare_level);
        imgRareLevel.setImageDrawable(res.getDrawable(resourceId));
        
        // 変身済みとしてDB更新
        ContentValues values = new ContentValues();
        values.put("is_transformed", 1);
        db.update("ultra_bros", values, "bros_no = ?", new String[]{brosNo});
	}

	/**
	 * Activityを離れる時の処理
	 */
	@Override
	protected void onPause() {
		super.onPause();
		//mad.stop();
	}

	/**
	 * Activity再表示時の処理
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//mad.start();
	}
	
	/**
	 * Activity破棄時の処理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
}
