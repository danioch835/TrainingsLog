package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gym.*;

public class SQLiteWriter implements dataWriter {
	
	private Connection connection = null;
	private Statement statement = null;
	private String databaseName = "gym.db";
	
	
	public SQLiteWriter() {
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
	
	public int addTraining(Training training) {
		String query = "INSERT INTO training (id, date, dayOfWeek) VALUES(NULL,date(?),?);";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, df.format(training.getDate()));
			preparedStatement.setString(2, training.getDayOfWeek());
			preparedStatement.execute();
			
			query = "SELECT last_insert_rowid() as id;";
			ResultSet result = statement.executeQuery(query);
			int lastId = result.getInt("id");
			return lastId;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void addExercise(Exercise exercise) {
		String query = "INSERT INTO exercise (id, name, category) VALUES(NULL,?,?);";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, exercise.getName());
			preparedStatement.setString(2, exercise.getCategory().toString());
			preparedStatement.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addWeights(Weights weights) {
		String query;
		if(weights.getNumberOfSeries() == 3) {
			query = "INSERT INTO weights (id, serie1, serie2, serie3) VALUES(NULL,?,?,?);";
		} else {
			query = "INSERT INTO weights (id, serie1, serie2, serie3, serie4) VALUES(NULL,?,?,?,?);";
		}
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ArrayList<Double> weightsToAdd = weights.getSeriesWeight();
			int i = 1;
			for(Double weight : weightsToAdd) {
				preparedStatement.setDouble(i++, weight);
			}
			preparedStatement.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addResult(Results result, String categoryName) {
		String query = "INSERT INTO " + categoryName.toLowerCase() + " (trainingID, exerciseID, weightID) VALUES(?,?,?);";
		
		System.out.println(categoryName.toLowerCase());
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, result.getTrainingID());
			preparedStatement.setInt(2, result.getExerciseID());
			preparedStatement.setInt(3, result.getWeightsID());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
