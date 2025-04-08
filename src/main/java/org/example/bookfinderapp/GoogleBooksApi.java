package org.example.bookfinderapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URLEncoder;


//This class handles the API call, builds the URL, fetches data, and parses the JSON response into  objects
public class GoogleBooksApi {

    private static final String API_KEY = "AIzaSyDxz7ndq3tOitDaZdCBIzS5FIvjf2m5Spc";

    public List<Book> getBooks(String title, String authors) {
        String apiURL = buildApiUrl(title, authors);
        String jsonResponse = fetchBooksFromApi(apiURL);
        return jsonResponse != null ? parseJsonResponse(jsonResponse) : new ArrayList<>();
    }

    private String buildApiUrl(String title, String authors) {
        try {
            String query = "";
            if (!title.isEmpty()) query += "intitle:" + URLEncoder.encode(title, "UTF-8");
            if (!authors.isEmpty()) query += "+inauthor:" + URLEncoder.encode(authors, "UTF-8");
            String apiUrl =  "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + API_KEY;

            // Debugging the generated API URL
            System.out.println("API URL: " + apiUrl);

            return apiUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private String fetchBooksFromApi(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                System.err.println("HTTP error code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private List<Book> parseJsonResponse(String jsonResponse) {
        List<Book> books = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        if (items != null) {
            for (JsonElement element : items) {
                JsonObject volumeInfo = element.getAsJsonObject().getAsJsonObject("volumeInfo");

                String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown";
                String author = volumeInfo.has("authors") ? volumeInfo.get("authors").getAsJsonArray().get(0).getAsString() : "Unknown";
                String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown";
                String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";
                String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
                String coverImageUrl = volumeInfo.has("imageLinks") ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "";

                books.add(new Book(title, author, publisher, publishedDate, description, coverImageUrl));
            }
        }
        return books;


    }


}//end of class