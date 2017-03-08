package models.cardModels;

import java.io.Serializable;
import java.util.List;

/**
 * Base Card Class That Is Responsible For Storing Base Information
 * 
 * @author Mitchell
 * 
 */
public class Card implements Comparable<Card>, Serializable{
  protected String layout;
  protected String text;
  protected String type;
  protected String name;
  protected String flavor;
  protected String artist;
  protected String manaCost;
  protected int multiverseID;
  protected List<String> types;
  protected List<String> subtypes;
  protected List<String> colorIdentity;

  protected CardRarity cardRarity;
  protected static final long serialVersionUID = 1L;
  
  
  public Card () {
    
  }
  
  public Card (String cardName) {
    this.name = cardName; 
    this.text = "";
  }

  /**
   * @return the flavour
   */
  public String getFlavor() {
    return flavor;
  }

  /**
   * @return the layout
   */
  public String getLayout() {
    return layout;
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the types
   */
  public List<String> getTypes() {
    return types;
  }

  /**
   * @return the subtypes
   */
  public List<String> getSubtypes() {
    return subtypes;
  }

  /**
   * @return the colorIdentity
   */
  public List<String> getColorIdentity() {
    return colorIdentity;
  }

  /**
   * @return the cardRarity
   */
  public CardRarity getCardRarity() {
    return cardRarity;
  }
  
  /**
   * @return the artist
   */
  public String getArtist() {
    return artist;
  }
  
  /**
   * @return the manaCost
   */
  public String getManaCost() {
    return manaCost;
  }

  /**
   * @return the multiverseID
   */
  public int getMultiverseID() {
    return multiverseID;
  }

  /**
   * @param multiverseID the multiverseID to set
   */
  public void setMultiverseID(int multiverseID) {
    this.multiverseID = multiverseID;
  }

  /**
   * @param manaCost the manaCost to set
   */
  public void setManaCost(String manaCost) {
    this.manaCost = manaCost;
  }

  /**
   * @param artist the artist to set
   */
  public void setArtist(String artist) {
    this.artist = artist;
  }

  /**
   * @param flavour the flavour to set
   */
  public void setFlavor(String flavour) {
    this.flavor = flavour;
  }

  /**
   * @param layout the layout to set
   */
  public void setLayout(String layout) {
    this.layout = layout;
  }

  /**
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = (text == null) ? "" : text;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = (type == null) ? "" : type;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param types the types to set
   */
  public void setTypes(List<String> types) {
    this.types = types;
  }

  /**
   * @param subtypes the subtypes to set
   */
  public void setSubtypes(List<String> subtypes) {
    this.subtypes = subtypes;
  }

  /**
   * @param colorIdentity the colorIdentity to set
   */
  public void setColorIdentity(List<String> colorIdentity) {
    this.colorIdentity = colorIdentity;
  }

  /**
   * @param cardRarity the cardRarity to set
   */
  public void setCardRarity(CardRarity cardRarity) {
    this.cardRarity = cardRarity;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Card) {
      Card testObject = (Card) obj;
      if (!testObject.name.equals(this.name)) {
        return false;
      }
      else if (!testObject.text.equals(this.text)) {
        return false;
      }
      return true;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    int hashCode = 1;
    hashCode += name.hashCode();
    hashCode += text.hashCode();
    return hashCode;
  }

  @Override
  public int compareTo(Card paramT) {
    return this.name.compareTo(paramT.name);
  }
  
  @Override
  public String toString() {
    return "Card [name=" + name + ", text=" + text +"]";
  }
}
