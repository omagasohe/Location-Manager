package Distance;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Locations.*;
//import java.util.*;

/**
 * Main Entry point in to the Program Everything else in this class is geared
 * towards the GUI This is an extension of a course assignment that required
 * using multi-dimentional arrays. This is more elegant but as I'm learning,
 * it'll get uglier before it's better.
 *
 */
public class Distance extends Frame implements WindowListener {

	private static final long serialVersionUID = 1L;

	private Label lblTo;
	private Label lblFrom;
	private Label lblIs;
	private Label lblClosest;
	private Label lblClosestIs;
	private Label lblName;
	private Label lblLat;
	private Label lblLon;

	private TextField tfDistance;
	private TextField tfClosest;
	private TextField tfName;
	private TextField tfLat;
	private TextField tfLon;

	private Button btnAdd;
	private Button btnDelete;

	private Choice chTo;
	private Choice chFrom;
	private Choice chDelete;
	private Choice chClosest;

	LocationList places = new LocationList();
    Choice[] distanceChoices = {chTo,chFrom,chDelete,chClosest};
	/**
	 * Entry
	 *
	 * @param args
	 */
	//
	public static void main(String[] args) {
		new Distance();
	}

	/**
	 * Starts the GUI. Has a Card layout panel for kicks.
	 */
	public Distance() {
		readDataFile("places.txt"); //At some point will be part of the LocationList class.
		//
        JTabbedPane tabbedPane = new JTabbedPane();
		setLayout(new FlowLayout());
		// Set up for the panel

		Panel pnlDistance = new Panel();
		Panel pnlClosest = new Panel();
		Panel pnlAdd = new Panel();
		Panel pnlDelete = new Panel();

		tabbedPane.addTab("Distance", null, pnlDistance, "Simple, Lazy and just plain useless for all but small lists.");
		tabbedPane.addTab("Closest", null, pnlClosest, "Simple, Lazy and just plain useless for all but small lists.");
		tabbedPane.addTab("Add", null, pnlAdd, "Simple, Lazy and just plain useless for all but small lists.");
		tabbedPane.addTab("Delete", null, pnlDelete, "Simple, Lazy and just plain useless for all but small lists.");

		// Labels for text
		lblFrom = new Label("The distance from"); // construct the Label
													// component
		lblTo = new Label("to"); // construct the Label component
		lblIs = new Label("is"); // construct the Label component
		lblClosest = new Label("The place closest to");
		lblClosestIs = new Label("is");
		lblName = new Label("Name:");
		lblLat = new Label("Latitude:");
		lblLon = new Label("Longitude:");
		// Setting up the drop down boxes from the "places"
		// Care should be takes to keep indexes the same since we're
		// using look up tables instead of algorithms.
		// Silly course designers.
		chFrom = new Choice();
		chTo = new Choice();
		chDelete = new Choice();
		chClosest = new Choice();
		for (Location c : places) {
			if (c.getName() != null) {
				chFrom.add(c.getName());
				chTo.add(c.getName());
				chDelete.add(c.getName());
				chClosest.add(c.getName());
			}
		}



		tfDistance = new TextField("0", 10); // construct the TextField
												// component
		tfDistance.setEditable(false); // set to read-only
		tfClosest = new TextField("", 30);
		tfClosest.setEditable(false);

		tfName = new TextField("", 20);
		tfLat = new TextField("", 20);
		tfLon = new TextField("", 20);

		btnAdd = new Button("Add Location");
		btnDelete = new Button("Delete Location");
		// Add controls to frames
		pnlDistance.add(lblFrom);
		pnlDistance.add(chFrom);
		pnlDistance.add(lblTo);
		pnlDistance.add(chTo);
		pnlDistance.add(lblIs);
		pnlDistance.add(tfDistance);

		pnlClosest.add(lblClosest);
		pnlClosest.add(chClosest);
		pnlClosest.add(lblClosestIs);
		pnlClosest.add(tfClosest);

		pnlAdd.add(lblName);
		pnlAdd.add(tfName);
		pnlAdd.add(lblLat);
		pnlAdd.add(tfLat);
		pnlAdd.add(lblLon);
		pnlAdd.add(tfLon);
		pnlAdd.add(btnAdd);

		pnlDelete.add(chDelete);
		pnlDelete.add(btnDelete);

		add(tabbedPane);

		// Set up Handlers
		addWindowListener(this);

		// Ugly but functional, Closest to place
		chClosest.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {
				tfClosest.setText(places.closest(chClosest.getSelectedIndex()).toString());
			}
		});

		DistanceItemListener listen = new DistanceItemListener();
		chFrom.addItemListener(listen);
		chTo.addItemListener(listen);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = tfName.getText();
				places.add(new Location(s, Double.parseDouble(tfLat.getText()), Double.parseDouble(tfLon.getText())));
				places.sort(Location.LocationNameComparator);
				int idx = places.indexOf(s);
				chFrom.insert(s, idx);
				chTo.insert(s, idx);
				chDelete.insert(s, idx);
				chClosest.insert(s, idx);
				tfName.setText("");
				tfLon.setText("");
				tfLat.setText("");
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int idx = chDelete.getSelectedIndex();
				chDelete.remove(idx);
				chTo.remove(idx);
				chFrom.remove(idx);
				chClosest.remove(idx);
				places.remove(idx);
			}
		});

		setTitle("Distance");
		setSize(900, 120);
		setVisible(true);

	}

	protected void readDataFile(String file) {
		try {
			File dir = new File(".");
			File fin = new File(dir.getCanonicalPath() + File.separator + file);

			FileInputStream fis = new FileInputStream(fin);

			// Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while ((line = br.readLine()) != null) {
				String words[] = line.split("\t");
				places.add(new Location(words[0], Double.parseDouble(words[1]), Double.parseDouble(words[2])));
			}

			br.close();

		} catch (IOException e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	// Window handler routines, Just catching the big red X button.
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0); // Terminate the program
	}

	// Not Used, but need to provide an empty body to compile.
	@Override
	public void windowOpened(WindowEvent evt) {
	}

	@Override
	public void windowClosed(WindowEvent evt) {
	}

	@Override
	public void windowIconified(WindowEvent evt) {
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
	}

	@Override
	public void windowActivated(WindowEvent evt) {
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {
	}

	/**
	 * For the distance drop down menus
	 *
	 * @author p2
	 *
	 */
	class DistanceItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			tfDistance.setText(String.format("%d",
					places.get(chFrom.getSelectedIndex()).distanceTo(places.get(chTo.getSelectedIndex()))));

		}

	}
}