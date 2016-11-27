import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import javax.security.auth.Refreshable;
import java.util.ArrayList;

/**
 * <h1>Advanced Java - Project06Fox</h1>
 * <h1>CpuView  Class</h1>
 * This is the class description provided the JavaFx viewing of the CPU database information along with editing
 * <p>
 * <b>Create Date: 11/6/2016</b>
 * <b>Due Date: 11/16/2016</b>
 *
 * @author Michael Fox
 */
public class CpuView
{
    private CpuController cpuController;

    private Label       lblCpuList;
    private Label       lblPrice;
    private Label       lblPerformance;
    private TextField   txtPrice;
    private TextField   txtPerformance;
    private Label       lblCpuName;
    private TextField   txtCpuName;
    private Label       lblCpuIdentifier;
    private TextField   txtCpuIdentifier;
    private Button      btnSave;
    private Button      btnDelete;
    private Button      btnClear;
    private Label       lblStatus;


//    private ListView< CPU > listViewCpu;

    // The table graphic component
    TableView< CPU > tableViewCpu;
    ObservableList< CPU > ObCpuList;
    /**
     * Default constructor for the Cpu View class.
     * @param c - CpuController object
     */
    public CpuView(CpuController c)
    {
        cpuController = c;

    }

    /**
     * This is the primary class for setting up the Java Fx GUI for the View class
     * @param mainStage - main stage of the Java Fx GUI
     */
    void Start(Stage mainStage)
    {
        mainStage.setTitle( "Fox CPU Viewer" );
        FlowPane root = new FlowPane( Orientation.VERTICAL, 20, 20 );
        root.setAlignment( Pos.CENTER );
        Scene scene01 = new Scene( root, 600, 700 );
        mainStage.setScene( scene01 );

        lblCpuList = new Label( "CPU Information List" );
        lblCpuIdentifier = new Label("CPU Identifier");
        txtCpuIdentifier = new TextField("");
        lblCpuName = new Label("CPU Name");
        txtCpuName = new TextField("");
        lblPrice = new Label("Price");
        txtPrice = new TextField("");
        lblPerformance = new Label("Performance");
        txtPerformance = new TextField("");
        btnSave = new Button( "Save" );
        btnDelete = new Button("Delete");
        btnClear = new Button("Clear");
        lblStatus = new Label("");

        //Set the Cpu identifier box to readonly
        txtCpuIdentifier.setDisable(true);

        //Set the button handler for the Save pushbutton
        btnSave.setOnAction( (wombat) -> { SaveButtonHandler(); } );
        btnDelete.setOnAction((wombat) -> {DeleteButtonHandler(); });
        btnClear.setOnAction((wombat) -> {ClearButtonHandler(); });

        //Do the setup of the table view
        SetupTableView();

        // Update the CPU list
        UpdateCpuList();

        MultipleSelectionModel< CPU > msm01 = tableViewCpu.getSelectionModel();

        //Capture the selection and alter the labels
        msm01.selectedItemProperty().addListener
        (
            (changedValue, oldValue, newValue) ->
            {
                UpdateCpuTextboxes(newValue);
            }
        );

        //Add the controls to the dialog
        root.getChildren().addAll(  lblCpuList,
                                    tableViewCpu,
                                    lblCpuIdentifier,
                                    txtCpuIdentifier,
                                    lblCpuName,
                                    txtCpuName,
                                    lblPerformance,
                                    txtPerformance,
                                    lblPrice,
                                    txtPrice,
                                    btnSave,
                                    btnDelete,
                                    btnClear,
                                    lblStatus);
        mainStage.show();
    }

    /**
     * Update the CPU text boxes
     * @param cpu - Input CPU object
     */
    void UpdateCpuTextboxes(CPU cpu)
    {
        if(cpu != null)
        {
            //If a valid CPU object then update the text boxes
            if (cpu.getValid() == true)
            {
                txtCpuIdentifier.setText(Integer.toString(cpu.getIdentifier()));

                String strPrice = String.format("%5.2f", cpu.getPrice());
                txtPrice.setText(strPrice);

                String strPerformance = Integer.toString(cpu.getPerformance());
                txtPerformance.setText(strPerformance);

                txtCpuName.setText((cpu.getCPUName()));
            }
        }
        else
        {
            txtCpuIdentifier.setText("");
            txtPrice.setText("");
            txtCpuName.setText("");
            txtPerformance.setText("");
        }

    }

    /**
     * Update the list view with the data
     */
    void UpdateCpuList()
    {
        ArrayList< CPU > cpuList = new ArrayList<>();

        cpuList = cpuController.getCpuList();

        String strStatus;
        strStatus = String.format("The list has %d\n", cpuList.size());
        lblStatus.setText(strStatus);

        // The backing data structure for the Table View
        ObCpuList = FXCollections.observableArrayList( cpuList );

        tableViewCpu.getItems().clear();
        tableViewCpu.setItems(ObCpuList);
        tableViewCpu.refresh();

    }

    /**
     * Setup the Table View for the CPU data
     */
    private void SetupTableView()
    {
        tableViewCpu = new TableView<CPU>(  );
        tableViewCpu.setPrefSize( 520, 160 );
        tableViewCpu.setEditable( true );

        // Sets up a column in the table.
        // By using properties with field names that follow the convention
        // we can easily connect a field from the datatype to a column in our
        // table.

        TableColumn< CPU, Integer > Identifier = new TableColumn<>( "Identifier" );
        Identifier.setCellValueFactory( new PropertyValueFactory<>( "Identifier" ) );
        tableViewCpu.getColumns().add( Identifier );

        ///////////////////

        TableColumn< CPU, String > cpuName = new TableColumn<>( "CPU Name" );
        cpuName.setCellValueFactory( new PropertyValueFactory<>( "CPUName" ) );
        cpuName.setCellFactory( TextFieldTableCell.forTableColumn() );
        cpuName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<CPU, String>>(){
                    public void handle( TableColumn.CellEditEvent<CPU, String> c ){
                        CPU  tempCpu = (CPU) c.getTableView().getItems().get(c.getTablePosition().getRow());
                        tempCpu.setCPUName(c.getNewValue());
                        System.out.println( "CPU: " + tempCpu.getCPUName() );
                    }
                }
        );
        tableViewCpu.getColumns().add( cpuName );

        ///////////////////

        TableColumn< CPU, Integer > Performance = new TableColumn<>( "Performance" );
        Performance.setCellValueFactory( new PropertyValueFactory<>( "Performance" ) );

        //Try to catch the invalid value entry by overriding the integer String Converter "fromString"
        Performance.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()
        {
            @Override
            public Integer fromString(String value)
            {
                try
                {
                    return super.fromString(value);
                }
                catch(NumberFormatException e)
                {
                    return Integer.MAX_VALUE; // An abnormal value
                }
            }
        }));

        Performance.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<CPU, Integer>>()
            {
                public void handle( TableColumn.CellEditEvent<CPU, Integer> c )
                {

                    CPU tempCpu = (CPU) c.getTableView().getItems().get(c.getTablePosition().getRow());

                    if(c.getNewValue() != Integer.MAX_VALUE)
                    {
                        tempCpu.setPerformance(c.getNewValue());

                        String strStatus;
                        strStatus = String.format("New Performance: " + tempCpu.getPerformance());
                        lblStatus.setText(strStatus);

                    }
                    else
                    {
                        int iRow = c.getTablePosition().getRow();

                        tempCpu.setPerformance(c.getOldValue());

                        String strStatus;
                        strStatus = String.format("Invalid entry so kept performance at : " + tempCpu.getPerformance() + " for " + iRow);
                        lblStatus.setText(strStatus);

                        //Set back to the previous value
                        c.getRowValue().setPerformance(c.getOldValue());

                        tableViewCpu.refresh();
                    }

                }
            }
        );
        tableViewCpu.getColumns().add( Performance );

        ///////////////////

        TableColumn< CPU, Double > Price = new TableColumn<>( "Price" );
        Price.setCellValueFactory( new PropertyValueFactory<>( "Price" ) );
        //Price.setCellFactory( TextFieldTableCell.<CPU, Double>forTableColumn(new DoubleStringConverter()) );

        //Try to catch the invalid value entry by overriding the integer String Converter "fromString"
        Price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()
        {
            @Override
            public Double fromString(String value)
            {
                try
                {
                    return super.fromString(value);
                }
                catch(NumberFormatException e)
                {
                    return Double.MAX_VALUE; // An abnormal value
                }
            }
        }));


        Price.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<CPU, Double>>()
            {
                public void handle( TableColumn.CellEditEvent<CPU, Double> c )
                {

                    CPU tempCpu = (CPU) c.getTableView().getItems().get(c.getTablePosition().getRow());

                    if(c.getNewValue() != Double.MAX_VALUE)
                    {
                        String strStatus;
                        strStatus = String.format("New Performance: " + tempCpu.getPrice());
                        lblStatus.setText(strStatus);
                    }
                    else
                    {
                        int iRow = c.getTablePosition().getRow();

                        tempCpu.setPrice(c.getOldValue());

                        String strStatus;
                        strStatus = String.format("Invalid entry so kept price at : " + tempCpu.getPrice() + " for " + iRow);
                        lblStatus.setText(strStatus);

                        //Set back to the previous value
                        c.getRowValue().setPrice(c.getOldValue());

                        tableViewCpu.refresh();

                    }
                }
            }
        );
        tableViewCpu.getColumns().add( Price );

        // The selection model is what we need to look at to connect listeners to to be able to handle acitons.
        TableView.TableViewSelectionModel< CPU > bookSelectionModel = tableViewCpu.getSelectionModel();
        bookSelectionModel.selectedIndexProperty().addListener(
            new ChangeListener< Number >(){
                public void changed(ObservableValue< ? extends Number > changed, Number oldValue, Number newValue)
                {
                    if(newValue != null)
                    {
                        try
                        {
                            CPU tempCpu = ObCpuList.get(newValue.intValue());

                            String strStatus;
                            strStatus = String.format("Selected : " + newValue + ", with name: " + tempCpu.getCPUName());
                            lblStatus.setText(strStatus);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
    }


    /**
     * Handle the Save push button hit
     */
    private void SaveButtonHandler()
    {
        int     iPerformance;
        int     iIdentifier = 0;
        double  dPrice;
        String  strCpuName;
        String  strStatus;
        CPU     cpu;

        //Clear the status before the callback
        lblStatus.setText("");

        //Save the CPU information via the controller
        if((txtCpuName.getText().isEmpty() == false) &&
           (txtPerformance.getText().isEmpty() == false) &&
           (txtPrice.getText().isEmpty() == false))
        {
            //Get the identifier number
            if(txtCpuIdentifier.getText().isEmpty() == false)
            {
                iIdentifier = Integer.parseInt(txtCpuIdentifier.getText());
            }

            //Grab the data from the rest of the text fields
            iPerformance = Integer.parseInt(txtPerformance.getText());
            dPrice = Double.parseDouble(txtPrice.getText());
            strCpuName = txtCpuName.getText();

            //Determine if we are updating or creating a new record with the save method
            if(iIdentifier > 0)
            {
                //Does the record already exist
                cpu = cpuController.getCpu(iIdentifier);

                if(cpu != null)
                {
                    //The record does indeed already exist so update it
                    cpuController.Update(iIdentifier, strCpuName, iPerformance, dPrice);
                }
                else
                {
                    //Could not find the record so go ahead and save it as a new record
                    cpuController.Save(strCpuName, iPerformance, dPrice);  //Save to the db
                }
            }
            else
            {
                //Perform the save operation
                cpuController.Save(strCpuName, iPerformance, dPrice);  //Save to the db
            }

            UpdateCpuList();    //based on the database update the ListView

            strStatus = String.format("Saved %s", strCpuName);
            lblStatus.setText(strStatus);
        }
        else
        {
            lblStatus.setText("All fields must be filled in before saving");
        }

    }


    /**
     * Clear the text fields
     */
    private void ClearButtonHandler()
    {
        //Clear the status before the callback
        lblStatus.setText("");

        txtCpuIdentifier.setText("");
        txtPrice.setText("");
        txtCpuName.setText("");
        txtPerformance.setText("");
    }

    /**
     * If the index is valid then delete the record from the database
     */
    private void DeleteButtonHandler()
    {
        int iIdentifier;
        String strStatus;

        //Clear the status before the callback
        lblStatus.setText("");

        if(txtCpuIdentifier.getText().isEmpty() == false)
        {
            iIdentifier = Integer.parseInt(txtCpuIdentifier.getText());

            cpuController.Delete(iIdentifier);

            UpdateCpuList();

            strStatus = String.format("Deleted %s", txtCpuName.getText());
            lblStatus.setText(strStatus);

        }
        else
        {
            lblStatus.setText("Could not delete the record");
        }
    }
}

