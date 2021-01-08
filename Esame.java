package com.siu.component;


public class Esame {
       String Materia;
       Date Data;
       Int Voto;
       public Esame()
	  {}
       String getMateria()
       {
           return Materia.toString();
       }
       Date getData()
          {
    	   return Date.Parse(Data);
      	   }
       Int getVoto()
       {
    	   return Voto;
      	   }
		// TODO Auto-generated method stub
       int setMateria()
       {
    	   this.Materia = Materia;
    	   return(Materia);
           
       }
       Date setData()
          {
    	   this.Data =Data;
    	   return(Date.Parse(Data));
      	   }
       int setVoto()
       {
    	   this.Voto = Voto;
    	   return(Voto);
      	   }
	}
