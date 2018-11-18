package it.univaq.rtv.Model;

import it.univaq.rtv.Model.FactoryMappa.*;

import java.io.IOException;

public class FactorMappa {

	public static AbstractMappa getMappa(String criteria ) throws IOException{
		if ( criteria.equals("Europa") )
			return new Europa();
		else if ( criteria.equals("USA") )
			return new USA();
		else if ( criteria.equals("Africa") )
			return new Africa();
		else if ( criteria.equals("Sud_America") )
			return new Sud_America();
		else if ( criteria.equals("Asia") )
			return new Asia();
		else
			return null;
	}




}

