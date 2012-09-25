package com.aharada.ultra100bros;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * オープニングタイトル画面を表示するActivity
 * @author a_harada
 */
public class OpeningActivity extends Activity {

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opening);

		// オープニング効果音
//		MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.opening_sound);
//		mMediaPlayer.start();

		final Handler mHandler = new Handler();
		Timer mTimer = new Timer();

		// 自動的に画面遷移する
		TimerTask mTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.post( new Runnable() {
					public void run() {
						// 画面遷移する
						Intent intent = new Intent(OpeningActivity.this, Ultra100brosActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}                    
		};
		mTimer.schedule(mTask, 3000);  
	}
}
