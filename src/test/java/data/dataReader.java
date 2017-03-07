package data;

import java.util.ArrayList;

import gym.*;
import gym.Exercise.CATEGORY_TYPE;

public interface dataReader {
	public ArrayList<Exercise> loadExercisesFromTraining(Training training);
	public ArrayList<Exercise> loadExercisesFromCategory(CATEGORY_TYPE category);
	public ArrayList<Training> loadTrainings();
	public ArrayList<Weights> loadWeights();
	public void close();
}
