package json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import valuta.Valuta;

public class AzuriranjeKursneListe {

	private final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute (){

		
		LinkedList<Valuta> valute = new LinkedList<>();
		
		try {
			FileReader out = new FileReader(putanjaDoFajlaKursnaLista);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			JsonObject jsonObject = gson.fromJson(out, JsonObject.class);
			
			JsonArray valutaArrey = jsonObject.get("valute").getAsJsonArray();
			
			for (int i = 0; i < valutaArrey.size(); i++) {
				JsonObject valutaJSON = (JsonObject) valutaArrey.get(i);
				
				Valuta valuta = new Valuta();
				valuta.setNaziv(valutaJSON.get("naziv").getAsString());
				valuta.setKurs(valutaJSON.get("kurs").getAsDouble());
				
				valute.add(valuta);
				
			}
			
			try {
				out.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
		} catch (JsonSyntaxException e) {
			
			e.printStackTrace();
		} catch (JsonIOException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		return valute;
		
	}
		
	public void upisiValute (LinkedList<Valuta> valute, GregorianCalendar datum){
		
		JsonObject jsonNovi= new JsonObject();
		JsonArray valuteArray= new JsonArray();
		
		for (int i = 0; i < valute.size(); i++) {
			Valuta v = valute.get(i);

			JsonObject valutaJSON = new JsonObject();

			valutaJSON.addProperty("naziv", v.getNaziv());
			valutaJSON.addProperty("kurs", v.getKurs());

			valuteArray.add(valutaJSON);
		}

		String datum1 = datum.get(GregorianCalendar.DAY_OF_MONTH)+ "." + datum.get(GregorianCalendar.MONTH)+1 + "." + datum.get(GregorianCalendar.YEAR);

		jsonNovi.addProperty("datum", datum1);
		jsonNovi.add("valute", valuteArray);

		try {
			
			FileWriter out = new FileWriter(putanjaDoFajlaKursnaLista);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			out.write(gson.toJson(jsonNovi));
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void azurirajValute(){

		LinkedList<Valuta> valute = ucitajValute();
		
		String[] naziviValuta = new String[valute.size()];
		
		for (int i = 0; i < valute.size(); i++) {
			naziviValuta[i] = valute.get(i).getNaziv();
		}
		
		JsonRatesAPIKomunikacija jsonRates = new JsonRatesAPIKomunikacija();
		
		LinkedList<Valuta> listaValuta = jsonRates.vratiIznosKurseva(naziviValuta);
		
		upisiValute(listaValuta, new GregorianCalendar());
	}
		
		
		
		
	}	
	

