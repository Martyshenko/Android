package savern.booklistingapp;

import android.text.TextUtils;

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


public final class QueryUtils {


    private QueryUtils() {

    }

    public static List<Book> fetchBooksData(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        // Extract relevant fields from the JSON response and return a {@link List<Books>} List
        return extractBooks(jsonResponse);
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    private static List<Book> extractBooks(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();


        try {
            // Create a JSONObject from the JSON response string
            JSONObject jsonObj = new JSONObject(jsonResponse);
            // Extract the JSONArray associated with the key called "items",
            JSONArray items = jsonObj.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                // Get a single book at position i within the list of books
                JSONObject bookJSONObj = items.getJSONObject(i);
                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which includes required information
                // for that book.
                JSONObject volumeInfo = bookJSONObj.getJSONObject("volumeInfo");
                String title = "";
                if (volumeInfo.has("title")) {
                    title = volumeInfo.getString("title");
                }
                String authors = "";
                if (volumeInfo.has("authors")) {
                    JSONArray authorsAr = volumeInfo.getJSONArray("authors");
                    authors = authorsAr.toString().replace("[", "").replace("]", "");
                }
                String description = "";
                if (bookJSONObj.has("description")) {
                    description = bookJSONObj.getString("description");
                }
                Book book = new Book(authors, title, description);
                // Add the new {@link Book} to the list of books.
                books.add(book);
            }
            return books;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

}