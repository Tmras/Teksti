import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Model {
	
	private static final long serialVersionUID = 1L;
	
	private JFileChooser chooser;
	private File chosen;			//this is the file that is currently opened
	private UserInterface1 u;		//tells the model wich user interface is used 
	
	public Model() {
		
		//initially our file is unnamed.txt
		chosen = new File("unnamed.txt"); 
		
	}
	
	public void setUi(UserInterface1 a){
		u = a;
	}
	
	private void setFile(File file) {
		chosen = file;
	}
	
	//this method fetches a file opens it and returns text in the file and 
	//name of the file se they can be displayed in the user interface
	public String[] openFile() {
		
		String text = "";
		String name = "";
		
		chooser = new JFileChooser();
		
		try {
			
			//we use file choose to fetch the file and get all needed info from it
			chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
			int res = chooser.showOpenDialog(chooser);
			if (res == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				setFile(file);
				text = read(file);
				name = file.getName();
			}

		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String[] table = {text, name};
		return table;
	
	}
	
	//this method goes through the file and returns it's content in String format
	private String read(File file) {
		
		String text = "";
		
		BufferedReader reader;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
	
			//let's use StringBuilder to build text
	        StringBuilder builder = new StringBuilder();
	        String row = reader.readLine();
			
	        //goes through all lines and appends them together
	        while (row != null) {
	            builder.append(row);
	            builder.append("\n");
	            row = reader.readLine();
	        }
	        
	        text = builder.toString();
	        reader.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return text;
	}
	
	public void save(String text) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chosen), "utf-8"));
			writer.write(text);
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//we use filechooser to chose a file where our writing is saved. This method 
	//creates the file
	public void saveAs(String text) {
		
		chooser = new JFileChooser();
		
		try {
			chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
			int res = chooser.showSaveDialog(chooser);
			if (res == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				setFile(file);
				fileSave(text, file);
				u.newTitle(file.getName()+" - Tekst!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//saves text to file 
	private void fileSave(String text, File file) {
		
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			writer.write(text);
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return chosen;
	}
	
	public void setFile() {
		chosen = new File("unnamed.txt");
		u.setTitle("unnamed.txr - Tekst!");
	}
	


}
