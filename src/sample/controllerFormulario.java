package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
    JFXButton BTNcalcular, BTNcancelar;
    @FXML
    JFXComboBox CBbirads, CBforma, CBmargen, CBdensidad;
    @FXML
    JFXTextField TFnombre, TFedad;
    @FXML
    Instances data;
    WekaWrapper objObe;


    public void initialize (URL location, ResourceBundle resources){
        BTNcalcular.setOnAction(calcular);

        CBbirads.getItems().addAll(1,2,3,4,5);
        CBforma.getItems().addAll("Redonda","ovalada","Lobular","Irregular");
        CBmargen.getItems().addAll("Circumscribed","microlobulated","Obscured","Ill-defined","Spiculated");
        CBdensidad.getItems().addAll("Alta","Iso","Baja","Contiene grasa");

        try {
            cargarInstancias();
            System.out.println("Instancias cargadas correctamente......");
        } catch (Exception e) {
            System.out.println("ERROR, no se pudieron cargar las instancias......");
            e.printStackTrace();
        }
    }


    javafx.event.EventHandler<ActionEvent> calcular = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int resultado = -1;
            String diagnostico = "";
            Persona p = obtenerDatos();
            try {
                resultado = creaInstancia(p);
                System.out.println("Instancia nueva creada y evaluada...");
            } catch (Exception e) {
                System.out.println("ERROR, no se pudo crear la instancia...");
                e.printStackTrace();
            }
            diagnostico = verificaResultado(resultado);
            /*JFXDialogLayout graphInfo=new JFXDialogLayout();
            graphInfo.setHeading(new Text("El diagnostico es..."));
            graphInfo.setBody(new Text(diagnostico));
            JFXDialog dialog=new JFXDialog(stackPane,graphInfo, JFXDialog.DialogTransition.LEFT);
            dialog.show();*/
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


        Instance instance = new DenseInstance(15);
        instance.setDataset(data);
        instance.setValue(0, p.getBirads()); //Genero
        instance.setValue(1, p.getEdad());//Edad
        instance.setValue(2, forma);//Familiar con sobrepeso
        instance.setValue(3, margen);//Comidas con altas cantidad de calorias
        instance.setValue(4, densidad);//Consumo de vegetales
        instance.setValue(5,"malignant");
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
        persona.setNombre(TFnombre.getText());
        persona.setEdad(Integer.parseInt(TFedad.getText()));
        persona.setBirads(Integer.parseInt(CBbirads.getSelectionModel().getSelectedItem().toString()));
        persona.setMargen(CBmargen.getSelectionModel().getSelectedItem().toString());
        persona.setForma(CBforma.getSelectionModel().getSelectedItem().toString());
        persona.setDensidad(CBdensidad.getSelectionModel().getSelectedItem().toString());
        return persona;
    }

    public String verificaResultado(int resultFinal){
        String diagnostico = "";
        if (resultFinal==0)
        diagnostico += "Maligno, recomendamos realizar una biopsia";
        else
        diagnostico += "Benigno, recomendamos no realizar una biopsia";

        System.out.println("Su diagnóstico es: " + diagnostico);
        return diagnostico;
    }
}
