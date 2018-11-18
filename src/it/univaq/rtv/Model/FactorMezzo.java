package it.univaq.rtv.Model;


import it.univaq.rtv.Model.FactoryMezzo.Aereo;
import it.univaq.rtv.Model.FactoryMezzo.IMezzo;
import it.univaq.rtv.Model.FactoryMezzo.Nave;
import it.univaq.rtv.Model.FactoryMezzo.Vagone;

public class FactorMezzo {

	public static IMezzo getMezzo(String criteria, Giocatore giocatore )
	{
		if ( criteria.equals("Vagone") )
			return new Vagone(giocatore);
		else if ( criteria.equals("Aereo") )
			return new Aereo(giocatore);
		else if ( criteria.equals("Nave") )
			return new Nave(giocatore);
		else
			return null;
	}

}