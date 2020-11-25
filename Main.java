import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AddressBook quaderno = new AddressBook();
		
		quaderno.addWindowStateListener( 
				new WindowAdapter() 	{
				public void WindowClosing( WindowEvent e )
				{	 	 
	  	
				System.exit(0);
		      }
		}
		);
	}

}