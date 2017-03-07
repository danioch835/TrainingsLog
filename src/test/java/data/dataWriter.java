package data;

import gym.*;

public interface dataWriter {
	public int addTraining(Training training);
	public void addExercise(Exercise exercise);
	public void addWeights(Weights weights);
	public void addResult(Results results, String categoryName);
	public void close();
}
