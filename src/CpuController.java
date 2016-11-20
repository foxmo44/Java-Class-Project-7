import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * <h1>Advanced Java - Project06Fox</h1>
 * <h1>CpuController  Class</h1>
 * This is the class has the business logic associated with this CPU list project
 * <p>
 * <b>Create Date: 11/6/2016</b>
 * <b>Due Date: 11/16/2016</b>
 *
 * @author Michael Fox
 */
public class CpuController
{
    private CpuView cpuView;

    /**
     * The default constructor for the Cpu Controller class.  This will instantiate the Cpu View class
     */
    public CpuController()
    {
        cpuView = new CpuView(this);
    }

    /**
     * Simply pass on the main stage to the view class
     * @param mainStage - main stage for the Java Fx GUI
     */
    void Start(Stage mainStage)
    {
        cpuView.Start(mainStage);
    }

    /**
     * Save to the model
     * @param strCpuName - name of the CPU
     * @param iPerformance - performance of the CPU
     * @param dPrice - price of the CPU
     * @return true if save was completed
     */
    public boolean Save(String strCpuName, int iPerformance, double dPrice)
    {
        return (CpuModel.insert(strCpuName, iPerformance, dPrice));
    }

    /**
     * Delete a record from the database
     * @param iIdentifier - Identifier of the record to delete
     * @return true if delete was completed
     */
   public boolean Delete(int iIdentifier)
    {
        return (CpuModel.delete(iIdentifier));
    }

    /**
     * Return the object associated with the identifier
     * @param iIdentifier - Identifier of the record to return
     * @return object contain the record's data
     */
    public CPU getCpu(int iIdentifier)
    {
        return(CpuModel.getCpu(iIdentifier));
    }

    /**
     * Given the record identifier, update the fields for that record
     * @param iIdentifier - identifier of the record to update
     * @param strCpuName - name of the CPU
     * @param iPerformance - performance of the CPU
     * @param dPrice - price of the CPU
     * @return true if the update was successful
     */
    public boolean Update(int iIdentifier, String strCpuName, int iPerformance, double dPrice)
    {
        return (CpuModel.update(iIdentifier, strCpuName, iPerformance, dPrice));
    }

    /**
     * Get the latest cpu list from the Model
     * @return Return a list of the CPUs
     */
    ArrayList< CPU > getCpuList()
    {
        return (CpuModel.getAllCpu());
    }
}
