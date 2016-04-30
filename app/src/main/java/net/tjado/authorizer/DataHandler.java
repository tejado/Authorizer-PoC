package net.tjado.authorizer;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tm on 21.04.16.
 */
public class DataHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "authorizerDB";

    // Credentials table name
    private static final String TABLE_CREDS = "credentials";


    // Shops Table Columns names
    // ToDo: removed, check for re-implementation


    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_CREDS + "(" +
                "id INTEGER PRIMARY KEY, username TEXT," +
                "password TEXT)";
        db.execSQL(CREATE_CREDENTIALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new credential
    // ToDo: implementation
    public void addCredential(String username, String Password) {
        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName()); // Shop Name
        values.put(KEY_SH_ADDR, shop.getAddress()); // Shop Phone Number
        // Inserting Row
        db.insert(TABLE_SHOPS, null, values);
        db.close(); // Closing database connection*/
    }

    // ToDo: implementation
/*
    // Getting one credential
    public Shop getShop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CREDS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SH_ADDR }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Shop contact = new Shop(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        // return shop
        return contact;
    }


    // Getting All Shops
    public List<Shop> getAllShops() {
        List<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop();
                shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setName(cursor.getString(1));
                shop.setAddress(cursor.getString(2));
                // Adding contact to list
                shopList.add(shop);
            } while (cursor.moveToNext());
        }
        // return contact list
        return shopList;
    }*/

}