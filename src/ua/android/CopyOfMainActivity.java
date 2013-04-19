package ua.android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CopyOfMainActivity extends Activity implements OnClickListener {

    final String LOG_TAG = "my_Log";

    String[] firstName = { "Анатолий", "Михаил", "Роман", "Владимир",
	    "Тимофей", "Александр", "Андрей" };
    String[] lastName = { "Пескарев", "Севин", "Сорокин", "Шевченко",
	    "Алексеев", "Токарчук", "Костин" };
    int[] ages = { 28, 34, 42, 39, 26, 33, 35 };

    DbHelper dbHelper;
    // Context context;
    SQLiteDatabase db;

    Button btnRead, btnInsert, btnUpdate, btnDelete;
    EditText etID, etFName, etLName, etAge;
    String table, id, fName, lName, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	initUI();
	

	dbHelper = new DbHelper(this);
	db = dbHelper.getWritableDatabase();
	table = "peoples";
	Cursor c = db.query(table, null, null, null, null, null, null);
	if (c.getCount() == 0) {
	    ContentValues cv = new ContentValues();
	    for (int i = 0; i < firstName.length; ++i) {
		cv.put("FirstName", firstName[i]);
		cv.put("LastName", lastName[i]);
		cv.put("age", ages[i]);
		long id = db.insert(table, null, cv);
		Log.d(LOG_TAG, "id = " + id);
	    }
	}
	c = db.query(table, null, null, null, null, null, null);
	c.close();
	dbHelper.close();
    }

    public void initUI() {

	etID = (EditText) findViewById(R.id.id);
	etFName = (EditText) findViewById(R.id.fName);
	etLName = (EditText) findViewById(R.id.lName);
	etAge = (EditText) findViewById(R.id.age);

	btnRead = (Button) findViewById(R.id.btnRead);
	btnInsert = (Button) findViewById(R.id.btnInsert);
	btnUpdate = (Button) findViewById(R.id.btnUpdate);
	btnDelete = (Button) findViewById(R.id.btnDelete);

	btnRead.setOnClickListener(this);
	btnInsert.setOnClickListener(this);
	btnUpdate.setOnClickListener(this);
	btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

	db = dbHelper.getWritableDatabase();
	ContentValues cv = new ContentValues();

	// Get display info
	id = etID.getText().toString();
	fName = etFName.getText().toString();
	lName = etLName.getText().toString();
	age = etAge.getText().toString();

	// переменные для query
	String[] columns = null; // список полей, которые мы хотим получить
	String selection = null; // строка условия WHERE
	String[] selectionArgs = null; // массив аргументов для selection. можно
				       // использовать знаки ? , а которые будут
				       // заменены этими значениями
	String groupBy = null; // группировка
	String having = null; // использование условий для агрегатных функций
	String orderBy = null; // сортировка

	Cursor c = null;

	switch (v.getId()) {
	case R.id.btnRead:
	    Log.d(LOG_TAG, "--- Все записи в таблице ---");
	    c = db.query(table, null, null, null, null, null, null);

	    if (c.moveToFirst()) {
		int idColIndex = c.getColumnIndex("id");
		int fNameColIndex = c.getColumnIndex("FirstName");
		int lNameColIndex = c.getColumnIndex("LastName");
		int ageColIndex = c.getColumnIndex("age");

		do {
		    // вывод значений в log
		    Log.d(LOG_TAG, "ID = " + c.getInt(idColIndex) + ";"
			    + " FirstName = " + c.getString(fNameColIndex)
			    + ";" + " LastName = " + c.getString(lNameColIndex)
			    + ";" + " Age = " + c.getInt(ageColIndex));
		} while (c.moveToNext());
	    } else {
		Log.d(LOG_TAG, "0 rows");

	    }
	    c.close();
	    break;
	case R.id.btnInsert:
	    if (db == null) {
		break;
	    }
	    c = db.query(table, null, null, null, null, null, null);
	    if (c.getCount() == 0) {
		break;
	    }
	    Log.d(LOG_TAG, "--- Вставка в таблицу ---");
	    cv.put("id", id);
	    cv.put("FirstName", fName);
	    cv.put("LastName", lName);
	    cv.put("Age", age);
	    long row = db.insert(table, null, cv);
	    Log.d(LOG_TAG, "Inserted row = " + row);
	    break;
	case R.id.btnUpdate:
	    if (id.equalsIgnoreCase("")) {
		break;
	    }
	    Log.d(LOG_TAG, "--- Изменение в таблице ---");
	    cv.put("ID", id);
	    cv.put("FirstName", fName);
	    cv.put("LastName", lName);
	    cv.put("Age", age);
	    int updCount = db.update(table, cv, "id = ?", new String[] {id});
	    Log.d(LOG_TAG, "updated rows count = " + updCount);
	    break;
	case R.id.btnDelete:
	    Log.d(LOG_TAG, "--- Удаление в таблице ---");
	    
	    int delCount = db.delete(table, "id = " + id, null );
	    Log.d(LOG_TAG, "deleted rows count = " + delCount);
	    break;

	default:
	    break;
	}
    }
}

/*
 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
 * menu; this adds items to the action bar if it is present.
 * getMenuInflater().inflate(R.menu.main, menu); return true; }
 */