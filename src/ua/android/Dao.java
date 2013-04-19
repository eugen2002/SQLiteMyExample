package ua.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao implements IDao {

    Context	context;
    SQLiteDatabase db;
    Cursor	 c;
    String	 table   = "";
    DbHelper       dbHelper;

    final String   LOG_TAG = "my_Log";

    public Dao(Context context) {
	this.context = context;
    }

    public void initDB() {

	dbHelper = new DbHelper(context);
	db = dbHelper.getWritableDatabase();
	table = dbHelper.getTableName();
	c = db.query(table, null, null, null, null, null, null);
    }

    public String readAll() {

	String result = "";
	if (c.moveToFirst()) {
	    int idColIndex = c.getColumnIndex("id");
	    int fNameColIndex = c.getColumnIndex("FirstName");
	    int lNameColIndex = c.getColumnIndex("LastName");
	    int ageColIndex = c.getColumnIndex("age");

	    do {
		// запись значений в переменную result
		result += c.getInt(idColIndex) + ". " + c.getString(fNameColIndex) + " "
			+ c.getString(lNameColIndex) + ", " + c.getInt(ageColIndex) + "\n";

		Log.d(LOG_TAG, result);
	    } while (c.moveToNext());
	} else {
	    Log.d(LOG_TAG, "0 rows");
	}
	c.close();
	return result;
    }

    public String insert(String id, String fName, String lName, String age) {

	int intId = Integer.valueOf(id);
	int intAge = Integer.valueOf(age);
	if (c.getCount() == 0) {
	    ContentValues cv = new ContentValues();
	    cv.put("id", intId);
	    cv.put("FirstName", fName);
	    cv.put("LastName", lName);
	    cv.put("age", intAge);
	    db.insert(table, null, cv);
	}
	return (intId + "." + fName + " " + lName + ", " + intAge);
    }

    public String update(String id, String fName, String lName, String age) {

	ContentValues cv = new ContentValues();
	int intId = Integer.valueOf(id);
	int intAge = Integer.valueOf(age);
	if (id.equalsIgnoreCase("")) {
	    Log.d(LOG_TAG, "Error");
	}
	cv.put("ID", intId);
	cv.put("FirstName", fName);
	cv.put("LastName", lName);
	cv.put("Age", intAge);
	db.update(table, cv, "id = ?", new String[] { id });
	return (intId + "." + fName + " " + lName + ", " + intAge);

    }

    public int deleteById(String id) {

	int intId = Integer.valueOf(id);
	db.delete(table, "id = " + intId, null);
	return intId;

    }

}
