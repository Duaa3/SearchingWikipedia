package org.example;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.example.getHttpResponse.getHttpResponse;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a topic of interest: ");
        String topic = scanner.nextLine();
        scanner.close();

        try {
            String url = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=" + URLEncoder.encode(topic, "UTF-8");
            String response = getHttpResponse(url);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("query").getJSONArray("search");
            if (jsonArray.length() > 0) {
                String pageTitle = jsonArray.getJSONObject(0).getString("title");
                String pageUrl = "https://en.wikipedia.org/wiki/" + pageTitle.replace(" ", "_");
                System.out.println("Here is the Wikipedia article for: \"" + topic + "\":");
                System.out.println(pageUrl);
                System.out.println("Here are some related snippets:");
                for (int i = 0; i < 3 && i < jsonArray.length(); i++) {
                    String snippet = jsonArray.getJSONObject(i).getString("snippet");
                    snippet = snippet.replaceAll("<.*?>", ""); // Remove HTML tags
                    snippet = snippet.replaceAll("\\s+", " "); // Collapse whitespace
                    System.out.println("* " +  snippet);

                }
            } else {
                System.out.println("Sorry no Wikipedia article Was found for \"" + topic + "\".");
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}

