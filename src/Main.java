import java.awt.Color;
import java.io.File;


public class Main {
	
    public static void main( String[] args )
    {
        try {

            ImagePNG png = new ImagePNG("./src/pngs/4.png");
            
            QuadTree test = new QuadTree(png);
            
            test.toString(test);
            System.out.println(test.leavesNumber());
            System.out.println(test.avgColor());
            System.out.println(test.getfilsSE().getImg().getPixel(test.getfilsSE().getcenterX() , test.getfilsSE().getcenterY()));
            System.out.println(test.colorimetricDifference(test.avgColor()));
            test.getfilsNO().compressDelta(129);
            test.toString(test);
            /*
            ImagePNG pngNB = new ImagePNG(png); // une copie
            ImagePNG pngR = new ImagePNG(png); // une autre copie

            // 1. MODIFICATION DES COULEURS

            Color col,nb,red;
            int r,g,b,l;
            for (int x=0 ; x<png.width(); x++)
            {
                for (int y=0 ; y<png.height() ; y++)
                {
                    // extraction de la couleur du pixel (x,y)
                    col = png.getPixel(x,y);
                    // modification de la couleur
                    r = col.getRed();
                    g = col.getGreen();
                    b = col.getBlue();
                    l = (r+g+b)/3;
                    nb = new Color(l,l,l);
                    red = new Color(l,0,0);
                    // inscription de la nouvelle couleur des pixels (x,y)
                    pngNB.setPixel(x,y,nb);
                    pngR.setPixel(x,y,red);
                }
            }
            png.save("./src/pngs/128-gnu.png");
            pngNB.save("./src/pngs/128-gnu.png"+"NB");
            pngR.save("./src/pngs/128-gnu.png"+"R");

            // 2. COMPARAISON DES RéSULTATS

            // calcul des indices de similarité
            double siNB = ImagePNG.computeEQM(png,pngNB);
            double siR = ImagePNG.computeEQM(png,pngR);

            // chargement des fichiers
            File fic = new File("./src/pngs/128-gnu.png");
            File ficNB =  new File("./src/pngs/128-gnu.png"+"NB");
            File ficR =  new File("./src/pngs/128-gnu.png"+"R");

            // rapport des tailles
            double wNB = Math.ceil(10000.0*ficNB.length() / fic.length())/100.0;
            double wR = Math.ceil(10000.0*ficR.length() / fic.length())/100.0;

            // affichage des critÃ¨res

            System.out.println("NB: taille="+wNB+"% / qualité="+siNB+"%");
            System.out.println("R: taille="+wR+"% / qualité="+siR+"%");
			

            System.out.println("NB: taille="+wNB+"% / qualité="+siNB+"%");
            System.out.println("R: taille="+wR+"% / qualité="+siR+"%");
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
