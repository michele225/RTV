package it.univaq.rtv.Model;

import it.univaq.rtv.Model.FactoryCitta.ICitta;

public class CartaPercorso{

	private ICitta CittaPartenza;
	private ICitta CittaArrivo;

    public CartaPercorso(ICitta CittaPartenza, ICitta CittaArrivo) {

        this.CittaPartenza=CittaPartenza;
        this.CittaArrivo=CittaArrivo;
    }
    public ICitta getCittaPartenza(){return this.CittaPartenza;}
    public ICitta getCittaArrivo(){return this.CittaArrivo;}


}