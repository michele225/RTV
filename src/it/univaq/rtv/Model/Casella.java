package it.univaq.rtv.Model;
import com.lynden.gmapsfx.javascript.object.LatLong;
import it.univaq.rtv.Model.FactoryMezzo.IMezzo;

public class Casella {
    
        private boolean occupata;
        private int id;
        private IMezzo m=null;
        private LatLong inizio;
        private LatLong fine;
	
        public Casella(int id){
            this.id=id;
            this.occupata=false;
            this.inizio=null;
            this.fine=null;
        }


    public LatLong getInizio() {
        return inizio;
    }

    public LatLong getFine() {
        return fine;
    }

    public void PosizionaGiocatore(Giocatore g){
            this.m=(IMezzo)g.getMezzo().get(g.getMezzo().size()-1);

            this.setOccupata(true);
        }
    public void ImpostaCoordinate(LatLong i,LatLong f){
	    this.inizio=i;
	    this.fine=f;
    }

    public int getId() {
        return id;
    }

    public boolean CheckOccupata(){
            return this.occupata;
        }

    public void setOccupata(boolean occupata) {
        this.occupata = occupata;
    }
}





