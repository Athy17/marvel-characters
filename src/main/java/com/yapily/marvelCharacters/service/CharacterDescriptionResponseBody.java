package com.yapily.marvelCharacters.service;

import java.util.List;

/**
 * Class to map Character description
 */
public class CharacterDescriptionResponseBody {

  public CharacterDescriptionResponse data;

  public CharacterDescriptionResponse getData() {
    return data;
  }

  public void setData(CharacterDescriptionResponse data) {
    this.data = data;
  }

  public static class CharacterDescriptionResponse {

    public List<CharacterDescriptionResult> results;

    public List<CharacterDescriptionResult> getResults() {
      return results;
    }

    public void setResults(List<CharacterDescriptionResult> results) {
      this.results = results;
    }
  }

  public static class CharacterDescriptionResult {

    private String id;
    private String name;
    private String description;
    private Thumbnail thumbnail;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Thumbnail getThumbnail() {
      return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
    }
  }

  static class Thumbnail {

    private String path;
    private String extension;

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public String getExtension() {
      return extension;
    }

    public void setExtension(String extension) {
      this.extension = extension;
    }
  }
}
