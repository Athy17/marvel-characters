package com.yapily.marvelCharacters.controller;

import com.yapily.marvelCharacters.service.CharacterDescriptionResponseBody;
import com.yapily.marvelCharacters.service.MarvelService;
import com.yapily.marvelCharacters.service.TranslationService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
public class CharacterController {

  @Autowired
  private MarvelService service;

  /**
   * Returns a list of Marvel characters id
   *
   * @return
   */
  @ApiOperation(value = "View List of Marvel Character Ids", response = Arrays.class, httpMethod = "GET")
  @RequestMapping("/characters")
  public ResponseEntity get() {
    // Calls the marvel api using the key and then stores the ids in a file
    // and return all the ids when this is called.
    String content = null;
    try {
      content = Files.readString(Paths.get("characterIds.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<String> ids = new ArrayList<>(Arrays.asList(content.split(",")));
    return ResponseEntity.ok(ids);
  }

  /**
   * Returns real time data for the given character id
   *
   * @return
   */
  @ApiOperation(value = "Returns descriptions for a marvel character based on id. " +
          "If language param is provided translate the description into an ISO-639-1 language", httpMethod = "GET")
  @RequestMapping(path = "/characters/{characterId}")
  public ResponseEntity getCharacterDetails(@PathVariable("characterId") String characterId,
                                            @RequestParam(value = "language", required = false) String language) throws IOException,
          InterruptedException {
    String[] isoLanguages = Locale.getISOLanguages();
    List<String> languageLists = Arrays.asList(isoLanguages);
    CharacterDescriptionResponseBody characterDescription = service.getCharacterDescription(characterId);
    CharacterDescriptionResponseBody.CharacterDescriptionResult result = null;
    if (characterDescription != null && characterDescription.getData() != null && !characterDescription.getData().getResults().isEmpty()) {
      result = characterDescription.getData().getResults().get(0);
      if (StringUtils.isNotBlank(language) && languageLists.contains(language)) {
        String translated = TranslationService.translateText(language, result.getDescription());
        result.setDescription(translated);
      }
    }

    return ResponseEntity.ok(result);
  }
}
