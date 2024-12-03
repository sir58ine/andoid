package isetb.ex.coach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ClientDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "client_db";
    public static final String TClient = "client";
    public static final String CID = "id";
    public static final String CNAME = "name";
    public static final String CLASTNAME = "last_name";
    public static final String CAGE = "age";
    public static final String CCIN = "cin";
    public static final String CCOUNTRY = "country";
    public static final String CPHOTO = "photo";

    public static final String CREATE_CLIENT_TABLE = "CREATE TABLE " + TClient + " ("
            + CID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CNAME + " VARCHAR(20) NOT NULL, "
            + CLASTNAME + " VARCHAR(20) NOT NULL, "
            + CAGE + " INTEGER NOT NULL, "
            + CCIN + " VARCHAR(8) NOT NULL UNIQUE, "
            + CCOUNTRY + " VARCHAR(30) NOT NULL, "
            + CPHOTO + " TEXT"
            + ");";

    private SQLiteDatabase db;

    public ClientDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CLIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TClient);
        onCreate(sqLiteDatabase);
    }

    // Ajouter un client
    public boolean addClient(Client c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CNAME, c.getName());
        values.put(CLASTNAME, c.getLastName());
        values.put(CAGE, c.getAge());
        values.put(CCIN, c.getCin());
        values.put(CCOUNTRY, c.getCountry());

        long result = db.insert(TClient, null, values);
        db.close();
        return result != -1;
    }

    // Modifier un client
    public void updateClient(Client c, int id) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CNAME, c.getName());
        values.put(CLASTNAME, c.getLastName());
        values.put(CAGE, c.getAge());
        values.put(CCIN, c.getCin());
        values.put(CCOUNTRY, c.getCountry());

        db.update(TClient, values, CID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Supprimer un client
    public boolean removeClient(int id) {
        db = this.getWritableDatabase();
        int rowsAffected = db.delete(TClient, CID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0; // Return true if a row was deleted
    }

    // Nombre de clients
    public int getClientCount() {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TClient, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    // Récupérer tous les clients
    public ArrayList<Client> getAllClients() {
        db = this.getReadableDatabase();
        ArrayList<Client> list = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(TClient, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(CID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(CNAME));
                    String lastName = cursor.getString(cursor.getColumnIndexOrThrow(CLASTNAME));
                    int age = cursor.getInt(cursor.getColumnIndexOrThrow(CAGE));
                    String cin = cursor.getString(cursor.getColumnIndexOrThrow(CCIN));
                    String country = cursor.getString(cursor.getColumnIndexOrThrow(CCOUNTRY));

                    Client client = new Client(lastName, name, id, cin, country, age);
                    list.add(client);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return list;
    }
}
