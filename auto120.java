package Auto120;

public class auto120 {
	
	
	public static void main(String[] args) {

			int x_counter = 0;
		    int x_clockstop = 0;
		    int x_playerid = 0;

		    boolean x_gameover = false;
		    
		    Game thisGame = new Game();

		      for (x_counter = 0; x_counter <= 10000; ++x_counter)
		      {
		         thisGame.g_logmove("------------------------------------------------------------" + Integer.toString(x_counter));
		         
		         thisGame.g_logmove("Game Move: " + Integer.toString(x_counter));
		                          
		          x_playerid = thisGame.g_get_playerturn();
		   
		          x_gameover = thisGame.g_player_action(x_playerid);

		          if (x_gameover ) {

		              x_clockstop = x_counter;
		              x_counter = 9999999;

		          }

		
		          thisGame.g_flip_player();

		        //  if (x_counter % 250 == 0)
		          //{
		            //  srand((unsigned int)time(NULL));
		          //}
		          


		      }

		      //delete[] thisGame;
		      thisGame.g_displaylog();

		      System.out.print("\nFINAL MOVE: " + Integer.toString(x_clockstop) + "\n");

		   }

}
