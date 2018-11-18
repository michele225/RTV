package it.univaq.rtv.controller;

import it.univaq.rtv.Model.FacadePartita;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class ControllerNumGiocatori {
    private Label SceltaGiocatori;
    private Button Uno;
    private Button Due;
    private Button Tre;
    private Button Quattro;
    private Button Cinque;
    private Label ScrittaGiocatori;
    private Label InizioPartita;
    private AnchorPane menu;
    private Label SceltaMappa;
    private Button Europa;
    private Button America;
    private Button Africa;
    private Button Sud_America;
    private Button Asia;





    public ControllerNumGiocatori(Label SceltaGiocatori, Button Uno, Button Due, Button Tre, Button Quattro, Button Cinque, Label InizioPartita, AnchorPane menu, Label SceltaMappa, Button Europa, Button America, Button Africa, Button Sud_America, Button Asia, Label ScrittaGiocatore){
        this.SceltaGiocatori=SceltaGiocatori;
        this.Uno=Uno;
        this.Due=Due;
        this.Tre=Tre;
        this.Quattro=Quattro;
        this.Cinque=Cinque;
        this.InizioPartita=InizioPartita;
        this.menu=menu;
        this.ScrittaGiocatori=ScrittaGiocatore;
        this.SceltaMappa=SceltaMappa;
        this.Europa=Europa;
        this.America=America;
        this.Africa=Africa;
        this.Sud_America=Sud_America;
        this.Asia=Asia;
        this.Europa.setVisible(true);
        this.America.setVisible(true);
        this.Africa.setVisible(true);
        this.Sud_America.setVisible(true);
        this.Asia.setVisible(true);
        this.SceltaMappa.setVisible(true);
        this.Uno.setVisible(false);
        this.Due.setVisible(false);
        this.Tre.setVisible(false);
        this.Quattro.setVisible(false);
        this.Cinque.setVisible(false);
        this.SceltaGiocatori.setVisible(false);
        this.InizioPartita.setVisible(false);
        this.ScrittaGiocatori.setVisible(false);
    }
    public void BornAgain(){
        this.Uno.setVisible(true);
        this.Due.setVisible(true);
        this.Tre.setVisible(true);
        this.Quattro.setVisible(true);
        this.Cinque.setVisible(true);
        this.SceltaGiocatori.setVisible(true);
        this.InizioPartita.setVisible(true);
        this.ScrittaGiocatori.setVisible(true);

    }
    public void SettaNumGiocatori(String n){
        FacadePartita.getIstance().CreaGiocatori(n);
        }

}

