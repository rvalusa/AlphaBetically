package com.alpha;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DataDBAdapter {
	protected static final String TAG = DataDBAdapter.class.getName();

	private final Context mCtx;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;
	
	private static String PROGRAMS_TABLE = "data";
	private static String CARDS_TABLE = "cards";
	private static String MISC_TABLE = "misc";
	private static String PREFERENCES_TABLE = "preferences";

	public static String PROGRAMS_ID = "id";
	public static String PROGRAMS_NAME = "name";
	public static String PROGRAMS_LOGO = "logo";
	public static String PROGRAMS_CARD = "card";
	public static String PROGRAMS_LANGUAGE = "language";
	public static String PROGRAMS_ENTERED_DIGITS = "enteredDigits";
	public static String PROGRAMS_TOTAL_DIGITS = "totalDigits";
	public static String PROGRAMS_ENTERED_DIGITS2 = "enteredDigits2";
	public static String PROGRAMS_TOTAL_DIGITS2 = "totalDigits2";
	public static String PROGRAMS_LEFT_ADDITION = "leftAddition";
	public static String PROGRAMS_MAX_DIGITS = "maxDigits";
	public static String PROGRAMS_MAP_SEARCH = "mapsearch";

	private static String CARDS_ID = "id";
	private static String CARDS_CODE = "code";
	private static String CARDS_PROGRAM_ID = "program_id";
	private static String CARDS_ORDER_NUMBER = "orderNumber";
	private static String CARDS_USAGE_NUMBER = "usageNumber";
	private static String CARDS_CNAME = "cname";
	private static String CARDS_CSYMBOLOGY = "csymbology";

	private static String MISC_ID = "id";
	private static String MISC_NAME = "name";
	private static String MISC_VALUE = "value";
	private static String MISC_LAST_CARD = "lastCard";
	private static String MISC_LAST_CARD_SECTION = "lastCardSection";

	private static String PREFERENCES_ID = "id";
	private static String PREFERENCES_LAST_SORT_TAB_INDEX = "lastSortTabIndex";

	public DataDBAdapter(Context ctx) {
		this.mCtx = ctx;
		mDbHelper = new DataBaseHelper(mCtx);
	}

	public DataDBAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
			Log.v(TAG, "database created");
		} catch (IOException ioe) {
			Log.v(TAG, ioe.toString() + "  Unable to create database.");
			throw new Error("Unable to create database");
		}
		return this;
	}

	public DataDBAdapter open() throws SQLException {
		// Create and open Database
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException sqle) {
			Log.v(TAG, sqle.toString());
			throw sqle;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public int countPrograms() {
		Cursor c = mDb.query(PROGRAMS_TABLE, new String[] {}, null, null, null,
				null, null);
		int count = c.getCount();
		c.close();
		return count;
	}

	public int countCards() {
		Cursor c = mDb.query(CARDS_TABLE, new String[] {}, null, null, null,
				null, null);
		int count = c.getCount();
		c.close();
		return count;
	}

	public Cursor programsList(String key) {
		Cursor c = mDb.query(PROGRAMS_TABLE, new String[] { PROGRAMS_ID,
				PROGRAMS_LOGO, PROGRAMS_NAME }, PROGRAMS_NAME + " LIKE '" + key
				+ "%'", null, null, null, PROGRAMS_NAME);
		c.close();
		return c;
	}

	public ArrayList<Listing> completeProgramsList(Bitmap emptyLogo) {
		Cursor programsListCursor = mDb.query(PROGRAMS_TABLE, new String[] {
				PROGRAMS_ID, PROGRAMS_NAME }, null, null, null,
				null, PROGRAMS_NAME);
		ArrayList<Listing> programsList = new ArrayList<Listing>();
		for (programsListCursor.isBeforeFirst(); programsListCursor
				.moveToNext(); programsListCursor.isAfterLast()) {
			Listing tempProgram = new Listing();
			tempProgram.setId(programsListCursor.getInt(programsListCursor
					.getColumnIndex(DataDBAdapter.PROGRAMS_ID)));
			tempProgram.setName(programsListCursor.getString(programsListCursor
					.getColumnIndex(DataDBAdapter.PROGRAMS_NAME)));
			//if (programsListCursor.getBlob(programsListCursor
			//		.getColumnIndex(CardBankDBAdapter.PROGRAMS_LOGO)) != null) {
			//	tempProgram
			//			.setLogoImage(blobToImage(programsListCursor
			//					.getBlob(programsListCursor
			//							.getColumnIndex(CardBankDBAdapter.PROGRAMS_LOGO))));
			//} else {
			//	tempProgram.setLogoImage(emptyLogo);
			//}
			programsList.add(tempProgram);
		}

		programsListCursor.close();
		return programsList;
	}
	
	public ArrayList<Data> completeCardsList(Bitmap emptyLogo) {
		String query = "select " + "p."+ PROGRAMS_NAME + ", p." + PROGRAMS_LOGO + ", c." + CARDS_ID + ", c." + CARDS_CODE + " from " + PROGRAMS_TABLE +" p, " + CARDS_TABLE + " c where " + "c." + CARDS_PROGRAM_ID + " = " + "p." + PROGRAMS_ID + " order by " + PROGRAMS_NAME; 
		Log.v(TAG,"Complete Card List Query: " +  query);
		Cursor cardsListCursor = mDb.rawQuery(query, null);
		Log.v(TAG, "Cards Count: " + cardsListCursor.getCount());
		ArrayList<Data> cardsList = new ArrayList<Data>();
		for (cardsListCursor.isBeforeFirst(); cardsListCursor
				.moveToNext(); cardsListCursor.isAfterLast()) {
			Data tempCards = new Data();
			tempCards.setId(cardsListCursor.getInt(cardsListCursor.getColumnIndex(DataDBAdapter.CARDS_ID)));
			tempCards.setProgramName(cardsListCursor.getString(cardsListCursor.getColumnIndex(DataDBAdapter.PROGRAMS_NAME)));
			tempCards.setCode(cardsListCursor.getString(cardsListCursor.getColumnIndex(DataDBAdapter.CARDS_CODE)));
			if (cardsListCursor.getBlob(cardsListCursor.getColumnIndex(DataDBAdapter.PROGRAMS_LOGO)) != null) {
				tempCards.setLogoImage(blobToImage(cardsListCursor.getBlob(cardsListCursor.getColumnIndex(DataDBAdapter.PROGRAMS_LOGO))));
			}
			else {
				tempCards.setLogoImage(emptyLogo);
			}
			tempCards.setIsCustom(false);
			cardsList.add(tempCards);
		}
		cardsListCursor.close();
		Cursor customCardsListCursor = mDb.query(CARDS_TABLE, new String[] {}, CARDS_PROGRAM_ID + "="
				+ "-10", null, null, null, null);
		Log.v(TAG, "Custom Cards Count: " + customCardsListCursor.getCount());
		for (customCardsListCursor.isBeforeFirst(); customCardsListCursor
				.moveToNext(); customCardsListCursor.isAfterLast()) {
			Data tempCards = new Data();
			tempCards.setId(customCardsListCursor.getInt(customCardsListCursor.getColumnIndex(DataDBAdapter.CARDS_ID)));
			tempCards.setCode(customCardsListCursor.getString(customCardsListCursor.getColumnIndex(DataDBAdapter.CARDS_CODE)));
			tempCards.setProgramName(customCardsListCursor.getString(customCardsListCursor.getColumnIndex(DataDBAdapter.CARDS_CNAME)));
			tempCards.setLogoImage(emptyLogo);
			tempCards.setIsCustom(true);
			cardsList.add(tempCards);
		}
		customCardsListCursor.close();
		
		return cardsList;
	}
	
	public Listing getProgramDetails(int id) {
		Listing program = new Listing();
		Cursor c = mDb.query(PROGRAMS_TABLE, new String[] {}, PROGRAMS_ID + "="
				+ id, null, null, null, null);
		c.moveToFirst();
		program.setId(c.getInt(c.getColumnIndex(DataDBAdapter.PROGRAMS_ID)));
		program.setName(c.getString(c.getColumnIndex(DataDBAdapter.PROGRAMS_NAME)));
		/*
		if (c.getBlob(c.getColumnIndex(CardBankDBAdapter.PROGRAMS_CARD)) != null) {
			program.setCardImage(blobToImage(c.getBlob(c.getColumnIndex(CardBankDBAdapter.PROGRAMS_CARD))));
		}
		if (c.getBlob(c.getColumnIndex(CardBankDBAdapter.PROGRAMS_LOGO)) != null) {
			program.setLogoImage(blobToImage(c.getBlob(c.getColumnIndex(CardBankDBAdapter.PROGRAMS_LOGO))));
		}
		program.setEnteredDigits(c.getInt(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_ENTERED_DIGITS)));
		program.setEnteredDigits2(c.getInt(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_ENTERED_DIGITS2)));
		program.setTotalDigits(c.getInt(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_TOTAL_DIGITS)));
		program.setTotalDigits2(c.getInt(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_TOTAL_DIGITS2)));
		program.setLeftAddition(c.getString(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_LEFT_ADDITION)));
		program.setMaxDigits(c.getInt(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_MAX_DIGITS)));
		program.setMapSearch(c.getString(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_MAP_SEARCH)));
		program.setLanguage(c.getString(c
				.getColumnIndex(CardBankDBAdapter.PROGRAMS_LANGUAGE)));
		c.close();*/
		return program;
	}
	
	public Data getCardDetails(int id) {
		Data card = new Data();
		Cursor c = mDb.query(CARDS_TABLE, new String[] {}, CARDS_ID + "="
				+ id, null, null, null, null);
		c.moveToFirst();
		card.setId(c.getInt(c.getColumnIndex(DataDBAdapter.CARDS_ID)));
		card.setProgramId(c.getInt(c.getColumnIndex(DataDBAdapter.CARDS_PROGRAM_ID)));
		card.setCode(c.getString(c.getColumnIndex(DataDBAdapter.CARDS_CODE)));
		card.setCname(c.getString(c.getColumnIndex(DataDBAdapter.CARDS_CNAME)));
		card.setCsymbology(c.getString(c.getColumnIndex(DataDBAdapter.CARDS_CSYMBOLOGY)));
		c.close();
		return card;
	}
	
	public long insertCard(int programID, String code)
	{
		ContentValues initialValues = new ContentValues();
        initialValues.put(CARDS_PROGRAM_ID, programID);
        initialValues.put(CARDS_CODE, code.toUpperCase());
        return mDb.insert(CARDS_TABLE, null, initialValues);
	}
	
	public long insertCustomCard(Data card)
	{
		ContentValues initialValues = new ContentValues();
        initialValues.put(CARDS_PROGRAM_ID, card.getProgramId());
        initialValues.put(CARDS_CODE, card.getCode().toUpperCase());
        initialValues.put(CARDS_CNAME, card.getCname());
        initialValues.put(CARDS_CSYMBOLOGY, card.getCsymbology());
        return mDb.insert(CARDS_TABLE, null, initialValues);
	}

	public Bitmap blobToImage(byte[] image) {
		byte[] b = image;
		Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		return bitmap;
	}
	
	public boolean deleteCard(long rowId) {
        return mDb.delete(CARDS_TABLE, CARDS_ID + "=" + rowId, null) > 0;
    }
	
	public boolean updateCard(long rowId, String code) {
        ContentValues args = new ContentValues();
        args.put(CARDS_CODE, code);
        return mDb.update(CARDS_TABLE, args, CARDS_ID + "=" + rowId, null) > 0;
    }
	
	public boolean updateCustomCard(long rowId, Data card) {
        ContentValues args = new ContentValues();
        args.put(CARDS_PROGRAM_ID, card.getProgramId());
        args.put(CARDS_CODE, card.getCode().toUpperCase());
        args.put(CARDS_CNAME, card.getCname());
        args.put(CARDS_CSYMBOLOGY, card.getCsymbology());
        return mDb.update(CARDS_TABLE, args, CARDS_ID + "=" + rowId, null) > 0;
    }


}
