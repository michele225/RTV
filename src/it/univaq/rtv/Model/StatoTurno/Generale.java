package it.univaq.rtv.Model.StatoTurno;




import it.univaq.rtv.Model.Giocatore;
import it.univaq.rtv.Model.StatoGiocatore.Attesa;
import it.univaq.rtv.Model.StatoGiocatore.IStato_Giocatore;
import it.univaq.rtv.Model.Turno;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Generale extends Thread implements IState_Turno {
	private Giocatore giocatore;


	@Override
	public ArrayList<Giocatore> InizioTurno(ArrayList<Giocatore> g, String NomeMappa, Turno t, IStato_Giocatore gioca) throws Exception {
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{

			}

		};
		g.get(0).setState(gioca);
		t.setState(this);
		this.giocatore=g.get(0);
		return g;//The task you want to do
	}
	@Override
	public void Fineturno(Giocatore g) {
	 	Attesa attesa=new Attesa();
	 	g.setState(attesa);
	}
	public void countTime() throws InterruptedException {
		this.start();
		this.sleep(600);

	}
	@Override
	public  ArrayList<Giocatore> OrdinaGiocatori(ArrayList<Giocatore> g){
		return g;
	}
	public void interrupt(){
		this.interrupt();
	}

}