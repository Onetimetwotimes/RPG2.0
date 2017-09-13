package RPGpackage;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import RPGpackage.Images;

public class RPGClass {
	static Boolean End = false;
	static Boolean won;
	static Boolean buff = false;
	static int buffDuration = 0;
	static int[] inventory = {0,0,0};
	static int enemy;
	static int stage = 0;
	static int stageCleared = 0;
	static int gold = 20;
	static int collect;
	static String _input;
	static Object[] encounter;
	static Random rng = new Random();	

	public static void main(String[] args) throws InterruptedException {
		// Start the adventure (A notable portion of this code has been copied and recontextualized from a previous assignment.)
		System.out.println("Buckle up!");
		
		RPG();
		}
	
	public static void RPG() throws InterruptedException {
		// Standard in/out solution aquired from Stack Overflow (https://stackoverflow.com/questions/5195739/how-to-convert-standard-input-to-a-string -aioobe)
		// Solution was recontextualized for this situation
		Scanner input = new Scanner(System.in);
		
		int HPPlayer = 20;
		System.out.println("Enter your name");
		String name = input.nextLine();
		System.out.println("Create a fantasy race");
		String race = input.nextLine();
		System.out.println("Create class");
		String _class = input.nextLine();
		System.out.println("Choose weapon");
		String weapon = input.nextLine();
		
		String characterParse_0 = ("You are " + name);
		
		//Efficient way of checking whether or not first character of a string is a vowel. (https://stackoverflow.com/questions/9790584/how-to-check-if-a-string-starts-with-one-of-several-prefixes -dejvuth) | Use of the "Elvis" or "Inline Conditional" operator 
		String characterParse_1 = Stream.of
				("A", "a", "E", "e", "I", "i", "O", "o", "U", "u").anyMatch
				(s -> _class.startsWith(s))
				? " an " : " a ";
		String characterParse_2 = (_class +  ", " + race + " from The City of Silvia. You fight with ");
		
		String characterParse_3 = Stream.of
				("A", "a", "E", "e", "I", "i", "O", "o", "U", "u").anyMatch
				(s -> weapon.startsWith(s)) ? " an " + weapon: " a " + weapon;
		
		System.out.println(characterParse_0 + characterParse_1 + characterParse_2 + characterParse_3);
		System.out.println("Would you like to see an image of the map?");
		_input = input.nextLine();
		if (Stream.of("y","Y").anyMatch(s -> _input.startsWith(s))) {
			Images.Map();
			}
		
		Scripts.begin(name); 
		
		//Loop that executes 1. The game has not ended by means of death 2. The stage that the player has cleared is less than or equal to 8
		while (!End && stageCleared <= 8) {
			_input = input.nextLine();	
			//When the player goes north:
			if (Pattern.matches("(?i)(go |)north", _input)) {
				stage++;
				
				System.out.println(
						"You find yourself " + stage + "0 meters north of the merchant and " + 
						(stageCleared - stage + 1) + "0 meters south of the front lines.");
				
				//Generate encounter
				enemy = rng.nextInt(2);
				
				//Encountering goblin
				if (enemy == 0 && stage > stageCleared) {
					encounter = Encounters.Goblin(HPPlayer, input, weapon, buff, (int) inventory[2]);
					won = (Boolean) encounter[0];
					HPPlayer = (int) encounter[1];
					collect = (int) encounter[2];
					
					if (won) {
						System.out.println("You won the battle and collected " + collect + " gold!");
						gold = gold + collect;
						stageCleared++;
						}
					else if (HPPlayer > 0) {
						System.out.println("You managed to find an empty area to the south where you can rest.");
						}
					else {
						Images.GameOver();
						End = true;
						}
					
					if ((boolean) encounter[3]) {
						inventory[2]--;
						}
					}
				
				//Encountering skeleton
				if (enemy == 1 && stage > stageCleared) {
					encounter = Encounters.Skeleton(HPPlayer, input, weapon, buff, (int) inventory[2]);
					won = (Boolean) encounter[0];
					HPPlayer = (int) encounter[1];
					collect = (int) encounter[2];
					
					if (won) {
						System.out.println("You won the battle and collected " + collect + " gold!");
						stageCleared++;
						gold = gold + collect;
						}
					else if (HPPlayer > 0) {
						System.out.println("You managed to find an empty area to the south where you can rest.");
						}
					else {
						Images.GameOver();
						End = true;
						}
					
					if ((boolean) encounter[3]) {
						inventory[2]--;
						}
					}
				
				//Check and update the status of the strength buff
				if (buffDuration > 0) {
					buffDuration--;
					System.out.println("Your strength potion will last " + buffDuration + " more encounters.");
					}
				else if (buff) {
					buff = false;
					System.out.println("Your strength potion has worn off!");
					}
				}
			
			//When the player goes south
			else if (Pattern.matches("(?i)(go |)south", _input)) {
				System.out.println(
						"You find yourself " + stage + "0 meters north of the merchant and " + 
						(stageCleared - stage + 1) + "0 meters south of the front lines.");
				
				if (stage > 0) {
					stage--;
					}
				else {
					encounter = Encounters.Merchant(gold, inventory, input);
					gold = (int) encounter[0];
					inventory = (int[]) encounter[1];
					}
				}
			
			//When the player goes west or east
			else if (Pattern.matches("(?i)(go |)(west|east)", _input)) {
				System.out.println("There's nothing there.");
				}
			
			//When the player views their inventory
			else if (Pattern.matches("(?i)(view|open|)inventory", _input)) {
				System.out.println(
						"You have:\n" + inventory[0] + " Health Potions | " +
						 inventory[1] + " Potions of Strength | " +						 
						 inventory[2] +" Scrolls of Escape");
				}
			
			//When the player uses anything
			else if (Pattern.matches("(?i)^(use|consume).*", _input)) {
				//Health potion
				if (Pattern.matches(".*(?i)health (potion|pot)$", _input)) {
					if (inventory[0] > 0) {
						int heal = rng.nextInt(13) + 6;
						if (HPPlayer + heal >= 20) {
							HPPlayer = 20;
							System.out.println("You fully restored your health.");
						}
						else {
							HPPlayer = HPPlayer + heal;
							System.out.println("Restored " + heal + " HP you now have " + HPPlayer + " HP");
							
						}
						
						inventory[0]--;
						}
					else {
						System.out.println("You don't have any health potions!");
						}
					}
				//Strength potion
				else if (Pattern.matches(".*(?i)strength (potion|pot)$", _input)) {
					if (inventory[1] > 0) {
						inventory[1]--;
						buff = true;
						buffDuration = 3;
						System.out.println("You feel a sudden surge of power rush through your body.");
						}
					else {
						System.out.println("You don't have any strength potions!");
						}
					}
				}
			
			//When the player tries to travel
			else if (Pattern.matches("(?i)^travel.*", _input)) {
				System.out.println("Where would you like to travel? \n Valid options are: Merchant | Front lines");
				_input = input.nextLine();
				//Front Lines
				if (Pattern.matches("(?i)front lines", _input)) {
					System.out.println("You find  yourself 10 meters south of the front lines");
					stage = stageCleared;
					}
				//Merchant
				else if (Pattern.matches("(?i)merchant", _input)) {
					System.out.println("You find yourself 10 meters north of the merchant.");
					stage = 0;
					}
				}
			//Invalid
			else {
				System.out.println("I don't understand.");
				}
			}
		//End of loop
		
		//When the player wins
		if (stageCleared >= 8 || _input.equals("Win!")) {
			System.out.println("After this perilous journey accross the Land of Youth, you find the ultimate loot!");
			Thread.sleep(2000);
			Images.Loot();
			Thread.sleep(1000);
			System.out.println("This bootiful duggy");
		}
		}
	}
