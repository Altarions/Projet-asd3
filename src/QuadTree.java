import java.util.ArrayList;

public class QuadTree {
	
	private QuadTree filsNO, filsNE, filsSO, filsSE;
	private ImagePNG img;
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur
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
		
		if(img.width()>1) {
			
			this.filsNO = new QuadTree(this.img, this.centerX/2, this.centerY/2, this.widthX/2);
			this.filsNE = new QuadTree(this.img, this.centerX/2+this.centerX, this.centerY/2, this.widthX/2);
			this.filsSO = new QuadTree(this.img, this.centerX/2, this.centerY/2+this.centerY, this.widthX/2);
			this.filsSE = new QuadTree(this.img, this.centerX/2+this.centerX, this.centerY/2+this.centerY, this.widthX/2);
			
		}else {
			this.vide = true;
		}
		
	}
	
	public void compressDelta(Integer delta) {}
	
	public void compressPhi(Integer phi) {
		
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
