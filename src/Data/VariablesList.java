package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import File.IO;

public class VariablesList {
	public static String[] File;
	public static String[] Stats;
	public static String[] Effects;
	
	public static String[][] getModel(String[] Array1){
		String[][] retval = new String[Array1.length][2];
		for(int a=0;a<Array1.length;a++){
			retval[a][0] = Array1[a];
			retval[a][1] = "1";
		}
		return retval;
	}
	public static String[][] getModel2(String[] Array1){
		String[][] retval = new String[Array1.length][2];
		for(int a=0;a<Array1.length;a++){
			retval[a][0] = Array1[a];
			retval[a][1] = "0";
		}
		return retval;
	}
	public static void writeToFile(){
		JSONArray fileObject = new JSONArray();
		JSONArray statsObject = new JSONArray();
		JSONArray effectsObject = new JSONArray();
		
		for(int a=0;a<File.length;a++){
			fileObject.put(File[a]);
		}
		for(int a=0;a<Stats.length;a++){
			statsObject.put(Stats[a]);
		}
		for(int a=0;a<Effects.length;a++){
			effectsObject.put(Effects[a]);
		}
		IO.writeString(fileObject.toString(), "Files/File.json");
		IO.writeString(statsObject.toString(), "Files/Stats.json");
		IO.writeString(effectsObject.toString(), "Files/Effects.json");
	}
	public static void readFromFile(){
		try {
			JSONArray tempArr = new JSONArray(IO.readFile("Files/File.json"));
			File = JSONtoArray(tempArr);
			
			tempArr = new JSONArray(IO.readFile("Files/Stats.json"));
			Stats = JSONtoArray(tempArr);
			
			tempArr = new JSONArray(IO.readFile("Files/Effects.json"));
			Effects = JSONtoArray(tempArr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static String[] JSONtoArray(JSONArray input){
		String[] retval = new String[input.length()];
		for(int a=0;a<input.length();a++){
			try {
				retval[a] = (String)input.get(a);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return retval;
	}
}
