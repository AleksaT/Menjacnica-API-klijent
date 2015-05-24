package main;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import valuta.Valuta;
import json.AzuriranjeKursneListe;
import json.JsonRatesAPIKomunikacija;

public class Main {

	public static void main(String[] args) {
		String[] valute = new String[4];
		valute[0]="EUR";
		valute[1]="USD";
		valute[2]="CAD";
		valute[3]="GBP";
		GregorianCalendar datum = new GregorianCalendar();
		LinkedList<Valuta> valute1 = new LinkedList<>();
		AzuriranjeKursneListe azk = new AzuriranjeKursneListe();
		JsonRatesAPIKomunikacija jrak = new JsonRatesAPIKomunikacija();
		valute1 = jrak.vratiIznosKurseva(valute);
		azk.upisiValute(valute1, datum);
		
		azk.azurirajValute();
        
        
	}

}
