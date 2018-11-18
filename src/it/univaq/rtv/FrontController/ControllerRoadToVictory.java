package it.univaq.rtv.FrontController;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import it.univaq.rtv.Model.*;
import it.univaq.rtv.Model.StatoGiocatore.Vincente;
import it.univaq.rtv.Utility.Utility;
import it.univaq.rtv.controller.ControllerDado;
import it.univaq.rtv.controller.ControllerMappa;
import it.univaq.rtv.controller.ControllerNumGiocatori;
import it.univaq.rtv.controller.ControllerSceltaMappa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import netscape.javascript.JSObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRoadToVictory implements Initializable, MapComponentInitializedListener {


    private Vincente vincente= new Vincente();

    @FXML
    public GoogleMapView googleMapView;
    public GoogleMap map;
    public Label SceltaGiocatori;
    public Label SceltaMappa;
    public Button Europa;
    public Button USA;
    public Button Africa;
    public Button Sud_America;
    public Button Asia;
    private String nomemappa="";
    public Button Uno;
    public Button Due;
    public Button Tre;
    public Button Quattro;
    public Button Cinque;
    public Label InizioPartita;
    public AnchorPane menu;
    public Label ScrittaGiocatori;
    public Button dadoButton;
    public Label NumberDado;
    public Label NumeroMezzo;
    public Label ErroreDado;
    public ImageView DadoImage;  //per impostare immagine dado
    public Label CartaObiettivo;
    public Label CartaPercorsoPartenza;
    public Label CartaPercorsoArrivo;
    public Label GiocatoreName;
    public Label FinePartita;
    public Button TurnoButton;
    public ControllerMappa controllerMappa;
    public ControllerDado controllerDado;
    private ControllerNumGiocatori ngioc;
    private ControllerSceltaMappa scmapp;
    private String Numero="";
    private Timestamp timestamp;
    @Override
    public void initialize(URL url, ResourceBundle rb){
       // googleMapView.addMapInializedListener(this);
    }
    @Override
    public void mapInitialized(){
        if(this.Numero!=""){
            ngioc.SettaNumGiocatori(this.Numero);
        }
        if(this.nomemappa!=""){
            this.controllerMappa =new ControllerMappa(googleMapView, CartaObiettivo, CartaPercorsoPartenza,CartaPercorsoArrivo,GiocatoreName,TurnoButton,NumeroMezzo,FinePartita);

            try {
                this.Creamappa(nomemappa);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    private void SetMappa(final ActionEvent event){
        event.consume();
        this.nomemappa=event.getTarget().toString().replace("Button[id=","").replaceAll(", styleClass=button]","");
        int pos=this.nomemappa.indexOf("'");
        this.nomemappa=this.nomemappa.substring(0,pos);
        this.scmapp=new ControllerSceltaMappa(SceltaMappa,Europa,USA,Africa,Sud_America,Asia,SceltaGiocatori,InizioPartita,menu,ScrittaGiocatori);
        this.mapInitialized();
    }
    @FXML
    private void Setgiocatore(final ActionEvent event){
        event.consume();
        this.Numero=event.getTarget().toString().replace("Button[id=","").replace(", styleClass=button]''","");
        this.ngioc=new ControllerNumGiocatori(SceltaGiocatori,Uno,Due,Tre,Quattro,Cinque,InizioPartita,menu,SceltaMappa,Europa,USA,Africa,Sud_America,Asia,ScrittaGiocatori);
    }

    @FXML
    public void LanciaDado(final ActionEvent event){
        event.consume();
        this.controllerDado =new ControllerDado(dadoButton,DadoImage);
        this.controllerDado.Lancia(this.controllerDado.getNumDado());
        this.NumeroMezzo.setText(String.valueOf(this.controllerDado.getNumDado()));


    }

    @FXML
    public void FineTurno(final ActionEvent event){
        event.consume();
        this.controllerDado.setDadoButton();
        this.FinisciTurno();
        this.setCarte();
        this.setObiettivo();
        this.setArrivo();
        this.setTurnoButton(false);
        this.setGiocatoreName();

    }

    @FXML
    public void Creamappa(String nomemappa) throws FileNotFoundException, IOException {
        this.controllerMappa.getPartita().AvviaPartita(nomemappa, this.controllerMappa.getPartita().getGiocatori());
        this.setGiocatoreName();
        final Polyline[] polyline = {null};
        MapOptions mapOptions = new MapOptions();

        /*Controllo per variare la mappa a seconda che sia giorno o pomeriggio*/
        MapTypeIdEnum mapTypeIdEnum;
        if(LocalDateTime.now().getHour()<12) {
             mapTypeIdEnum= MapTypeIdEnum.ROADMAP;
        }
        else {
            mapTypeIdEnum= MapTypeIdEnum.HYBRID;
        }

        System.out.println();
        mapOptions.center(new LatLong(Utility.CalcolaCentro().getLatitude(),Utility.CalcolaCentro().getLongitude()))
                .mapType(mapTypeIdEnum)
                .zoom(4)
                .scrollWheel(false)
                .maxZoom(8)
                .streetViewControl(false)
                .zoomControl(true)
                .mapTypeControl(false)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false);
        this.map = this.googleMapView.createMap(mapOptions, false);
        this.setCarte();

        //ArrayList<Percorso> percorsi = new ArrayList<>();
        //percorsi = mappa.DammiPercorsi();



        this.setMarker();
        for(int a=0;a<this.controllerMappa.getNumGiocatori();a++){
            for (int j = 0; j < this.controllerMappa.getNumPercorsiMappa(); j++) {
                for (int cont = 0; cont < this.controllerMappa.getNumCasellePercorso(j); cont++) {
                    if (this.controllerMappa.IsPartenza(a,j,cont)){
                        LatLong[] latLongs1 = {this.controllerMappa.getInizioCasella(j,cont), this.controllerMappa.getFineCasella(j,cont)};
                        PolylineOptions polylineOptions1 = new PolylineOptions();
                        polylineOptions1.path(new MVCArray(latLongs1))
                                .clickable(true)
                                .draggable(false)
                                .strokeColor("blue")
                                .strokeWeight(10)
                                .visible(true);
                        polyline[0] = new Polyline(polylineOptions1);
                        polyline[0].setDraggable(false);
                        map.addMapShape(polyline[0]);
                        this.controllerMappa.setMezzo(a,1);
                        this.controllerMappa.PosizionaMezzoGioc(a,j,cont);
                        this.controllerMappa.setMossaGioc(a,j,cont);
                        this.controllerMappa.setMosseGioc(a,j,cont);
                        }



                    }

                }
            }


        for (int j = 0; j < this.controllerMappa.Duplicati().size(); j++) {
            ArrayList<Casella> caselle = this.controllerMappa.Duplicati().get(j).getCaselle();
            for (int cont = 0; cont < caselle.size(); cont++) {
                Polyline finalPolyline = null;
                LatLong[] latLongs = {caselle.get(cont).getInizio(), caselle.get(cont).getFine()};
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.path(new MVCArray(latLongs))
                        .clickable(true)
                        .draggable(false)
                        .strokeColor("gray")
                        .strokeWeight(6)
                        .visible(true);
                polyline[0] = new Polyline(polylineOptions);
                polyline[0].setDraggable(false);
                map.addMapShape(polyline[0]);
                finalPolyline = polyline[0];
                int finalI = cont;
                Polyline finalPolyline1 = finalPolyline;
                map.addUIEventHandler(polyline[0], UIEventType.click, (JSObject obj) -> {
                    try{
                        if(this.controllerMappa.PosizionaMezzoPartita(finalPolyline1, polylineOptions,finalI,caselle)){
                            this.setGiocatoreName();
                            this.PosizionaMezzo(finalPolyline1,polylineOptions);
                        }

                    }
                    catch (Exception f){
                        f.printStackTrace();
                    }

                });
            }
        }

    }





    public void PosizionaMezzo(Polyline finalPolyline1, PolylineOptions polylineOptions){



        if (this.controllerMappa.getNumMezzi(0) > 0) {
         
           if(this.controllerMappa.ControlloObiettivo())  this.setObiettivo();
            if(this.controllerMappa.ControlloArrivo()) this.setArrivo();
            if(this.controllerMappa.ControlloFine()) this.FinePartita();
            this.NumeroMezzo.setText(String.valueOf(this.controllerMappa.getNumMezzi(0)));
            polylineOptions.strokeColor(this.controllerMappa.getColoreGiocatore(0));
            finalPolyline1.setVisible(false);
            Polyline polyline1 = new Polyline(polylineOptions);
            map.addMapShape(polyline1);
        }
        else if (this.controllerMappa.getNumMezzi(0)== 0) {

            if(this.controllerMappa.ControlloObiettivo())  this.setObiettivo();
            if(this.controllerMappa.ControlloArrivo()) this.setArrivo();
            if(this.controllerMappa.ControlloFine()) this.FinePartita();
            this.NumeroMezzo.setText(String.valueOf(0));
            polylineOptions.strokeColor(this.controllerMappa.getColoreGiocatore(0));
            finalPolyline1.setVisible(false);
            Polyline polyline1 = new Polyline(polylineOptions);
            this.map.addMapShape(polyline1);
            this.TurnoButton.setVisible(true);

        }
    }







    public void setCarte(){

        this.CartaObiettivo.setText("Città obiettivo: " + this.controllerMappa.getNomeCittaObiettivo(0));
        this.CartaPercorsoPartenza.setText("Partenza:" + this.controllerMappa.getNomeCPCittaPartenza(0));
        this.CartaPercorsoArrivo.setText("Arrivo:" + this.controllerMappa.getNomeCPCittaArrivo(0));
    }



    public void setMarker(){
        ArrayList<Marker> markers= new ArrayList<>();
        for(int i=0; i<this.controllerMappa.getPartita().getGiocatori().size();i++){
            for (int j = 0; j < this.controllerMappa.getPartita().getMappa().getCitta().size(); j++) {
                LatLong coorPartenza = this.controllerMappa.getPartita().getMappa().getCitta().get(j).getCoordinate();
                if (this.controllerMappa.getPartita().getMappa().getCitta().get(j).getNome().equals(this.controllerMappa.getNomeCPCittaPartenza(i))) {
                    this.controllerMappa.setMezzoGioc(i,j,"Vagone");
                    this.controllerMappa.OccupaCittaPartenza(i);
                    MarkerOptions MarkerPartenza = new MarkerOptions();
                    if(this.controllerMappa.getColoreGiocatore(i)=="aqua")  MarkerPartenza.icon("http://oi63.tinypic.com/iqh2mx.jpg");
                    if(this.controllerMappa.getColoreGiocatore(i)=="red")  MarkerPartenza.icon("http://oi64.tinypic.com/wan96r.jpg");
                    if(this.controllerMappa.getColoreGiocatore(i)=="orange")  MarkerPartenza.icon("http://oi64.tinypic.com/331lhly.jpg");
                    if(this.controllerMappa.getColoreGiocatore(i)=="pink")  MarkerPartenza.icon("http://oi66.tinypic.com/20k831c.jpg");
                    if(this.controllerMappa.getColoreGiocatore(i)=="white")  MarkerPartenza.icon("http://oi64.tinypic.com/6tiflx.jpg");
                    if(this.controllerMappa.getColoreGiocatore(i)=="teal")  MarkerPartenza.icon("http://oi64.tinypic.com/othy13.jpg");
                    MarkerPartenza.position(coorPartenza);
                    MarkerPartenza.visible(Boolean.TRUE);
                    String nome_giocatore=this.controllerMappa.getPartita().getGiocatori().get(i).getUsername().substring(0,1);
                    MarkerPartenza.label(nome_giocatore.concat(this.controllerMappa.getPartita().getGiocatori().get(i).getUsername().substring(this.controllerMappa.getUsername(i).length()-1)));
                    Marker m1 = new Marker(MarkerPartenza);
                    markers.add(m1);
                    map.addMarkers(markers);

                }
            }
        }
    }

    public void setGiocatoreName(){
        this.GiocatoreName.setText(this.controllerMappa.getPartita().getGiocatori().get(0).getUsername());
        String color= Utility.ColorToRgba(this.controllerMappa.getColoreGiocatore(0));
        String style = "-fx-background-color:"+color;
        this.GiocatoreName.setStyle(style);
        // this.GiocatoreName.setTextFill(Color.web(g.getColor()));

    }

    public void setObiettivo(){
        if(this.controllerMappa.getPartita().getGiocatori().get(0).getObiettivo()==true) {
            //this.CartaObiettivo.setTextFill(Color.web("green"));   Per settare testo della label
            // this.CartaObiettivo.setBackground(Background.EMPTY);   Nel caso si voglia riportare il background invisibile, alla mossa successiva
            String style = "-fx-background-color:" + Utility.ColorToRgba("green");
            this.CartaObiettivo.setStyle(style);
        }
        else {
            this.CartaObiettivo.setTextFill(Color.web("black"));
            String style = "-fx-background-color:"+Utility.ColorToRgba("white");
            this.CartaObiettivo.setStyle(style);
        }

    }


    public void setArrivo(){
        if(this.controllerMappa.getPartita().getGiocatori().get(0).getArrivo()==true) {
            String style = "-fx-background-color:" +Utility.ColorToRgba("green");
            this.CartaPercorsoArrivo.setStyle(style);
        }
        else {
            this.CartaObiettivo.setTextFill(Color.web("black"));
            String style = "-fx-background-color:"+Utility.ColorToRgba("white");
            this.CartaPercorsoArrivo.setStyle(style);
        }

    }

    public void FinePartita(){
        this.FinePartita.setVisible(true);
        this.FinePartita.setText(this.controllerMappa.getUsername(0)+" HAI VINTO LA PARTITA!!!!!!");
        String color= Utility.ColorToRgba(this.controllerMappa.getColoreGiocatore(0));
        String style = "-fx-background-color:"+ color;
        this.FinePartita.setStyle(style);
        this.menu.setVisible(false);
    }

    //Queste 2 funzioni andranno nel controller FINETURNO
    public void FinisciTurno(){
        this.controllerMappa.getPartita().setGiocatori(FacadePartita.getIstance().FineTurno());

    }
    public void setTurnoButton(Boolean button){
        this.TurnoButton.setVisible(button);
    }


}
