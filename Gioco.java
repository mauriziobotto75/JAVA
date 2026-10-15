package dati;

public class Gioco {
	private Giocatore user;
	
	public Gioco( Giocatore user){
		setUser(user);
	}

	public Giocatore getUser() {
		return user;
	}

	public void setUser(Giocatore user) {
		this.user = user;
	}

	public void attendi( Giocatore utente){
		System.out.println(utente.getNazione());
		System.out.println(utente.getPopolazione());
		utente.setPopolazione((int)(utente.getPopolazione()+utente.getPopolazione()*0.1));
		utente.setSoldati((int)(utente.getSoldati()+utente.getSoldati()*0.1));
		utente.setSoldatiDisp((int) (utente.getSoldatiDisp()+utente.getSoldati()*0.1));
		addDenaro(utente);
		addCibo(utente);
	}
	public void addestra(Giocatore utente){
		utente.setSoldati((int)(utente.getSoldati()+utente.getSoldati()*0.3));
		utente.setSoldatiDisp((int) (utente.getSoldatiDisp()+utente.getSoldati()*0.3));
		addDenaro(utente);
		addCibo(utente);
	}
	public void incrementa(Giocatore utente){
		utente.setPopolazione((int)(utente.getPopolazione()+utente.getPopolazione()*0.3));
		addDenaro(utente);
		addCibo(utente);
	}
//	public void attacca(){
//		
//	}
	public void addDenaro(Giocatore utente){
		utente.setDenaro((int)(utente.getDenaro()+(utente.getPopolazione()*0.1)-(utente.getSoldati()*0.1)));
	}
	public void addCibo(Giocatore utente){
		utente.setCibo((int)(utente.getCibo()+(utente.getPopolazione()*0.1)-(utente.getSoldati()*0.1)));
	}
	
	public void setSoldati(Giocatore utente, int numeroTot){
		utente.setSoldatiDisp((int)(utente.getSoldatiDisp())-numeroTot);
	}
	
	public String attacca(Giocatore utente1, Giocatore utente2, int num, String cittaavv, String cittamia){
		boolean condizione=true;
		boolean condizione2=true;
		int index=-1;
		int indice=-1;
		String parte[]=cittaavv.split(",");
		String citta=parte[0];
		String pars[]=cittamia.split(",");
		String citt=pars[0];
		System.out.println("citta: "+citta);
		
		for(int i=0; i<utente2.arrayL.size()&&condizione; i++){
			System.out.println(utente2.arrayL.get(i).getNome());
			if (utente2.arrayL.get(i).getNome().equals(citta))
				{
				index=i;
				condizione=false;
				System.out.println(index);
				}
			}
		for(int i=0; i<utente1.arrayL.size()&&condizione2; i++){
			System.out.println(utente1.arrayL.get(i).getNome());
			if (utente1.arrayL.get(i).getNome().equals(citt))
				{
				indice=i;
				condizione2=false;
				System.out.println(index);
				}
			}
	
		System.out.println("test");
		System.out.println(num);
		System.out.println(utente1.arrayL.get(indice).getNsoldati());
		System.out.println("fine test");
		if(utente1.arrayL.get(indice).getNsoldati()<num)
			return "pirla";
			else{
	System.out.println("indice "+index);
	int soldati1=num;
	int soldati2=utente2.arrayL.get(index).getNsoldati();
	System.out.println(soldati1);
	System.out.println(soldati2);

	while(soldati1>0 && soldati2>0){
	int dado1=(int) (Math.random()*10);
	int dado2=(int) (Math.random()*10);
	if (dado1>dado2)
		soldati2=soldati2-10;
	else
		soldati1=soldati1-10;
	
	System.out.println("giocatore1= "+soldati1);
	System.out.println("giocatore2= "+soldati2);
	}
	
	System.out.println(soldati1);
	System.out.println(soldati2);

	if (soldati1==0){
		utente1.arrayL.get(indice).setNsoldati(utente1.arrayL.get(indice).getNsoldati()-num);
		utente2.setSoldati(utente2.getSoldati()-utente2.arrayL.get(index).getNsoldati()+soldati2);
		utente2.arrayL.get(index).setNsoldati(soldati2);
		utente1.setSoldati(utente1.getSoldati()-num);
		System.out.println("giocatore1 sconfitto"); 
		return "sconfitta";
	}
	else
	{
		Object temp=utente2.arrayL.get(index);
		utente1.arrayL.add(utente2.arrayL.get(index));
		int j = utente1.arrayL.indexOf(temp);
		utente1.arrayL.get(j).setNsoldati(soldati1);
		utente2.arrayL.remove(utente2.arrayL.get(index));
		utente1.arrayL.get(indice).setNsoldati(utente1.arrayL.get(indice).getNsoldati()-num);
		utente1.setSoldati(utente1.getSoldati()-num+soldati1);
		utente2.setSoldati(utente2.getSoldati()-utente2.arrayL.get(index).getNsoldati());
		utente1.setPopolazione(utente1.getPopolazione()+utente1.arrayL.get(j).getPopolazione());
		utente2.setPopolazione(utente2.getPopolazione()-utente1.arrayL.get(j).getPopolazione());
		return "vittoria";
	}

	}
	}
}
