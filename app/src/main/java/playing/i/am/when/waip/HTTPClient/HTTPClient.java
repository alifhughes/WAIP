package playing.i.am.when.waip.HTTPClient;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handles the http request for the user
 */
public class HTTPClient {

    /**
     * Connection for the Http request
     *
     * @type HttpURLConnection
     */
    HttpURLConnection con = null;

    /**
     * Reader for the connection
     *
     * @type BufferedReader
     */
    BufferedReader in = null;

    /**
     * Connect to the URL passed in
     *
     * @param urlStr            URL string to connect to
     * @param requestMethod     Request method to connect to
     * @param readTimeout       Time out for reading the connection
     * @param connectionTimeout Connection time out for the connection
     * @return HTTPClient       Implement fluent interface
     */
    public HTTPClient connect (
        String urlStr,
        String requestMethod,
        int readTimeout,
        int connectionTimeout
    ) {

        try {
            // Create URL from url string
            URL url = new URL(urlStr);

            //creating an HTTP connection
            con = (HttpURLConnection) url.openConnection();

            // Set read timeout
            con.setReadTimeout(readTimeout);

            // Connection time out
            con.setConnectTimeout(connectionTimeout);

            // Default
            con.setChunkedStreamingMode(0);

            // Set request method
            con.setRequestMethod(requestMethod);

            // Set to true so that can read response back
            con.setDoInput(true);

            // Connect
            con.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Implement fluent interface
        return this;

    }

    /**
     * Open a buffered stream and read in lines from the connection
     *
     * @return JSONObject
     */
    public JSONObject read(){

        // Initialise response string
        String responseStr = "";

        // Initialise JSON response object
        JSONObject result = null;

        try {

            // Open a buffered stream
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));

            // Read from connection
            responseStr = in.readLine();

            // Parse response string to json object
            result = new JSONObject(responseStr);

            // Return the result
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Close the BufferedStream and Connection
     *
     * @return void
     */
    public void disconnect() {

        try {
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


/**
 * implement setters for this
    con.setChunkedStreamingMode(0); //default
    con.setRequestMethod("GET");    //use HTTP GET
    con.setDoInput(true);           //so that we can read response back
    con.connect();

 */
}