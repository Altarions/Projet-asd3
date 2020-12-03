import java.awt.Color;


public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE; // NO=1 , NE = 2, SO = 4 , SE = 3
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur , int car les images sont de taille 2^n
	private boolean vide;
	private Color colorPixel;
	
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
		this.setVide(false);
		
		if(this.widthX > 1) {
			Integer a = this.widthX/2;

			this.filsNO = new QuadTree(img, this.centerX-a, this.centerY-a, a);
			this.filsNE = new QuadTree(img, this.centerX+a, this.centerY-a, a);
			this.filsSO = new QuadTree(img, this.centerX-a, this.centerY+a, a);
			this.filsSE = new QuadTree(img, this.centerX+a, this.centerY+a, a);
			
		}else {
			if(this.isVide() != true && this.widthX == 1) {
				this.filsNO = new QuadTree(img, this.centerX-1, this.centerY-1, 0);
				this.filsNE = new QuadTree(img, this.centerX, this.centerY-1, 0);
				this.filsSO = new QuadTree(img, this.centerX-1, this.centerY, 0);
				this.filsSE = new QuadTree(img, this.centerX, this.centerY, 0);
				
			}else {
				this.setVide(true);
				this.setColorPixel(img.getPixel(this.centerX, this.centerY));
			}
		}
	}
	
	public QuadTree getfilsNO() {return filsNO;}
	public QuadTree getfilsNE() {return filsNE;}
	public QuadTree getfilsSO() {return filsSO;}
	public QuadTree getfilsSE() {return filsSE;}
	
	public int getcenterX() {return centerX;}
	public int getcenterY() {return centerY;}
	
	public boolean isVide() {return vide;}
	public void setVide(boolean vide) {this.vide = vide;}
	
	public Color getColorPixel() {return colorPixel;}
	public void setColorPixel(Color colorPixel) {this.colorPixel = colorPixel;}
	
	public boolean hasNoKids() {
		return  this.filsNO.isVide() && this.filsSE.isVide() && this.filsNE.isVide() && this.filsSE.isVide() ;
	}
	
	public int leavesNumber() {
		int res = 0;
		if(this.isVide()) {
			res++;
		}else {
			res = this.filsNE.leavesNumber()  + this.filsNO.leavesNumber() + this.filsSO.leavesNumber() +  this.filsSE.leavesNumber() ;
		}
		return res;
	}
	
	public Color avgColor () {
		Color avgColorNO =filsNO.getColorPixel();
		Color avgColorNE =filsNE.getColorPixel();
		Color avgColorSO =filsSO.getColorPixel();
		Color avgColorSE =filsSE.getColorPixel();

		int avgColorRed   = (avgColorNO.getRed()   + avgColorNE.getRed()   + avgColorSO.getRed()   + avgColorSE.getRed()   )  /4;
		int avgColorBlue  = (avgColorNO.getBlue()  + avgColorNE.getBlue()  + avgColorSO.getBlue()  + avgColorSE.getBlue()  )  /4;
		int avgColorGreen = (avgColorNO.getGreen() + avgColorNE.getGreen() + avgColorSO.getGreen() + avgColorSE.getGreen() )  /4;
		Color avgColor = new Color (avgColorRed, avgColorGreen , avgColorBlue );
		return avgColor;
	}
	
	
	private int colorimetricDifferenceOne (Color x) {
		
		int Ri = (this.getColorPixel().getRed());
		int Rm = x.getRed();
		int Bi = (this.getColorPixel().getBlue());
		int Bm = x.getBlue();
		int Vi = (this.getColorPixel().getGreen());
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
				//this.img.setPixel(this.centerX,this.centerY, deltaColor);
			}
		}
	}
	
	
	
	public void compressPhi(Integer phi) {
		//if ( this.widthx <= phi)){
			// replace 4 nodes 
		//}
	}
	public void deleteKids() {
		this.vide = true;
		this.filsNE = null;
		this.filsNO = null;
		this.filsSE = null;
		this.filsSO = null;
	}
	
	public ImagePNG toPNG(ImagePNG img, QuadTree quadTree) {
		ImagePNG newImg =  new ImagePNG(img);
		
		if(quadTree.isVide() != true) {
			newImg = toPNG(newImg, quadTree.filsNO);
			newImg = toPNG(newImg, quadTree.filsNE);
			newImg = toPNG(newImg, quadTree.filsSO);
			newImg = toPNG(newImg, quadTree.filsSE);
				
		}else {
			newImg.setPixel(quadTree.centerX, quadTree.centerY, quadTree.colorPixel);
		}
		
		return newImg;
	}
	
	public String toString(String chaine, QuadTree quadTree) {
		if(quadTree.isVide() != true) {
			chaine += ("(");
			chaine = toString(chaine, quadTree.filsNO);
			chaine += (" ");
			chaine = toString(chaine, quadTree.filsNE);
			chaine += (" ");
			chaine = toString(chaine, quadTree.filsSO);
			chaine += (" ");
			chaine = toString(chaine, quadTree.filsSE);
			chaine += (")");
				
		}else {
			chaine += ImagePNG.colorToHex(quadTree.getColorPixel());
			
		}
		return chaine;
	}

}
