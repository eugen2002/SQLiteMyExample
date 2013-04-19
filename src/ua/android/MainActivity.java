package ua.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    final String LOG_TAG = "my_Log";

    Button       btnRead, btnInsert, btnUpdate, btnDelete;
    EditText     etID, etFName, etLName, etAge;
    String       table, id, fName, lName, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	initUI();
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

	// Get display info
	id = etID.getText().toString();
	fName = etFName.getText().toString();
	lName = etLName.getText().toString();
	age = etAge.getText().toString();

	IDao dao = new Dao(this);

	switch (v.getId()) {
	    case R.id.btnRead:
		dao.initDB();
		String result = dao.readAll();
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		break;
	    case R.id.btnInsert:
		dao.initDB();
		String resInsert = dao.insert(id, fName, lName, age);
		Toast.makeText(this, "Inserted row: \n " + resInsert, Toast.LENGTH_LONG).show();
		break;
	    case R.id.btnUpdate:
		dao.initDB();
		String resUpdate = dao.update(id, fName, lName, age);
		Toast.makeText(this, "Updated row: \n " + resUpdate, Toast.LENGTH_LONG).show();
		break;
	    case R.id.btnDelete:
		dao.initDB();
		int resDelete = dao.deleteById(id);
		Toast.makeText(this, "Deleted row: \n " + resDelete, Toast.LENGTH_LONG).show();
		break;
	    default:
		Toast.makeText(this, "No selection", Toast.LENGTH_LONG).show();
		break;
	}
    }
}
