import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

public class UserInterface1 extends JFrame {
		
	
	private static final long serialVersionUID = 1L;
	
    private JMenuBar menubar;
    
    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem saveas;
    private JMenuItem newFile;
    private JMenuItem print;
    private JMenuItem exit;
    
    private JMenu edit;
    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem cut;
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem selectAll;
    
    private JMenu options;
    private JMenu fontColor;
    private JMenuItem blue;
    private JMenuItem white;
    private JMenuItem black;
    private JMenu backColor;
    private JMenuItem backBlack;
    private JMenuItem backWhite;
    
    private UIManager UIManager;
    private JTextArea text;
    private Box box;
    private  Model model;
    private JScrollPane scroll;
    private UndoManager undoRestore;
    
    //this variable is used when we see wether or not changes has been made to 
    //text
    private String thisIsCompared; 
    							   
	public UserInterface1(Model m) {
		
        super();
        setTitle(m.getFile().getName() + " - Tekst!");
        
        //UIMager sets the look and feel that current system uses
        UIManager = new UIManager();
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
        thisIsCompared = ""; 
        model = m;
        undoRestore = new UndoManager();
        box = new Box(BoxLayout.Y_AXIS);
     
        FlowLayout f = new FlowLayout();
        f.setAlignment((int) LEFT_ALIGNMENT);
        menubar = new JMenuBar();
        menubar.setLayout(f);
        file = new JMenu( "file");
      
        edit = new JMenu( "edit" );
        options = new JMenu("settings");
        fontColor = new JMenu("font color");
        backColor = new JMenu("background color");
        open = new JMenuItem( "open" );
        save = new JMenuItem( "save" );
        newFile = new JMenuItem("new");
        exit = new JMenuItem("exit");
        saveas = new JMenuItem( "save as" );
        undo = new JMenuItem( "undo" );
        redo = new JMenuItem( "redo" );
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("choose all");
        print = new JMenuItem("print");
        blue = new JMenuItem("blue");
        black = new JMenuItem("black");
        white = new JMenuItem("white");
        backBlack = new JMenuItem("black");
        backWhite = new JMenuItem("white");
       
        //every item has it's own actionlister
        open.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		
        		String whatIsSet[] = model.openFile(); 
        	    text.setText(whatIsSet[0]);
        	    thisIsCompared = whatIsSet[0];
        	    setTitle(whatIsSet[1] + " - Tekst!");
        	    
        	}
        });
        
       save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        	    model.save(text.getText());     
        	}
        });
        
      saveas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        	    model.saveAs(text.getText());     
        	}
        });
      
      newFile.addActionListener(new ActionListener() {
      		public void actionPerformed(ActionEvent ae) {
      			
      			try {
      				if (!text.getText().equals(thisIsCompared)) {
      					int whatIsDone = whatIsDone(); //0 cancel, 1 save, 2 don't save
      				
      					if (whatIsDone == 1) { //if file doesn't have name then save as
      					
      						if (model.getFile().getName().equals("unnamed.txt") ) {
      							model.saveAs(text.getText());
      						}
      						else  {
      							model.save(text.getText());
      						}
      					
      						text.setText("");
      						thisIsCompared = "";
      						setTitle("unnamed.txt - Tekst!");
      					}
      					else if (whatIsDone == 2) {
      						text.setText("");
      						thisIsCompared = "";
      					}
      				}
      		}
      		catch (Exception e){
      			e.printStackTrace();
      		}
      	}});
       
      	//if changes has been made then ask if we want to save
      	exit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    		    try {
    		    	if (!text.getText().equals(thisIsCompared)) {
    		    		int whatIsDone = whatIsDone(); //0 cancel, 1 save, 2 don't save
    		    		if (whatIsDone == 1) {
    					
    		    			if (model.getFile().getName().equals("unnamed.txt") ) {
    		    				model.saveAs(text.getText());
    		    			}
    		    			else  {
    		    				model.save(text.getText());
    		    			}
    					
    		    			text.setText("");
    		    			thisIsCompared = "";
    		    			System.exit(0);
    		    		}
    				
    		    		else if (whatIsDone == 2) {
    		    			System.exit(0);
    		    		}
    		    	}
    		    	else {
    		    		System.exit(0);
    		    	}
    		    }
    		    catch(Exception e){
    		    	e.printStackTrace();
    		    }
      }});
      
      print.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  
    		  try {
    			    boolean finish = text.print(); 
    			    if (finish) {
    			        System.out.println("success");
    			       
    			    } else {
    			        System.out.println("canceled");
    			       
    			    }
    			} 
    		  	catch (Exception e) {
    			    e.printStackTrace(); 
    			}
    	  }
      });
      
      redo.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    	        try {
    	            undoRestore.redo();
    	        } 
    	        catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	  }
      });
      
      undo.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    	        try {
    	            undoRestore.undo();
    	        } 
    	        catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	  }
      });
      cut.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.cut();
    	  }
      });
      
      copy.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.copy();
    	  }
      });
      
      paste.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.paste();
    	  }
      });
      
      selectAll.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.selectAll();
    	  }
      });
      
      blue.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.setForeground(Color.BLUE);
    	  }
      });
      black.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.setForeground(Color.BLACK);
    	  }
      });      
      white.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.setForeground(Color.WHITE);
    	  }
      });
      backWhite.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.setBackground(Color.WHITE);
    	  }
      });
      backBlack.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae) {
    		  text.setBackground(Color.BLACK);
    	  }
      });
      

      
      file.add(newFile);
      file.add(open);
      file.add(save);
      file.add(saveas);
      file.add(print);
      file.add(exit);
      menubar.add(file);
      edit.add(undo);
      edit.add(redo);
      edit.add(cut);
      edit.add(copy);
      edit.add(paste);
      edit.add(selectAll);
      menubar.add(edit);
      fontColor.add(blue);
      fontColor.add(black);
      fontColor.add(white);
      backColor.add(backBlack);
      backColor.add(backWhite);
      options.add(fontColor);
      options.add(backColor);
      menubar.add(options);
 
      box.add(menubar);
      text = new JTextArea(35, 70);
      text.getDocument().addUndoableEditListener(undoRestore);
      scroll = new JScrollPane (text);
      scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
      box.add(scroll);
      box.setAlignmentX(LEFT_ALIGNMENT);
      getContentPane().add(box);
        
      pack();

	}
	
	//uses dialog to ask wich option we choose for saving
	private int whatIsDone() {
		int res = 0;
    
		int whatItGives = JOptionPane.showConfirmDialog(this
	               , "Do you want to save the changes"
	               ,""
	               ,JOptionPane.YES_NO_CANCEL_OPTION,
	               JOptionPane.INFORMATION_MESSAGE);

	    if(whatItGives == JOptionPane.YES_OPTION){
	    	res = 1;
	    }
	    else if(whatItGives == JOptionPane.NO_OPTION){
	    	res = 2;
	    }
	    else if(whatItGives == JOptionPane.CANCEL_OPTION){
	    	res = 0;
	    }
		
		return res;
	}
	
	public void newTitle(String s) {
		setTitle(s);
	}
}
