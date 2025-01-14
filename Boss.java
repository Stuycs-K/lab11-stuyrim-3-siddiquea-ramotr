public class Boss extends Adventurer{
  int employees, employeesMax;
  String preferredLanguage;

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public Boss(String name, int hp, String language){
    super(name,hp);
    malwareMax = 18;
    malware = malwareMax/2;
    preferredLanguage = language;
  }

  public Boss(String name, int hp){
    this(name,hp,"manipulation");
  }

  public Boss(String name){
    this(name,50);
  }

  public Boss(){
    this("bossman");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "employees";
  }

  public int getSpecial(){
    return x;
  }

  public void setSpecial(int n){
    x = n;
  }

  public int getSpecialMax(){
    return x;
  }

  /*Deal 5 damage to opponent, restores 2 malware*/
  public String attack(Adventurer other){
    int damage = 2;
    other.applyDamage(damage);
    restoreSpecial(2);
    return this + " gave  "+ other +  damage +
    "years of unpaid work!";
  }

  /*Deal 3-10 damage to opponent, only if malware is high enough.
  *Reduces malware by 8.
  */
  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = 6
      other.applyDamage(damage);
      return this + " used their "+preferredLanguage+
      " skills to hack the matrix. "+
      " This glitched out "+other+" dealing "+ damage +" points of damage.";
    }else{
      return "Not enough malware to use the ultimate code. Instead "+attack(other);
    }

  }
  /*Restores 2 special to other*/
  public String support(Adventurer other){
    return "Gives a computer to "+other+" and restores "
    + other.restoreSpecial(2)+" "+other.getSpecialName();
  }
  /*Restores 6 special and 4 wealth to self.*/
  public String support(){
    restoreWealth(4);
    return this+" buys a new computer to restore "+restoreSpecial(6)+" "
    + getSpecialName()+ " and 4 wealth";
  }
}
