package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemList {
	public static JSONObject ItemList;
	
	public static void addItem(String input){
		JSONObject newItem = new JSONObject();
//		FileProperty
		JSONObject fileProperty = new JSONObject();
		JSONObject statsProperty = new JSONObject();
		JSONObject effectsProperty = new JSONObject();
		
		try {
			for(int a=0;a<VariablesList.File.length;a++){
				fileProperty.put(VariablesList.File[a], "");
			}
		} catch (JSONException e) {	
			e.printStackTrace();
		}
//		StatsProperty
		try {
			for(int a=0;a<VariablesList.Stats.length;a++){
				statsProperty.put(VariablesList.Stats[a], "");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		effectsProperty
		try {
			for(int a=0;a<VariablesList.Effects.length;a++){
				effectsProperty.put(VariablesList.Effects[a], "");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			newItem.put("File", fileProperty);
			newItem.put("Stats", statsProperty);
			newItem.put("Effects", effectsProperty);
			
			ItemList.put(input, newItem);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static String[][] getItemList(){
		String[][] retval = new String[ItemList.length()][2];
		String[] list = JSONObject.getNames(ItemList);
		for(int a=0;a<ItemList.length();a++){
			retval[a][0] = String.valueOf(a);
			retval[a][1] = list[a];
			System.out.println(list[a]);
		}
		return retval;
	}
	
	public static String[][] getVarList(String Key, String VarType, String[] varList){
		String[][] retval = new String[varList.length][2];
		try {
			JSONObject tempVar = ItemList.getJSONObject(Key);
			tempVar = tempVar.getJSONObject(VarType);
			
			for(int a=0;a<varList.length;a++){
				retval[a][0] = varList[a];
				retval[a][1] = String.valueOf(tempVar.get(varList[a]));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retval;
	}
	
	public static void init(){
		ItemList = new JSONObject();
	}
}
