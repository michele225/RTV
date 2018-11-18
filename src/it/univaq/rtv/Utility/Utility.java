package it.univaq.rtv.Utility;


import com.lynden.gmapsfx.javascript.object.LatLong;
import it.univaq.rtv.Model.Casella;
import it.univaq.rtv.Model.FacadePartita;
import it.univaq.rtv.Model.FactoryCitta.ICitta;
import it.univaq.rtv.Model.Giocatore;
import it.univaq.rtv.Model.Percorso;

import java.util.ArrayList;
import java.util.Random;


//Classe di utilità per funzioni di supporto al gioco RTV
public class Utility {
    public static ArrayList<String> colori= new ArrayList<String>() {{
        add("red");
        add("aqua");
        add("orange");
        add("pink");
        add("teal");
        add("white");
    }};

    public static String ColorToRgba(String colore){
        String c = null;
        if(colore=="red")  c="rgba(255,0,0,1)";
        else if(colore=="aqua")  c="rgba(0,255,255,1)";
        else if(colore=="orange")  c="rgba(255,165,0,1)";
        else if(colore=="pink")  c="rgba(255,192,203,1)";
        else if(colore=="green") c="rgba(45,255,13,1)";
        else if(colore=="white") c="rgba(255,255,255,1)";
        else if(colore=="teal") c="rgba(0,128,128,1)";
        else if(colore=="black") c="rgba(0,0,0,1)";
        return c;
    }
    public static String Colori(){

        Random num= new Random();
        String colorescelto;
        int n=num.nextInt(colori.size());
        colorescelto=colori.get(n);
        colori.remove(colori.get(n));
        return colorescelto;

    }
    public static int StringtoInteger(String s){
        if(s.equals("Uno")) return 1;
        else if(s.equals("Due")) return 2;
        else if(s.equals("Tre")) return 3;
        else if(s.equals("Quattro")) return 4;
        else if(s.equals("Cinque")) return 5;
        else if(s.equals("Sei")) return 6;
        else if(s.equals("Sette")) return 7;
        else if(s.equals("Otto")) return 8;
        else if(s.equals("Nove")) return 9;
        else return 0;


    }
    public static boolean IsPartenza(Giocatore giocatore, Casella casella){
        if(Math.abs(giocatore.ChiediCartaPercorso().getCittaPartenza().getCoordinate().getLatitude() - casella.getInizio().getLatitude())<0.5 && Math.abs(giocatore.ChiediCartaPercorso().getCittaPartenza().getCoordinate().getLongitude() - casella.getInizio().getLongitude())<0.5) return true;
        else return false;
    }
    public static boolean EqualsIdCasella(Casella casella1, Casella casella2){
        if(casella1.getId()==casella2.getId())return true;
        else return false;
    }
    public static boolean EqualsCitta(Giocatore gioc, String cittausate ){
        if(gioc.ChiediCartaObiettivo().getCittaObiettivo().getNome().equals(cittausate)||gioc.ChiediCartaPercorso().getCittaPartenza().getNome().equals(cittausate)||gioc.ChiediCartaPercorso().getCittaArrivo().getNome().equals(cittausate)||gioc.ChiediCartaObiettivo().getCittaObiettivo().getNome().equals(gioc.ChiediCartaPercorso().getCittaPartenza().getNome()))return true;
        else return false;
    }

    public static boolean EqualsPartenza(Percorso p1, Percorso p2){
        if((Math.abs(p1.getCasellaPartenza().getInizio().getLatitude() - p2.getCasellaPartenza().getInizio().getLatitude()) < 0.0005
                && Math.abs(p1.getCasellaPartenza().getInizio().getLongitude() - p2.getCasellaPartenza().getInizio().getLongitude()) < 0.005)) return true;
        else return false;
    }
    public static boolean EqualsPartenzaArrivo(Percorso p1, Percorso p2){
        if((Math.abs(p1.getCasellaPartenza().getInizio().getLatitude() - p2.getCasellaArrivo().getInizio().getLatitude()) < 0.0005
                && Math.abs(p1.getCasellaPartenza().getInizio().getLongitude() - p2.getCasellaArrivo().getInizio().getLongitude()) < 0.005)) return true;
        else return false;
    }
    public static boolean EqualsArrivoPartenza(Percorso p1, Percorso p2){
        if((Math.abs(p1.getCasellaArrivo().getInizio().getLatitude() - p2.getCasellaPartenza().getInizio().getLatitude()) < 0.0005
                && Math.abs(p1.getCasellaArrivo().getInizio().getLongitude() - p2.getCasellaPartenza().getInizio().getLongitude()) < 0.005)) return true;
        else return false;
    }
    public static boolean EqualsArrivo(Percorso p1, Percorso p2){
        if((Math.abs(p1.getCasellaArrivo().getInizio().getLatitude() - p2.getCasellaArrivo().getInizio().getLatitude()) < 0.0005
                && Math.abs(p1.getCasellaArrivo().getInizio().getLongitude() - p2.getCasellaArrivo().getInizio().getLongitude()) < 0.005)) return true;
        else return false;
    }

    public static LatLong CalcolaCentro(){
        ArrayList<ICitta> cittas=new ArrayList<ICitta>();
        cittas= FacadePartita.getIstance().getMappa().getCitta();

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
    }
}
