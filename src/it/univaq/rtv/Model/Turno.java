package it.univaq.rtv.Model;

import it.univaq.rtv.Model.StatoTurno.IState_Turno;

public class Turno {
    private IState_Turno s;

    public Turno(){
            s = null;
    }

    public void setState(IState_Turno state){
            this.s = state;
    }

    public IState_Turno getState(){
            return s;
    }
}
