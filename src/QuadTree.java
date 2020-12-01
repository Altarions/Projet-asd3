import java.awt.Color;
import java.util.ArrayList;

public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE; // NO=1 , NE = 2, SO = 4 , SE = 3
	private ImagePNG img;
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur , int car les images sont de taille 2^n
	private boolean vide; 

	public QuadTree(ImagePNG img){
		
		this.setImg(img);
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
		this.setImg(img);
		this.vide = false;
		constructQuadTree();
	}
	/**
	 * @role:
	 * @return:
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
	
	public boolean hasNoKids() {
		return  this.filsNO.vide && this.filsSE.vide && this.filsNE.vide && this.filsSE.vide ;
	}
	
	public int leavesNumber() {
		int res = 0;
		if(this.vide) {
			res++;
		}else {
			res = this.filsNE.leavesNumber()  + this.filsNO.leavesNumber() + this.filsSO.leavesNumber() +  this.filsSE.leavesNumber() ;
		}
		return res;
	}
	
	public Color avgColor () {
		Color avgColorNO =filsNO.getImg().getPixel(this.filsNO.centerX , this.filsNO.centerY) ;
		Color avgColorNE =filsNE.getImg().getPixel(this.filsNE.centerX , this.filsNE.centerY) ;
		Color avgColorSO =filsSO.getImg().getPixel(this.filsSO.centerX , this.filsSO.centerY) ;
		Color avgColorSE =filsSE.getImg().getPixel(this.filsSE.centerX , this.filsSE.centerY) ;

		int avgColorRed   = (avgColorNO.getRed()   + avgColorNE.getRed()   + avgColorSO.getRed()   + avgColorSE.getRed()   )  /4;
		int avgColorBlue  = (avgColorNO.getBlue()  + avgColorNE.getBlue()  + avgColorSO.getBlue()  + avgColorSE.getBlue()  )  /4;
		int avgColorGreen = (avgColorNO.getGreen() + avgColorNE.getGreen() + avgColorSO.getGreen() + avgColorSE.getGreen() )  /4;
		Color avgColor = new Color (avgColorRed, avgColorGreen , avgColorBlue );
		return avgColor;
	}
	
	
	private int colorimetricDifferenceOne (Color x) {
		
		int Ri = (this.getImg().getPixel(this.centerX, this.centerY).getRed());
		int Rm = x.getRed();
		int Bi = (this.getImg().getPixel(this.centerX, this.centerY).getBlue());
		int Bm = x.getBlue();
		int Vi = (this.getImg().getPixel(this.centerX, this.centerY).getGreen());
		int Vm = x.getGreen();
		
		int Rr = (Ri - Rm ) * (Ri - Rm );
		int Vr = (Vi - Vm ) * (Vi - Vm );
		int Br = (Bi - Bm ) * (Bi - Bm );
		int gamma = (int) Math.sqrt((Rr+Vr+Br)/ 3 ) ;
		return gamma;
	}
	public int colorimetricDifference(Color x) {
		 int res1 =  Math.max(filsNO.colorimetricDifferenceOne(x) , filsNE.colorimetricDifferenceOne(x));
		 int res2 =  Math.max(filsSO.colorimetricDifferenceOne(x) , filsSE.colorimetricDifferenceOne(x));
		 int res =   Math.max(res1 ,res2);
		 return res;
	}
	
	public void compressDelta(Integer delta) {
		if (delta > 192 || delta < 0) {
			System.out.println("detla's value is wrong");
		}else {
			int test = (int) this.colorimetricDifference(avgColor());
			Color deltaColor = new Color(test,test,test);
			if ( test  <= delta) {
				// replace 4 nodes by the avg color
				this.deleteKids();
				this.img.setPixel(this.centerX,this.centerY, deltaColor);
			}
		}
	}
	
	public void compressPhi(Integer phi) {
		//if ( this.widthx <= phi)){
			// replace 4 nodes 
		//}
	}
	public void deleteKids() {
		this.filsNE = null;
		this.filsNO = null;
		this.filsSE = null;
		this.filsSO = null;
	}
	
	public void toPng() {
		
	}
	
	public void toString(QuadTree quadTree) {
		if(quadTree.vide != true) {
			System.out.print("(");
			toString(quadTree.filsNO);
			toString(quadTree.filsNE);
			toString(quadTree.filsSO);
			toString(quadTree.filsSE);
			System.out.print(")");
				
		}else {
			System.out.print((quadTree.getImg().getPixel(quadTree.centerX, quadTree.centerY))); //ImagePNG.colorToHex
			System.out.print("  ");
		}
	}
	public ImagePNG getImg() {
		return img;
	}
	public void setImg(ImagePNG img) {
		this.img = img;
	}
	public QuadTree getfilsNO() {
		return filsNO;
	}
	public QuadTree getfilsNE() {
		return filsNE;
	}
	public QuadTree getfilsSO() {
		return filsSO;
	}
	public QuadTree getfilsSE() {
		return filsSE;
	}
	public int getcenterX() {
		return centerX;
	}
	public int getcenterY() {
		return centerY;
	}
}
