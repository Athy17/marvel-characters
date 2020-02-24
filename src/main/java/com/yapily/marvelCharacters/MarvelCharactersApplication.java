package com.yapily.marvelCharacters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com"})
public class MarvelCharactersApplication {


  public static void main(String[] args) {
    SpringApplication.run(MarvelCharactersApplication.class, args);
  }

}
