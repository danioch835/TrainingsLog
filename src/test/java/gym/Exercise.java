package gym;

public class Exercise {
	private int id;
	private String name;
	private CATEGORY_TYPE category;
	private Weights weights;
	
	public Exercise(int id, String name, CATEGORY_TYPE category, Weights weights) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.weights = weights;
	}
	
	public Exercise(int id, String name, CATEGORY_TYPE category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	public Exercise(String name, CATEGORY_TYPE category, Weights weights) {
		this.name = name;
		this.category = category;
		this.weights = weights;
	}
	
	public Exercise(String name, CATEGORY_TYPE category) {
		this.name = name;
		this.category = category;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCategory(CATEGORY_TYPE category) {
		this.category = category;
	}
	
	public CATEGORY_TYPE getCategory() {
		return category;
	}
	
	public void setWeights(Weights weights) {
		this.weights = weights;
	}
	
	public Weights getWeights() {
		return weights;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public enum CATEGORY_TYPE {
		KLATKA,
		TRICEPS,
		PLECY,
		BICEPS,
		BARKI,
		NOGI,
		BRZUCH
	}
	
}
