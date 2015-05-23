package json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		
	}

