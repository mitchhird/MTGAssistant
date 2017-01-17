package util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import models.cardModels.Card;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONCard extends Card{
  protected String artist;
  protected String cmc;
  protected String imageName;
  protected String manaCost;
  protected String rarity;
  
  @Override
  public String toString() {
    return "[JSONCard(" + name + ")]";
  }
  
  
  /**
   * @return the artist
   */
  public String getArtist() {
    return artist;
  }
  /**
   * @return the cmc
   */
  public String getCmc() {
    return cmc;
  }
  /**
   * @return the imageName
   */
  public String getImageName() {
    return imageName;
  }
  /**
   * @return the manaCost
   */
  public String getManaCost() {
    return manaCost;
  }
  /**
   * @return the rarity
   */
  public String getRarity() {
    return rarity;
  }
  /**
   * @param artist the artist to set
   */
  public void setArtist(String artist) {
    this.artist = artist;
  }
  /**
   * @param cmc the cmc to set
   */
  public void setCmc(String cmc) {
    this.cmc = cmc;
  }
  /**
   * @param imageName the imageName to set
   */
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
  /**
   * @param manaCost the manaCost to set
   */
  public void setManaCost(String manaCost) {
    this.manaCost = manaCost;
  }
  /**
   * @param rarity the rarity to set
   */
  public void setRarity(String rarity) {
    this.rarity = rarity;
  }
}
