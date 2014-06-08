import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class KerbalParts {
	
	//private static int cpt = 0;
	private static ArrayList<String> key;
	private static ArrayList<String> keyF;
	private File file;
	private static String rep;
	
	//public void kerbal(){
	public void kerbal(){
		file = new File("");
		rep = file.getAbsolutePath();
		key  = new ArrayList<String>();
		keyF = new ArrayList<String>();
		
		listeRepertoire(new File(rep));
		analyseKey();
		writeFile();
	}
	
	private void analyseKey(){
		for(int i = 0 ; i<key.size(); i++){
			key.set(i,key.get(i).replace("name = ",""));
			key.set(i,key.get(i).replace("_","."));
			
			if(key.get(i).contains("@")    ){ key.remove(key.get(i)); }
			
			if(key.get(i).contains("//") ){ 
				if(key.get(i).charAt(0) == '/' ){ 
					key.remove(key.get(i)); 
				}else{
					key.set(i,key.get(i).substring(0,key.get(i).indexOf('\t')));
				}
			}
		}
		for(String s: key){
			if (!keyF.contains(s)){ keyF.add(s); }
		}
	}
	
	private void listeRepertoire(File repertoire) {
        
		//System.out.println(repertoire.getAbsolutePath());
		
		if(repertoire.getAbsolutePath().contains(".cfg")){
        	//System.out.println(repertoire.getAbsolutePath());
        	readFile(repertoire.getAbsolutePath());
        }
 
        if ( repertoire.isDirectory ( ) ) {
                File[] list = repertoire.listFiles();
                if (list != null){
	                for ( int i = 0; i < list.length; i++) {
	                        // Appel récursif sur les sous-répertoires
	                        listeRepertoire( list[i]);
	                } 
                } else {
                	System.err.println(repertoire + " : Erreur de lecture.");
                }
        }
	}
	
	private void readFile(String dir){
		
		//lecture du fichier texte	
		try{
			InputStream       ips  = new FileInputStream  (dir ); 
			InputStreamReader ipsr = new InputStreamReader(ips );
			BufferedReader    br   = new BufferedReader   (ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				if(ligne.contains("name = ")){
					//System.out.println(ligne);
					key.add(ligne.trim());
				}
			}
			br.close();
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	private static void writeFile(){
		//création ou ajout dans le fichier texte
		try {
			System.out.println(rep);
			
			File           fileWrite  = new File           (rep+"/parts.txt");
			//File           fileWrite  = new File           (rep+"\\parts.txt");
			FileWriter     fw         = new FileWriter     (fileWrite);
			BufferedWriter bw         = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw);

			for(String s : keyF){
				fichierSortie.println (s); 
			}
			
			fichierSortie.close();
			//System.out.println("Le fichier " + fileWrite + " a été créé!"); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public static void main (String[]args){
		KerbalParts kp = new KerbalParts();
		//kp.kerbal(new File("C:/Users/Kreus'/Desktop/Kerbal/Pack mod Multi"));
		kp.kerbal();
	}
}
