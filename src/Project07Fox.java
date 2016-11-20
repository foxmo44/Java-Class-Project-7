import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <h1>Advanced Java - Project07Fox</h1>
 * <h1>Project07Fox  Class</h1>
 * This is the class contains the main function that instantiates the controller object to get it all kicked off.
 * This module is kept intentionally small with the functionally kept in the MVC
 * <p>
 * <b>Create Date: 11/20/2016</b>
 * <b>Due Date: 11/30/2016</b>
 *
 * @author Michael Fox
 */
public class Project07Fox extends Application
{
    private static CpuController cpuController;

    /**
     * The main running function of the application
     * @param args - command line arguments for the application
     */
    public static void main(String [] args)
    {
        //Use the model view controller solution (MVC) model for the CPU interface to the db and viewing
        cpuController = new CpuController();

        launch( args );

    }

    /**
     * The start function used by JavaFx.  This will pass the mainstage to the view class
     * @param mainStage - The main stage of the Java Fx GUI
     */
    public void start( Stage mainStage )
    {
        cpuController.Start(mainStage);
    }
}
