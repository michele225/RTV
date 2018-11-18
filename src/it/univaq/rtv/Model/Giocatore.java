package it.univaq.rtv.Model;

import it.univaq.rtv.Model.FactoryMezzo.IMezzo;
import it.univaq.rtv.Model.StatoGiocatore.Attesa;
import it.univaq.rtv.Model.StatoGiocatore.IStato_Giocatore;

import java.util.ArrayList;

public class Giocatore implements Comparable<Giocatore>,Runnable {
	private int id;
	private String username;
	private CartaPercorso c=null;
	private CartaObiettivo c1=null;
	private ArrayList<IMezzo> IMezzo =null;
	private IStato_Giocatore IStato_giocatore;
	private String color;
	private ArrayList<Casella> mosse=new ArrayList<>();
	private boolean Obiettivo=false;
	private boolean Arrivo=false;

	public void setState(IStato_Giocatore state){
		this.IStato_giocatore = state;
	}

	public IStato_Giocatore getState(){
		return this.IStato_giocatore;
	}
	@Override
	public int compareTo(Giocatore g){
		int compare=((Giocatore) g).getId();
		return this.id-compare;
	}
	@Override
	public void run(){
		//this.Posizionamezzo();
	}
	public Giocatore(int id, String u,String color1){
		this.color=color1;
		this.id=id;
		this.username=u;
		Attesa attesa=new Attesa();
		attesa.Ruolo(this);

	}
	public String getColor(){
		return this.color;
	}
	public int getId(){
		return this.id;
	}

	public ArrayList<IMezzo> getMezzi(){
		return this.IMezzo;
	}

	public String getUsername(){
		return this.username;
	}

	public void setMossa(Casella mossa) {
		this.mosse.add(mossa);
	}
	public void setMosse(ArrayList<Casella> mosse) {
		this.mosse.addAll(mosse);
	}
	public void removeMossa(Casella mossa) {
		this.mosse.remove(mossa);
	}

	public ArrayList<Casella> getMosse() {
		return this.mosse;
	}

	public int LanciaDado() {
		return SingletonDado.getIstance().Lancia();
	}

	public void setMezzo(int taglia){
		this.IMezzo =new ArrayList<IMezzo>();
		FactorMezzo factorymezzo=new FactorMezzo();
		for(int i=0;i<taglia;i++){
			this.IMezzo.add(factorymezzo.getMezzo("Vagone", this));
		}


	}

	public ArrayList<IMezzo> getMezzo() {
		return IMezzo;
	}

	public void PescaDueCarte() {
		SingletonMazzoObiettivo m= new SingletonMazzoObiettivo();
		m=m.getIstance1();
		this.c1=m.PescaCarta();
		SingletonMazzoPercorso m1= new SingletonMazzoPercorso();
		m1=m1.getIstance1();
		this.c=m1.PescaCarta();

	}

	public void RestituisciCarte(){
		SingletonMazzoObiettivo mo= new SingletonMazzoObiettivo();
		mo=mo.getIstance1();
		SingletonMazzoPercorso mp= new SingletonMazzoPercorso();
		mp=mp.getIstance1();
		mo.ReinserisciCarta(this.ChiediCartaObiettivo());
		mp.ReinserisciCarta(this.ChiediCartaPercorso());
	}


	public CartaPercorso ChiediCartaPercorso() {
		return this.c;
	}

	public CartaObiettivo ChiediCartaObiettivo() {
		return this.c1;
	}


	public void PosizionaMezzo(Casella c) {

		int pos=this.IMezzo.size()-1;
		c.PosizionaGiocatore(this);
		this.IMezzo.remove(pos);


	}

	public boolean getObiettivo() {
		return this.Obiettivo;
	}
	public void Obiettivoraggiunto() {
		this.Obiettivo=true;
	}
	public boolean getArrivo() {
		return this.Arrivo;
	}
	public void Arrivoraggiunto() {
		this.Arrivo=true;
	}
}