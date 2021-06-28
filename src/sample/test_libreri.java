package sample;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.Scanner;

public class test_libreri extends WekaWrapper {
    Instances data;
    WekaWrapper objObe;

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
        Instance instance = new DenseInstance(6);
        instance.setDataset(data);
        //PRUEBA DE DATOS REGISTRADOS:SE ESPERA LA SALIDA INDICADA
        System.out.println("Ingrese BI-RADS");
        int a= Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese EDAD");
        int b= Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese FORMA");
        int c= Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese MARGEN");
        int d= Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese DENSIDAD");
        int e= Integer.parseInt(scanner.nextLine());

        instance.setValue(0, a); //BI-RADS
        instance.setValue(1, b);//AGE
        instance.setValue(2, c);//SHAPE
        instance.setValue(3, d);//MARGIN
        instance.setValue(4, e);//DENSITY
        instance.setValue(5, "malignant");//SEVERITY (1)
        double result = objObe.classifyInstance(instance);
        int resultFinal = (int)result;
        if (resultFinal==0)
            System.out.println(" Maligno, recomendamos realizar una biopsia");
        else
        System.out.println(" Benigno, recomendamos no realizar una biopsia");
    }
}