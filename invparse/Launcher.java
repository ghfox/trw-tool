/*
 * 
 *H W Aycock
 * Simple tool for making something more useable out of Ecwids CSVs.
 */

package invparse;

import java.io.FileNotFoundException;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException{
        View view = new View();
        InvParse model = new InvParse("*","output.csv");
        Controller controller = new Controller(model,view);
        view.registerListener(controller);
    }
    
}
