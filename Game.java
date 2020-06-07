package Auto120;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {

		int g_movecounter = 1; //for logging

		String[] g_movelog = new String[999999];

		int g_playerturn;

		Player[] g_players = new Player[3];
		
		//TODO initiate players 1 and 2
				
		int g_dievalue = 0;

		boolean g_gameover = false;

		int m_owner;

		int m_location;
	
	public Game() {
		// TODO Auto-generated constructor stub
		
		
		g_movecounter = 0;

		g_playerturn = g_return_random(2); //TODO

		g_logmove("Player " + Integer.toString(g_playerturn) + " will go first."); //TODO

		g_dievalue = 0;

		g_gameover = false;

		//rest of game

		g_newgame(); //TODO

		//g_displaylog();
	
	}
	
	
	//methods
	public void g_newgame() {
		//create players was done in constructor, now assign markers

		g_players[1] = new Player(1);
		
		g_logmove("Player 1 created.");

		g_players[2] = new Player(2);
		
		g_logmove("Player 2 created.");

	}

	public int g_get_playerturn() {
		return g_playerturn;
	}

	public void g_flip_player() {
		if (g_playerturn == 1) {
			g_playerturn = 2;
		}
		else
		{
			g_playerturn = 1;

		}
	}
	
	
	Player g_return_other_player() {


		if (g_playerturn == 1) {

			return g_players[2];


		}
		else
		{
			return g_players[1];

		}

	}

	public int g_diceroll() {

		g_dievalue = g_return_random(6);
		
		return g_dievalue;
	}


	public Player g_return_player(int x_playerid) {

		return g_players[x_playerid];
		

	}

	public void g_displaylog()
	{
		int x_counter = 0;

		for (x_counter = 0; x_counter <= g_movecounter-1; x_counter++)
		{
			System.out.print(g_movelog[x_counter] + "\n");
			
		}


	}	
	
	public int g_return_random(int g_upper)
	{
		
		Random r = new Random();
		
		return r.nextInt((g_upper - 1) + 1) + 1;
		
	}

	public void g_logmove(String x_logmove)
	{
		
		Integer x_movecounter = this.g_movecounter;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");  
		
		LocalDateTime now = LocalDateTime.now();  
		
		String x_timestamp = dtf.format(now);  
		
		g_movelog[x_movecounter] = (x_timestamp + " -- " + x_logmove);

		this.g_movecounter++;
		
	}

	
	

	

	public boolean g_move_onto_board_set_D(Player x_player)
	{


		int x_piece_pointer = g_find_to_move_onto_board(x_player);

		if (x_piece_pointer == 0)
		{

			//no more pieces to move on to the board
			g_logmove("Player " + Integer.toString(x_player.p_get_playerid())  + " did not have pieces to move into play.");
			
			return false;

			}

			else

			{
				g_marker_move(x_player.p_get_playerid(), x_piece_pointer);
								
				return true;
			}
		}

	

	//D - find a marker not on the board, select one to move

public	int g_find_to_move_onto_board(Player x_player) {

		int[] x_piece_array_pointer = {0,0,0,0,0}; 
		
		
		int x_counter_array = 0;

		int x_counter = 0;

		for (x_counter = 1; x_counter <= 4; x_counter++) 
		{


			if (x_player.p_pieces[x_counter].m_get_location() == 1) {
	
				x_piece_array_pointer[++x_counter_array] = x_counter;

			}


		}

		//If there is one or more, return one at random
		if (x_counter_array > 0) {

			x_counter = g_return_random(x_counter_array);

			return x_piece_array_pointer[x_counter];
		}
		else
		{
			//there were no markers 'off the board'...return an empty pointer;

			g_logmove("Player " +  Integer.toString(x_player.p_get_playerid()) + " was in SET D but could not find markers.");


			return 0;
		}
	}

public void g_marker_move(int x_playerid, int x_piece_pointer)
{
	String x_logstring = "";

	String x_text = "";

	int x_old_position = 0;

	int x_new_location = 0;
	
	Player x_other_player = g_return_other_player();

	x_old_position = g_players[x_playerid].p_pieces[x_piece_pointer].m_get_location();

			
	if (g_dievalue != 0)
	{
	
		x_new_location = g_players[x_playerid].p_pieces[x_piece_pointer].m_calclocation(g_dievalue);

	}

	else

	{
		//Piece has been bumped to the start either due to clash or blow-out
		x_new_location = 1;
	}

	if (x_new_location > 120)
	{
		//blow-out!
		
		x_logstring = "Player " + Integer.toString(x_playerid) + " busted piece " + Integer.toString(x_piece_pointer) + " to a value of " + Integer.toString(x_new_location) + "!";

		g_logmove(x_logstring);

		x_new_location = 1;

	}

	
	g_players[x_playerid].p_pieces[x_piece_pointer].m_setlocation(x_new_location);

	x_logstring = "[[[Player " + Integer.toString(x_playerid)  + " moved piece " + Integer.toString(x_piece_pointer) + " from position " + Integer.toString(x_old_position) + " to " + Integer.toString(g_players[x_playerid].p_pieces[x_piece_pointer].m_get_location())  + "]]]";

	g_logmove(x_logstring);

	//call clash detect unless it has moved to 1

	if (x_new_location == 120)
	{
		//piece has made it to the end and will be disabled

		if (g_players[x_playerid].p_pieces[x_piece_pointer].m_get_status())
		{
			x_text = "ACTIVE";

		}

		else

		{
			x_text = "INACTIVE";

		}

		x_logstring = "Player " + Integer.toString(x_playerid) + "s piece " + Integer.toString(x_piece_pointer) + " has reached position " + Integer.toString(g_players[x_playerid].p_pieces[x_piece_pointer].m_get_location())  + " successfully and is now " + x_text;

		g_logmove(x_logstring);

		}


	if (x_new_location != 1 && x_new_location != 120)
	{

			g_detect_clash(g_players[x_playerid].p_pieces[x_piece_pointer].m_get_location(), x_other_player);
		
	}

	
	g_gameover = g_players[x_playerid].p_check_game_status(g_players[x_playerid]);

	
	}

public boolean g_detect_clash(int x_location, Player x_player) {

	//iterate through opposing players active pieces and reset them if the new move has caused a clash


	int x_counter = 0;
	
	boolean x_return_flag = false;


	for (x_counter = 1; x_counter <= 4; x_counter++) {

		//find an (opposition) player's marker which is on the board but active

		if (x_player.p_pieces[x_counter].m_get_location() == x_location && x_player.p_pieces[x_counter].m_get_status())
		{

			//bump the clash piece

			g_dievalue = 0;

			g_marker_move(x_player.p_get_playerid(), x_counter);

			g_logmove("Player "  + Integer.toString(x_player.p_get_playerid()) + "'s piece " + Integer.toString(x_counter) + " was bumped to the start of the board!");
			
			x_return_flag = true;

		} //if it doesn't find a clash, do nothing

	}

	return x_return_flag;
}

int g_find_to_move_in_play(Player x_player)
{

	
	int[] x_piece_array_pointer = { 0,0,0,0,0 };

	int[] x_piece_array_backup = { 0,0,0,0,0 };

	int x_counter_array = 0;

	int x_counter_loop = 0;

	int x_temp_value = 0;

	int x_counter = 0;

	
	final Set<Integer> x_temp_magic_numbers = new HashSet<Integer>();
	
	x_temp_magic_numbers.addAll( Arrays.asList( 20,24,30,40,60));
	
	
//	integer x_temp_magic_numbers[] = { 20,24,30,40,60 };

	boolean x_test_1 = false;

	boolean x_test_2 = false;

	for (x_counter = 1; x_counter <= 4; x_counter++)
	
		//find a player's marker which is active

		if (x_player.p_pieces[x_counter].m_get_status())

		{
			x_counter_array++;

			x_piece_array_pointer[x_counter_array] = x_counter;

			//x_piece_array_backup[x_counter_array] = x_counter;  //redundant?

		}

			//hopefully won't fail as index 0 is not set  //well...it did.. kes
			System.arraycopy(x_piece_array_pointer, 0, x_piece_array_backup, 0, 5);
	
	

	//If there is one or more, return one at random
	if (x_counter_array > 0)
	{

		g_logmove("Considering between " + Integer.toString(x_counter_array) + " potential piece(s).");


		for (x_counter_loop = 1; x_counter_loop <= x_counter_array; x_counter_loop++)
		{
			x_test_1 = false;

			x_test_2 = false;

			x_temp_value = x_piece_array_pointer[x_counter_loop];

			g_logmove("Step " + Integer.toString(x_counter_loop) + " of " + Integer.toString(x_counter_array) + "..Looking at piece: " + Integer.toString(x_temp_value) + " at location " + Integer.toString(x_player.p_pieces[x_temp_value].m_get_location()));

			
			
			x_test_1 = x_temp_magic_numbers.contains(x_player.p_pieces[x_temp_value].m_get_location());
			
			if (x_test_1)
			{
				g_logmove("Considering ignoring piece " + Integer.toString(x_temp_value) + " as it is on a penultimate number.");
			}

			if ((x_player.p_pieces[x_temp_value].m_get_location() * g_dievalue) > 120)
			{
				x_test_2 = true;

				g_logmove("Considering ignoring piece " + Integer.toString(x_temp_value) + " as it may cause a blowout.");

			}

			if (x_test_1 || x_test_2)
			{
				//one of these is a 'good' number we wish to avoid moving randomly, or could cause a blow-out

				x_piece_array_backup[x_counter_loop] = 999;
			}

		}

		//find the number of pieces in the backup array
		x_counter = 0;

			for(x_counter_loop = 1; x_counter_loop <= 4; x_counter_loop++)
			{
				if (x_piece_array_backup[x_counter_loop] != 999 && x_piece_array_backup[x_counter_loop] > 0)
				{
					x_counter++;
				}
			}




			//pick a pointer at random from the remainder    
			if (x_counter == 0)
			{
				x_temp_value = 0;

				g_logmove("Ignored too many...reverting."); //must choose a random from the remaining 'good' pointers 

				for (x_counter_loop = 1; x_counter_loop <= 4; x_counter_loop++)
				
				{
					if (x_piece_array_pointer[x_counter_loop] != 999 && x_piece_array_pointer[x_counter_loop] > 0)
					{
						x_temp_value++;
					}
				}

				g_logmove("Player " + Integer.toString(x_player.p_get_playerid()) + " has " + Integer.toString(x_temp_value) + " possible piece(s) to move which are on the board.");


				x_test_1 = true;

				while (x_test_1)
				{
					x_counter_array = g_return_random(4);

					if (x_piece_array_pointer[x_counter_array] > 0 && x_piece_array_pointer[x_counter_array] != 999)
					{
						x_test_1 = false; //found one to move
					}

				}

			}

			else

			{  //pick one from the backup array to use
				x_temp_value = 0;

				for (x_counter_loop = 1; x_counter_loop <= 4; x_counter_loop++)

				{
					if (x_piece_array_backup[x_counter_loop] != 999 && x_piece_array_backup[x_counter_loop] > 0)
					{
						x_temp_value++;
					}
				}

				g_logmove("Choosing one from the remaining markers..."); //must choose a random from the remaining 'good' pointers 

				g_logmove("Player " + Integer.toString(x_player.p_get_playerid()) + " has " + Integer.toString(x_temp_value) + " possible piece(s) to move which are on the board.");

				x_test_1 = true;

				while (x_test_1)
				{
					x_counter_array = g_return_random(4);

					if (x_piece_array_pointer[x_counter_array] > 0 && x_piece_array_pointer[x_counter_array] != 999)
					{
						x_test_1 = false; //found one to move
					}

				}

			}


		
		g_logmove("Player " + Integer.toString(x_player.p_get_playerid())  + " has chose to move " + Integer.toString(x_piece_array_pointer[x_counter_array]));

	}


	else

	{

		//there were no markers 'on the board'...return an empty pointer;
		//this is captured by the calling function and acted upon        

		return 0;
	}

	return(x_piece_array_pointer[x_counter_array]);

} //end of function



//core calling function from controlling class
	@SuppressWarnings("unused")
	public boolean g_player_action(int x_playerid) {

		//properties
		boolean x_result = false;

		String x_test;

		String x_temp_return;
				
		int x_temp_magic_numbers[] = { 0, 20, 24, 30, 40, 60, 120 };

		int x_temp_factor_numbers[] = { 0, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 25, 50 };

		String x_logstring = "";

		g_dievalue = g_diceroll();

		x_logstring = "[[[Player " + Integer.toString(x_playerid) + " rolled a " + Integer.toString(g_dievalue) + " ]]]";

		g_logmove(x_logstring);



		if (g_dievalue == 1) {

			g_logmove("Player " + Integer.toString(x_playerid)  + " has to forfeit their move!");

		}
		else
		{

			//SET A

			x_result = g_target_magic_numbers(x_playerid, x_temp_magic_numbers, "penultimate",6);
				
			if (x_result)
			{
				x_temp_return = "TRUE";
			}
			else {
				x_temp_return = "FALSE";
			}

			x_temp_return = "";



			//SET B
			if (!x_result)
			{
				g_logmove("Player " + Integer.toString(x_playerid) + " did not find any penultimate targets.");


					x_result = g_target_magic_numbers(x_playerid, x_temp_factor_numbers, "factor", 12);
				

				if (x_result)
				{
					x_temp_return = "TRUE";
				}
				else { x_temp_return = "FALSE";
				}

				x_temp_return = ""; //BUG??


			}

			//SET C
			if (!x_result)
			{
				//call function C here, return TRUE if it triggers successfully

				g_logmove("Player " + Integer.toString(x_playerid) + " did not find any factor targets.");

				x_result = g_target_potential_clashes_set_C(g_players[x_playerid]);


				if (x_result)
				{
					x_temp_return = "TRUE";
				}
				else {
					x_temp_return = "FALSE";
				}


				x_temp_return = "";

			}

			//SET D
			if (!x_result)
			{

				x_result = g_move_onto_board_set_D(g_players[x_playerid]);

				if (x_result)
				{
					x_temp_return = "TRUE";
				}
				else {
					x_temp_return = "FALSE";
				}

				x_temp_return = "";

			
			}

		}

		if (g_gameover)
		{
			System.out.print("**************************************\n");

			System.out.print("***   Player " + Integer.toString(x_playerid) + " HAS WON THE MATCH! ***\n");

			System.out.print("**************************************\n");

			g_logmove("************************************");

			g_logmove("***   Player " + Integer.toString(x_playerid) + " HAS WON THE MATCH! ***");


			g_logmove("************************************");

		}

		return g_gameover;


	}
	
	
	public	boolean g_target_magic_numbers(int x_playerid, int x_magicnumbers[], String x_type, int x_magic_count)
	{

		//properties
		int[][] x_forecast_pointers = new int[5][3];
		boolean x_found_target = false;
		boolean x_test_count_flag = false;
		int x_piece_pointer = 0;
		int x_counter = 0;
		int x_counter_m = 0;
		int x_counter_test = 0;
		int x_temp_forecast = 0;
				
		//populate current players positions and status to temporary array
		for (x_counter = 1; x_counter <= 4; x_counter++)
		{
			//HERE
			if (!g_players[x_playerid].p_pieces[x_counter].m_get_status())
				
			{
				//this piece is out of play - set up a dummy which will never get hit

				x_forecast_pointers[x_counter][0] = x_counter;

				x_forecast_pointers[x_counter][1] = 999;

				x_forecast_pointers[x_counter][2] = 0;

			}

			else

			{
				//otherwise, put in a forecast of where it would land based on the dice roll

				x_forecast_pointers[x_counter][0] = x_counter;

				x_forecast_pointers[x_counter][1] = g_players[x_playerid].p_pieces[x_counter].m_get_location() * g_dievalue;

				x_forecast_pointers[x_counter][2] = 0;

			}
		}


		//now for each potential location see if there is a match in magic numbers
		for (x_counter_m = 1; x_counter_m <= 4; x_counter_m++)
		{
			x_temp_forecast = x_forecast_pointers[x_counter_m][1];

			for (x_counter = 1; x_counter <= x_magic_count; x_counter++) //iterate through magic numbers to see if there is a match
			{
								
				if (x_magicnumbers[x_counter] == x_temp_forecast)
				{ //found one
					x_forecast_pointers[x_counter_m][2] = 1;
					x_counter = 999;
				
				}

			}

			//clear the array of player's pieces that are not a likely hit
			x_test_count_flag = false;

			for (x_counter_test = 1; x_counter_test <= 4; x_counter_test++)
			{
				if (x_forecast_pointers[x_counter_test][2] == 1)
				{
					//found at least one
					x_test_count_flag = true;

				}

				else

				{
					x_forecast_pointers[x_counter_test][2] = 999;

				}
			}


			//got at least one potential target
			if (x_test_count_flag)
			{

				//now we have an array of only the possible markers to select to target
				//loop until we find one that is not 999

				while (!x_found_target)
				{
					x_counter = g_return_random(4);

					if (x_forecast_pointers[x_counter][2] != 999)
					{
						//the piece we choose to move
						x_piece_pointer = x_forecast_pointers[x_counter][0];

						g_logmove("Player " + Integer.toString(x_playerid) + " is targeting " + x_type + " number " + Integer.toString(x_forecast_pointers[x_counter][1]) + " with piece " + Integer.toString(x_piece_pointer));

						g_marker_move(x_playerid,  x_piece_pointer);
						//here??

						x_found_target = true;
					

					}
				}
			}

			else

			{
				x_found_target = false;

			}


		} //END OF SET B

		return x_found_target;
	}
	
	
	public boolean g_target_potential_clashes_set_C(Player x_player) {

		//properties
		int[][] x_forecast_pointers = new int[5][3];
		boolean x_found_target = false;
		boolean x_test_count_flag = false;
		int x_piece_pointer = 0;
		int x_temp_branch = 2;
		int x_counter = 0;
		int x_counter_o = 0;
		int x_counter_p = 0;
		int x_counter_test = 0;
		int x_temp_opp_location = 0;
		Player x_temp_opp = g_return_other_player();
		boolean x_offboard_flag = false;
		boolean x_onboard_flag = false;


		//populate current players positions and status to temporary array
		for (x_counter = 1; x_counter <= 4; x_counter++)
		{

			if (!x_player.p_pieces[x_counter].m_get_status())
			{
				//this piece is out of play - set up a dummy which will never get hit

				x_forecast_pointers[x_counter][0] = x_counter;

				x_forecast_pointers[x_counter][1] = 999;

				x_forecast_pointers[x_counter][2] = 0;


			}

			else

			{
				//otherwise, put in a forecast of where it would land based on the dice roll

				x_forecast_pointers[x_counter][0] = x_counter;

				x_forecast_pointers[x_counter][1] = x_player.p_pieces[x_counter].m_get_location() * g_dievalue;

				x_forecast_pointers[x_counter][2] = 0;

			}
		}


		//now for each potential location see if there is a match in the opponent's pieces

		for (x_counter_o = 1; x_counter_o <= 4; x_counter_o++)
		{

			if (!x_temp_opp.p_pieces[x_counter_o].m_get_status())

			{
				//opp position is out of play and should be ignored -  dummy value
				g_logmove("Ignoring target piece " + Integer.toString(x_counter_o) + " as it is out of play.");

				x_temp_opp_location = 888;
			}

			else

			{
				//hold the location of a potential target piece to hit
				x_temp_opp_location = x_temp_opp.p_pieces[x_counter_o].m_get_location();
				
			}

			for (x_counter_p = 1; x_counter_p <= 4; x_counter_p++)
			{
				//check that the locations match and that the opponents piece  is not at the start, or inactive:

				if (x_forecast_pointers[x_counter_p][1] == x_temp_opp_location && x_temp_opp_location != 1 && x_temp_opp_location != 888)

				{
					//we have a potential target
					x_forecast_pointers[x_counter_p][2] = 1;

					g_logmove("Player " + Integer.toString(x_temp_opp.p_get_playerid()) + "'s marker " + Integer.toString(x_counter_o) + " at location " + Integer.toString(x_temp_opp.p_pieces[x_counter_o].m_get_location())  + " is a target of piece " + Integer.toString(x_counter_p));

					//don't even know if this works in c++
					break; //we only need to know this once         

				}

			} //move on to next player piece

		} //move on to next opponent piece


		//clear the array of player's pieces that are not a likely hit
		x_test_count_flag = false;

		for (x_counter_test = 1; x_counter_test <= 4; x_counter_test++)
		{
			if (x_forecast_pointers[x_counter_test][2] == 1)
			{
				//found at least one
				x_test_count_flag = true;

			}

			else

			{
				x_forecast_pointers[x_counter_test][0] = 999;
			}
		}


		//got at least one potential target
		if (x_test_count_flag)
		{
			x_counter = 0;

			while (!x_found_target)
			{
				x_counter = g_return_random(4);

				if (x_forecast_pointers[x_counter][0] != 999)
				{

					x_piece_pointer = x_forecast_pointers[x_counter][0];

					g_logmove("Player " + Integer.toString(x_player.p_get_playerid()) + " has targets to consider and chose to move piece " + Integer.toString(x_piece_pointer));

					g_marker_move(x_player.p_get_playerid(), x_piece_pointer);

					x_found_target = true;

					return x_found_target;
				}
			}
		}

		else

		{
			g_logmove("Player " + Integer.toString(x_player.p_get_playerid()) + " could not find a clash target so is going to find a pointer at random to move.");

			//toss up between on or off board
			x_offboard_flag = false;

			x_onboard_flag = false;

			for (x_counter = 1; x_counter <= 4; x_counter++)
			{
				//loop and see if there is a mix of on-board or off-board marker; do a coin toss if there is
				if (x_player.p_pieces[x_counter].m_get_location() == 1 && x_player.p_pieces[x_counter].m_get_status())
				{
					x_offboard_flag = true;  //we could get a piece off the board
				}

				if (x_player.p_pieces[x_counter].m_get_location() > 1 && x_player.p_pieces[x_counter].m_get_status())
				{
					x_onboard_flag = true;  // we could get a piece on the board
				}

			}

			if (x_onboard_flag)
			{

				if (x_offboard_flag)
				{

					//choice is a wonderful thing - 1 is on-board, 2 is off-board
					x_temp_branch = g_return_random(2);

				}
				else

					x_temp_branch = 1;

			}

			if (x_temp_branch == 1)
			{

				x_piece_pointer = 0;

				g_logmove("Finding a piece on the board.");

				x_piece_pointer = g_find_to_move_in_play(x_player);

				if (x_piece_pointer != 0)
				{
					//found one... move it        

					g_marker_move(x_player.p_get_playerid(), x_piece_pointer);

					x_found_target = true;

				}

				else

				{

					//didn't find one, have to force to go down the SET D path.

					x_temp_branch = 0;

					x_found_target = false;

				}
			}

			if (x_temp_branch == 0)
			{

				x_found_target = false;

				g_logmove("Finding a piece OFF the board using SET D.");
			}


		}
		
		return x_found_target;

	} //END OF SET C


	

} //END OF CLASS
