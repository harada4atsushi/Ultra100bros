package com.aharada.ultra100bros;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * 加速度センサーの感度を調整するダイアログの暮らす
 * @author a_harada
 */
public class DialogAdjustAccelerometer extends Dialog implements OnClickListener {

	// デフォルトの加速度センサー調整値
	public static int DEFAULT_ACCELEROMETER_ADJUST_VALUE = 2;

	// 加速度センサー調整値のキー
	public static String KEY_ACCELEROMETER_ADJUST_VALUE = "KEY_ACCELEROMETER_ADJUST_VALUE";

	// 設定値
	private SharedPreferences sharedPreferences;
	
	// 設定値表示TextView
	private TextView txtView;
	
	/**
	 * コンストラクタ
	 * @param context Context
	 */
	public DialogAdjustAccelerometer(Context context) {
		super(context);
	}
	
	/**
	 * onCreate時の処理
	 * @param savedInstanceState Bundle
	 */
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle("センサー調整");
		setContentView(R.layout.dialog_adjust_accelerometer);
		
		SeekBar seekBar = (SeekBar)findViewById(R.id.adjust_bar);
        txtView = (TextView)findViewById(R.id.seek_value);

        // 設定値を取得
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        int adjustAccelerometer = sharedPreferences.getInt(KEY_ACCELEROMETER_ADJUST_VALUE, DEFAULT_ACCELEROMETER_ADJUST_VALUE);
        seekBar.setProgress(adjustAccelerometer);
        
        // OKボタンにイベントを設定
        Button okButton = (Button)findViewById(R.id.button_ok);
        okButton.setOnClickListener(this);
        
        // シークバーの初期値をTextViewに表示
        txtView.setText("設定値：" + seekBar.getProgress());

        // シークバー値変更時の処理
	    seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	            // ツマミをドラッグしたときに呼ばれる
	        	txtView.setText("設定値：" + progress);
	        }
	        public void onStartTrackingTouch(SeekBar seekBar) {}
	        public void onStopTrackingTouch(SeekBar seekBar) {}
        });
	}
	
	/**
	 * クリック時の処理
	 * @param view View
	 */
	public void onClick(View view) {
		SeekBar seekBar = (SeekBar)findViewById(R.id.adjust_bar);
		Editor ed = sharedPreferences.edit();
		ed.putInt(KEY_ACCELEROMETER_ADJUST_VALUE, seekBar.getProgress());
		ed.commit();
		dismiss();
	}

}
