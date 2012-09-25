package com.aharada.ultra100bros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB管理を補助するクラス
 * @author a_harada
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DBNAME = "ultra100bros.db";
	private static final int DBVERSION = 1;
	
	/**
	 * ウルトラ兄弟情報CREATE文
	 */
	private final String CREATE_TABLE_ULTRA_BROS = 
			"create table ultra_bros " +
				"(_id integer primary key autoincrement, " +
				"bros_no, " +
				"rare_level integer not null," +
				"is_transformed integer not null, " +
				"is_displayed integer not null, " +
				"constraint ct_is_transformed check (is_transformed in (0, 1))," +
				"constraint ct_is_displayed check (is_transformed in (0, 1)))";

	/**
	 * コンストラクタ
	 * @param context Context
	 */
	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	/**
	 * OnCreate時の処理
	 * @param db SQLiteDatabase
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 初期データ投入
		db.execSQL(CREATE_TABLE_ULTRA_BROS);
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('001', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('002', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('003', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('004', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('005', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('006', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('007', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('008', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('009', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('010', 4, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('011', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('012', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('013', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('014', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('015', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('016', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('017', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('018', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('019', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('020', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('021', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('022', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('023', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('024', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('025', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('026', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('027', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('028', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('029', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('030', 3, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('031', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('032', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('033', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('034', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('035', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('036', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('037', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('038', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('039', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('040', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('041', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('042', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('043', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('044', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('045', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('046', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('047', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('048', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('049', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('050', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('051', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('052', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('053', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('054', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('055', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('056', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('057', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('058', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('059', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('060', 2, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('061', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('062', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('063', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('064', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('065', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('066', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('067', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('068', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('069', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('070', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('071', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('072', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('073', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('074', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('075', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('076', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('077', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('078', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('079', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('080', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('081', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('082', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('083', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('084', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('085', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('086', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('087', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('088', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('089', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('090', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('091', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('092', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('093', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('094', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('095', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('096', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('097', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('098', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('099', 1, 0 ,0);");
		db.execSQL("insert into ultra_bros(bros_no, rare_level, is_transformed, is_displayed) values ('100', 1, 0 ,0);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
