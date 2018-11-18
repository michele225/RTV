package it.univaq.rtv.Model;

import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import it.univaq.rtv.Model.FactoryMappa.AbstractMappa;
import it.univaq.rtv.Model.FactoryMezzo.IMezzo;
import it.univaq.rtv.Model.StatoGiocatore.Attesa;
import it.univaq.rtv.Model.StatoGiocatore.Gioca;
import it.univaq.rtv.Model.StatoGiocatore.Vincente;
import it.univaq.rtv.Model.StatoTurno.Generale;
import it.univaq.rtv.Model.StatoTurno.Iniziale;
import it.univaq.rtv.Utility.Utility;
import it.univaq.rtv.controller.ControllerDado;
import it.univaq.rtv.controller.ControllerMappa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FacadePartita {


    private ArrayList<Giocatore> Giocatori=new ArrayList<>();
    private ControllerMappa controllerMappa;
    private ControllerDado controllerDado;
    private AbstractMappa mappa;
    private Generale general;
    private Gioca gioca=new Gioca();
    private Vincente vincente= new Vincente();
    private static FacadePartita istance = null;

    public static FacadePartita getIstance(){
        if(istance==null){
            istance = new FacadePartita();
        }
        return istance;
    }
    protected FacadePartita(){

    }



    public AbstractMappa AvviaPartita(String Nome_mappa, ArrayList<Giocatore> giocatoriArrayList){
        Turno t= new Turno();
        Iniziale i=new Iniziale();
        try{
            ArrayList<Giocatore> giocatori_ordinati=i.OrdinaGiocatori(giocatoriArrayList);
            Attesa attesa=new Attesa();
            giocatori_ordinati=i.InizioTurno(giocatori_ordinati,Nome_mappa,t, attesa);
            giocatoriArrayList=giocatori_ordinati;
            this.general=new Generale();
            this.mappa =i.getMappa();
            this.Giocatori=this.general.InizioTurno(giocatori_ordinati,Nome_mappa,t, this.gioca);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return this.mappa;
        }


    }




    public int LanciaDado(ArrayList<Giocatore> giocatoreArrayList) {
       int n = giocatoreArrayList.get(0).LanciaDado();
       giocatoreArrayList.get(0).setMezzo(n);
       return n;
    }

    public boolean PosizionaMezzo(Polyline finalPolyline1, PolylineOptions polylineOptions, int finalI, ArrayList<Casella> caselle) throws FileNotFoundException,IOException {
            //this.viewMappa.setGiocatoreName(this.Giocatori.get(0));
            MVCArray path = finalPolyline1.getPath();
            polylineOptions.path(path);
            String coordinata = String.valueOf(path.getAt(0));
            String[] LatLinea = coordinata.split(",");
            double Lat = Double.valueOf(LatLinea[0].replace("(", ""));
            double Long = Double.valueOf(LatLinea[1].replace(")", ""));
            LatLong LongCasellaInizio = caselle.get(finalI).getInizio();
            LatLong LongCasellaFine = caselle.get(finalI).getFine();
            Casella Casella_premuta = caselle.get(finalI);


            if ((LongCasellaInizio.getLatitude() == Lat && LongCasellaInizio.getLongitude() == Long) ||
                    (LongCasellaFine.getLatitude() == Lat && LongCasellaFine.getLongitude() == Long)) {

                Percorso PercorsoPremuto = null;
                PercorsoPremuto = mappa.getPercorsoByCasella(Casella_premuta);

                                for (int g2 = 0; g2 < this.Giocatori.get(0).getMosse().size(); g2++) {
                                    //aggiungi caselle dei percorsi vicini
                                    if (Casella_premuta.getId() == this.Giocatori.get(0).getMosse().get(g2).getId()) {
                                        ArrayList<Percorso> percorsi_vicini = new ArrayList<>();
                                        if (Casella_premuta.getId() == PercorsoPremuto.getCasellaPartenza().getId())
                                            percorsi_vicini = this.mappa.getViciniPercorsoPartenza(PercorsoPremuto);

                                        else if (Casella_premuta.getId() == PercorsoPremuto.getCasellaArrivo().getId())
                                            percorsi_vicini = this.mappa.getViciniPercorsoArrivo(PercorsoPremuto);

                                        if (percorsi_vicini.size() == 0) ;
                                        else {
                                            ArrayList<Casella> casellaArrayList = this.mappa.getCaselleVicinePercorsi(percorsi_vicini, Casella_premuta);
                                            casellaArrayList.remove(null);
                                            this.Giocatori.get(0).setMosse(casellaArrayList);
                                        }
                                        this.Giocatori.get(0).setMossa(PercorsoPremuto.CalcolaCasellaVicina(Casella_premuta));
                                        this.Giocatori.get(0).PosizionaMezzo(Casella_premuta);
                                        this.Giocatori.get(0).removeMossa(Casella_premuta);
                                        if(Math.abs(Casella_premuta.getInizio().getLatitude()-this.Giocatori.get(0).ChiediCartaObiettivo().getCittaObiettivo().getCoordinate().getLatitude())<0.005){
                                            this.Giocatori.get(0).Obiettivoraggiunto();
                                        }
                                        if(Math.abs(Casella_premuta.getInizio().getLatitude()-this.Giocatori.get(0).ChiediCartaPercorso().getCittaArrivo().getCoordinate().getLatitude())<0.005){
                                            this.Giocatori.get(0).Arrivoraggiunto();
                                        }
                                        if(this.Giocatori.get(0).getObiettivo()==true && this.Giocatori.get(0).getArrivo()==true) {
                                            this.Giocatori.get(0).setState(vincente);
                                        }
                                        return true;


                                    }


                                    }

                                }
                return false;

    }



    public ArrayList<Giocatore> FineTurno() {
            try{
                Turno t = new Turno();
                Giocatore giocatore_backup = this.Giocatori.get(0);
                this.Giocatori.remove(giocatore_backup);
                this.Giocatori.add(this.Giocatori.size(), giocatore_backup);

                this.general.InizioTurno(this.Giocatori, this.mappa.getNome(), t, this.gioca);
                this.general.Fineturno(this.Giocatori.get(0));

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                return this.Giocatori;
            }

    }

    public void setGiocatori(ArrayList<Giocatore> giocatorearraylist){
        this.Giocatori = giocatorearraylist;
    }

    public ArrayList<Giocatore> getGiocatori(){
        return this.Giocatori;
    }

    public void CreaGiocatori(String n){
        for(int i = 1; i<= Utility.StringtoInteger(n); i++){
            Giocatore giocatore = new Giocatore(i,"Giocatore"+i, Utility.Colori());
            this.Giocatori.add(giocatore);
        }
    }

    public AbstractMappa getMappa(){
        return this.mappa;
    }

    public void setIMezzoGioc(int gioc, int j,String mezzo){
        FactorMezzo factorMezzo =new FactorMezzo();
        IMezzo mezGioc1= factorMezzo.getMezzo(mezzo, FacadePartita.getIstance().getGiocatori().get(gioc));
        mappa.getCitta().get(j).setIMezzo(mezGioc1);
    }

    public ArrayList<Casella> CaselleVicini(int percorso, int casella) {
        ArrayList<Percorso> percorsi_vicini = new ArrayList<>();
        Casella cas = this.getMappa().DammiPercorsi().get(percorso).getCaselle().get(casella);
        if (Utility.EqualsIdCasella(cas, this.getMappa().getPercorsoByCasella(cas).getCasellaPartenza())) {
            percorsi_vicini = mappa.getViciniPercorsoPartenza(mappa.getPercorsoByCasella(cas));
        } else if (Utility.EqualsIdCasella(cas, mappa.getPercorsoByCasella(cas).getCasellaArrivo())) {
            percorsi_vicini = mappa.getViciniPercorsoArrivo(mappa.getPercorsoByCasella(cas));
        }

        if (percorsi_vicini.size() == 0) ;
        else {
            ArrayList<Casella> casellaArrayList = mappa.getCaselleVicinePercorsi(percorsi_vicini, cas);
            casellaArrayList.remove(null);
            return casellaArrayList;
        }
        return null;
    }

}
