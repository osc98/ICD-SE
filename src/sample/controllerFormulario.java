package sample;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class controllerFormulario implements Initializable {
    @FXML
    JFXButton btnCalcular, btnCancelar, btnImprimir;
    @FXML
    JFXComboBox cbBirads, cbForma, cbMargen, cbDensidad;
    @FXML
    JFXTextField tfNombre, TFedad;
    @FXML
    StackPane stackPane;
    Instances data;
    WekaWrapper objObe;
    boolean bandera=false;

    public void initialize (URL location, ResourceBundle resources){
        bandera=false;
        btnCancelar.setVisible(false);
        btnCalcular.setOnAction(calcular);
        btnImprimir.setOnAction(crearPdf);
        btnCancelar.setOnAction(cancelar);
        cbBirads.getItems().addAll(1,2,3,4,5);
        cbForma.getItems().addAll("Redonda","ovalada","Lobular","Irregular");
        cbMargen.getItems().addAll("Circumscribed","microlobulated","Obscured","Ill-defined","Spiculated");
        cbDensidad.getItems().addAll("Alta","Iso","Baja","Contiene grasa");
        TFedad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,//validar numeros only
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    TFedad.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        try {
            cargarInstancias();
            System.out.println("Instancias cargadas correctamente......");
        } catch (Exception e) {
            System.out.println("ERROR, no se pudieron cargar las instancias......");
            e.printStackTrace();
        }
    }
    javafx.event.EventHandler<ActionEvent> crearPdf= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = (Stage) btnImprimir.getScene().getWindow();
            if (bandera == true){

            controllerForm cf = new controllerForm();
            pdf codf =new pdf();
            codf.impresion(stage,"oscar",1,2,3,4,5);
        }
        }
    };
    javafx.event.EventHandler<ActionEvent> cancelar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            cbBirads.setDisable(false);
            cbForma.setDisable(false);
            cbDensidad.setDisable(false);
            cbMargen.setDisable(false);
            tfNombre.setDisable(false);
            TFedad.setDisable(false);
            btnCancelar.setVisible(false);
            btnImprimir.setVisible(false);
        }
    };
    javafx.event.EventHandler<ActionEvent> calcular = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int resultado = -1;
            String diagnostico = "";
            Persona p = obtenerDatos();
            try {
                cbBirads.setDisable(true);
                cbForma.setDisable(true);
                cbDensidad.setDisable(true);
                cbMargen.setDisable(true);
                tfNombre.setDisable(true);
                TFedad.setDisable(true);
                resultado = creaInstancia(p);
                System.out.println("Instancia nueva creada y evaluada...");
            } catch (Exception e) {
                System.out.println("ERROR, no se pudo crear la instancia...");
                e.printStackTrace();
            }
            diagnostico = verificaResultado(resultado);
            if (diagnostico!=null){
                bandera=true;
                btnCancelar.setVisible(true);
                btnImprimir.setVisible(true);
            }
            JFXDialogLayout graphInfo=new JFXDialogLayout();
            graphInfo.setHeading(new Text("El diagnostico es..."));
            graphInfo.setBody(new Text(diagnostico));
            JFXDialog dialog=new JFXDialog(stackPane,graphInfo, JFXDialog.DialogTransition.LEFT);
            dialog.show();
        }
    };

    public int creaInstancia(Persona p) throws Exception {

        int forma;
        if(p.getForma().equals("Redonda"))
            forma = 1;
        else if(p.getForma().equals("Ovalada"))
            forma = 2;
        else if(p.getForma().equals("Lobular"))
            forma = 3;
        else forma = 4;


        int margen;
        if(p.getMargen().equals("Circumscribed"))
            margen = 1;
        else if(p.getMargen().equals("Microlobulated"))
            margen = 2;
        else if(p.getMargen().equals("Obscured"))
            margen = 3;
        else if(p.getMargen().equals("Ill-defined"))
            margen = 4;
        else margen = 5;

        //DUDA RESPECTO A LAS RESPUESTAS
        int densidad;
        if(p.getDensidad().equals("Alta"))
            densidad = 1;
        else if(p.getDensidad().equals("Iso"))
            densidad = 2;
        else if(p.getDensidad().equals("Baja"))
            densidad = 3;
        else densidad = 4;


        Instance instance = new DenseInstance(6);
        instance.setDataset(data);
        instance.setValue(0, p.getBirads()); //Genero
        instance.setValue(1, p.getEdad());//Edad
        instance.setValue(2, forma);//Forma
        instance.setValue(3, margen);//margen
        instance.setValue(4, densidad);//Densidad
        instance.setValue(5,"malignant");//Valor string
        double result = objObe.classifyInstance(instance);
        int resultFinal = (int)result;
        return resultFinal;
    }

    public void cargarInstancias() throws Exception {
        Scanner scanner= new Scanner(System.in);
        File file = new File("mammographic_masses.arff");
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(file.getAbsolutePath());
        data = source.getDataSet();
        data.setClassIndex(data.numAttributes() -1);
        System.out.println(data.numInstances() + " instancias cargadas.");
        System.out.println(data.toString());
        objObe = new WekaWrapper();
        objObe.buildClassifier(data);
        System.out.println(objObe);
    }
    public Persona obtenerDatos(){
        Persona persona = new Persona();
        persona.setNombre(tfNombre.getText());
        persona.setEdad(Integer.parseInt(TFedad.getText()));
        persona.setBirads(Integer.parseInt(cbBirads.getSelectionModel().getSelectedItem().toString()));
        persona.setMargen(cbMargen.getSelectionModel().getSelectedItem().toString());
        persona.setForma(cbForma.getSelectionModel().getSelectedItem().toString());
        persona.setDensidad(cbDensidad.getSelectionModel().getSelectedItem().toString());
        return persona;
    }

    public String verificaResultado(int resultFinal){
        String diagnostico = "";
        if (resultFinal==0) {
            diagnostico += "Maligno, recomendamos realizar una biopsia";
            bandera=true;
        }
        else {
            diagnostico += "Benigno, recomendamos no realizar una biopsia";
            bandera=true;
        }

        System.out.println("Su diagnóstico es: " + diagnostico);
        return diagnostico;
    }
}
