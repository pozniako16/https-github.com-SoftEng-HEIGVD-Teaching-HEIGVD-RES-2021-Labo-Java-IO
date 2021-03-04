package ch.heigvd.res.labio.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class provides a method to invoke a web service and to receive a quote.
 * The implementation uses the jersey framework, but you do not need to worry
 * about that. You do not need to modify this file.
 *
 * You will use this class to fetch quotes, which you will then save on the
 * file system.
 *
 * @author Olivier Liechti
 * @author Daniel Palumbo
 */
public class QuoteClient {

  /*
   * This has changed in the 2016 version of the lab. We were using the "itheardquotes" API, which is now down. We have
   * replaced it with another API that generates random jokes.
   */
  static String WEB_SERVICE_ENDPOINT = "http://api.icndb.com/jokes/random?firstName=Olivier&lastName=Liechti&escape=javascript";

  /**
   * Use this method to invoke the iheartquotes.com web service and receive
   * an instance of a Quote.
   *
   * @return an instance of Quote, with values provided by the web service
   */
  public Quote fetchQuote() throws URISyntaxException, IOException, InterruptedException {
    // Use JAVA 11 http client and request feature
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(WEB_SERVICE_ENDPOINT))
            .header("Accept", "application/json")
            .build();

    HttpClient client = HttpClient.newBuilder().build();

    // Make the call to the API and map the response body to a Quote object
    return new ObjectMapper().readValue(client.send(request, HttpResponse.BodyHandlers.ofString()).body(), Quote.class);
  }

}
