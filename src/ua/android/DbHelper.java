package ua.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "my_Log";
    
//    public String table = "peoples";
    public String table = "peoples";
    
    public DbHelper(Context context) {
	super(context, "DataBase01", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	Log.d(LOG_TAG, "--- onCreate database ---");
	// создаем таблицу с полями
	String query = "create table " + table + " ("
		+ "id integer primary key autoincrement," + "FirstName text,"
		+ "LastName text," + "age integer" + ");";
	db.execSQL(query);
    }

    public String getTableName() {
	return table;
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//	String query = "create " + table2 + " ("
//		+ "id integer primary key autoincrement," + "FirstName text,"
//		+ "LastName text," + "age integer" + ");";
//	db.execSQL(query);

    }
}
