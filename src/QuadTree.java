import java.util.ArrayList;

public class QuadTree {
	
	private ArrayList<QuadTree> listQuadTree;//filsNO,NE,SO,SE
	private ImagePNG img;
	private Integer centerX, centerY, widthX; //centre du quadTree et largeur

	public QuadTree(ImagePNG img){
		
		this.img = img;
		this.centerX = img.width()/2;
		this.centerY = img.width()/2;
		this.widthX = img.width()/2;
		this.listQuadTree = constructQuadTree();
	}
	public QuadTree(ImagePNG img, Integer centerX, Integer centerY, Integer widthX){
		this.centerX = centerX;
		this.centerY = centerY;
		this.widthX = widthX;
		this.img = img;
		this.listQuadTree = constructQuadTree();
	}
	/**
	 * @role:
	 * @return:
	 */
	private ArrayList<QuadTree> constructQuadTree() {
		ArrayList<QuadTree> list = new ArrayList<QuadTree>();
		
		if(img.width()>0 ) {
			
			for(int i=0; i<4; i++) {
				
			}
		}
		
		
		return list;
		
	}
	
	public void compressDelta(Integer delta) {}
	
	public void compressPhi(Integer phi) {
		
	}
	
	public void toPng() {
		
	}
	
	public String toString() {
		return "";
	}
}
