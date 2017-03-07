package gym;

import java.util.ArrayList;

public class Weights {
	private int id;
	private int numberOfSeries;
	ArrayList<Double> seriesWeight;
	
	public Weights(int id, ArrayList<Double> seriesWeight) {
		this.id = id;
		this.seriesWeight = seriesWeight;
	}
	
	public Weights(ArrayList<Double> seriesWeight) {
		this.seriesWeight = seriesWeight;
		setNumberOfSeries();
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	private void setNumberOfSeries() {
		this.numberOfSeries = seriesWeight.size();
	}
	
	public int getNumberOfSeries() {
		return numberOfSeries;
	}
	
	public void setSeriesWeight(ArrayList<Double> seriesWeight) {
		this.seriesWeight = seriesWeight;
		setNumberOfSeries();
	}
	
	public ArrayList<Double> getSeriesWeight() {
		return seriesWeight;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Double weight : seriesWeight) {
			result += Double.toString(weight) + " - ";
		}
		result = result.substring(0, result.length()-2);
		return result;
	}
	
}
