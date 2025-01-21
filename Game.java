import java.util.*;
public class Game{
  private static final int WIDTH = 80;
  private static final int HEIGHT = 30;
  private static final int BORDER_COLOR = Text.WHITE;
  private static final int BORDER_BACKGROUND = Text.BLACK + Text.BACKGROUND;

  public static void main(String[] args) {
        ArrayList<Adventurer> party = new ArrayList<>();
        party.add(createRandomAdventurer());
        party.add(createRandomAdventurer());

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
            enemies.add(createRandomAdventurer("Enemy3"));
        }

        ArrayList<String> actionLog = new ArrayList<>();
        run(party, enemies, actionLog);
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
    public static Adventurer createRandomAdventurer(){
      int index = (int)(Math.random()*3);
      if (index == 0) {
        return new Accountant();
      }
      else if (index == 1) {
        return new Hacker();
      }
      return new Secretary();
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
    public static void drawParty(ArrayList<Adventurer> party, int startRow) {
        clearBox(startRow, 3, 20 * party.size(), 5);
        for (int i = 0; i < party.size(); i++) {
            Adventurer a = party.get(i);
            int colOffset = i * 26;
            String name = a.getWealth() > 0 ? a.getName() : a.getName() + " [DEAD]";
            drawText(name, startRow, 3 + colOffset);
            drawText("Wealth: " + (a.getWealth() > 0 ? colorByPercent(a.getWealth(), a.getmaxWealth()) : "0/0"), startRow+1, 3 + colOffset);
            drawText(a.getSpecialName() + ": " + a.getSpecial() + "/" + a.getSpecialMax(), startRow+2, 3 + colOffset);
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
  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies, ArrayList<String> actionLog) {
        clearBox(1, 1, WIDTH, HEIGHT);
        drawParty(party, 2);
        drawParty(enemies, 7);
        drawActionLog(actionLog);
        drawBackground();
    }


  

  public static String userInput(Scanner in){
     Text.go(HEIGHT + 3, 1);
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

    public static void run(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies, ArrayList<String> actionLog) {
        Text.hideCursor();
        Text.clear();

        boolean partyTurn = true;
        int whichPlayer = 0;
        int whichOpponent = 0;
        String input = "";
        Scanner in = new Scanner(System.in);
        drawScreen(party, enemies, actionLog);

        while (!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("q")) {
            if (party.isEmpty()) {
                gameOver(false);
                return;
            } else if (enemies.isEmpty()) {
                gameOver(true);
                return;
            }

            if (partyTurn) {
                Adventurer currentPlayer = party.get(whichPlayer);
                if (currentPlayer.getWealth() > 0) {
                    clearBox(HEIGHT + 1, 1, WIDTH, 1);
                    drawText("Enter command for " + currentPlayer.getName() + ": attack (a) / special (sp) / support (su) / quit (q)", HEIGHT+1, 1);
                    drawText("Press space, then enter target (as an integer) for " + currentPlayer.getName(), HEIGHT+2, 1);
                    input = userInput(in);
                    if (input.startsWith("attack") || input.startsWith("a")) {
                        int target = parseTarget(input);
                        if (target >= 0 && target < enemies.size()) {
                            String log = currentPlayer.attack(enemies.get(target));
                            actionLog.add(log);
                            if (enemies.get(target).getWealth() <= 0) {
                                enemies.remove(target);
                            }
                        } else {
                            actionLog.add("Invalid target.");
                        }
                    } else if (input.startsWith("special") || input.startsWith("sp")) {
                        int target = parseTarget(input);
                        if (target >= 0 && target < enemies.size()) {
                            String log = currentPlayer.specialAttack(enemies.get(target));
                            actionLog.add(log);
                            if (enemies.get(target).getWealth() <= 0) {
                                enemies.remove(target);
                            }
                        } else {
                            actionLog.add("Invalid target.");
                        }
                    } else if (input.startsWith("support") || input.startsWith("su")) {
                        int target = parseTarget(input);
                        if (target >= 0 && target < party.size()) {
                            String log = currentPlayer.support(party.get(target));
                            actionLog.add(log);
                        } else {
                            actionLog.add("Invalid target.");
                        }
                    }
                }
                whichPlayer++;
                if (whichPlayer >= party.size()) {
                    partyTurn = false;
                    whichPlayer = 0;
                }
            } else {
                Adventurer currentEnemy = enemies.get(whichOpponent);
                Adventurer target = party.get((int) (Math.random() * party.size()));
                String log = currentEnemy.attack(target);
                actionLog.add(log);
                if (target.getWealth() <= 0) {
                    party.remove(target);
                }
                whichOpponent++;
                if (whichOpponent >= enemies.size()) {
                    partyTurn = true;
                    whichOpponent = 0;
                }
            }

            drawScreen(party, enemies, actionLog);
        }

        quit();
    }
	
   private static int parseTarget(String input) {
        try {
            return Integer.parseInt(input.split(" ")[1]);
        } catch (Exception e) {
            return -1;
        }
    }
	public static void gameOver(boolean playerWins) {
    Text.clear();
    String message;
    if (playerWins) {
        message = "You Win!";
    } else {
        message = "You Lose!";
    }
    drawText(message, HEIGHT / 2, WIDTH / 2 - message.length() / 2);
    quit();
}
 public static void drawActionLog(ArrayList<String> actionLog) {
        int row = 13;
        int col = 3;
        int height = 15;
        int width = 50;
        clearBox(row, col, width, height);
        drawText("Action Log:", row, col);
        int logStart = Math.max(0, actionLog.size() - (height - 1));
        for (int i = 0; i < height - 1 && logStart + i < actionLog.size(); i++) {
            TextBox(row + 1 + i, col, 77, 15, actionLog.get(logStart + i));
        }
    }
  
  
  
}
