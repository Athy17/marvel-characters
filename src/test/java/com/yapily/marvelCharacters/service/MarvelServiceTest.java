package com.yapily.marvelCharacters.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class MarvelServiceTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  public void testReturnsCharacterIds() throws IOException, InterruptedException {
    // Given
    MarvelService service = new MarvelService();
    // When
    CharacterDescriptionResponseBody characterDescription = service.getCharacterDescription("1009146");
    // Then
    assertThat(characterDescription, is(notNullValue()));
    assertThat(characterDescription.getData(), is(notNullValue()));
    assertThat(characterDescription.getData().getResults(), hasSize(1));
  }
}