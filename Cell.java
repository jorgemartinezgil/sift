
public class Cell {
	private int level;
	private int numChildren;
	private int numBrothers;

	private int numLeftBrothers;
	private int numSameLvl;

	private String name;
	
	public Cell (int lvl, int nChild, int nBro, int nLeftBro, int nSameLvl, String nam){
		this.level = lvl;
		this.numChildren = nChild;
		this.numBrothers = nBro;
		this.numLeftBrothers= nLeftBro;
		this.numSameLvl = nSameLvl;
		this.name = nam;
	}
	
	/**
	 * Now we are going to implement all the access methods
	 */
	
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int lvl){
		this.level = lvl;	
		}
	
	
	
	public int getNumChildren(){
		return this.numChildren;
	}
	public void setNumChildren(int nChild){
		this.numChildren = nChild;
	}
	
	public int getNumSameLevel(){
		return this.numSameLvl;
	}
	public void setNumSameLevel(int nSameLvl){
		this.numSameLvl = nSameLvl;
	}
	
	
	
	public int getNumBrothers(){
		return this.numBrothers;
	}
	public void setNumBrothers(int nBro){
		this.numBrothers = nBro;
	}
	
	
	
	public int getNumLeftBrothers(){
		return this.numLeftBrothers;
	}
	public void setNumLeftBrothers(int nLBro){
		this.numLeftBrothers= nLBro;
	}
	
	
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String nam){
		this.name = nam;
	}
	
	
	
}
