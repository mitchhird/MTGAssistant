package util.JSONConvertTools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONLegality {
  protected String format;
  protected String legality;
  /**
   * @return the format
   */
  public String getFormat() {
    return format;
  }
  /**
   * @return the legality
   */
  public String getLegality() {
    return legality;
  }
  /**
   * @param format the format to set
   */
  public void setFormat(String format) {
    this.format = format;
  }
  /**
   * @param legality the legality to set
   */
  public void setLegality(String legality) {
    this.legality = legality;
  }
}
