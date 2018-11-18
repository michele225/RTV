package it.univaq.rtv.Model.FactoryCitta;

import com.lynden.gmapsfx.javascript.object.LatLong;
import it.univaq.rtv.Model.FactorMezzo;
import it.univaq.rtv.Model.FactoryMezzo.IMezzo;
import it.univaq.rtv.Model.Giocatore;

public class Normale implements ICitta {
    private String Nome;
    private LatLong coordinate;
    private boolean occupata;
    private IMezzo IMezzo =null;
    public Normale() {
        this.Nome="";
        this.coordinate=null;
        this.occupata=false;
    }
    public Normale(String Nome){
        this.Nome=Nome;
        this.coordinate= null;
        this.occupata= false;

    }
    @Override
    public String getNome(){
        return this.Nome;
    }
    @Override
    public void setOccupata(boolean occupata) {
        this.occupata = occupata;
    }
    @Override
    public boolean getOccupata(){return this.occupata;}
    @Override
    public void ImpostaCoordinate(LatLong l){
        this.coordinate=l;
    }
    @Override
    public void PosizionaGiocatore(FactorMezzo Mezzo, Giocatore g) {
        this.IMezzo =Mezzo.getMezzo("Vagone", g);
    }
    @Override
    public IMezzo getIMezzo(){
        return this.IMezzo;
    }
    @Override
    public LatLong getCoordinate() {
        return coordinate;
    }
    @Override
    public void setIMezzo(IMezzo IMezzo) {
        this.IMezzo = IMezzo;
    }
}