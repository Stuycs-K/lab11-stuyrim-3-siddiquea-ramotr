import java.util.Random;
public class Secretary extends Adventurer{
  private int supplies, maxSupplies;
  private String preferredLanguage;
  //Abstract methods are meant to be implemented in child classes.
  /*
  all adventurers must have a custom special
  consumable resource (mana/rage/money/witts etc)
  */

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

  //concrete method written using abstract methods.
  //refill special resource by amount, but only up to at most getSpecialMax()
  public int restoreSpecial(int n){
    if( n > getSpecialMax() - getSpecial()){
      n = getSpecialMax() - getSpecial();
    }
    setSpecial(getSpecial()+n);
    return n;
  }

  /*
  all adventurers must have a way to attack enemies and
  support their allys
  */
  //hurt or hinder the target adventurer
  public String attack(Adventurer other) {
    other.applyDamage(2);
    restoreSpecial(2);
    return this+" threw a stapler at "+other+" and dealt 2 points of wealth. They then restock some office supplies.";
  }

  /*This is an example of an improvement that you can make to allow
   * for more flexible targetting.
   */
  //heal or buff the party
  //public abstract String support(ArrayList<Adventurer> others);

  //heal or buff the target adventurer
  public String support(Adventurer other) {
    return this+" gave "+other+"a new printer and restored "+other.restoreSpecial(5)+other.getSpecialName();
  }

  //heal or buff self
  public String support() {
    setWealth()
  }

  //hurt or hinder the target adventurer, consume some special resource
  public abstract String specialAttack(Adventurer other);

  /*
  standard methods
  */

  public void applyDamage(int amount){
    this.wealth -= amount;
  }

  //You did it wrong if this happens.
  public Adventurer(){
    this("Lester-the-noArg-constructor-string");
  }

  public Adventurer(String name){
    this(name, 10);
  }

  public Adventurer(String name, int hp){
    this.name = name;
    this.wealth = hp;
    this.maxWealth = hp;
  }

  //toString method
  public String toString(){
    return this.getName();
  }

  //Get Methods
  public String getName(){
    return name;
  }

  public int getWealth(){
    return wealth;
  }

  public int getmaxWealth(){
    return maxWealth;
  }
  public void setmaxWealth(int newMax){
    maxWealth = newMax;
  }

  //Set Methods
  public void setWealth(int health){
    this.wealth = health;
  }

  public void setName(String s){
    this.name = s;
  }

  public int restoreWealth(int n){
    if( n > getmaxWealth() - getWealth()){
      n = getmaxWealth() - getWealth();
    }
    setWealth(getWealth()+n);
    return n;
  }
}
