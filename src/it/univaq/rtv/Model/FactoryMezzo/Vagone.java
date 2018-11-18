package it.univaq.rtv.Model.FactoryMezzo;


import it.univaq.rtv.Model.Giocatore;

public class Vagone implements IMezzo {
		private int id;
		private Giocatore g;
        public Vagone(Giocatore g) {
			this.g=g;
        }

		public void CreaMezzo() {
			// TODO - implement Vagone.CreaMezzo
			throw new UnsupportedOperationException();
		}

		public void DammiMezzo() {
			// TODO - implement Vagone.DammiMezzo
			throw new UnsupportedOperationException();
		}

}