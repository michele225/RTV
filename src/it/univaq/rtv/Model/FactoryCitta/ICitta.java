package it.univaq.rtv.Model.FactoryCitta;

import com.lynden.gmapsfx.javascript.object.LatLong;
import it.univaq.rtv.Model.FactorMezzo;
import it.univaq.rtv.Model.FactoryMezzo.IMezzo;
import it.univaq.rtv.Model.Giocatore;


public interface ICitta {

	public String getNome();

	public void setOccupata(boolean occupata);

	public boolean getOccupata();

	public void ImpostaCoordinate(LatLong l);

	public void PosizionaGiocatore(FactorMezzo Mezzo, Giocatore g);

	public IMezzo getIMezzo();

	public LatLong getCoordinate();

	public void setIMezzo(IMezzo IMezzo);
}