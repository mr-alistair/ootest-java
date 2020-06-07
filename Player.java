package Auto120;



public class Player {

	
	//properties
		int p_playerid;
		Marker[] p_pieces = new Marker[5];

		
		
	public Player() {
		//default empty constructor
	}
	
	public Player(int x_id)
	{

		int x_counter = 0;

		p_playerid = x_id;

		for (x_counter = 1; x_counter <= 4; x_counter++)
		{
			
			p_pieces[x_counter] = new Marker(x_id);

			
		}

	}

	
	public Marker p_assignpiece(int x_player)
	{

		Marker x_marker = new Marker(x_player);
		
		return x_marker;
			
	}
	
	public int p_get_playerid()
	{
		return p_playerid;
	}
	
	public boolean p_check_game_status(Player x_player) {

		int x_counter = 0;

		boolean x_game_over = true;
				
		for (x_counter = 1; x_counter <= 4; x_counter++)
		{
			
			if (x_player.p_pieces[x_counter].m_get_location() != 120)
			{
				
				x_game_over = false;
				
			}
		
		}

		return x_game_over;
	}

}
