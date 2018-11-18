package it.univaq.rtv.Model.StatoTurno;

import it.univaq.rtv.Model.*;
import it.univaq.rtv.Model.FactoryMappa.AbstractMappa;
import it.univaq.rtv.Model.StatoGiocatore.IStato_Giocatore;
import it.univaq.rtv.Utility.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Iniziale implements IState_Turno {
	private AbstractMappa mappa;
	@Override
	public ArrayList<Giocatore> InizioTurno(ArrayList<Giocatore> g, String nomeMappa, Turno t, IStato_Giocatore gioca)throws FileNotFoundException,IOException{

		this.mappa= FactorMappa.getMappa(nomeMappa);
		SingletonMazzoPercorso.getIstance(mappa.DammiPercorsi());
		SingletonMazzoObiettivo.getIstance(mappa.getCitta());
		ArrayList<String> CittaUsate= new ArrayList<>();
		for(int i=0;i<g.size();i++){
			g.get(i).PescaDueCarte();
			for(int c=0;c<CittaUsate.size();c++){
				while(Utility.EqualsCitta(g.get(i),CittaUsate.get(c))){
					g.get(i).RestituisciCarte();
					g.get(i).PescaDueCarte();
					c=-1;
					break;
				}

			}
			CittaUsate.add(g.get(i).ChiediCartaObiettivo().getCittaObiettivo().getNome());
			CittaUsate.add(g.get(i).ChiediCartaPercorso().getCittaPartenza().getNome());
			CittaUsate.add(g.get(i).ChiediCartaPercorso().getCittaArrivo().getNome());

		}
		mappa.PopolaMappa(g);
		t.setState(this);
		return g;

	}
	@Override
	public void Fineturno(Giocatore g) {
		return;
	}
	@Override
	public ArrayList<Giocatore> OrdinaGiocatori(ArrayList<Giocatore> g) {
		Collections.sort(g);
		return g;
	}

	public AbstractMappa getMappa() {
		return mappa;
	}
}