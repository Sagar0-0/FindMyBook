package com.example.android.findmybook;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


     public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }

    public static List<Book> fetchBookData(String s) {

        URL url = createUrl(s);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeaturesFromJson(jsonResponse);
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);

        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> extractFeaturesFromJson(String jsonResponse) {
        if(jsonResponse==null)return null;

        List<Book> books = new ArrayList<>();
        try {
            JSONObject root=new JSONObject(jsonResponse);
            JSONArray jsonArray=root.optJSONArray("items");

            if(jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject oneobject = jsonArray.getJSONObject(i);

                    JSONObject volumeinfo = oneobject.getJSONObject("volumeInfo");

                    String bookname = volumeinfo.getString("title");

                    JSONArray authorarray = volumeinfo.optJSONArray("authors");
                    String authorName = "Unknown Author";
                    if (authorarray != null) {
                        authorName = authorarray.getString(0);
                    }


                    JSONObject imglinks = volumeinfo.getJSONObject("imageLinks");
                    String img = imglinks.getString("thumbnail");
                    String originalImg = httpremoval(img);

                    String lang = volumeinfo.getString("language");

                    String infoLink = volumeinfo.getString("infoLink");
                    String bookurl = httpremoval(infoLink);

                    books.add(new Book(bookname, authorName, lang, originalImg, bookurl));
                }
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Book JSON results", e);
        }

        return books;
    }
    public static String httpremoval(String httpurl){
        String[] httpsplit = httpurl.split("//");
        return "https://"+httpsplit[1];
    }


}
