import java.awt.Color;
import java.util.ArrayList;

public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE; // NO=1 , NE = 2, SO = 4 , SE = 3
	private ImagePNG img;
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur , int car les images sont de taille 2^n
	private boolean vide;

	public QuadTree(ImagePNG img){
		
		this.img = img;
		this.centerX = img.width()/2;
		this.centerY = img.width()/2;
		this.widthX = img.width()/2;
		this.vide = false;
		constructQuadTree();
	}
	public QuadTree(ImagePNG img, Integer centerX, Integer centerY, Integer widthX){
		this.centerX = centerX;
		this.centerY = centerY;
		this.widthX = widthX;
		this.img = img;
		this.vide = false;
		constructQuadTree();
	}
	/**
	 * @role:
	 * @return:
	 */
	private void constructQuadTree() {
		
		if(img.width() > 1) {
			
			this.filsNO = new QuadTree(this.img, this.centerX/2              ,this.centerY/2              ,this.widthX/2);
			this.filsNE = new QuadTree(this.img, this.centerX/2+this.centerX ,this.centerY/2              ,this.widthX/2);
			this.filsSO = new QuadTree(this.img, this.centerX/2              ,this.centerY/2+this.centerY ,this.widthX/2);
			this.filsSE = new QuadTree(this.img, this.centerX/2+this.centerX ,this.centerY/2+this.centerY ,this.widthX/2);
			
		}else {
			this.vide = true;
		}
		
	}
	
	private Color avgColor () {
		Color avgColorNO = new Color (0,0,0);
		Color avgColorNE = new Color (0,0,0);
		Color avgColorSO = new Color (0,0,0);
		Color avgColorSE = new Color (0,0,0);
		
		avgColorNO =filsNO.img.getPixel(this.centerX , this.centerY) ;
		avgColorNE =filsNE.img.getPixel(this.centerX , this.centerY) ;
		avgColorSO =filsSO.img.getPixel(this.centerX , this.centerY) ;
		avgColorSE =filsSE.img.getPixel(this.centerX , this.centerY) ;

		int avgColorRed   = (avgColorNO.getRed()   + avgColorNE.getRed()   + avgColorSO.getRed()   + avgColorSE.getRed()   )  /4;
		int avgColorBlue  = (avgColorNO.getBlue()  + avgColorNE.getBlue()  + avgColorSO.getBlue()  + avgColorSE.getBlue()  )  /4;
		int avgColorGreen = (avgColorNO.getGreen() + avgColorNE.getGreen() + avgColorSO.getGreen() + avgColorSE.getGreen() )  /4;
		Color avgColor = new Color (avgColorRed, avgColorGreen , avgColorBlue );
		return avgColor;
	}
	
	
	private double colorimetricDifference () {
		Color avgColorNO = new Color (0,0,0);
		Color avgColorNE = new Color (0,0,0);
		Color avgColorSO = new Color (0,0,0);
		Color avgColorSE = new Color (0,0,0);
		
		avgColorNO =filsNO.img.getPixel(this.centerX , this.centerY) ;
		avgColorNE =filsNE.img.getPixel(this.centerX , this.centerY) ;
		avgColorSO =filsSO.img.getPixel(this.centerX , this.centerY) ;
		avgColorSE =filsSE.img.getPixel(this.centerX , this.centerY) ;
		
		int Ri = (avgColorNO.getRed()  + avgColorNE.getRed()   + avgColorSO.getRed()   + avgColorSE.getRed());
		int Rm = this.avgColor().getRed();
		int Bi = (avgColorNO.getBlue()  + avgColorNE.getBlue()  + avgColorSO.getBlue()  + avgColorSE.getBlue());
		int Bm = this.avgColor().getBlue();
		int Vi = (avgColorNO.getGreen() + avgColorNE.getGreen() + avgColorSO.getGreen() + avgColorSE.getGreen());
		int Vm =this.avgColor().getGreen();
		
		int Rr = (Ri - Rm ) * (Ri - Rm );
		int Vr = (Vi - Vm ) * (Vi - Vm );
		int Br = (Bi - Bm ) * (Bi - Bm );
		double gamma = Math.sqrt((Rr+Vr+Br)/ 3 ) ;
		return gamma;
	}
	
	public void compressDelta(Integer delta) {
		double test = this.colorimetricDifference();
		if ( test  <= delta) {
			// replace 4 nodes by the moyen color
		}
	}
	
	public void compressPhi(Integer phi) {
		//if ( this.widthx <= phi)){
			// replace 4 nodes 
		//}
	}
	
	public void toPng() {
		
	}
	
	public void toString(QuadTree quadTree) {
		if(quadTree.vide != false) {
			if(quadTree.filsSE.vide != false ) {
				toString(quadTree.filsNO);
				
			}
		}	
	}
}
