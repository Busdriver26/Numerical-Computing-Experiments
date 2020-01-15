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

    @FXML // fx:id="GE"
    private Button GE; // Value injected by FXMLLoader

    @FXML
    void gausselimination(MouseEvent event){
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
        double triangle[][]=new double[n][n];
        double b[]=new double[n];
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
        //for(int j=0;j<n;j++){
        //    Trix.appendText(String.format("%.5f",b[j])+" ");
        //}
        //Solve the equation
        //TRIU
        for(int k=0;k<n-1;k++){
            for(int i=k+1;i<n;i++){
                matrix[i][k]/=matrix[k][k];
                for(int j=k+1;j<n;j++){
                    matrix[i][j] -=matrix[i][k]*matrix[k][j];
                }
                b[i]-= matrix[i][k]*b[k];
                matrix[i][k]=0;
            }
        }

        //
        for(int i=n-1;i>=0;i--){
            for(int j=i+1;j<n;++j){
                b[i] = b[i] - matrix[i][j]*b[j];
                //System.out.println(b[i]);
            }
            b[i]=b[i]/matrix[i][i];
            //System.out.println(b[i]);
        }
        //Output
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Trix.appendText(String.format("%.3f",matrix[i][j])+" ");
            }
            Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.16f",i+1,b[i])+" ");
        }
    }

    @FXML
    void gausseliminationpp(MouseEvent event){
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
        double triangle[][]=new double[n][n];
        double b[]=new double[n];
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
        //pp
        double temp[] = new double[n];
        double tempb;
        for(int k=0;k<n-1;k++){
            int max=k+1;
            //select the Main element
            for(int i=k+2;i<n;i++){
                if(matrix[i][k]>matrix[max][k]) max=i;
            }
            //exchange the Main and 00
            for(int j=0;j<n;j++){
                temp[j]=matrix[max][j];
                matrix[max][j]=matrix[k+1][j];
                matrix[k+1][j]=temp[j];
            }
            tempb = b[k+1];
            b[k+1]=b[max];
            b[max]=tempb;
            for(int i=k+1;i<n;i++){
                matrix[i][k]/=matrix[k][k];
                for(int j=k+1;j<n;j++){
                    matrix[i][j] -=matrix[i][k]*matrix[k][j];
                }
                b[i]-= matrix[i][k]*b[k];
                matrix[i][k]=0;
            }
        }
        //
        for(int i=n-1;i>=0;i--){
            for(int j=i+1;j<n;++j){
                b[i] = b[i] - matrix[i][j]*b[j];
                //System.out.println(b[i]);
            }
            b[i]=b[i]/matrix[i][i];
            //System.out.println(b[i]);
        }
        //same
        //Output
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Trix.appendText(String.format("%.3f",matrix[i][j])+" ");
            }
            Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.16f",i+1,b[i])+" ");
        }
    }

    @FXML
    void thomas(MouseEvent event){
        //inputZone
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
        double triangle[][]=new double[n][n];
        double b[]=new double[n];
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
        //thomas
        //change the matrix into a,b,c
        double a[]=new double[n];
        double b1[]=new double[n];
        double c[]=new double[n];
        double x[]=new double[n];
        double u[]=new double[n];
        double v[]=new double[n];
        double y[]=new double[n];
        c[0]=0;
        v[n-1]=0;
        for(int j=0;j<n;j++){
            a[j]=matrix[j][j];
            if(j!=0)c[j]=matrix[j][j-1];
            if(j!=n-1)b1[j]=matrix[j][j+1];
            //Trix.appendText(String.format("a%d:%.5f",j,a[j])+" ");
            //Trix.appendText("\n");
            //Trix.appendText(String.format("b%d:%.5f",j,b1[j])+" ");
            //Trix.appendText("\n");
            //Trix.appendText(String.format("c%d:%.5f",j,c[j])+" ");
            //Trix.appendText("\n");
        }
        //use a,b,c to calculate
        for(int k=0;k<n;k++){
            if(k==0){u[k]=a[k];}
            else {u[k]=a[k]-c[k]*v[k-1];}
            v[k]=b1[k]/u[k];
            if(k==0){y[k]=b[k]/u[k];}
            else{y[k]=(b[k]-c[k]*y[k-1])/u[k];}
        }
        x[n-1]=y[n-1];
        for(int i=n-1;i>=0;i--){
            if(i!=n-1){x[i]=y[i]-v[i]*x[i+1];}
        }
        //Output
        for(int i=0;i<n;i++){
            //for(int j=0;j<n;j++){
                //Trix.appendText(String.format("%.5f",matrix[i][j])+" ");
            //}
            Trix.appendText("N/A");
            Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.16f",i+1,x[i])+" ");
        }
    }

    @FXML
    void ldlt(MouseEvent event){
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
        double triangle[][]=new double[n][n];
        double b[]=new double[n];
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
        //LDLT
        double L[][]=new double[n][n];
        double D[] = new double[n];
        for(int i=0;i<n;i++){
            L[i][i]=1;
        }
        double temp;
        for(int i=0;i<n;i++){
            temp=0;
            for(int k=0;k<i;k++){
                temp+= L[i][k]*L[i][k]*D[k];
            }
            D[i]=matrix[i][i]-temp;
            for(int j=i+1;j<n;j++){
                temp=0;
                for(int k=0;k<i;k++){
                    temp+=L[j][k]*L[i][k]*D[k];
                }
                L[j][i]=(matrix[j][i]-temp)/D[i];
            }
        }
        //calc
        double z[]= new double[n];
        double y[]= new double[n];
        double x[]= new double[n];
        double ans=0;
        for(int i=0;i<n;i++){
            ans=0;
            for(int j=0;j<=i-1;j++){
                ans += L[i][j]*z[j];
            }
            z[i]=b[i]-ans;
            y[i] = z[i]/D[i];
        }
        x[n-1]=y[n-1];
        for(int i=n-1;i>=0;i--){
            ans=0;
            for(int j=i+1;j<=n-1;j++){
                ans+=L[j][i]*x[j];
            }
            x[i]=y[i]-ans;
        }
        //Output
        Trix.appendText("L:\n");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Trix.appendText(String.format("%.3f",L[i][j])+" ");
            }
            Trix.appendText("\n");
            Solution.appendText(String.format("x%d=%.16f",i+1,x[i])+" ");
        }
        Trix.appendText("D:\n");
        for(int i=0;i<n;i++){
            Trix.appendText(String.format("D[%d,%d]=%.3f",i,i,D[i])+" ");
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Trix.clear();
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
