package it.univaq.rtv.Model.StatoGiocatore;

import it.univaq.rtv.Model.Giocatore;


public class Gioca implements IStato_Giocatore {
    @Override
    public  void Ruolo(Giocatore g){
            g.setState(this);
    }

}
