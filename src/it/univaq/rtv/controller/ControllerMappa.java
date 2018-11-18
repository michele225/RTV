package it.univaq.rtv.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import it.univaq.rtv.Model.*;
import it.univaq.rtv.Model.FacadePartita;
import it.univaq.rtv.Utility.Utility;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMappa {



    private GoogleMapView googleMapView;
    private GoogleMap map;
    private Label CartaObiettivo;
    private Label CartaPercorsoPartenza;
    private Label CartaPercorsoArrivo;
    private Label GiocatoreName;
    private Label FinePartita;
    private Button TurnoButton;
    private Label NumeroMezzo;

    public ControllerMappa(GoogleMapView googleMapView, Label CartaObiettivo, Label CartapercorsoPartenza, Label CartapercorsoArrivo, Label GiocatoreName, Button TurnoButton, Label NumeroMezzo, Label FinePartita) {
        this.googleMapView = googleMapView;
        this.GiocatoreName = GiocatoreName;
        this.CartaObiettivo = CartaObiettivo;
        this.CartaPercorsoArrivo = CartapercorsoArrivo;
        this.CartaPercorsoPartenza = CartapercorsoPartenza;
        this.TurnoButton = TurnoButton;
        this.FinePartita = FinePartita;
        this.NumeroMezzo = NumeroMezzo;

    }


    public boolean ControlloObiettivo(){
        if (this.getPartita().getGiocatori().get(0).getObiettivo()) {
           return true;
        }
        else return false;
    }



    public boolean ControlloArrivo() {
        if (this.getPartita().getGiocatori().get(0).getArrivo()) {
            return true;
            }
        else return false;
        }



    public boolean ControlloFine( ){
            if (this.ControlloObiettivo() && this.ControlloArrivo()) {
                return true;
        }
        else return false;

    }

    public String getColoreGiocatore(int i){
        return this.getPartita().getGiocatori().get(i).getColor();
    }

    public String getUsername(int i){
        return this.getPartita().getGiocatori().get(i).getUsername();
    }


    public String getNomeCPCittaPartenza(int gioc){
        return this.getPartita().getGiocatori().get(gioc).ChiediCartaPercorso().getCittaPartenza().getNome();

    }

    public String getNomeCPCittaArrivo(int gioc){
        return this.getPartita().getGiocatori().get(gioc).ChiediCartaPercorso().getCittaArrivo().getNome();

    }

    public String getNomeCittaObiettivo(int gioc){
        return this.getPartita().getGiocatori().get(gioc).ChiediCartaObiettivo().getCittaObiettivo().getNome();

    }

    public void OccupaCittaPartenza(int i){
        this.getPartita().getGiocatori().get(i).ChiediCartaPercorso().getCittaPartenza().setOccupata(true);
    }

    public void setMezzoGioc(int i, int j, String mezzo){
        this.getPartita().setIMezzoGioc(i,j,mezzo);
    }


    public int getNumGiocatori(){
        return this.getPartita().getGiocatori().size();
    }

    public int getNumMezzi(int gioc){
        return this.getPartita().getGiocatori().get(gioc).getMezzi().size();
    }

    /*
    public LatLong getCoordinateCentroMappa(String mappa){
        return FacadePartita.getIstance().AvviaPartita(mappa,FacadePartita.getIstance().getGiocatori()).CalcolaCentro();
    }*/

    public int getNumPercorsiMappa(){
        return this.getPartita().getMappa().DammiPercorsi().size();
    }

    public int getNumCasellePercorso(int percorso){
        return this.getPartita().getMappa().DammiPercorsi().get(percorso).getCaselle().size();
    }

    public boolean IsPartenza(int gioc, int percorso, int casella){
        return Utility.IsPartenza(this.getPartita().getGiocatori().get(gioc),this.getPartita().getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella));
    }

    public LatLong getInizioCasella(int percorso,int casella){
        return this.getPartita().getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella).getInizio();
    }

    public LatLong getFineCasella(int percorso,int casella){
        return this.getPartita().getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella).getFine();
    }



    public void setMezzo(int gioc,int i){
        this.getPartita().getGiocatori().get(gioc).setMezzo(i);
    }

    public void PosizionaMezzoGioc(int gioc, int percorso, int casella){
        this.getPartita().getGiocatori().get(gioc).PosizionaMezzo(FacadePartita.getIstance().getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella));
    }

    public void setMossaGioc(int gioc, int percorso, int casella){
        this.getPartita().getGiocatori().get(gioc).setMossa(FacadePartita.getIstance().getMappa().DammiPercorsi().get(percorso).CalcolaCasellaVicina(FacadePartita.getIstance().getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella)));
    }

    public FacadePartita getPartita(){
        return FacadePartita.getIstance();
    }

    public void setMosseGioc(int gioc,int percorso, int casella){
        this.getPartita().getGiocatori().get(gioc).setMosse(FacadePartita.getIstance().CaselleVicini(percorso,casella));
    }

    public ArrayList<Percorso> Duplicati(){
        return this.getPartita().getMappa().RimuoviDuplicati(FacadePartita.getIstance().getMappa().DammiPercorsi());
    }

    public boolean PosizionaMezzoPartita(Polyline finalPolyline1, PolylineOptions polylineOptions,int finalI,ArrayList<Casella> caselle) throws IOException {
        return this.getPartita().PosizionaMezzo(finalPolyline1, polylineOptions,finalI,caselle);
    }
}

