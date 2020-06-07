package Auto120;
/**
 * 
 */

/**
 * @author The Lloyd Family
 *
 */

 

public class Marker {

	
	int m_owner;
	
	int m_location;
	
	
	public Marker() {
		// null marker constructor
	}

	public Marker(int x_id) {
		// null marker constructor
		
		m_owner = x_id;
		
		m_location = 1;
		
	}

	public void m_assignowner (int x_owner)
	{
		m_owner = x_owner;
	}
	
	public int m_calclocation(int x_diceroll)
	{
		int x_calcvalue = m_location * x_diceroll;
		
		return x_calcvalue;
		
	}
	
	public void m_setlocation(int x_newlocation)
	{
		m_location = x_newlocation;
			
	}
	
	public int m_get_location() {

		return m_location;

	}
	
	public boolean m_get_status() {

		if (m_location == 120)
		{
			return false;
		}
		else {
			return true;
		}


	}
	
}
	
	

