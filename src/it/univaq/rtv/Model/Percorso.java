package it.univaq.rtv.Model;

import com.lynden.gmapsfx.javascript.object.LatLong;
import it.univaq.rtv.Model.FactoryCitta.ICitta;

import java.util.ArrayList;

public class Percorso {
	private int id;
	private ICitta Cittapartenza;
	private ICitta CittaArrivo;
	public static int identificativo=0;
	private double distanza;
	ArrayList<Casella> caselle=new ArrayList<Casella>();

	public Percorso(int num, ICitta Cp, ICitta Ca){
		this.id=num;
		this.CittaArrivo=Ca;
		this.Cittapartenza=Cp;
		this.calcolaDistanza();
		this.setCaselle();

	}
	private void setCaselle(){
		//Valori utilizzati per coordinate casella
		double err=0.1;
		double spezzata =0.2;
		double dist= this.distanza;
		LatLong partenza=this.Cittapartenza.getCoordinate();
		LatLong arrivo=this.CittaArrivo.getCoordinate();

		if(arrivo.getLatitude()>partenza.getLatitude()&&arrivo.getLongitude()<partenza.getLongitude()){
			double x=partenza.getLatitude();
			double y=partenza.getLongitude();
			int n= (int) dist/100;
			LatLong resultold=partenza;
			double passo=arrivo.getLatitude()-partenza.getLatitude();
			passo/=n;
			double passo1=partenza.getLongitude()-arrivo.getLongitude();
			passo1/=n;


			for(double i=x,j=y;i<=arrivo.getLatitude()+err&&j>=arrivo.getLongitude()-err;){
				// System.out.println(l+" "+l1+" "+passo);
				//    System.out.println(i+" "+j);
				LatLong l1= new LatLong(i+spezzata,j+spezzata);
				LatLong[] result={resultold,l1};
				resultold=new LatLong(i,j);
				i=i+passo;
				j=j-passo1;
				Casella c=new Casella(identificativo);
				c.ImpostaCoordinate(resultold,l1);
				this.caselle.add(c);
				identificativo++;

			}
		}
		if(arrivo.getLatitude()>partenza.getLatitude()&&arrivo.getLongitude()>partenza.getLongitude()){
			double x=arrivo.getLatitude();
			double y=arrivo.getLongitude();
			int n= (int) dist/100;
			LatLong resultold=arrivo;
			double passo=partenza.getLatitude()-arrivo.getLatitude();
			passo/=n;
			double passo1=arrivo.getLongitude()-partenza.getLongitude();
			passo1/=n;


			for(double i=x,j=y;i>=partenza.getLatitude()+err&&j>=partenza.getLongitude()-err;){
				// System.out.println(l+" "+l1+" "+passo);
				//    System.out.println(i+" "+j);

				LatLong l1= new LatLong(i+spezzata,j+spezzata);
				LatLong[] result={resultold,l1};
				resultold=new LatLong(i,j);
				i=i+passo;
				j=j-passo1;
				Casella c=new Casella(identificativo);
				c.ImpostaCoordinate(resultold,l1);
				this.caselle.add(c);
				identificativo++;

			}
		}

		if(arrivo.getLatitude()<partenza.getLatitude()&&arrivo.getLongitude()<partenza.getLongitude()){
			double x=arrivo.getLatitude();
			double y=arrivo.getLongitude();
			int n= (int) dist/100;
			LatLong resultold=arrivo;
			double passo=partenza.getLatitude()-arrivo.getLatitude();
			passo/=n;
			double passo1=arrivo.getLongitude()-partenza.getLongitude();
			passo1/=n;

			for(double i=x,j=y;i<=partenza.getLatitude()+err&&j<=partenza.getLongitude()-err;){
				// System.out.println(l+" "+l1+" "+passo);
				//    System.out.println(i+" "+j);
				LatLong l1= new LatLong(i+spezzata,j+spezzata);
				LatLong[] result={resultold,l1};
				resultold=new LatLong(i,j);
				i=i+passo;
				j=j-passo1;
				Casella c=new Casella(identificativo);
				c.ImpostaCoordinate(resultold,l1);
				this.caselle.add(c);
				identificativo++;


			}
		}

		if(partenza.getLatitude()>arrivo.getLatitude()&&partenza.getLongitude()<arrivo.getLongitude()){
			double x=arrivo.getLatitude();
			double y=arrivo.getLongitude();
			int n= (int) dist/100;
			LatLong resultold=arrivo;
			double passo=partenza.getLatitude()-arrivo.getLatitude();
			passo/=n;
			double passo1=arrivo.getLongitude()-partenza.getLongitude();
			passo1/=n;

			for(double i=x,j=y;i<=partenza.getLatitude()+err&&j>=partenza.getLongitude()-err;){
				// System.out.println(l+" "+l1+" "+passo);
				//    System.out.println(i+" "+j);

				LatLong l1= new LatLong(i+spezzata,j+spezzata);
				LatLong[] result={resultold,l1};
				resultold=new LatLong(i,j);
				i=i+passo;
				j=j-passo1;
				Casella c=new Casella(identificativo);
				c.ImpostaCoordinate(resultold,l1);
				this.caselle.add(c);
				identificativo++;


			}
		}




	}

	/**
	 *
	 * @param CartaPercorso
	 * @param FactorMezzo
	 * @param Giocatore
	 */
	public void TrovaPercorso(CartaPercorso c, FactorMezzo mez, Giocatore g) {

		if(c.getCittaPartenza()==this.Cittapartenza&&this.Cittapartenza.getIMezzo()==null)
			this.Cittapartenza.PosizionaGiocatore(mez,g);
	}



	public boolean CheckSuiVicini(Casella c) {
		int pos=1;
		for(int i = 0; i<caselle.size(); i++){

			if(c.getId()==caselle.get(i).getId()){
				pos = i;

			}
		}
		if(caselle.get(1).CheckOccupata()&&(caselle.get(1).getId()-1==c.getId()||caselle.get(1).getId()+1==c.getId())){
			return true;
		}
		else if((caselle.get(pos-1).CheckOccupata()||caselle.get(pos+1).CheckOccupata())
				&&((caselle.get(pos-1).getId()+1==c.getId()||caselle.get(pos+1).getId()-1==c.getId())||
				(caselle.get(pos-1).getId()-1==c.getId()||caselle.get(pos+1).getId()+1==c.getId()))){
			return  true;
		}
		else return false;
	}

	//ritorna citta partenza o arrivo
	public int getid(){
		return this.id;
	}
	public ICitta getCittapartenza(){
		return this.Cittapartenza;
	}
	public ICitta getCittaArrivo(){
		return this.CittaArrivo;
	}
	public ArrayList<Casella> getCaselle(){
		return this.caselle;
	}
	public void removeCasella(Casella casella){
		this.caselle.remove(casella);
	}


	//FUNZIONE CHE DEVE RESTITUIRE 2 CASELLE, UNA INIZIALE E UNA FINALE DEL PERCORSO IN QUESTIONE
	public ArrayList<Casella> getCaselleAntipodi(){
		ArrayList<Casella> c= new ArrayList<>();

		c.add(0,this.getCaselle().get(0));
		c.add(1,this.getCaselle().get(this.getCaselle().size()-1));

		return c;

	}

	//Funzione che restituisce le due citta del percorso
	public ArrayList<ICitta> getCittas(){
		ArrayList<ICitta> cit= new ArrayList<>();
		cit.add(0,getCittapartenza());
		cit.add(1,getCittaArrivo());

		return cit;
	}

	/*******Funzione che permette di capire come è posizionato il percorso, e dove si trova la casella di partenza
	 * in questo modo possiamo distinguere i vicini di arrivo e partenza del percorso.*****/

	public Casella CalcolaCasellaVicina(Casella casella){
		if(casella.getId()==this.caselle.get(0).getId()&&(!this.caselle.get(1).CheckOccupata())){
			return this.caselle.get(1);
		}
		else if (casella.getId()==this.caselle.get(this.caselle.size()-1).getId()&&(!this.caselle.get(this.caselle.size()-2).CheckOccupata())){

			return this.caselle.get(this.caselle.size()-2);
		}
		else{
			int pos=1;
			for(int i = 0; i<caselle.size(); i++){

				if(casella.getId()==caselle.get(i).getId()){
					pos = i;

				}
			}

			if(pos>0 && pos<caselle.size()-1){
				if (!this.caselle.get(pos + 1).CheckOccupata())
					return this.caselle.get(pos + 1);
				else if (!this.caselle.get(pos - 1).CheckOccupata())
					return this.caselle.get(pos - 1);
			}
			else if (pos==0){
				return this.caselle.get(0);

			}
			else{
				return this.caselle.get(this.caselle.size()-1);
			}
		}
		return null;

	}

	public Casella getCasellaPartenza(){
		double R = 6372.795477598;  //Con errore dello 0.3%

		double k= 2* 3.14/360;
		Casella casellaPartenza = new Casella(0);

		double distCasella0 = R* Math.acos(Math.sin(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(0).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCasellaN = R* Math.acos(Math.sin(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCittaPartenza = R* Math.acos(Math.sin(this.getCittapartenza().getCoordinate().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCittapartenza().getCoordinate().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCittapartenza().getCoordinate().getLongitude()*k-(-6.260309699999993*k)));

		if(Math.abs(distCittaPartenza-distCasella0)<= Math.abs(distCittaPartenza-distCasellaN)){
			casellaPartenza= this.getCaselle().get(0);
		}
		else casellaPartenza= this.getCaselle().get(this.getCaselle().size()-1);

		return casellaPartenza;
	}

	public Casella getCasellaPerVicino(Casella casella_iniziale){
		double R = 6372.795477598;  //Con errore dello 0.3%

		double k= 2* 3.14/360;
		Casella casella_vicina = new Casella(0);

		double distCasella0 = R* Math.acos(Math.sin(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(0).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCasellaN = R* Math.acos(Math.sin(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCasella_iniziale = R* Math.acos(Math.sin(casella_iniziale.getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(casella_iniziale.getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(casella_iniziale.getInizio().getLongitude()*k-(-6.260309699999993*k)));

		if(Math.abs(distCasella_iniziale-distCasella0)<= Math.abs(distCasella_iniziale-distCasellaN)){
			casella_vicina= this.getCaselle().get(0);
		}
		else
			casella_vicina= this.getCaselle().get(this.getCaselle().size()-1);

		return casella_vicina;
	}

	public Casella getCasellaArrivo(){
		double R = 6372.795477598;  //Con errore dello 0.3%

		double k= 2* 3.14/360;
		Casella casellaArrivo = new Casella(0);

		double distCasella0 = R* Math.acos(Math.sin(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(0).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(0).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCasellaN = R* Math.acos(Math.sin(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCaselle().get(this.getCaselle().size()-1).getInizio().getLongitude()*k-(-6.260309699999993*k)));

		double distCittaArrivo = R* Math.acos(Math.sin(this.getCittaArrivo().getCoordinate().getLatitude()*k) * Math.sin(53.3498053*k) +
				Math.cos(this.getCittaArrivo().getCoordinate().getLatitude()*k) * Math.cos(53.3498053*k) * Math.cos(this.getCittaArrivo().getCoordinate().getLongitude()*k-(-6.260309699999993*k)));

		if(Math.abs(distCittaArrivo-distCasella0)<= Math.abs(distCittaArrivo-distCasellaN)){
			casellaArrivo= this.getCaselle().get(0);
		}
		else if(Math.abs(distCittaArrivo-distCasella0)> Math.abs(distCittaArrivo-distCasellaN)){
			casellaArrivo= this.getCaselle().get(this.getCaselle().size()-1);
		}

		return casellaArrivo;

	}
	public void calcolaDistanza(){

		/**** Algoritmo per calcolare la distanza tra le Città **********/
		double R = 6372.795477598;  //Con errore dello 0.3%

		double k= 2* 3.14/360;

		this.distanza = R* Math.acos(Math.sin(this.Cittapartenza.getCoordinate().getLatitude()*k) * Math.sin(this.CittaArrivo.getCoordinate().getLatitude()*k) +
				Math.cos(this.Cittapartenza.getCoordinate().getLatitude()*k) * Math.cos(this.CittaArrivo.getCoordinate().getLatitude()*k) * Math.cos(this.Cittapartenza.getCoordinate().getLongitude()*k-(this.CittaArrivo.getCoordinate().getLongitude()*k)));



	}






}