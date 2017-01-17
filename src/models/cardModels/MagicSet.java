package models.cardModels;

import java.util.List;

import util.JSONCard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Standard MTG Set. Contains common things like name, code, gatherer code and release data
 * 
 * @author Mitchell
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MagicSet {
  private String name;
  private String code;
  private String gathererCode;
  private String border;
  private String releaseDate;
  private String magicCardsInfoCode;
  private List<String> booster;
  private List<JSONCard> cards;
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @return the gathererCode
   */
  public String getGathererCode() {
    return gathererCode;
  }

  /**
   * @return the border
   */
  public String getBorder() {
    return border;
  }

  /**
   * @return the releaseDate
   */
  public String getReleaseDate() {
    return releaseDate;
  }

  /**
   * @return the magicCardsInfoCode
   */
  public String getMagicCardsInfoCode() {
    return magicCardsInfoCode;
  }

  /**
   * @return the booster
   */
  public List<String> getBooster() {
    return booster;
  }

  /**
   * @param booster the booster to set
   */
  public void setBooster(List<String> booster) {
    this.booster = booster;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @param gathererCode the gathererCode to set
   */
  public void setGathererCode(String gathererCode) {
    this.gathererCode = gathererCode;
  }

  /**
   * @param border the border to set
   */
  public void setBorder(String border) {
    this.border = border;
  }

  /**
   * @param releaseDate the releaseDate to set
   */
  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  /**
   * @param magicCardsInfoCode the magicCardsInfoCode to set
   */
  public void setMagicCardsInfoCode(String magicCardsInfoCode) {
    this.magicCardsInfoCode = magicCardsInfoCode;
  }

  /**
   * @return the cards
   */
  public List<JSONCard> getCards() {
    return cards;
  }

  /**
   * @param cards the cards to set
   */
  public void setCards(List<JSONCard> cards) {
    this.cards = cards;
  }

  
}
