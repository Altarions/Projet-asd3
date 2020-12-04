import java.awt.Color;


public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE; // NO=1 , NE = 2, SO = 4 , SE = 3
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur , int car les images sont de taille 2^n
	private boolean vide;
	private Color colorPixel;
	
	/**
	 * @role: constructor of the QuadTree class, creates the compressed quadTree from an image.
	 * @param img: 
	 * @param centerX: position X of the quadTree
	 * @param centerY: position Y of the quadTree
	 * @param widthX:
	 * @throws Throwable 
	 */
	public QuadTree(ImagePNG img, Integer centerX, Integer centerY, Integer widthX) throws Throwable{
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
			this.compressWithNoLost();
		}else {
			if(this.isVide() == false && this.widthX == 1) {
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
	
	//GET AND SET
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
	
	public void compressWithNoLost() throws Throwable {
		if(this.hasNoKids() != true) {	
			this.filsNE.compressWithNoLost();
			this.filsNO.compressWithNoLost();
			this.filsSE.compressWithNoLost();
			this.filsSO.compressWithNoLost();
				
		}else {
			if(this.sameKids()) {
				System.out.println(this.avgColor());
				this.colorPixel = this.getfilsNE().colorPixel;
				this.deleteKids();		
			}		
		}
	}

	/**
	 * @role: compress the quadtree.
	 * @param delta
	 * @throws Throwable 
	 */
	public void compressDelta(Integer delta) throws Throwable {
        if (delta > 192 || delta < 0) {
            System.out.println("detla's value is wrong");
        }else {
        	if(this.hasNoKids() != true) {	
    			this.filsNE.compressDelta(delta);
    			this.filsNO.compressDelta(delta);
    			this.filsSE.compressDelta(delta);
    			this.filsSO.compressDelta(delta);
    				
    		}else {
    			if(colorimetricDifference(avgColor()) <= delta) {
    				this.colorPixel = this.avgColor();
    				this.deleteKids();
    				
    			}
    		}
        }
	}
	
	/**
	 * @role
	 * @param phi
	 * @throws Throwable 
	 */
	public void compressPhi(Integer phi) throws Throwable {
		if( phi < 0) {
			System.out.println("phi cant be negative");
			
		}else {
			if ( this.leavesNumber() > phi){
				
				if (this.hasNoKids()) {
					Color deltaColor = this.avgColor();
					this.setColorPixel(deltaColor);
					this.deleteKids();
				}else {
					filsNE.compressPhi(phi);
					filsNO.compressPhi(phi);
					filsSE.compressPhi(phi);
					filsSO.compressPhi(phi);
				}
			}
			
		}
				
	}
	
	/**
	 * @role
	 * @param img
	 * @param quadTree
	 * @return
	 */
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

	@SuppressWarnings("deprecation")
	public void deleteKids() throws Throwable {
		
		this.filsNE.finalize();
		this.filsNO.finalize();
		this.filsSE.finalize();
		this.filsSO.finalize();
		this.vide = true;

	}
	
	public boolean hasNoKids() {
		return  this.filsNO.isVide() && this.filsSE.isVide() && this.filsNE.isVide() && this.filsSE.isVide() ;
	}
	
	private boolean sameKids() {
	    return (this.getfilsNE().getColorPixel().equals(this.getfilsNO().getColorPixel()) && this.getfilsSE().getColorPixel().equals(this.getfilsSO().getColorPixel()) && this.getfilsNE().getColorPixel().equals(this.getfilsSE().getColorPixel())) ;
	}
	
	
	@Override
	public String toString() {
		
		if(this.isVide() != true) {
			return "("+this.filsNO.toString()+" "+this.filsNE.toString()+" "+this.filsSO.toString()+" "+this.filsSE.toString()+")";
				
		}else {
			return ImagePNG.colorToHex(this.getColorPixel());
			
		}
		

	}

}
