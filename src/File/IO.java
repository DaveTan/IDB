package File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO {
	public static void writeString(String input, String FileName){
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try{
			fw = new FileWriter(FileName);
			bw = new BufferedWriter(fw);
			bw.write(input);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public static String readFile(String FileName){
		String retval = "";
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(FileName);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(FileName));
			while ((sCurrentLine = br.readLine()) != null) {
				retval+=sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		return retval;
		}

	}
}