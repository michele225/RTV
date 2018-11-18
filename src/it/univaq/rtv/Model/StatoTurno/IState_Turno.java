package it.univaq.rtv.Model.StatoTurno;




import it.univaq.rtv.Model.Giocatore;
import it.univaq.rtv.Model.StatoGiocatore.IStato_Giocatore;
import it.univaq.rtv.Model.Turno;

import java.util.ArrayList;


public interface IState_Turno {

	ArrayList<Giocatore> InizioTurno(ArrayList<Giocatore> g, String nomeMappa, Turno t, IStato_Giocatore gioca) throws Exception;
	void Fineturno(Giocatore g);
	ArrayList<Giocatore> OrdinaGiocatori(ArrayList<Giocatore> g);


}