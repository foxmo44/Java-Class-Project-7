import java.sql.*;
import java.util.ArrayList;

/**
 * <h1>Advanced Java - Project06Fox</h1>
 * <h1>CpuModel  Class</h1>
 * This is the class is the interface to the database of CPUs
 * <p>
 * <b>Create Date: 11/6/2016</b>
 * <b>Due Date: 11/16/2016</b>
 *
 * @author Michael Fox
 */
public class CpuModel
{
    /**
     * The interface objects for the database
     */
    private static Connection c;
    private static Statement s;
    private static ResultSet r;
    private static boolean bConnected = false;

    /**
     * constructore for the CpuModel that will connect to the database
     */
    public CpuModel()
    {

    }

    /**
     * @return - Get the list of CPU
     */
    public static ArrayList<CPU> getAllCpu()
    {
        ArrayList<CPU> cpuList = new ArrayList<>();

        //Loop through the records sets and populate the CPU list
        try
        {
            if (bConnected == false)
            {
                bConnected = Connect("cpudb", "tcc2016", "tcc2016");
            }

            if (bConnected == true)
            {
                System.out.println("Connected db");
            } else
            {
                System.out.println("****Could not Connect to db****");
            }

            s = c.createStatement();

            r = s.executeQuery("SELECT * from cputable order by price DESC");

            while (r.next())
            {
                CPU tempCPU = new CPU(r.getInt("id"), r.getString("cpuname"), r.getInt("performance"), r.getFloat("price"));

                cpuList.add(tempCPU);

                tempCPU.getCPUNameProperty().addListener(
                        ( o, oldValue, newValue ) -> {
                            System.out.println( "oldv: " + oldValue + "newv: " + newValue );

                            updateCpuName( tempCPU.getIdentifier(), newValue );
                        }
                );

                //TODO DO THE OTHER PROPERTIES
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return cpuList;
    }


    /**
     * Function to take care of connecting to the database
     *
     * @param strDatabase -  name of the database
     * @param strUser     - user name
     * @param strPassword - password for the user
     * @return status either true or false when connecting
     */
    private static boolean Connect(String strDatabase, String strUser, String strPassword)
    {
        boolean bConnectStatus = false;

        try
        {
            //Had to suppress a SSL warning message that kept popping up
            c = DriverManager.getConnection("jdbc:mysql://localhost/" + strDatabase + "?autoReconnect=true&useSSL=false", strUser, strPassword);

            System.out.println("Database connection made\n");

            bConnectStatus = true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return (bConnectStatus);
    }


    /**
     * insert the cpu information into the database
     * @param strCpuName - name of the CPU
     * @param iPerformance - Performance rating of the CPU
     * @param dPrice - the price of the CPU
     * @return true if the insert was completed
     */
    public static boolean insert(String strCpuName, int iPerformance, double dPrice)
    {
        String strSql;
        boolean bRetValue = false;

        try
        {
            if (bConnected == false)
            {
                bConnected = Connect("cpudb", "tcc2016", "tcc2016");
            }

            s = c.createStatement();

            strSql = "insert into cputable( cpuname, performance, price) values('"
                    + strCpuName + "',"
                    + iPerformance + ","
                    + dPrice + ")";

            //System.out.println(strSql);
            bRetValue = s.execute(strSql);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(bRetValue);
    }

    /**
     * Delete the record associated with the input identifier
     * @param iIdentifier - identifier of record to delete
     * @return true if the delete was successful
     */
    public static boolean delete(int iIdentifier)
    {
        boolean bRetValue = false;

        try
        {
            if (bConnected == false)
            {
                bConnected = Connect("cpudb", "tcc2016", "tcc2016");
            }

            s = c.createStatement();

            //System.out.println(strSql);
            bRetValue = s.execute("delete from cputable where id = " + iIdentifier );

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(bRetValue);
    }


    /**
     * Update the CPU name for the identifier
     * @param iIdentifier
     * @param strCpuName
     * @return
     */
    public static boolean updateCpuName(int iIdentifier, String strCpuName)
    {
        //TODO DO THE UPDATE SQL for update CPU name
        return(true);
    }


    /**
     * If the identifier exists then up date the record with the name, performance, and price
     * @param iIdentifier - identifier in the database
     * @param strCpuName - Name of the CPU
     * @param iPerformance - Performance of the CPU
     * @param dPrice - Price of the CPU
     * @return - Return true if successful
     */
    public static boolean update(int iIdentifier, String strCpuName, int iPerformance, double dPrice)
    {
        boolean bRetValue = false;
        String strSql;

        try
        {
            if (bConnected == false)
            {
                bConnected = Connect("cpudb", "tcc2016", "tcc2016");
            }

            s = c.createStatement();

            strSql = String.format("update cputable set cpuname = '" + strCpuName
                                    + "', performance =" + iPerformance
                                    + ", price = " + dPrice
                                    + " where id = "  + iIdentifier );

            System.out.printf("update with SQL [" + strSql + "]\n");

            bRetValue = s.execute(strSql);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(bRetValue);
    }

    /**
     * Return an object CPU that is associated with the identifier in the database
     * @param iIdentifier - identifier to the CPU to retrieve
     * @return - CPU object that was filled or null if not filled
     */
    public static CPU getCpu(int iIdentifier)
    {
        boolean bStatus;
        CPU     retValue = null;
        String  strSql;

        try
        {
            if (bConnected == false)
            {
                bConnected = Connect("cpudb", "tcc2016", "tcc2016");
            }

            s = c.createStatement();

            strSql = String.format( "SELECT * from cputable where id = "   + iIdentifier);

            System.out.printf("getCPU with SQL [" + strSql + "]\n");

            r = s.executeQuery(strSql);

            r.next();

            retValue = new CPU(r.getInt("id"), r.getString("cpuname"), r.getInt("performance"), r.getFloat("price"));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(retValue);
    }
}


