package com.yapily.marvelCharacters.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarvelService {

  private final String apiKey = "";
  private final String apiPrivateKey = "";
  private static final Integer requestLimit = 100;

  private static final ObjectMapper om = new ObjectMapper();

  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  private final String charactersUrl = "https://gateway.marvel.com:443/v1/public/characters";
  private final String characterDescUrl = "https://gateway.marvel.com:443/v1/public/characters";

  @EventListener(ApplicationReadyEvent.class)
  public void getAllMarvelCharacters() throws IOException, InterruptedException {
    HttpRequest request = createHttpRequest(charactersUrl + "?limit=" + requestLimit);
    List<Integer> characterList = addCharacterIdsToList(request);
    writeToFile(characterList);
  }

  private HttpRequest createHttpRequest(String url) {
    Date date = new Date();
    String timeStamp = String.valueOf(date.getTime());

    String newString = url + "&ts=" + timeStamp + "&apikey=" + apiKey +
            "&hash=" + hash(apiKey, apiPrivateKey, timeStamp);
    return HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(newString))
            .setHeader("Content-Type", "application/json")
            .build();
  }

  private List<Integer> addCharacterIdsToList(HttpRequest request) throws IOException, InterruptedException {
    List<Integer> characterList = new ArrayList<>();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    CharactersResponseBody actual = om.readValue(response.body(), CharactersResponseBody.class);

    if (actual != null && actual.getData() != null && actual.getData().getTotal() != null) {
      int count = actual.getData().getTotal() / requestLimit;
      int i = 0;
      while (i <= count) {
        actual.getData().getResults().forEach(t -> characterList.add(t.getId()));
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        actual = om.readValue(response.body(), CharactersResponseBody.class);
        i++;
      }
    }
    return characterList;

  }

  public static String hash(String publicApiKey, String privateApiKey, String timestamp) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      String toHash = timestamp + privateApiKey + publicApiKey;
      return new BigInteger(1, md.digest(toHash.getBytes())).toString(16);
    } catch (NoSuchAlgorithmException e) {
    }
    return null;
  }

  private void writeToFile(List<Integer> characterList) throws IOException {
    Writer fileWriter = new FileWriter("characterIds.txt", false);

    characterList.stream().distinct().forEach(c -> {
      try {
        fileWriter.write(c.toString() + ",");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    fileWriter.close();
  }

  public CharacterDescriptionResponseBody getCharacterDescription(String characterId)
          throws IOException, InterruptedException {
    HttpRequest request = createHttpRequest(characterDescUrl + "/" + characterId);

    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    return om.readValue(response.body(), CharacterDescriptionResponseBody.class);
  }
}
