package com.aharada.ultra100bros;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ToggleButton;

/**
 * ウルトラ兄弟変身画面（TOP画面）のActivityクラス
 * @author a_harada
 */
public class Ultra100brosActivity extends Activity 
	implements SensorEventListener {
	
	/**
	 * 広告
	 */
	//private MasAdView mad = null;
	
	/**
	 * DBから取得するカラム名の配列
	 */
	private static final String[] SELECT_COLUMNS = {"count(*) as count"};
	
	/**
	 * DBから取得するWhere条件
	 */
	private static final String WHERE = "is_transformed = 0";
	
	// センサーマネージャ
	private SensorManager mSensorManager;

	// 加速度センサーの値
	private float[] accelerations = new float[3];
	
	// 加速度センサーの値
	private float[] sumAccelerations = new float[3];

	// ローパスフィルター算出用
	private final float alpha = 0.8f;

	// ローパスフィルター
	private float[] gravity = new float[3];
	
	// SensorChange開始時
	private long preTime = 0;
	
	// 設定値
	private  SharedPreferences sharedPreferences;
	
	// 初回SensorChange判定フラグ
	private boolean isFirstSensorChange = true;
	  
	private MediaPlayer mMediaPlayer;
	
	private Handler mHandler = new Handler();
	
	// DatabaseHelper
	private DatabaseHelper dbHelper;
	
	// 更新可能DB
    private SQLiteDatabase db;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        // 広告表示
        /*
        mad = (MasAdView)findViewById(R.id.adview);
        mad.setAuid(getResources().getString(R.string.au_id));
        mad.start();
        */
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mMediaPlayer = MediaPlayer.create(this, R.raw.ultra_sound);
        
        // DB初期化
    	dbHelper = new DatabaseHelper(this);
    	db = dbHelper.getReadableDatabase();
    	
    	// コンプリートボタン表示処理
    	displayCompleteButton();
    }
    
    /**
     * Activityを離れる時の処理
     */
    @Override
    protected void onPause() {
    	super.onPause();
    	// センサー登録解除
    	mSensorManager.unregisterListener(this);
    	//mad.stop();
    }
    
    /**
     * メニュー項目作成時の処理
     * @param menu Menu
     */
    /*public boolean onCreateOptionsMenu(Menu menu) {
    	boolean ret = super.onCreateOptionsMenu(menu);
    	menu.add(0, Menu.FIRST , Menu.NONE , "センサー調整")
    		.setIcon(android.R.drawable.ic_menu_preferences);
    	return ret;
    }*/
    
    /**
     * メニューアイテム選択時の処理
     * @param item MenuItem
     */
    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case Menu.FIRST:
	        	Dialog dlg = new DialogAdjustAccelerometer(this);
	            dlg.show();
	            return true;
	        default:
	            return true;
        }
    }*/
    
	/**
	 * 加速度のセンサーの値が変更されたとき
	 * @param event SensorEvent
	 */
	@Override
	public synchronized void onSensorChanged(SensorEvent event) {
		
		// 加速度センサー以外は処理しない
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}

		long curTime = System.currentTimeMillis();
		long diffTime = curTime - preTime;

		// ローパスフィルターで重力加速度を抽出する
		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

		// 初回SensorChangeの場合は処理しない(ローパスフィルターが正しく取得できないため)
		if (isFirstSensorChange) {
			isFirstSensorChange = false;
			return;
		}
		
		// 重力加速度を測定値から除く
		accelerations[0] = event.values[0] - gravity[0];
		accelerations[1] = event.values[1] - gravity[1];
		accelerations[2] = event.values[2] - gravity[2];
		
		// 0.2秒間の加速度を計測する
		if (diffTime > 200f) {
			
			// しきい値判定で変身作動
			float adjustAccelerometer = (float)sharedPreferences.getInt(
					DialogAdjustAccelerometer.KEY_ACCELEROMETER_ADJUST_VALUE, 
					DialogAdjustAccelerometer.DEFAULT_ACCELEROMETER_ADJUST_VALUE);
			//Log.v("accel", " y=" + sumAccelerations[1] + " z=" + sumAccelerations[2]);
			//Log.v("accel", "adjustAccelerometer=" + adjustAccelerometer);
			if ((sumAccelerations[1] > adjustAccelerometer
					&& sumAccelerations[2] < sumAccelerations[1]) || adjustAccelerometer == 0f) {
					transformAndTransition();
			}
			// クリア
			sumAccelerations[0] = 0f;
			sumAccelerations[1] = 0f;
			sumAccelerations[2] = 0f;
			preTime = curTime;
			
		} else {
			if (accelerations[1] > 0) sumAccelerations[1] += accelerations[1];
			sumAccelerations[2] += Math.abs(accelerations[2]);
		}
	}
	
	/**
	 * 変身ボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnTransForm(View view) {
		ToggleButton tglBtn = (ToggleButton)view.findViewById(R.id.btn_transform);
		if (tglBtn.isChecked()) {
			registAccelerometerSensor();
		} else {
			mSensorManager.unregisterListener(this);
		}
	}
	
	/**
	 * 兄弟一覧ボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnBrosList(View view) {
		// 画面遷移する
		Intent intent = new Intent(Ultra100brosActivity.this, UltraBrosListActivity.class);
		startActivity(intent);
	}

	/**
	 * センサー調整ボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnSensorAdjust(View view) {
		Dialog dlg = new DialogAdjustAccelerometer(this);
        dlg.show();
	}
	
	/**
	 * コンプリートボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnComplete(View view) {
		// 画面遷移する
		Intent intent = new Intent(Ultra100brosActivity.this, UltraBrosCompleteActivity.class);
		startActivity(intent);
	}
	
	/**
	 * レビュー投稿ボタン押下時の処理
	 * @param view View
	 */
	public void onClickBtnReviewUp(View view) {
		// マーケットを開く
		Uri url = Uri.parse("market://details?id=com.aharada.ultra100bros");
		Intent intent = new Intent(Intent.ACTION_VIEW, url);
		startActivity(intent);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// 使用しない
	}
	
	/**
	 * Activity再表示時の処理
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Toggleボタンオフ
		((ToggleButton)findViewById(R.id.btn_transform)).setChecked(false);
		//mad.start();
	}
	
    /**
     * Activityが破棄されるとき
     */
	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	mSensorManager.unregisterListener(this);
    	mMediaPlayer.release();
    	db.close();
    }
	
	/**
	 * 加速度センサーリスニング開始する
	 */
	private void registAccelerometerSensor() {
		// センサーサービス取得
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		// 加速度センサーをリスニング
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}
	
	/**
	 * 変身したウルトラマン画面へ遷移する
	 */
	private void transformAndTransition() {
		// センサー登録解除
		mSensorManager.unregisterListener(this);

		// 変身効果音
        mMediaPlayer.start();
        
		// 画面を点滅させる
/*      
  		AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(2500);
        alpha.setInterpolator(new CycleInterpolator(8));
        findViewById(R.id.anim_region).startAnimation(alpha);
*/        
        // バイブレータ起動
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);
        
		mHandler.postDelayed(new Runnable() {
			public void run() {
				// 画面遷移する
				Intent intent = new Intent(Ultra100brosActivity.this, UltraDisplayActivity.class);
				startActivity(intent);
			}
		}, 2500);
	}
	
	/**
	 * コンプリートボタンの表示判定をする
	 */
	private void displayCompleteButton() {
		
		// DBからウルトラ兄弟のデータを取得する
		Cursor cursor = db.query("ultra_bros", SELECT_COLUMNS, WHERE, null, null, null, null);
  		startManagingCursor(cursor);
		
        int rowCount = 0;
        if (cursor.moveToNext()) {
        	rowCount = cursor.getInt(cursor.getColumnIndex("count"));
        	
        	// すべてのウルトラ兄弟が表示済みの場合
        	if (rowCount == 0) {
        		ImageView btnComplete = (ImageView)findViewById(R.id.btn_complete);
        		btnComplete.setVisibility(View.VISIBLE);
        	}
        }
	}
	
	/**
	 * 一定時間処理をストップさせるメソッド
	 * @param msec 一時停止するミリ秒数
	 */
	/*private synchronized void sleep(long msec) {
		try {
			wait(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
}

