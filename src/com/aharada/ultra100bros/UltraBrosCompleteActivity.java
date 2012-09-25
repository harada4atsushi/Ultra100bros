package com.aharada.ultra100bros;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * ウルトラ兄弟コンプリート画面のActivityクラス
 * @author a_harada
 */
public class UltraBrosCompleteActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.ultra_bros_complete);
    }

	/**
	 * コンプリート画像クリック時の処理
	 * @param view View
	 */
	public void onClickCompleteImage(View view) {
		finish();
	}	
}
