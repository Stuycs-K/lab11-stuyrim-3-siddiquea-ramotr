import java.util.Random;
public class Secretary extends Adventurer{
  private int supplies, maxSupplies;
  private String preferredLanguage;
  //Abstract methods are meant to be implemented in child classes.
  /*
  all adventurers must have a custom special
  consumable resource (mana/rage/money/witts etc)
  */
  public Secretary(){
    this("Jean-Pierre");
  }

  public Secretary(String name){
    this(name, 10);
  }

  public Adventurer(String name, int hp){
    this.name = name;
    this.wealth = hp;
    this.maxWealth = hp;
  }

  //give it a short name (fewer than 13 characters)
  public  String getSpecialName() {
    return "supplies";
  }
  //accessor methods
  public int getSpecial() {
    return supplies;
  }
  public int getSpecialMax() {
    return maxSupplies;
  }
  public void setSpecial(int n) {
    supplies = n;
  }

  //hurt or hinder the target adventurer
  public String attack(Adventurer other) {
    other.applyDamage(2);
    restoreSpecial(2);
    return this+" threw a stapler at "+other+" and dealt 2 points of wealth. They then restock some office supplies.";
  }

  //heal or buff the target adventurer
  public String support(Adventurer other) {
    return this+" gave "+other+"a new printer and restored "+other.restoreSpecial(5)+other.getSpecialName();
  }

  //heal or buff self
  public String support() {
    restoreWealth()
  }

  //hurt or hinder the target adventurer, consume some special resource
  public abstract String specialAttack(Adventurer other);
}
