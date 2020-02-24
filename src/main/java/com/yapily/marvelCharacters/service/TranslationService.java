package com.yapily.marvelCharacters.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;

public class TranslationService {

  // Translating Text
  public static String translateText(String targetLanguage, String text)
          throws IOException {
    Translate.TranslateOption options = Translate.TranslateOption.sourceLanguage("en").targetLanguage(targetLanguage);
    Translate translate = TranslateOptions.getDefaultInstance().getService();
    Translation translation = translate.translate(text, options);
    return translation.getTranslatedText();
  }
}
