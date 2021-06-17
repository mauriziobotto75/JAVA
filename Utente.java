
import java.sql.Date;

 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maurizio
 * 
 */
 

public class Utente
{
    String userName;
    String password;
    String nome;
    String cognome;
    String eMail;
    Date dataUltimoAccesso; 
           public Utente(String userName, String password, String nome, String cognome, String email, Date dataUltimoAccesso)
                 {  
                     this.userName = userName;
                     this.password = password;
                     this.cognome = cognome;
                     this.nome = nome;
                     this.eMail = email;
                     this.dataUltimoAccesso = dataUltimoAccesso;
                  }
           private String getNome(String nome)
                       {  
                           return(nome);
                       } 
           private String getCognome(String cognome)
                       {  
                           return(cognome);
                       } 
           private String getPassword(String password)
                       {  
                           return(password);
                       } 
           
               private String geteMail(String eMail)
                       {  
                           return(eMail);
                       }      
                private String getUserName(String UserName)
                       {  
                           return(UserName);
                       }  
                 public Date getDataUltimoAccesso (Date dataUltimoAccesso)
                       {  
                           return(dataUltimoAccesso);
                       } 
              private String setNome(String nome)
                       {  
                            this.nome = nome;
                            return null;
                       } 
           private String setCognome(String cognome)
                       {  
                           this.cognome = cognome;
                           return null;
                       } 
           private String setPassword(String password)
                       {  
                           this.password = password;
                           return null;
                       } 
           
               private String seteMail(String eMail)
                       { 
                           this.eMail = eMail;
                           return(eMail);
                       }      
                private String setUserName(String UserName)
                       {  
                           this.userName = userName;
                           return(UserName);
                       }      
                 private Date setDataUltimoAccesso (Date dataUltimoAccesso)
                       {  
                           this.dataUltimoAccesso  = dataUltimoAccesso;
                 
                           return(dataUltimoAccesso);
                       } 

  
    }
    

