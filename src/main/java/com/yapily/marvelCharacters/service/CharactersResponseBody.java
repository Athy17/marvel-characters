package com.yapily.marvelCharacters.service;

import java.util.List;

/**
 * class to map characters details
 */
public class CharactersResponseBody {

  public CharactersResponseBodyData data;

  public CharactersResponseBodyData getData() {
    return data;
  }

  public void setData(CharactersResponseBodyData data) {
    this.data = data;
  }

  static class CharactersResponseBodyData {

    private Integer total;

    private Integer count;

    public List<CharactersResponseBodyDataResults> results;

    public List<CharactersResponseBodyDataResults> getResults() {
      return results;
    }

    public void setResults(List<CharactersResponseBodyDataResults> results) {
      this.results = results;
    }

    public Integer getTotal() {
      return total;
    }

    public void setTotal(Integer total) {
      this.total = total;
    }

    public Integer getCount() {
      return count;
    }

    public void setCount(Integer count) {
      this.count = count;
    }
  }

  static class CharactersResponseBodyDataResults {

    public Integer id;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }
}


