package RPGpackage;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

//Class that handles all encouters
public class Encounters {
	static Random rng = new Random();
	static Boolean won;
	static Boolean fled;
	static Boolean shopping = true;
	static boolean escaped;
	static int collect;

				
				public static Object[] Goblin(int HPPlayer, Scanner input, String weapon, boolean buff, int scrolls) throws InterruptedException {
					Images.Goblin();
					int HPGoblin = rng.nextInt(20) + rng.nextInt(6);
					int difficulty = rng.nextInt(2);
					int playerDamage = 0;
					int goblinDamage = 0;
					fled = false;
					escaped = false;
					
					System.out.println("You've encountered a level " + (difficulty + 1) + " goblin! What will you do?");

					while (HPGoblin > 0 && HPPlayer > 0 && !fled) {
						String _input = input.nextLine();
						
						if (Pattern.matches("([Aa]ttack|[Kk]ill|[Aa]ttack [Gg]oblin|[Kk]ill [Gg]oblin)", _input)) {
							playerDamage = !buff ? rng.nextInt(7) : rng.nextInt(13) + 4;
							HPGoblin = HPGoblin - playerDamage;
							System.out.println("You attack with your " + weapon + " and deal " + playerDamage + " damage. The goblin now has " + HPGoblin + "HP");
							}
						
						else if (Pattern.matches("([Ff]lee|[Rr]un)", _input)) {
							System.out.println("You fled."); //Tell the player that they have fled.
							fled = true;
						}
						
						else if (Pattern.matches("([Nn]othing|[Ww]ait|[Ii]dle|[Ss]kip|[Ss]kip turn])", _input)) {
							System.out.println("You do nothing...");
						}
						else if (Pattern.matches("(?i)((use |consume |)(escape scroll|scroll of escape)|escape)", _input)) {
							if (scrolls > 0) {
								System.out.println("You quickly read the words as written on the scroll and are suddenly very disoriented.");
								escaped = true;
								fled = true;								
							}
							else {
								System.out.println("You don't have any Scrolls of Escape!");
							}
						}
						else {
							System.out.println("I don't understand");
						}
						
							Thread.sleep(1000);
						
						if (difficulty == 0 && HPGoblin > 0 && !escaped) {
						goblinDamage = rng.nextInt(5) ;
						HPPlayer = HPPlayer - goblinDamage;
						System.out.println("The goblin attacks! It dealt " + goblinDamage + " damage. You now have " + HPPlayer + "HP");
					}

						if (difficulty == 1 && HPGoblin > 0 && !escaped) 
						{
						goblinDamage = rng.nextInt(7);
						HPPlayer = HPPlayer - goblinDamage;
						System.out.println("The goblin attacks! It dealt " + goblinDamage + " damage. You now have " + HPPlayer + "HP");
					}
					
						if (difficulty == 2 && HPGoblin > 0 && !escaped) {
						goblinDamage = rng.nextInt(9);
						HPPlayer = HPPlayer - goblinDamage;
						System.out.println("The goblin attacks! It dealt " + goblinDamage + " damage. You now have " + HPPlayer + "HP");
					}

					}
					
					
					if (HPGoblin <= 0) {
						won = true;
						collect = rng.nextInt(21) + 5;
					}
					else if (!(HPPlayer > 0) || fled) {
						won = false;
					}
					return new Object[] {won, HPPlayer, collect, escaped};
				}
	
	public static Object[] Skeleton(int HPPlayer, Scanner input, String weapon, boolean buff, int scrolls) throws InterruptedException {
		Images.Skeleton();
		int HPSkeleton = rng.nextInt(20) + rng.nextInt(6);
		int difficulty = rng.nextInt(2);
		int playerDamage = 0;
		int skeletonDamage = 0;
		fled = false;	
		escaped = false;
		
		System.out.println("You've encountered a level " + (difficulty + 1) + " skeleton! What will you do?");

		while (HPSkeleton > 0 && HPPlayer > 0 && !fled) {
			String _input = input.nextLine();
			
			if (Pattern.matches("([Aa]ttack|[Kk]ill|[Aa]ttack [Ss]keleton|[Kk]ill [Ss]keleton)", _input)) {
			playerDamage = !buff ? rng.nextInt(7) : rng.nextInt(13) + 4;
			HPSkeleton = HPSkeleton - playerDamage;
			System.out.println("You attack with your " + weapon + " and deal " + playerDamage + " damage. The skeleton now has " + HPSkeleton + "HP");
			}
			
			else if (Pattern.matches("([Ff]lee|[Rr]un)", _input)) {
				System.out.println("You fled."); //Tell the player that they have fled.
				fled = true;
			}
			else if (Pattern.matches("(?i)((use |consume |)(escape scroll|scroll of escape)|escape)", _input)) {
				if (scrolls > 0) {
					System.out.println("You quickly read the words as written on the scroll and are suddenly very disoriented.");
					escaped = true;
					fled = true;								
				}
				else {
					System.out.println("You don't have any Scrolls of Escape!");
				}
			}
			else if (Pattern.matches("([Nn]othing|[Ww]ait|[Ii]dle|[Ss]kip|[Ss]kip turn])", _input)) {
				System.out.println("You do nothing...");
			}
			
			else {
				System.out.println("I don't understand");
			}
			
				Thread.sleep(1000);
			
			if (difficulty == 0 && HPSkeleton > 0 && !escaped) {
			skeletonDamage = rng.nextInt(5) ;
			HPPlayer = HPPlayer - skeletonDamage;
			System.out.println("The skeleton attacks! It dealt " + skeletonDamage + " damage. You now have " + HPPlayer + "HP");
		}

			if (difficulty == 1 && HPSkeleton > 0 && !escaped) 
			{
			skeletonDamage = rng.nextInt(7);
			HPPlayer = HPPlayer - skeletonDamage;
			System.out.println("The skeleton attacks! It dealt " + skeletonDamage + " damage. You now have " + HPPlayer + "HP");
		}
		
			if (difficulty == 2 && HPSkeleton > 0 && !escaped) {
			skeletonDamage = rng.nextInt(9);
			HPPlayer = HPPlayer - skeletonDamage;
			System.out.println("The skeleton attacks! It dealt " + skeletonDamage + " damage. You now have " + HPPlayer + "HP");
		}

		}
		
		
		if (HPSkeleton <= 0) {
			won = true;
			collect = rng.nextInt(21) + 5;
		}
		else if (HPPlayer <= 0 || fled) {
			won = false;
		}
		return new Object[] {won, HPPlayer, collect, escaped};
	}

	public static Object[] Merchant(int gold, int[] inventory, Scanner input) throws InterruptedException {
		shopping = true;
		while (shopping) {
			System.out.println("You have " + gold + " gold peices");
			System.out.println("The merchant is selling:");
			System.out.println("Health potions - 15 Gp ea. | Strength potions - 20 Gp ea. | Scrolls of Escape - 10 Gp ea. \n"
			+ "Health potions restore up to 20 health\n"
			+ "Strength potions increase your potential damage and guarantee that damage will be done (lasts 3 encounters) \n"
			+ "Scrolls of Escape can be used to return to a safe place without giving the enemy an attack of opportunity");
						
			String _input = input.nextLine();

			if (Pattern.matches("(?i)(buy |)health (pot|potion)", _input)) {
				if (gold >= 15) {
					System.out.println("Bought health potion");
					inventory[0]++;
					gold = gold - 15;
				}
				else {
					System.out.println("Insufficient funds!");
				}

				}
			else if (Pattern.matches("(?i)(buy |)strength (pot|potion)", _input)) {
				if (gold >= 20) {
					System.out.println("Bought strength potion");
					inventory[1]++;
					gold = gold - 20;
				}
				else {
					System.out.println("Insufficient funds!");
				}

			}
			else if (Pattern.matches("(?i)(buy |)(scroll of escape|escape scroll)", _input)) {
				if (gold >= 10) {
					System.out.println("Bought Scroll of Escape");
					inventory[2]++;
					gold = gold - 10;
				}
				else {
					System.out.println("Insufficient funds!");
				}

			}
			
			else if (Pattern.matches("(?i)((go |)north|leave)", _input)) {
				shopping = false;
		}	
			else {
				System.out.println("You can't do that!");
			}
		}
		return new Object[] {gold, inventory};

		
		
		
	}


}
