package it.univaq.rtv.Model.FactoryMappa;


import it.univaq.rtv.Model.*;
import it.univaq.rtv.Model.FactoryCitta.ICitta;
import it.univaq.rtv.Model.FactoryMezzo.Vagone;
import it.univaq.rtv.Utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public abstract class AbstractMappa {
    protected String nome;
    protected ArrayList<Percorso> p=new ArrayList<Percorso>();
    protected abstract ArrayList<ICitta> CreaMappa() throws IOException;


    public String getNome() {
        return this.nome;
    }


    public void AddPercorso(Percorso p1){
        this.p.add(p1);
    }



    public ArrayList<Percorso> DammiPercorsi() {

        return this.p;

    }


    /**
     *
     * @param Giocatori
     */
    public void PopolaMappa(ArrayList<Giocatore> giocatores) {
        for(int i=0; i<giocatores.size();i++){
            Giocatore g=giocatores.get(i);
            FactorMezzo factory= new FactorMezzo();
            Vagone v=(Vagone) factory.getMezzo("Vagone", g);
            CartaPercorso c1=g.ChiediCartaPercorso();
            for(int j=0;j<this.p.size();j++){
                Percorso p1=p.get(j);
                p1.TrovaPercorso(c1,factory,g);
            }
        }
    }


    public ArrayList<ICitta> getCitta(){
        ArrayList<ICitta> c=new ArrayList<ICitta>();
        for(int i=0; i<this.p.size();i++) {
            Percorso percorso = p.get(i);
            ICitta citta1=percorso.getCittapartenza();
            ICitta citta2=percorso.getCittaArrivo();
            c.add(citta1);
            c.add(citta2);
        }
        java.util.Set setta_citta=new HashSet(c);
        ArrayList<ICitta> c1=new ArrayList<ICitta>(setta_citta);
        return c1;

    }


    public ArrayList<Percorso> getViciniPercorsoPartenza(Percorso percorso){
        ArrayList<Percorso> percorsos=new ArrayList<>();
        for (int a=0; a < this.DammiPercorsi().size(); a++) {
            Percorso per1 = this.DammiPercorsi().get(a);
            if(percorso.getid()==per1.getid());
            else if ( percorso.getCittapartenza().getNome().equals(per1.getCittapartenza().getNome())
                    ||percorso.getCittapartenza().getNome().equals(per1.getCittaArrivo().getNome())
            ){
                percorsos.add(per1);

            }

        }
        return percorsos;
    }

    public ArrayList<Percorso> getViciniPercorsoArrivo(Percorso percorso){
        ArrayList<Percorso> percorsos=new ArrayList<>();
        for (int a=0; a < this.DammiPercorsi().size(); a++) {
            Percorso per1 = this.DammiPercorsi().get(a);
            if(percorso.getid()==per1.getid());
            else if (  percorso.getCittaArrivo().getNome().equals(per1.getCittaArrivo().getNome())
                    ||percorso.getCittaArrivo().getNome().equals(per1.getCittapartenza().getNome())
            ){
                percorsos.add(per1);

            }

        }
        return percorsos;
    }

    public ArrayList<Casella> getCaselleVicinePercorsi(ArrayList<Percorso> p, Casella c){
        ArrayList<Casella> casellas= new ArrayList<>();
        for(int i=0; i<p.size();i++)
            casellas.add(p.get(i).getCasellaPerVicino(c));
        return casellas;

    }





    public Percorso getPercorsoByCasella(Casella c){
        ArrayList<Percorso> percorsi = this.DammiPercorsi();
        boolean esci=false;
        int i;
        ArrayList<Casella> caselle = new ArrayList<>();
        for (i=0; i <percorsi.size();i++){
            caselle= percorsi.get(i).getCaselle();
            for(int j=0;j<caselle.size();j++) {
                if (c.getId() ==caselle.get(j).getId()) {
                    esci=true;
                    break;}
            }
            if(esci==true) break;
        }
        return percorsi.get(i);

    }

    public ArrayList<Percorso> RimuoviDuplicati(ArrayList<Percorso> percorsi) {
        ArrayList<Percorso> percorso_no_s = percorsi;
        for (int i = 0; i < percorsi.size(); i++) {
            Percorso percorso = percorsi.get(i);
            for (int j = 0; j < percorsi.size(); j++) {
                Percorso percorso1 = percorsi.get(j);
                if (percorso.getid() != percorso1.getid()) {
                    if (Utility.EqualsPartenza(percorso1,percorso)) {
                        percorso1.removeCasella(percorso1.getCasellaPartenza());
                        percorso_no_s.remove(percorso1);
                        percorso_no_s.add(percorso1);
                    } else if (Utility.EqualsPartenzaArrivo(percorso1,percorso)) {
                        percorso1.removeCasella(percorso1.getCasellaPartenza());
                        percorso_no_s.remove(percorso1);
                        percorso_no_s.add(percorso1);
                    } else if (Utility.EqualsArrivoPartenza(percorso1,percorso)) {
                        percorso1.removeCasella(percorso1.getCasellaArrivo());
                        percorso_no_s.remove(percorso1);
                        percorso_no_s.add(percorso1);
                    } else if (Utility.EqualsArrivo(percorso1,percorso)) {
                        percorso1.removeCasella(percorso1.getCasellaArrivo());
                        percorso_no_s.remove(percorso1);
                        percorso_no_s.add(percorso1);
                    }

                }
            }
        }
        return percorso_no_s;

    }

    /*
    public LatLong CalcolaCentro(){
        ArrayList<ICitta> cittas=new ArrayList<ICitta>();
        cittas=this.getCitta();

        LatLong l=null;
        double inizioLat=cittas.get(0).getCoordinate().getLatitude();
        double inizioLong=cittas.get(0).getCoordinate().getLongitude();
        double lat_min=inizioLat,lat_max=inizioLat,long_min=inizioLong, long_max=inizioLong, lat, longi;

        for (int i=1; i<cittas.size();i++){
            if(cittas.get(i).getCoordinate().getLatitude()>lat_max) lat_max=cittas.get(i).getCoordinate().getLatitude();
            if(cittas.get(i).getCoordinate().getLatitude()<lat_min) lat_min=cittas.get(i).getCoordinate().getLatitude();
            if(cittas.get(i).getCoordinate().getLongitude()>long_max) long_max=cittas.get(i).getCoordinate().getLongitude();
            if(cittas.get(i).getCoordinate().getLongitude()<long_min) long_min=cittas.get(i).getCoordinate().getLongitude();
        }
        lat=(lat_max+lat_min)/2;
        longi=(long_max+long_min)/2;
        l=new LatLong(lat,longi);
        return l;
    }*/


}
