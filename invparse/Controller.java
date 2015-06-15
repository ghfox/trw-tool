package invparse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


public class Controller implements ActionListener {
    
	private InvParse model;

	private View view;
	

	public Controller(InvParse model, View view) {
		this.model = model;
		this.view = view;
        }
        
    @Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Generate CSV")){
                    if(model.getInput().equals("*")){
                       view.alert("Please Select A File To Process");
                    }else{
                        try {
                            System.out.println(view.getFilter());
                            model.setFilter(view.getFilter());
                            model.run();    
                            view.alert("Files Processed");
                        } catch (Exception ex) {
                            view.alert("An Error Occured\n" + ex);
                        }
                    }
                }
                if (command.equals("Open..")){
                    model.setInput(view.openFile());
                }
                if (command.equals("Choose File Destination")){
                    model.setOutput(view.saveAs());
                }
                view.update(model.getInput(), model.getOutput());
    }
}