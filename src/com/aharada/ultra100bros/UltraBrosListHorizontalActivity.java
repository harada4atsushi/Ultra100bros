package com.aharada.ultra100bros;

import mediba.ad.sdk.android.openx.MasAdView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * ウルトラ兄弟一覧を表示するActivity
 * @author a.harada
 */
public class UltraBrosListHorizontalActivity extends Activity
	implements OnGestureListener {
	
	/**
	 * 広告
	 */
	//private MasAdView mad = null;
	
	/**
	 * ViewFiliperクラス
	 */
	private ViewFlipper viewFlipper;
	 
	/**
	 * DatabaseHelper
	 */
	private DatabaseHelper dbHelper;
	
	/**
	 * 更新可能DB
	 */
	private SQLiteDatabase db;
	
	// フリックアニメーション定義
	private Animation inFromRightAnimation;
	private Animation inFromLeftAnimation;
	private Animation outToRightAnimation;
	private Animation outToLeftAnimation;
	private GestureDetector mGestureDetector;
	
	
	/**
	 * DBから取得するカラム名の配列
	 */
	private static final String[] SELECT_COLUMNS = {"_id", "bros_no", "rare_level", "is_transformed", "is_displayed"};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.ultra_bros_list_horizontal);
    
    	// 広告表示
    	/*
        mad = (MasAdView)findViewById(R.id.adview);
        mad.setAuid(getResources().getString(R.string.au_id));
        mad.start();
        */
    	
    	// アニメーションを定義
    	mGestureDetector = new GestureDetector(this);
	    inFromRightAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
  	    inFromLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
  	    outToRightAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);
	  	outToLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
	  	
	  	Intent intent = getIntent();
	  	String selectBrosNo = intent.getStringExtra("bros_no");
	  	
	  	// DB取得
	  	dbHelper = new DatabaseHelper(this);
	  	db = dbHelper.getWritableDatabase();
	  	
	  	// 変身済みウルトラ兄弟をすべて描画する
	  	createUltraBrosDisplay(selectBrosNo);
    }
    
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
	 * TOPボタン押下時の処理
	 * @param view View
	 */
	public void onClickTopButton(View view) {
		// TOPへ戻る
		Intent intent = new Intent(UltraBrosListHorizontalActivity.this, Ultra100brosActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
	 * フリック時の処理 
	 * @param e1
	 * @param e2
	 * @param velocityX
	 * @param velocityY
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
		View lastChild = viewFlipper.getChildAt(viewFlipper.getChildCount() - 1);
		View firstChild = viewFlipper.getChildAt(0);
		// 右へフリックされ、かつ先頭のView以外
		if (velocityX > 0 && viewFlipper.getCurrentView() != firstChild) {
			viewFlipper.setInAnimation(inFromLeftAnimation);
			viewFlipper.setOutAnimation(outToRightAnimation);
			viewFlipper.showPrevious();
		// 左へフリックされ、かつ最後のView以外
		} else if (velocityX < 0 && viewFlipper.getCurrentView() != lastChild) {
			viewFlipper.setInAnimation(inFromRightAnimation);
			viewFlipper.setOutAnimation(outToLeftAnimation);
			viewFlipper.showNext();
		}
		// 表示済みに更新する
		TextView pBrosNoView = (TextView)viewFlipper.getCurrentView().findViewById(R.id.p_bros_no);
		updateUltraBrosDisplayed(pBrosNoView.getText().toString());
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {}


	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

	/**
	 * 変身済みウルトラ兄弟を描画する
	 * @param selectBrosNo 選択されたウルトラ兄弟No
	 */
	private void createUltraBrosDisplay(String selectBrosNo) {
		
		// 変身済みウルトラ兄弟のレコードを取得する
		Cursor cursor = db.query("ultra_bros", SELECT_COLUMNS, "is_transformed = ?",
				new String[]{"1"}, null, null, null);
  		startManagingCursor(cursor);

  		viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
	  	String brosNo;
  		Resources res = getResources();
  		int rareLevel;
  		int resourceId;
  		int cnt = 0;
  		
  		while (cursor.moveToNext()) {
  			View displayView = this.getLayoutInflater().inflate(R.layout.horizontal_display, null);
  	  		rareLevel = cursor.getInt(cursor.getColumnIndex("rare_level"));
  			brosNo = cursor.getString(cursor.getColumnIndex("bros_no"));
  			
  			// パラメータ用兄弟番号画像をセットする
  	        TextView textBrosNo = (TextView)displayView.findViewById(R.id.p_bros_no);
  	        textBrosNo.setText(brosNo);
  	        
  	        // 兄弟番号画像をセットする
  	        resourceId = res.getIdentifier("no" + brosNo, "drawable", R.class.getPackage().getName());
  	        ImageView imgBrosNo = (ImageView)displayView.findViewById(R.id.ultra_bros_no);
  	        imgBrosNo.setImageDrawable(res.getDrawable(resourceId));

  	        // ウルトラ兄弟名画像をセットする
  	        resourceId = res.getIdentifier("ultra_name_" + brosNo, "drawable", R.class.getPackage().getName());
  	        ImageView imgBrosName = (ImageView)displayView.findViewById(R.id.ultra_bros_name);
  	        imgBrosName.setImageDrawable(res.getDrawable(resourceId));

  	        // ウルトラ兄弟本体画像をセットする
  	        resourceId = res.getIdentifier("no" + brosNo + "_ultrabros", "drawable", R.class.getPackage().getName());
  	        ImageView imgUltraBody = (ImageView)displayView.findViewById(R.id.ultra_bros_body);
  	        imgUltraBody.setImageDrawable(res.getDrawable(resourceId));

  	        // ウルトラ兄弟説明テキスト画像をセットする
  	        resourceId = res.getIdentifier("ultra_text_" + brosNo, "drawable", R.class.getPackage().getName());
  	        ImageView imgUltraText = (ImageView)displayView.findViewById(R.id.ultra_bros_text);
  	        imgUltraText.setImageDrawable(res.getDrawable(resourceId));
  	        
  	        // ウルトラ兄弟レアレベル画像をセットする
  	        resourceId = res.getIdentifier("rare_level" + rareLevel, "drawable", R.class.getPackage().getName());
  	        ImageView imgRareLevel = (ImageView)displayView.findViewById(R.id.rare_level);
  	        imgRareLevel.setImageDrawable(res.getDrawable(resourceId));
  	        
  	        viewFlipper.addView(displayView);
  	        if (selectBrosNo.equals(brosNo)) {
  	        	viewFlipper.setDisplayedChild(cnt);
  	        	updateUltraBrosDisplayed(brosNo);
  	        }
  	        // 最初のViewは左矢印非表示
  	        if (cursor.isFirst()) {
  	        	((ImageView)displayView.findViewById(R.id.arrow_left)).setVisibility(View.INVISIBLE);
  	        }
  	        // 最後のViewは右矢印非表示
  	        if (cursor.isLast()) {
  	        	((ImageView)displayView.findViewById(R.id.arrow_right)).setVisibility(View.INVISIBLE);
  	        }
  	        cnt++;
  		}
	}
	
	/**
	 * ウルトラ兄弟を表示済み状態に更新する
	 */
	private void updateUltraBrosDisplayed(String brosNo) {
		// 表示済みとしてDB更新
		ContentValues values = new ContentValues();
		values.put("is_displayed", 1);
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
