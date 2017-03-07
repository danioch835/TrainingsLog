package gui;

import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import data.SQLiteReader;
import data.SQLiteWriter;
import data.dataReader;
import data.dataWriter;
import gym.Exercise;
import gym.Exercise.CATEGORY_TYPE;
import gym.Results;
import javafx.scene.control.ComboBox;
import gym.Training;
import gym.Weights;

import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Component;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GymApplicationWindow {

	private JFrame frame;
	private JButton btnAddExercise;
	private JTextField exerciseName;
	private JButton btnAddWeight;
	private JTextField weightSerie1;
	private JTextField weightSerie2;
	private JTextField weightSerie3;
	private JTextField weightSerie4;
	private JTextField trainingDayOfWeek;

	private dataReader sqlReader;
	private dataWriter sqlWriter;
	private JTable table;
	private JTable trainingTable;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GymApplicationWindow window = new GymApplicationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GymApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		sqlWriter = new SQLiteWriter();
		sqlReader = new SQLiteReader();
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sqlWriter.close();
				sqlReader.close();
			}
		});
		frame.setBounds(100, 100, 692, 612);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 39, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(0, 23, 666, 539);
		frame.getContentPane().add(desktopPane);
		
		JComboBox displayTrainingDate = new JComboBox();
		displayTrainingDate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					displayTrainingExercises(table, (Training)displayTrainingDate.getSelectedItem());
				}
			}
		});
		displayTrainingDate.setBounds(23, 25, 136, 20);
		fillTrainingDatesBox(displayTrainingDate);
		desktopPane.add(displayTrainingDate);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 69, 426, 111);
		desktopPane.add(scrollPane);
		
		table = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.addColumn("Category");
		tableModel.addColumn("Exercise");
		tableModel.addColumn("Weight");
		scrollPane.setViewportView(table);
		
		JComboBox exerciseBox = new JComboBox();
		JComboBox weight = new JComboBox();
		
		JInternalFrame addExerciseFrame = new JInternalFrame("Add exercise");
		addExerciseFrame.setBounds(359, 113, 265, 180);
		desktopPane.add(addExerciseFrame);
		
		exerciseName = new JTextField();
		exerciseName.setColumns(10);
		
		JComboBox categoryBox = new JComboBox();
		categoryBox.setModel(new DefaultComboBoxModel(CATEGORY_TYPE.values()));
		
		btnAddExercise = new JButton("Add exercise");
		btnAddExercise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String exerciseToAddName = exerciseName.getText();
				CATEGORY_TYPE exerciseCategoryType = (CATEGORY_TYPE)categoryBox.getSelectedItem();
				Exercise exercise = new Exercise(exerciseToAddName, exerciseCategoryType);
				sqlWriter.addExercise(exercise);
				fillExercicesBox(exerciseBox, exerciseCategoryType);
			}
		});
		
		JButton btnCancelAddExercise = new JButton("Cancel");
		btnCancelAddExercise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addExerciseFrame.setVisible(false);
			}
		});
		
		
		JLabel lblExerciseName = new JLabel("Exercise name");
		
		JLabel lblCategory = new JLabel("Category");
		GroupLayout groupLayout = new GroupLayout(addExerciseFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(78)
					.addComponent(lblExerciseName)
					.addContainerGap(285, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(71)
					.addComponent(categoryBox, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(272, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(96)
					.addComponent(lblCategory)
					.addContainerGap(291, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(exerciseName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAddExercise)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCancelAddExercise, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
					.addGap(160))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCategory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(categoryBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(lblExerciseName)
					.addGap(11)
					.addComponent(exerciseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddExercise)
						.addComponent(btnCancelAddExercise))
					.addGap(76))
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnAddExercise, btnCancelAddExercise});
		addExerciseFrame.getContentPane().setLayout(groupLayout);
		
		JInternalFrame addWeightFrame = new JInternalFrame("Add weight");
		addWeightFrame.setBounds(23, 113, 206, 201);
		desktopPane.add(addWeightFrame);
		
		weightSerie1 = new JTextField();
		weightSerie1.setColumns(5);
		
		weightSerie2 = new JTextField();
		weightSerie2.setColumns(5);
		
		weightSerie4 = new JTextField();
		weightSerie4.setColumns(5);
		
		weightSerie3 = new JTextField();
		weightSerie3.setColumns(5);
		
		btnAddWeight = new JButton("Add weight");
		btnAddWeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Double> weights = new ArrayList<Double>();
				weights.add(Double.parseDouble(weightSerie1.getText()));
				weights.add(Double.parseDouble(weightSerie2.getText()));
				weights.add(Double.parseDouble(weightSerie3.getText()));
				if(!weightSerie4.getText().isEmpty()) {
					weights.add(Double.parseDouble(weightSerie4.getText()));
				}
				
				Weights weightsToAdd = new Weights(weights);
				sqlWriter.addWeights(weightsToAdd);
				fillWeightsBox(weight);
			}
		});
		
		JButton btnCancelAddWeight = new JButton("Cancel");
		btnCancelAddWeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWeightFrame.setVisible(false);
				weightSerie1.setText("");
				weightSerie2.setText("");
				weightSerie3.setText("");
				weightSerie4.setText("");
			}
		});
		
		JLabel lblSerie = new JLabel("Serie 1");
		
		JLabel lblSerie_1 = new JLabel("Serie 2");
		
		JLabel lblSerie_2 = new JLabel("Serie 3");
		
		JLabel lblSerie_3 = new JLabel("Serie 4");
		GroupLayout groupLayout_1 = new GroupLayout(addWeightFrame.getContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap(37, Short.MAX_VALUE)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblSerie)
						.addComponent(lblSerie_1)
						.addComponent(lblSerie_2)
						.addComponent(lblSerie_3))
					.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
								.addComponent(weightSerie2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weightSerie1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout_1.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout_1.createParallelGroup(Alignment.LEADING)
								.addComponent(weightSerie3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weightSerie4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(44))
				.addGroup(groupLayout_1.createSequentialGroup()
					.addGap(6)
					.addComponent(btnAddWeight)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancelAddWeight)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(weightSerie1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSerie))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(weightSerie2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSerie_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerie_2)
						.addComponent(weightSerie3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerie_3)
						.addComponent(weightSerie4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(groupLayout_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddWeight)
						.addComponent(btnCancelAddWeight))
					.addGap(56))
		);
		groupLayout_1.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnAddWeight, btnCancelAddWeight});
		addWeightFrame.getContentPane().setLayout(groupLayout_1);
		
		JInternalFrame addTrainingFrame = new JInternalFrame("Add training");
		addTrainingFrame.setResizable(true);
		addTrainingFrame.setBounds(218, 25, 341, 420);
		desktopPane.add(addTrainingFrame);
		addTrainingFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		addTrainingFrame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAddTraining = new JButton("Add training");
		panel_2.add(btnAddTraining);
		
		JButton btnCancelAddTraining = new JButton("Cancel");
		btnCancelAddTraining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTrainingFrame.setVisible(false);
			}
		});
		panel_2.add(btnCancelAddTraining);
		
		JPanel panel = new JPanel();
		addTrainingFrame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox trainingDay = new JComboBox();
		panel.add(trainingDay);
		
		fillDaysBox(trainingDay, 2017, 2);
		
		trainingDay.setSelectedIndex(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
		
		JComboBox trainingMonth = new JComboBox();
		panel.add(trainingMonth);
		
		int actualMonthIndex = trainingMonth.getSelectedIndex();
		
		trainingMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int actualMonthIndex = trainingMonth.getSelectedIndex();
				fillDaysBox(trainingDay, Calendar.getInstance().get(Calendar.YEAR), actualMonthIndex);
			}
		});
		fillMonthsBox(trainingMonth);
		trainingMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		
		JComboBox trainingYear = new JComboBox();
		panel.add(trainingYear);
		fillYearsBox(trainingYear);
		
		trainingDayOfWeek = new JTextField();
		panel.add(trainingDayOfWeek);
		trainingDayOfWeek.setEditable(false);
		trainingDayOfWeek.setColumns(10);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		addTrainingFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		trainingTable = new JTable();
		DefaultTableModel trainingTableModel = (DefaultTableModel)trainingTable.getModel();
		trainingTableModel.addColumn("Category");
		trainingTableModel.addColumn("Exercise");
		trainingTableModel.addColumn("Weight");
		scrollPane_1.setViewportView(trainingTable);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{87, 67, 28, 28, 95, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		

		GridBagConstraints gbc_exerciseBox = new GridBagConstraints();
		gbc_exerciseBox.anchor = GridBagConstraints.WEST;
		gbc_exerciseBox.insets = new Insets(0, 0, 5, 5);
		gbc_exerciseBox.gridx = 2;
		gbc_exerciseBox.gridy = 1;
		panel_1.add(exerciseBox, gbc_exerciseBox);
		
		
		GridBagConstraints gbc_weight = new GridBagConstraints();
		gbc_weight.anchor = GridBagConstraints.WEST;
		gbc_weight.insets = new Insets(0, 0, 5, 5);
		gbc_weight.gridx = 2;
		gbc_weight.gridy = 2;
		panel_1.add(weight, gbc_weight);
		fillWeightsBox(weight);
		
		JComboBox exerciseCategory = new JComboBox();
		GridBagConstraints gbc_exerciseCategory = new GridBagConstraints();
		gbc_exerciseCategory.anchor = GridBagConstraints.WEST;
		gbc_exerciseCategory.insets = new Insets(0, 0, 5, 5);
		gbc_exerciseCategory.gridx = 2;
		gbc_exerciseCategory.gridy = 0;
		panel_1.add(exerciseCategory, gbc_exerciseCategory);
		exerciseCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				fillExercicesBox(exerciseBox, (CATEGORY_TYPE)exerciseCategory.getSelectedItem());
			}
		});
		
		exerciseCategory.setModel(new DefaultComboBoxModel(CATEGORY_TYPE.values()));
		fillExercicesBox(exerciseBox, (CATEGORY_TYPE)exerciseCategory.getSelectedItem());
		
		JButton btnAddExerciseToTraining = new JButton("Add exercise");
		GridBagConstraints gbc_btnAddExerciseToTraining = new GridBagConstraints();
		gbc_btnAddExerciseToTraining.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddExerciseToTraining.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAddExerciseToTraining.gridx = 2;
		gbc_btnAddExerciseToTraining.gridy = 3;
		panel_1.add(btnAddExerciseToTraining, gbc_btnAddExerciseToTraining);
		
		ArrayList<Exercise> exercisesAddedToTraining = new ArrayList<Exercise>();
		
		btnAddExerciseToTraining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exercise exerciseToAdd = (Exercise)exerciseBox.getSelectedItem();
				Weights weightToAdd = (Weights)weight.getSelectedItem();
				exerciseToAdd.setWeights(weightToAdd);
				exercisesAddedToTraining.add(exerciseToAdd);
				Vector dataRow = new Vector();
				dataRow.add(exerciseToAdd.getCategory());
				dataRow.add(exerciseToAdd.getName());
				dataRow.add(weightToAdd.toString());
				trainingTableModel.addRow(dataRow);
			}
		});
		
		btnAddTraining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(trainingYear.getSelectedItem().toString() + "-" + Integer.toString(trainingMonth.getSelectedIndex()+1) + "-" + trainingDay.getSelectedItem().toString());
					System.out.println(date);
					Training training = new Training(date);
					int trainingToAddId = sqlWriter.addTraining(training);
					fillDisplayTrainingDateBox(displayTrainingDate);
					
					for(Exercise exercise : exercisesAddedToTraining) {
						Results resultToAdd = new Results(trainingToAddId,exercise.getID(), exercise.getWeights().getID());
						sqlWriter.addResult(resultToAdd, exercise.getCategory().toString().toLowerCase());
					}
					
					exercisesAddedToTraining.clear();
					
				} catch(ParseException exception) {
					exception.printStackTrace();
				}				
			}
		});
		
		JMenuItem addTrainingMenuItem = new JMenuItem("Add training");
		addTrainingMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTrainingFrame.setVisible(true);
			}
		});
		mnNewMenu.add(addTrainingMenuItem);
		
		JMenuItem addExerciseMenuItem = new JMenuItem("Add exercise");
		addExerciseMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				addExerciseFrame.setVisible(true);
			}
		});
		mnNewMenu.add(addExerciseMenuItem);
		
		JMenuItem addWeightMenuItem = new JMenuItem("Add weight");
		addWeightMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWeightFrame.setVisible(true);
			}
		});
		mnNewMenu.add(addWeightMenuItem);
		
	}
	
	private void fillWeightsBox(JComboBox weightsBox) {
		weightsBox.removeAllItems();
		ArrayList<Weights> weightsList = sqlReader.loadWeights();
		for(Weights weights : weightsList) {
			weightsBox.addItem(weights);
		}
	}
	
	private void fillExercicesBox(JComboBox exercisesBox, CATEGORY_TYPE category) {
		exercisesBox.removeAllItems();
		ArrayList<Exercise> exercises = sqlReader.loadExercisesFromCategory(category);
		exercisesBox.removeAllItems();
		for(Exercise exercise : exercises) {
			exercisesBox.addItem(exercise);
		}
	}
	
	private void fillTrainingDatesBox(JComboBox trainingsBox) {
		ArrayList<Training> trainings = sqlReader.loadTrainings();
		for(Training training : trainings) {
			trainingsBox.addItem(training);
		}
	}
	
	private void fillMonthsBox(JComboBox monthsBox) {
		String[] months = new DateFormatSymbols().getMonths();
		for(int i = 0; i < months.length; i++) {
			monthsBox.addItem(months[i]);
		}
	}
	
	private void fillDaysBox(JComboBox daysBox, int actualYear, int actualMonth) {
		int actualDayIndex = daysBox.getSelectedIndex();
		daysBox.removeAllItems();
		Calendar myCalendar = new GregorianCalendar(actualYear, actualMonth, 1);
		for(int i=1; i <= myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			daysBox.addItem(i);
		}
		if(actualDayIndex < daysBox.getItemCount()) {
			daysBox.setSelectedIndex(actualDayIndex);
		} else {
			daysBox.setSelectedIndex(daysBox.getItemCount()-1);
		}
		
	}
	
	private void fillYearsBox(JComboBox yearsBox) {
		int actualYear = Calendar.getInstance().get(Calendar.YEAR);
		yearsBox.addItem(actualYear);
	}
	
	private void fillDisplayTrainingDateBox (JComboBox trainingDateBox) {
		Training tempTraining = (Training)trainingDateBox.getSelectedItem();
		int indexToSelect = 0;
		trainingDateBox.removeAllItems();
		ArrayList<Training> trainings = sqlReader.loadTrainings();
		for(Training training : trainings) {
			trainingDateBox.addItem(training);
			if(tempTraining.getID() == training.getID()) {
				indexToSelect = trainings.indexOf(training);
			}
		}
		trainingDateBox.setSelectedIndex(indexToSelect);
	}
	
	private void displayTrainingExercises(JTable table, Training training) {
		if(table != null) {
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			ArrayList<Exercise> exercises = sqlReader.loadExercisesFromTraining(training);
			int tableRowCount = table.getRowCount();
			for(int i=tableRowCount; i > 0; i--) {
				tableModel.removeRow(i-1);
			}
			for(Exercise exercise : exercises) {
				Vector rowData = new Vector();
				rowData.add(exercise.getCategory().toString());
				rowData.add(exercise.getName());
				rowData.add(exercise.getWeights().toString());
				tableModel.addRow(rowData);
			}
		}
	}
}
