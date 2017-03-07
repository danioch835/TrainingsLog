package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import gym.Exercise;
import gym.Training;
import gym.Weights;
import gym.Exercise.CATEGORY_TYPE;

public class SQLiteReader implements dataReader {
	
	private Connection connection;
	private Statement statement;
	private String databaseName = "gym.db";
	
	public SQLiteReader() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
			statement = connection.createStatement();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Exercise> loadExercisesFromTraining(Training training) {
		ArrayList<Exercise> exercises =  new ArrayList<Exercise>();
		String query = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date trainingDate = training.getDate();
		String trainingDateString = sdf.format(trainingDate);
		for(CATEGORY_TYPE category : CATEGORY_TYPE.values()) {
			query = "SELECT e.name,e.category,w.serie1,w.serie2,w.serie3,w.serie4 from " + category.toString().toLowerCase() + " as c JOIN training as t ON c.trainingID=t.id JOIN exercise as e ON e.id=c.exerciseID JOIN weights as w ON w.id=weightID WHERE t.date=date(?)";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, trainingDateString);
				ResultSet result = preparedStatement.executeQuery();
				
				while(result.next()) {
					ArrayList<Double> series = new ArrayList<Double>();
					series.add(result.getDouble("serie1"));
					series.add(result.getDouble("serie2"));
					series.add(result.getDouble("serie3"));
					series.add(result.getDouble("serie4"));
					Weights tempWeight = new Weights(series);
					String exerciseName = result.getString("name");
					String exerciseCategoryString = result.getString("category");
					CATEGORY_TYPE exerciseCategory = CATEGORY_TYPE.valueOf(exerciseCategoryString);
					Exercise tempExercise = new Exercise(exerciseName, exerciseCategory, tempWeight);
					exercises.add(tempExercise);
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return exercises;
	}
	
	public ArrayList<Exercise> loadExercisesFromCategory(CATEGORY_TYPE category) {
		ArrayList<Exercise> exercisesList = new ArrayList<Exercise>();
		
		String query = "SELECT id, name FROM exercise WHERE category=?;";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, category.toString());
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				exercisesList.add(new Exercise(id, name, category));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return exercisesList;
	}
	public ArrayList<Training> loadTrainings() {
		ArrayList<Training> trainingsList = new ArrayList<Training>();
		
		String query = "SELECT id, date, dayOfWeek FROM training ORDER BY date DESC";
		
		try {
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				int id = result.getInt("id");
				String stringDate = result.getString("date");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(stringDate);
				trainingsList.add(new Training(id, date));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		return trainingsList;
	}
	public ArrayList<Weights> loadWeights() {
		ArrayList<Weights> weightsList = new ArrayList<Weights>();
		
		String query = "SELECT id, serie1, serie2, serie3, serie4 FROM weights ORDER BY serie1 DESC, serie2 DESC, serie3 DESC, serie4 DESC;";
		Double[] serie = new Double[4];
		
		try {
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				int id = result.getInt("id");
				serie[0] = result.getDouble("serie1");
				serie[1] = result.getDouble("serie2");
				serie[2] = result.getDouble("serie3");
				serie[3] = result.getDouble("serie4");
				
				ArrayList<Double> weights = new ArrayList<Double>(Arrays.asList(serie));
				if(weights.get(weights.size()-1) == 0) {
					weights.remove(weights.size()-1);
				}
				weightsList.add(new Weights(id, weights));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return weightsList;
	}
	
	public void close() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
