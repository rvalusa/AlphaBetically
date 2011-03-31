package com.alpha;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper{
	private static String TAG = DataBaseHelper.class.getName();
	private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
    private static String DB_NAME = "Data.sqlite";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
    
    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
    	DB_PATH = "/data/data/" + context.getPackageName() +"/databases/";
        this.myContext = context;
        Log.v(TAG, "DBPath: " + DB_PATH);
    }	
 
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		Log.v(TAG, "database does exist");
    	}else{
    		Log.v(TAG, "database does not exist");
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    	}catch(SQLiteException e){
            Log.v(TAG, e.toString() + "   database doesn't exists yet..");
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null;
    }
 
    private void copyDataBase() throws IOException{
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
    	String outFileName = DB_PATH + DB_NAME;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public boolean openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    	return myDataBase != null;
    }
 
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(TAG, "Upgrading database, this will drop database and recreate.");
	}
	
	
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}