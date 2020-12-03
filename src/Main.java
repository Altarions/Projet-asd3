import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static ImagePNG png;
	private static Scanner sc = new Scanner(System.in);
	private static QuadTree quadTree;
	
    public static void main( String[] args ){
    	
    	
        try {

        	int choix = menu();
        			
        	
        	if(choix>1 && choix <8 && png != null || choix == 1) {
                       
                switch(choix){
     	           case 1: loadImg();break;
     	           case 2: delta();break;
     	           case 3: phi();break;
     	           case 4: savePng();break;
     	           case 5: saveTxt();break;
     	           case 6: compare();break;
     	           

                }
        	}else {
        		System.out.println("Le choix n'existe pas ou vous devez charger une image!");
        	}
        	System.out.println("\n");
        	if(choix != 7) {
        		main(args);
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static int menu() {
		
		System.out.println("Menu traitement d'image");
    	System.out.println("	1: Charge une image");
    	System.out.println("	2: Apllique une compression Delta");
    	System.out.println("	3: Applique une compression Phi");
    	System.out.println("	4: Sauvegarde le quadtree dans un fichier PNG");
    	System.out.println("	5: Sauvegarde la représentation textuelle du quadtree dans un fichier TXT");
    	System.out.println("	6: Donne les mesures comparative de deux fichiers images PNG");
    	System.out.println("	7: Arreter le programme");
    	System.out.print("\nEntrez un choix : ");
    	
		return sc.nextInt();
	}

	private static void loadImg() throws IOException {
		
		int choix;
		String adress = "./src/pngs/";
		
		System.out.println("Chargement d'une image");
    	System.out.println("	1: 1024-cube.png");
    	System.out.println("	2: 128-gnu.png");
    	System.out.println("	3: 16.png");
    	System.out.println("	4: 2.png");
    	System.out.println("	5: 256-trash.png");
    	System.out.println("	6: 256-tux.png");
    	System.out.println("	7: 32-tux.png");
    	System.out.println("	8: 4.png");
    	System.out.println("	9: 512-books.png");
    	System.out.println("	10: 64-tuxette.png");
    	System.out.println("	11: 8-tux.png");
    	System.out.println("	12: i.png");
    	System.out.print("\nEntrez un choix : ");
    	
    	choix = sc.nextInt();	
    	
    	if(choix>0 && choix <13) {
                   
    		switch(choix){
	           case 1: adress += "1024-cube.png";break;
	           case 2: adress += "128-gnu.png";break;
	           case 3: adress += "16.png";break;
	           case 4: adress += "2.png";break;
	           case 5: adress += "256-trash.png";break;
	           case 6: adress += "256-tux.png";break;
	           case 7: adress += "32-tux.png";break;
	           case 8: adress += "4.png";break;
	           case 9: adress += "512-books.png";break;
	           case 10: adress += "64-tuxette.png";break;
	           case 11: adress += "8-tux.png";break;
	           case 12: adress += "i.png";break;
	           
            }
    		
    		png = new ImagePNG(adress);
    		quadTree = new QuadTree(png, png.width()/2, png.height()/2, png.width()/2);
    		System.out.println("\nChargement réussi\n");
    		
    	}else {
    		System.out.println("\nLe choix n'existe pas !\n");
    		loadImg();
    	}
		
		
	}
	private static void delta() {
		
		System.out.println("Choisissez un delta pour la compression : ");
		int delta = sc.nextInt();
		
		quadTree.compressDelta(delta);
		
		System.out.println("\nCompressé\n");
		
	}
	private static void phi() {
		// TODO Auto-generated method stub
		
	}
	private static void savePng() throws IOException {
		
		ImagePNG newPng = quadTree.toPNG(png, quadTree);
		
        newPng.save("./src/result/result.png");
        System.out.println("\nAjout réussi\n");
		
	}
	
	/**
	 * @role: 
	 * @throws IOException
	 */
	private static void saveTxt() throws IOException {
		File dataFile = new File("./src/result/result.txt");
		
		if (!dataFile.exists()) {
			dataFile.delete();
		    dataFile.createNewFile();
		}
		 FileWriter fw = new FileWriter(dataFile.getAbsoluteFile());
		 BufferedWriter bw = new BufferedWriter(fw);
		 bw.write(quadTree.toString());
		 bw.close();

		 System.out.println("\nAjout réussi\n");
		
	}
	private static void compare() {
		// TODO Auto-generated method stub
		
	}

}
