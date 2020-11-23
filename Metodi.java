	package biblioteca;

import javax.swing.JOptionPane;

public class Metodi {
	
	 public static boolean IsNumber(String s){
		int n=s.length();
		boolean chk=true;
		for(int i=0;i<n;i++){
			if((s.charAt(i)<48) || (s.charAt(i)>57)){
				chk=false;
				JOptionPane.showMessageDialog(null, "Il carattere non e valido");
			}
		}
	
		return(chk);
	}
	 
	 public static boolean IsString(String s){
			int n=s.length();
			boolean chk=true;
			for(int i=0;i<n;i++){
				if((s.toLowerCase().charAt(i)<97) || (s.toLowerCase().charAt(i)>122)){
					chk=false;
					JOptionPane.showMessageDialog(null, "Stringa non valida");
				}
			}
		
			return(chk);
	 }
	

}
