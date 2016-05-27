package pbru.sawangjit.tanatip.wherepbru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class  MainActivity extends AppCompatActivity {

    private Mymanage mymanage;
    private String urlJSON = "http://swiftcodingthai.com/pbru/get_room.php";
    private EditText editText;
    private String roomString;
    private String[] resultStrings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        mymanage = new Mymanage(this);

        // mymanage.addRoom("build", "room", "lat", "lng", "icon");


        deleteAlSQLitel();

        SynRoom synRoom = new SynRoom();
        synRoom.execute();

    }   //main method

    public void clickSearch(View view) {

        roomString = editText.getText().toString().trim();

        if (roomString.equals("")) {
            Toast.makeText(this, "กรุณากรอกเลขห้อง ก่อน", Toast.LENGTH_SHORT).show();
        } else {
            searchRoom();
        }

    }   //clicksrarch

    private void searchRoom() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM roomTABLE WHERE Room = " + "'" + roomString + "'", null);
            cursor.moveToFirst();

            resultStrings = new String[cursor.getColumnCount()];
            for (int i=0; i<cursor.getColumnCount();i++) {
                resultStrings[i] = cursor.getString(i);
            }   //for

            myAlert();


        } catch (Exception e) {
            Toast.makeText(this, "ไม่มี" + roomString + "ในฐานข้อมูล ของเรา", Toast.LENGTH_SHORT).show();
        }
    }   //searchRoom

    private void myAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.build1);
        builder.setCancelable(false);
        builder.setTitle("ห้อง" + roomString);
        builder.setMessage("อยู่ท ี่อาคาร" + resultStrings[1]);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });
        builder.show();


    }   //Myalert

    public void clickListBuild(View view) {
    }

    public void clickAdmin(View view) {
    }


    public class SynRoom extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                return null;
            }

        }   //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("whereV1", "JSON ==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    String strBuild = jsonObject.getString(Mymanage.column_Build);
                    String strRoom = jsonObject.getString(Mymanage.column_Room);
                    String strLat = jsonObject.getString(Mymanage.column_Lat);
                    String strLng = jsonObject.getString(Mymanage.column_Lng);
                    String strIcon = jsonObject.getString(Mymanage.column_Icon);

                    mymanage.addRoom(strBuild, strRoom, strLat, strLng, strIcon);


                }//for


            } catch (Exception e) {
                e.printStackTrace();
            }

        } //onPost


    }   //SynRoom class


    private void deleteAlSQLitel() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(Mymanage.room_table, null, null);

    }

}   // main class
