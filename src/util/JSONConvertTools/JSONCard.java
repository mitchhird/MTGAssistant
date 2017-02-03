package util.JSONConvertTools;

import java.util.ArrayList;
import java.util.List;

import models.cardModels.Card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONCard extends Card {
  protected String cmc;
  protected String imageName;
  protected String rarity;
  protected String power;
  protected String toughness;
  
  protected List<JSONLegality> legalities;
  
  
  @Override
  public String toString() {
    return "[JSONCard(" + name + ")]";
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
   * @return the rarity
   */
  public String getRarity() {
    return rarity;
  }
  
  /**
   * @return the legalities
   */
  public List<JSONLegality> getLegalities() {
    if (legalities == null) {
      legalities = new ArrayList<>();
    }
    return legalities;
  }

  /**
   * @param legalities the legalities to set
   */
  public void setLegalities(List<JSONLegality> legalities) {
    this.legalities = legalities;
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
   * @param rarity the rarity to set
   */
  public void setRarity(String rarity) {
    this.rarity = rarity;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String getToughness() {
    return toughness;
  }

  public void setToughness(String toughness) {
    this.toughness = toughness;
  }
}
