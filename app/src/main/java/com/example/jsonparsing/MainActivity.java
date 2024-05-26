package com.example.jsonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private ProgressDialog pDialog;

    private static String url = "https://gist.githubusercontent.com/Baalmart/8414268/raw/43b0e25711472de37319d870cb4f4b35b1ec9d26/contacts";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void...arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            /*
            String jsonStr = "{ \"contacts\" :[" +
                    "{\"id\":\"c200\",\"name\":\"Ravi Tamada\",\"email\":\"ravi@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c201\",\"name\":\"Johnny Depp\",\"email\":\"johnny_depp@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c202\",\"name\":\"Leonardo Dicaprio\",\"email\":\"leonardo_dicaprio@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c203\",\"name\":\"John Wayne\",\"email\":\"john_wayne@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c204\",\"name\":\"Angelina Jolie\",\"email\":\"angelina_jolie@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"female\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c205\",\"name\":\"Dido\",\"email\":\"dido@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"female\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c206\",\"name\":\"Adele\",\"email\":\"adele@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"female\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c207\",\"name\":\"Hugh Jackman\",\"email\":\"hugh_jackman@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c208\",\"name\":\"Will Smith\",\"email\":\"will_smith@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c209\",\"name\":\"Clint Eastwood\",\"email\":\"clint_eastwood@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c2010\",\"name\":\"Barack Obama\",\"email\":\"barack_obama@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c2011\",\"name\":\"Kate Winslet\",\"email\":\"kate_winslet@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"female\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    ",{\"id\":\"c2012\",\"name\":\"Eminem\",\"email\":\"eminem@gmail.com\",\"address\":\"xx-xx-xxxx,x - street, x - country\",\"gender\":\"male\",\"phone\":{\"mobile\":\"+91 0000000000\",\"home\":\"00 000000\",\"office\":\"00 000000\"}}" +
                    "] }"; */

            //Toast.makeText(getApplicationContext(), "Response from url: " + jsonStr, Toast.LENGTH_LONG).show();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                    R.layout.json_list, new String[]{"name", "email", "mobile"},
                    new int[]{R.id.name, R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}