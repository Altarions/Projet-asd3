import java.awt.Color;
import java.util.ArrayList;

public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE; // NO=1 , NE = 2, SO = 4 , SE = 3
	private ImagePNG img;
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur , int car les images sont de taille 2^n
	private boolean vide;
	
	/**
	 * @role: constructor of QuadTree class with un param.
	 * @param img: 
	 */
	public QuadTree(ImagePNG img){
		
		this.setImg(img);
		this.centerX = img.width()/2;
		this.centerY = img.width()/2;
		this.widthX = img.width()/2;
		this.vide = false;
		constructQuadTree();
	}
	/**
	 * @role:
	 * @param img:
	 * @param centerX:
	 * @param centerY:
	 * @param widthX:
	 */
	public QuadTree(ImagePNG img, Integer centerX, Integer centerY, Integer widthX){
		this.centerX = centerX;
		this.centerY = centerY;
		this.widthX = widthX;
		this.setImg(img);
		this.vide = false;
		constructQuadTree();
	}
	/**
	 * @role:
	 * 
	 */
	private void constructQuadTree() {
		
		if(this.widthX > 1) {
			Integer a = this.widthX/2;

			this.filsNO = new QuadTree(this.getImg(), this.centerX-a, this.centerY-a, a);
			this.filsNE = new QuadTree(this.getImg(), this.centerX+a, this.centerY-a, a);
			this.filsSO = new QuadTree(this.getImg(), this.centerX-a, this.centerY+a, a);
			this.filsSE = new QuadTree(this.getImg(), this.centerX+a, this.centerY+a, a);
			
		}else {
			if(this.vide != true && this.widthX == 1) {
				this.filsNO = new QuadTree(this.getImg(), this.centerX-1, this.centerY-1, 0);
				this.filsNE = new QuadTree(this.getImg(), this.centerX, this.centerY-1, 0);
				this.filsSO = new QuadTree(this.getImg(), this.centerX-1, this.centerY, 0);
				this.filsSE = new QuadTree(this.getImg(), this.centerX, this.centerY, 0);
				
			}else {
				this.vide = true;
			}
		}
		
	}
	
	private Color avgColor () {
		Color avgColorNO = new Color (0,0,0);
		Color avgColorNE = new Color (0,0,0);
		Color avgColorSO = new Color (0,0,0);
		Color avgColorSE = new Color (0,0,0);
		
		avgColorNO =filsNO.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorNE =filsNE.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorSO =filsSO.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorSE =filsSE.getImg().getPixel(this.centerX , this.centerY) ;

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
		
		avgColorNO =filsNO.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorNE =filsNE.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorSO =filsSO.getImg().getPixel(this.centerX , this.centerY) ;
		avgColorSE =filsSE.getImg().getPixel(this.centerX , this.centerY) ;
		
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
	
	public ImagePNG toPng() {
		ImagePNG newImg = new ImagePNG(this.img);
		
		return newImg;
	}
	
	public void toString(QuadTree quadTree) {
		;
		if(quadTree.vide != true) {
			System.out.print("(");
			toString(quadTree.filsNO);
			System.out.print(" ");
			toString(quadTree.filsNE);
			System.out.print(" ");
			toString(quadTree.filsSO);
			System.out.print(" ");
			toString(quadTree.filsSE);
			System.out.print(")");
				
		}else {
			System.out.print(ImagePNG.colorToHex(quadTree.getImg().getPixel(quadTree.centerX, quadTree.centerY)));
		}
	}
	public ImagePNG getImg() {
		return img;
	}
	public void setImg(ImagePNG img) {
		this.img = img;
	}
}
