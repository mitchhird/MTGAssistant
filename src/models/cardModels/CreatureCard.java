package models.cardModels;

/**
 * A Slight Extension On The Card Class. Creatures have power and toughness
 * values whereas normal generic cards do not.
 * 
 * @author Mitchell
 */
public class CreatureCard extends Card {

  private int creaturePower;
  private int creatureToughness;
  
  // Default Constructor For The Card Object
  public CreatureCard() {
      
  }

  public int getCreaturePower() {
    return creaturePower;
  }

  public void setCreaturePower(int creaturePower) {
    this.creaturePower = creaturePower;
  }

  public int getCreatureToughness() {
    return creatureToughness;
  }

  public void setCreatureToughness(int creatureToughness) {
    this.creatureToughness = creatureToughness;
  }
}
