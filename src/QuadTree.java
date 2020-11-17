import java.util.ArrayList;

public class QuadTree {
	
	private ArrayList<QuadTree> listQuadTree;
	private ImagePNG img;

	public QuadTree(ImagePNG img){
		this.img = img;
		this.listQuadTree = constructQuadTree();
	}
	/**
	 * @role:
	 * @return:
	 */
	private ArrayList<QuadTree> constructQuadTree() {
		ArrayList<QuadTree> list = new ArrayList<QuadTree>();
		
		if(img.width()>0) {
			
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
