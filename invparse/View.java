/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package invparse;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.util.*;
import javax.swing.*; 
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cook
 */
public class View extends JFrame{
    JPanel panel, checks;
    String[] options = {"Glitter20","EasyWeed","CHEMICA","TRWHolo","Metal20","Strip15"};
    JLabel inputLabel = new JLabel("select a file");
    JTextArea outputLabel = new JTextArea("Saving as: " + "output.csv", 2,20);
    
    public View(){
        super();
        panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TRW: Ecwid CSV Helper");
        
        add(panel);
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(3,2));
		
        				
        panel.add(inputLabel);
                
        JButton openButton = new JButton("Open..");		
        panel.add(openButton);
        
        panel.add(outputLabel);
        outputLabel.setLineWrap(true);
        outputLabel.setWrapStyleWord(true);
        outputLabel.setEditable(false);
                
        JButton destButton = new JButton("Choose File Destination");		
        panel.add(destButton);
        
        this.checks = new JPanel();
        checks.setLayout(new GridLayout(4,2));
        
	for(String option : options ){
            checks.add(new JCheckBox(option, true));
        }
        panel.add(checks);
        
        JButton genButton = new JButton("Generate CSV");
        panel.add(genButton);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setSize(450, 200);
    }       
    
    public List<String> getFilter() {
        List<String> list = new ArrayList<String>();
        Component[] components = checks.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
               JCheckBox box = (JCheckBox) component;
               if(box.isSelected()){
                   list.add(box.getText());
               }
            }
        }
        return list;
    }
    
    public void registerListener(Controller controller) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(controller);
            }
        }
    }
    
 
    public String openFile(){
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        filechooser.setFileFilter(filter);
        int returnValue = filechooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File file = filechooser.getSelectedFile();
            return(file.getAbsolutePath());
        }else
            return "* Choose a file to open";
    }
    
    public String saveAs() {
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        filechooser.setFileFilter(filter);
        int returnValue = filechooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = filechooser.getSelectedFile();
                if (!file.toString().contains(".csv")){
                    file = new File(file.toString() + ".csv");
                }
                return(file.getAbsolutePath());
            }
        else {
            return "output.csv";
        }
    }
    
    public void update(String input, String output){
        inputLabel.setText(input);
        outputLabel.setText("Saving as: "+ output);
    }
    
    public void alert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    
}
