package playing.i.am.when.waip;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import playing.i.am.when.waip.HTTPClient.HTTPClient;


public class Home extends ActionBarActivity {

    /**
     * Text view to hold the results of the http get
     *
     * @type TextView
     */
    TextView allResults = null;

    /**
     * AsyncTask Class to call the http request
     *
     * @type AsyncTask
     */
    AsyncTask<Void, Void, String> GetAllResults = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialise text view
        allResults = (TextView) findViewById(R.id.allResults);

        // Initialise and execute GetAllResults
        GetAllResults = new GetAllResults();
        GetAllResults.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Makes the http connection GET to get all fixtures for today
     *
     * @return responseStr All results from http get
     */
    protected String getAllResults(){

        // Initialise url
        String url = "http://api.football-data.org/v1/fixtures";

        // Initialise httpClient
        HTTPClient httpClient = new HTTPClient();

        // Initialise Json response object
        JSONObject response;

        // Connect to url
        httpClient.connect(url, "GET", 10000, 20000);

        // Read data
        response = httpClient.read();

        // Close connection
        httpClient.disconnect();

        // Response to string
        String responseString = response.toString();

        // Return response as string
        return responseString;


    }

    /**
     * Class run asynchronously to get all fixtures of today
     */
    private class GetAllResults extends AsyncTask<Void, Void, String> {

        /**
         * Progress dialog for loading screen
         *
         * @type ProgressDialog
         */
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected String doInBackground(Void... params) {
            Log.i("WebsServiceTask", "Task invoked");

            // Get the response from all results
            String responseStr = getAllResults();

            // Return response as string
            return responseStr;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Log.i("WebsServiceTask", "post execute invoked");

            // Set the response string to view text field
            allResults.setText(s);
        }
    }
}


