package firebase.app.fromscratch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText ciudad;
    TextView tiempo;
    Button consulta;
    String url_ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ciudad = findViewById(R.id.CiudadeditText);
        tiempo = findViewById(R.id.tiempoTextView);
        consulta = findViewById(R.id.tiempoButton);

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url_ciudad = ciudad.getText().toString();

                MiHilo hilo = new MiHilo();
                hilo.execute("http://api.openweathermap.org/data/2.5/weather?q="+url_ciudad+"&appid=028fbc7ae2775ff24d237daabc1c1e69");
            }
        });







    }


    public class MiHilo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection;
            URL url;
            connection = null;
            String result;
            result ="";

            try{

                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                int data = inputStream.read();

                while(data != -1){
                    result += (char) data;
                    data = inputStream.read();
                }

            }catch (Exception e){

                e.printStackTrace();

            }

            Log.i("RESULT", result);

            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("weather");

                Log.i("WEATHER", jsonArray.toString());

                for(int i=0; i<jsonArray.length(); i++){

                    JSONObject jsonitem = jsonArray.getJSONObject(i);
                    tiempo.setText(jsonitem.getString("description"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
