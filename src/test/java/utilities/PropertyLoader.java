package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	
	private static Properties prop;
	
	public PropertyLoader(){
		if(prop==null) {
			try {
				System.out.println("Prop is Loading in Constructor");
				FileReader reader = new FileReader("src//test//resources//Application.properties"); 
					prop = new Properties();
					prop.load(reader);
				}catch(IOException io) {
					System.out.println(io.getMessage());
				}		
		}
			
	}
	
	public String getBookingUserName() {
		return prop.getProperty("booking.username");
	}
	
	public String getBookingPassword() {
		return prop.getProperty("booking.password");
	}
	

}
