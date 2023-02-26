package org.example;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class  getHttpResponse {
public static String getHttpResponse(String urlString) throws IOException {
    URL url = new URL(urlString);
    try (Scanner scanner = new Scanner(url.openStream(), "UTF-8")) {
        return scanner.useDelimiter("\\A").next();
    }
}
}

