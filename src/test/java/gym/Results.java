package gym;

public class Results {
	private int trainingID;
	private int exerciseID;
	private int weightsID;
	
	public Results(int trainingID, int exerciseID, int weightsID) {
		this.trainingID = trainingID;
		this.exerciseID = exerciseID;
		this.weightsID = weightsID;
	}
	
	public int getTrainingID() {
		return trainingID;
	}
	
	public int getExerciseID() {
		return exerciseID;
	}
	
	public int getWeightsID() {
		return weightsID;
	}
	
}
