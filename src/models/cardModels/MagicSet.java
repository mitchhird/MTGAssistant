package models.cardModels;


/**
 * A Standard MTG Set. Contains common things like name, code, gatherer code and release data
 * @author Mitchell
 */
public class MagicSet {
  protected String name;
  protected String code;
  protected String gathererCode;
  protected String border;
  protected String releaseDate;
  protected String magicCardsInfoCode;

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
  
  @Override
  public String toString() {
    return name + " - (" + code + ")";
  }

}
