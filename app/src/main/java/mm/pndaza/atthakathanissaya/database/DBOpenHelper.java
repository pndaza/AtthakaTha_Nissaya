package mm.pndaza.atthakathanissaya.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import mm.pndaza.atthakathanissaya.model.AtthaBook;
import mm.pndaza.atthakathanissaya.model.Nsy;

import static android.content.ContentValues.TAG;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper sInstance;
    private static final String DATABASE_NAME = "attha_nsy.db";
    private static final int DATABASE_VERSION = 1;


    public static synchronized DBOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (sInstance == null) {
            sInstance = new DBOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBOpenHelper(Context context) {
        super(context, context.getFilesDir() + "/databases/" + DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<AtthaBook> getBooks(int category) {
        ArrayList<AtthaBook> bookList = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT id, name, first_page, last_page FROM atthabook where category = " + category, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                bookList.add(new AtthaBook(
                        cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("first_page")),
                        cursor.getInt(cursor.getColumnIndex("last_page"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    public ArrayList<Nsy> getNsyBooks(String bookid, int pageNumber) {

        ArrayList<Nsy> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT nsyid, nsypagenumber, name FROM pagematch INNER JOIN nsybook " +
                        "ON pagematch.nsyid = nsybook.id WHERE bookid = ? AND bookpagenumber = ? AND nsypagenumber != 0",
                new String[]{String.valueOf(bookid), String.valueOf(pageNumber)});

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                String nsyid = cursor.getString(cursor.getColumnIndex("nsyid"));
                int nsyPageNumber = cursor.getInt(cursor.getColumnIndex("nsypagenumber"));
                String nsyName = cursor.getString(cursor.getColumnIndex("name"));
                list.add(new Nsy(nsyid, nsyName, nsyPageNumber));
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "getNsyBooks: cursor count " + cursor.getCount());
        cursor.close();
        return list;
    }


    public boolean isNsyExist(String bookid, int pageNumber) {

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT nsyid FROM pagematch WHERE bookid = ? and bookpagenumber = ?",
                new String[]{String.valueOf(bookid), String.valueOf(pageNumber)});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }
}
