package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="RES"
    private Button RES; // Value injected by FXMLLoader

    @FXML // fx:id="BT"
    private TextField BT; // Value injected by FXMLLoader

    @FXML // fx:id="LDLT"
    private Button LDLT; // Value injected by FXMLLoader

    @FXML // fx:id="TH"
    private Button TH; // Value injected by FXMLLoader

    @FXML // fx:id="INI"
    private Button INI; // Value injected by FXMLLoader

    @FXML // fx:id="GP"
    private Button GP; // Value injected by FXMLLoader

    @FXML // fx:id="Trix"
    private TextArea Trix; // Value injected by FXMLLoader

    @FXML // fx:id="matx"
    private TextArea matx; // Value injected by FXMLLoader

    @FXML // fx:id="Solution"
    private TextArea Solution; // Value injected by FXMLLoader

    @FXML
    private TextField omega;

    @FXML // fx:id="GE"
    private Button GE; // Value injected by FXMLLoader

    @FXML
    private TextField eps;

    @FXML
    private TextField itt;

    @FXML
    private TextField origin;

    @FXML
    void Jacobi(MouseEvent event){
        Trix.clear();
        Solution.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            Trix.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double b[]=new double[n];
        double x[]=new double[n];//for solution
        double x2[]=new double[n];
        double eps1;
        String temp;
        temp=eps.getText();
        eps1 = Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
            }
        }
        String binput=BT.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            b[i] = Double.parseDouble(b_mid[i]);
        }
        //Solve the equation
        //build matrix R & g
        double R[][]=new double[n][n];
        double G[]=new double[n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if(i==j){R[i][j]=0;}
                else{
                    R[i][j]=-matrix[i][j]/matrix[i][i];
                }
            }
            G[i]=b[i]/matrix[i][i];
        }
        //iterate
        temp = origin.getText();
        double origin=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            x[i]=origin;
            x2[i]=0;
        }
        long t=0;
        while(t<=100000){
            t++;
            double max=0;
            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    x2[i] += R[i][j] * x[j];
                }
                x2[i] += G[i];
                if(Math.abs(x2[i]-x[i])>max){
                    max=Math.abs(x2[i]-x[i]);
                }
            }
            for(int i=0;i<n;i++){
                x[i]=x2[i];
                x2[i]=0;
            }
            if(max<=eps1){
                break;
            }
        }
        //Output
        //Trix.appendText("Matrix R:\n");
        Trix.appendText("FIN.\n");
        for(int i=0;i<n;i++){
            if(Double.isNaN(x[i])){
                Solution.setText("Divergent.");
                //break;
            }
            for(int j=0;j<n;j++){
                Trix.appendText(String.format("%.3f",R[i][j])+" ");
            }
            //Trix.appendText("FIN.\n");
            itt.setText(String.format("%d",t)+"\n");
            Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.7f",i+1,x[i])+"\n");
        }
    }

    @FXML
    void GS(MouseEvent event){
        Trix.clear();
        Solution.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            Trix.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double b[]=new double[n];
        double x[]=new double[n];//for solution
        double x2[]=new double[n];
        double eps1;
        String temp;
        temp=eps.getText();
        eps1 = Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
            }
        }
        String binput=BT.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            b[i] = Double.parseDouble(b_mid[i]);
        }
        //Solve the equation
        //build matrix R & g
        double R[][]=new double[n][n];
        double G[]=new double[n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if(i==j){R[i][j]=0;}
                else{
                    R[i][j]=-matrix[i][j]/matrix[i][i];
                }
            }
            G[i]=b[i]/matrix[i][i];
        }
        //iterate
        temp = origin.getText();
        double origin=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            x[i]=origin;
            x2[i]=0;
        }
        int jdg=0;
        long t=0;
        while(jdg==0&&t<=100000){
            t++;
            double max=0;
            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    x2[i] += R[i][j] * x[j];
                }
                x2[i] += G[i];
                if(Math.abs(x2[i]-x[i])>max){
                    max=Math.abs(x2[i]-x[i]);
                }
                x[i]=x2[i];
                x2[i]=0;
            }
            if(max<=eps1){
                jdg=1;
            }
        }
        //Output
        //Trix.appendText("Matrix R:\n");
        Trix.appendText("FIN.\n");
        for(int i=0;i<n;i++){
            if(Double.isNaN(x[i])){
                Solution.setText("Divergent.");
                break;
            }
            //for(int j=0;j<n;j++){
            //    Trix.appendText(String.format("%.3f",R[i][j])+" ");
            //}
            itt.setText(String.format("%d",t)+"\n");
            //Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.7f",i+1,x[i])+"\n");
        }
    }

    @FXML
    void SOR(MouseEvent event){
        Trix.clear();
        Solution.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            Trix.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double b[]=new double[n];
        double x[]=new double[n];//for solution
        double x2[]=new double[n];
        double eps1,omega1;
        String temp;
        temp=eps.getText();
        eps1 = Double.parseDouble(temp);
        temp=omega.getText();
        omega1=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
            }
        }
        String binput=BT.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            b[i] = Double.parseDouble(b_mid[i]);
        }
        //Solve the equation
        //build matrix R & g
        double R[][]=new double[n][n];
        double G[]=new double[n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if(i==j){R[i][j]=0;}
                else{
                    R[i][j]=-matrix[i][j]/matrix[i][i];
                }
            }
            G[i]=b[i]/matrix[i][i];
        }
        //iterate
        temp = origin.getText();
        double origin=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            x[i]=origin;
            x2[i]=0;
        }
        int jdg=0;
        long t=0;
        while(jdg==0&&t<=100000){
            t++;
            double max=0;
            for(int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    x2[i] += R[i][j] * x[j];
                }
                x2[i] += G[i];
                if(Math.abs(x2[i]-x[i])>max){
                    max=Math.abs(x2[i]-x[i]);
                }
                x[i]=(1-omega1)*x[i]+omega1*x2[i];
                x2[i]=0;
            }
            if(max<=eps1){
                jdg=1;
            }
        }
        //Output
        ///Trix.appendText("Matrix R:\n");
        Trix.appendText("FIN.\n");
        for(int i=0;i<n;i++){
            if(Double.isNaN(x[i])){
                Solution.setText("Divergent.");
                break;
            }
            //for(int j=0;j<n;j++){
            //    Trix.appendText(String.format("%.3f",R[i][j])+" ");
            //}
            //Trix.appendText("FIN.\n");
            itt.setText(String.format("%d",t)+"\n");
            //Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.7f",i+1,x[i])+"\n");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Trix.clear();
        itt.clear();
        Solution.clear();
        assert RES != null : "fx:id=\"RES\" was not injected: check your FXML file 'sample.fxml'.";
        assert BT != null : "fx:id=\"BT\" was not injected: check your FXML file 'sample.fxml'.";
        assert LDLT != null : "fx:id=\"LDLT\" was not injected: check your FXML file 'sample.fxml'.";
        assert TH != null : "fx:id=\"TH\" was not injected: check your FXML file 'sample.fxml'.";
        assert INI != null : "fx:id=\"INI\" was not injected: check your FXML file 'sample.fxml'.";
        assert GP != null : "fx:id=\"GP\" was not injected: check your FXML file 'sample.fxml'.";
        assert Trix != null : "fx:id=\"Trix\" was not injected: check your FXML file 'sample.fxml'.";
        assert matx != null : "fx:id=\"matx\" was not injected: check your FXML file 'sample.fxml'.";
        assert Solution != null : "fx:id=\"Solution\" was not injected: check your FXML file 'sample.fxml'.";
        assert GE != null : "fx:id=\"GE\" was not injected: check your FXML file 'sample.fxml'.";

    }
}
