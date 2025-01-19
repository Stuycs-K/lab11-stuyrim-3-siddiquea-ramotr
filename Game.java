import java.util.*;
public class Game{
  private static final int WIDTH = 80;
  private static final int HEIGHT = 30;
  private static final int BORDER_COLOR = Text.WHITE;
  private static final int BORDER_BACKGROUND = Text.BLACK + Text.BACKGROUND;

  public static void main(String[] args) {
  Text.clear();
        ArrayList<Adventurer> party = new ArrayList<>();
        party.add(createRandomAdventurer("Bob"));
        party.add(createRandomAdventurer("Jim"));
        party.add(createRandomAdventurer("Sam"));

        ArrayList<Adventurer> enemies = new ArrayList<>();

        
        System.out.println("Choose your enemies:");
        System.out.println("1. Play only against the boss");
        System.out.println("2. Play against other enemies (not including the boss)");

        Scanner in = new Scanner(System.in);
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Enter your choice (1 or 2): ");
            if (in.hasNextInt()) {
                choice = in.nextInt();
            } else {
                in.next(); 
            }
        }

        if (choice == 1) {
            enemies.add(new Boss("Big Boss"));
        } else if (choice == 2) {
            enemies.add(createRandomAdventurer("Enemy1"));
            enemies.add(createRandomAdventurer("Enemy2")); 
        }

        String playerLog = "";
        String enemyLog = "";

        run(party, enemies, playerLog, enemyLog);
    }

  //Display the borders of your screen that will not change.
  //Do not write over the blank areas where text will appear or parties will appear.
  public static void drawBackground(){
    Text.go(1,1);
    System.out.print("\u2554");
    for (int i=0; i<WIDTH-2; i++) {
      System.out.print("\u2550");
    }
    System.out.print("\u2557");
    for (int i = 2;i<HEIGHT;i++) {
      Text.go(i,1);
      System.out.print("\u2551");
      Text.go(i,80);
      System.out.print("\u2551");
    }
    Text.go(30,1);
    System.out.print("\u255A");
    for (int i=0; i<WIDTH-2; i++) {
      System.out.print("\u2550");
    }
    System.out.print("\u255D");
    System.out.println("");
  }

  //Display a line of text starting at
  //(columns and rows start at 1 (not zero) in the terminal)
  //use this method in your other text drawing methods to make things simpler.
  public static void drawText(String s,int startRow, int startCol){
    Text.go(startRow,startCol);
    System.out.print(s);
  }

  /*Use this method to place text on the screen at a particular location.
  *When the length of the text exceeds width, continue on the next line
  *for up to height lines.
  *All remaining locations in the text box should be written with spaces to
  *clear previously written text.
  *@param row the row to start the top left corner of the text box.
  *@param col the column to start the top left corner of the text box.
  *@param width the number of characters per row
  *@param height the number of rows
  */
  public static void TextBox(int row, int col, int width, int height, String text){
    clearBox(row,col,width,height);
    int startRow = row;
    while(text.length()>0 && row<=startRow+height) {
      if (text.length()>=width) {
        drawText(text.substring(0,width),row,col);
        text = text.substring(width);
      }
      else {
        drawText(text,row,col);
        text = "";
      }
      row++;
    }
  }
  public static void clearBox(int row, int col, int width, int height){
    for (int y=row; y<row+height;y++){
      Text.go(y,col);
      for (int x=0; x<width;x++){
        System.out.print(" ");
      }
    }
  }
  




    //return a random adventurer (choose between all available subclasses)
    //feel free to overload this method to allow specific names/stats.
    public static Adventurer createRandomAdventurer(String name){
      int index = (int)(Math.random()*3);
      if (index == 0) {
        return new Accountant(name);
      }
      else if (index == 1) {
        return new Hacker(name);
      }
      return new Secretary(name);
    }

    /*Display a List of 2-4 adventurers on the rows row through row+3 (4 rows max)
    *Should include Name HP and Special on 3 separate lines.
    *Note there is one blank row reserved for your use if you choose.
    *Format:
    *Bob          Amy        Jun
    *HP: 10       HP: 15     HP:19
    *Caffeine: 20 Mana: 10   Snark: 1
    * ***THIS ROW INTENTIONALLY LEFT BLANK***
    */
    public static void drawParty(ArrayList<Adventurer> party,int startCol){
     for (int i = 0; i < party.size(); i++) {
            Adventurer a = party.get(i);
            int colOffset = i * 20; 
            drawText(a.getName(), 2, startCol + colOffset); 
            drawText("Wealth: " + colorByPercent(a.getWealth(), a.getmaxWealth()), 3, startCol + colOffset); // Display wealth
            drawText(a.getSpecialName() + ": " + a.getSpecial() + "/" + a.getSpecialMax(), 4, startCol + colOffset); // Display special resource
        }
    }

  //Use this to create a colorized number string based on the % compared to the max value.
  public static String colorByPercent(int hp, int maxHP){
     double percent = (double) hp / maxHP;
    String color;
    if (percent < 0.25) {
      color = "\u001B[31m"; // Red
    } else if (percent < 0.75) {
      color = "\u001B[33m"; // Yellow
    } else {
      color = "\u001B[37m"; // White
    }
    return color + String.format("%2s", hp+"") + "/" + String.format("%2s", maxHP+"") + "\u001B[0m";
  }




  //Display the party and enemies
  //Do not write over the blank areas where text will appear.
  //Place the cursor at the place where the user will by typing their input at the end of this method.
  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies, String playerLog, String enemyLog) {
        drawParty(party, 3); 
        drawParty(enemies, 45); 
        TextBox(25, 3, 35, 3, playerLog); 
        TextBox(25, 42, 35, 3, enemyLog); 
    }


  

  public static String userInput(Scanner in){
     Text.go(HEIGHT + 1, 1);
        Text.showCursor();
        String input = in.nextLine();
        Text.hideCursor();
        return input;
    }

  public static void quit(){
    Text.reset();
    Text.showCursor();
    Text.go(32,1);
  }

  public static void run(){
    //Clear and initialize
    Text.hideCursor();
    Text.clear();
    drawBackground();

    //Things to attack:
    //Make an ArrayList of Adventurers and add 1-3 enemies to it.
    //If only 1 enemy is added it should be the boss class.
    //start with 1 boss and modify the code to allow 2-3 adventurers later.
    ArrayList<Adventurer>enemies = new ArrayList<Adventurer>();
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //YOUR CODE HERE
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/

    //Adventurers you control:
    //Make an ArrayList of Adventurers and add 2-4 Adventurers to it.
    ArrayList<Adventurer> party = new ArrayList<>();
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //YOUR CODE HERE
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/

    boolean partyTurn = true;
    int whichPlayer = 0;
    int whichOpponent = 0;
    int turn = 0;
    String input = "";//blank to get into the main loop.
    Scanner in = new Scanner(System.in);
    //Draw the window border

    //You can add parameters to draw screen!
    drawScreen();//initial state.

    //Main loop

    //display this prompt at the start of the game.
    String preprompt = "Enter command for "+party.get(whichPlayer)+": attack/special/quit";

    while(! (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){
      //Read user input
      input = userInput(in);

      //example debug statment
      TextBox(24,2,1,78,"input: "+input+" partyTurn:"+partyTurn+ " whichPlayer="+whichPlayer+ " whichOpp="+whichOpponent );

      //display event based on last turn's input
      if(partyTurn){

        //Process user input for the last Adventurer:
        if(input.equals("attack") || input.equals("a")){
          /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
          //YOUR CODE HERE
          /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
        }
        else if(input.equals("special") || input.equals("sp")){
          /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
          //YOUR CODE HERE
          /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
        }
        else if(input.startsWith("su ") || input.startsWith("support ")){
          //"support 0" or "su 0" or "su 2" etc.
          //assume the value that follows su  is an integer.
          /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
          //YOUR CODE HERE
          /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
        }

        //You should decide when you want to re-ask for user input
        //If no errors:
        whichPlayer++;


        if(whichPlayer < party.size()){
          //This is a player turn.
          //Decide where to draw the following prompt:
          String prompt = "Enter command for "+party.get(whichPlayer)+": attack/special/quit";


        }else{
          //This is after the player's turn, and allows the user to see the enemy turn
          //Decide where to draw the following prompt:
          String prompt = "press enter to see monster's turn";

          partyTurn = false;
          whichOpponent = 0;
        }
        //done with one party member
      }else{
        //not the party turn!


        //enemy attacks a randomly chosen person with a randomly chosen attack.z`
        //Enemy action choices go here!
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        //YOUR CODE HERE
        /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/


        //Decide where to draw the following prompt:
        String prompt = "press enter to see next turn";

        whichOpponent++;

      }//end of one enemy.

      //modify this if statement.
      if(!partyTurn && whichOpponent >= enemies.size()){
        //THIS BLOCK IS TO END THE ENEMY TURN
        //It only triggers after the last enemy goes.
        whichPlayer = 0;
        turn++;
        partyTurn=true;
        //display this prompt before player's turn
        String prompt = "Enter command for "+party.get(whichPlayer)+": attack/special/quit";
      }

      //display the updated screen after input has been processed.
      drawScreen();


    }//end of main game loop


    //After quit reset things:
    quit();
  }
}
