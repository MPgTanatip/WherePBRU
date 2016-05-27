package pbru.sawangjit.tanatip.wherepbru;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class  MainActivity extends AppCompatActivity {

    private Mymanage mymanage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mymanage = new Mymanage(this);

        // mymanage.addRoom("build", "room", "lat", "lng", "icon");

        deleteAlSQLitel();

    }   //main method

    private void deleteAlSQLitel() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(Mymanage.room_table, null, null);



    }

}   // main class
