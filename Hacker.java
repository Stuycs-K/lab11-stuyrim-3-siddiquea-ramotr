public class Hacker extends Adventurer{
  int malware, malwareMax;
  String preferredLanguage;

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public Hacker(String name, int hp, String language){
    super(name,hp);
    malwareMax = 14;
    malware = malwareMax/2;
    preferredLanguage = language;
  }

  public Hacker(String name, int hp){
    this(name,hp,"html");
  }

  public Hacker(String name){
    this(name,24);
  }

  public Hacker(){
    this("steve");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "malware";
  }

  public int getSpecial(){
    return malware;
  }

  public void setSpecial(int n){
    malware = n;
  }

  public int getSpecialMax(){
    return malwareMax;
  }

  /*Deal 2-7 damage to opponent, restores 2 caffeine*/
  public String attack(Adventurer other){
    int damage = 3;
    other.applyDamage(damage);
    restoreSpecial(2);
    return this + " attacked "+ other + " and dealt "+ damage +
    " points of damage.";
  }

  /*Deal 3-12 damage to opponent, only if caffeine is high enough.
  *Reduces caffeine by 8.
  */
  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*7+4);
      other.applyDamage(damage);
      return this + " used their "+preferredLanguage+
      " skills to hack the matrix. "+
      " This glitched out "+other+" dealing "+ damage +" points of damage.";
    }else{
      return "Not enough malware to use the ultimate code. Instead "+attack(other);
    }

  }
  /*Restores 5 special to other*/
  public String support(Adventurer other){
    return "Gives a computer to "+other+" and restores "
    + other.restoreSpecial(2)+" "+other.getSpecialName();
  }
  /*Restores 6 special and 1 hp to self.*/
  public String support(){
    int wealth = 1;
    setWealth(getWealth()+wealth);
    return this+" buys a new computer to restore "+restoreSpecial(6)+" "
    + getSpecialName()+ " and "+wealth+" wealth";
  }
}
