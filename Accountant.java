public class Accountant extends Adventurer{
  int caffeine, caffeineMax;
  String preferredLanguage;

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public Accountant(String name, int hp, String language){
    super(name,hp);
    caffeineMax = 14;
    caffeine = caffeineMax/2;
    preferredLanguage = language;
  }

  public Accountant(String name, int hp){
    this(name,hp,"Financial Analysis");
  }

  public Accountant(String name){
    this(name,30);
  }

  public Accountant(){
    this("John");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "caffeine";
  }

  public int getSpecial(){
    return caffeine;
  }

  public void setSpecial(int n){
    caffeine = n;
  }

  public int getSpecialMax(){
    return caffeineMax;
  }

  /*Deal 6 damage to opponent, restores 2 caffeine*/
  public String attack(Adventurer other){
    other.applyDamage(6);
    restoreSpecial(2);
    return this + " attacked "+ other + " and dealt 4 points of damage. They then take a sip of their coffee.";
  }

  /*Deal 4-8 damage to opponent, only if caffeine is high enough.
  *Reduces caffeine by 8.
  */
  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+4);
      other.applyDamage(damage);
      restoreWealth(3);
      return this + " used their "+preferredLanguage+" skills to find a mistake in "+other+"'s tax reports. This lost them "+damage+" wealth and gained "+this+" 3 wealth.";
    }else{
      return "Not enough caffeine to analyze "+other+"'s financial situation. Instead "+attack(other);
    }

  }
  /*Restores 5 special to other*/
  public String support(Adventurer other){
    return "Gives a coffee to "+other+" and restores "
    + other.restoreSpecial(5)+" "+other.getSpecialName();
  }
  /*Restores 3 special and 1 hp to self.*/
  public String support(){
    int wealth = 2;
    setHP(getHP()+wealth);
    return this+" drinks a coffee to restores "+restoreSpecial(2)+" "
    + getSpecialName()+ " and "+wealth+" HP";
  }
}
