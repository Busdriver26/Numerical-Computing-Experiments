package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Jama.*;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TextField P;

    @FXML
    private Button RES;

    @FXML
    private TextField times;

    @FXML
    private Button pmt;

    @FXML
    private TextField Eigenvalue;

    @FXML
    private TextField EPS;

    @FXML
    private TextArea matx;

    @FXML
    private TextField V0T;

    @FXML
    private TextField EIGENVECTOR;

    @FXML
    private Button pmtwp;

    @FXML
    private Button ipmtwp;

    @FXML
    private Button ipmt;

    @FXML
    double[] gp(double[][] matrix,double []b,int n){
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
        return b;
    }

    @FXML
    void power(MouseEvent event){
        EIGENVECTOR.clear();
        Eigenvalue.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            EIGENVECTOR.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double V[]=new double[n];
        double xk1[]=new double[n];//for solution
        double xk2[]=new double[n];
        double xk3[]=new double[n];
        double yk[]=new double[n];
        double vct[]=new double[n];
        double eps1;
        double p;
        double times1;
        String temp;
        temp=EPS.getText();
        eps1 = Double.parseDouble(temp);
        //temp=P.getText();
        //p=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
            }
        }
        String binput=V0T.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            V[i] = Double.parseDouble(b_mid[i]);
        }
        //Matrix Calculation
        //0
        double max=0;
        double eigv1=-999,eigv2=-100;
        for(int i=0;i<n;i++){
            xk1[i]=V[i];
            xk2[i]=0;
            if(Math.abs(xk1[i])>Math.abs(max))max=xk1[i];
        }
        for(times1=0;times1<1000000;times1++) {
            //1.Yk=xk1/(xkmax)
            for (int i = 0; i < n; i++) {
                yk[i] = xk1[i] / max;
            }
            //2.xk2=A*yk
            max=0;
            for(int i=0;i<n;i++){
                xk2[i]=0;
                for(int j=0;j<n;j++) {
                    xk2[i] += yk[j] * matrix[i][j];
                }
                if(Math.abs(xk2[i])>Math.abs(max))max=xk2[i];
            }
            //3.lambda= xk+1 maxi(yi=1)
            eigv2=max;
            if(Math.abs(eigv2-eigv1)<eps1 && times1>2){
                //V0T.appendText("0");
                break;
            }
            if(times1>1500){
               // V0T.appendText("2");
                for (int i = 0; i < n; i++) {
                    xk3[i] = 0;
                    for (int j = 0; j < n; j++) {
                        xk3[i] += xk2[j] * matrix[i][j];
                    }
                    if (Math.abs(xk3[i]) > Math.abs(max)) max = xk3[i];

                }
                eigv2 = -Math.sqrt(xk3[0] / yk[0]);
                double maxx3=0;
                for(int i=0;i<n;i++){
                    yk[i]=xk3[i]+eigv2*xk2[i];
                    if(Math.abs(yk[i])>Math.abs(maxx3))maxx3=yk[i];
                }
                for(int i=0;i<n;i++)
                {
                    yk[i]/=maxx3;
                }
                break;
            }
            eigv1=eigv2;
            max=0;
            //4.vector=(y0,y1,...,yn)
            for(int i=0;i<n;i++){
                xk1[i]=xk2[i];
                xk2[i]=0;
                if(xk1[i]>max)max=xk1[i];
            }
        }
        //Output
        EIGENVECTOR.appendText("(");
        for(int i=0;i<n;i++){
            EIGENVECTOR.appendText(String.format("%.4f",yk[i]));
            if(i!=n-1) EIGENVECTOR.appendText(",");
        }
        EIGENVECTOR.appendText(")");
        Eigenvalue.setText(String.format("%.6f",eigv2));
        times.setText(String.format("%.0f",times1));
    }

    @FXML
    void ipower(MouseEvent event){
        EIGENVECTOR.clear();
        Eigenvalue.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            EIGENVECTOR.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double V[]=new double[n];
        double xk1[]=new double[n];//for solution
        double xk2[]=new double[n];
        double xk3[]=new double[n];
        double yk[]=new double[n];
        double vct[]=new double[n];
        double eps1;
        double p;
        double times1;
        String temp;
        temp=EPS.getText();
        eps1 = Double.parseDouble(temp);
        temp=P.getText();
        p=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
                if(i==j){
                    matrix[i][j]-=p;
                }
            }
        }
        String binput=V0T.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            V[i] = Double.parseDouble(b_mid[i]);
        }
        //Matrix Calculation
        //0
        double max=0;
        double eigv1=-999,eigv2=-100;
        for(int i=0;i<n;i++){
            xk1[i]=V[i];
            xk2[i]=0;
            if(Math.abs(xk1[i])>Math.abs(max))max=xk1[i];
        }
        for(times1=0;times1<1000000;times1++) {
            //1.Yk=xk1/(xkmax)
            for (int i = 0; i < n; i++) {
                yk[i] = xk1[i] / max;
            }
            //2.xk2=A*yk
            max=0;
            for(int i=0;i<n;i++){
                xk2[i]=0;
                for(int j=0;j<n;j++) {
                    xk2[i] += yk[j] * matrix[i][j];
                }
                if(Math.abs(xk2[i])>Math.abs(max))max=xk2[i];
            }
            //3.lambda= xk+1 maxi(yi=1)
            eigv2=max;
            if(Math.abs(eigv2-eigv1)<eps1 && times1>2){
                //V0T.appendText("0");
                break;
            }
            if(times1>1500){
                if(xk1[0]*xk2[0]<0 && false){
                    eigv2=Math.abs(max);
                    eigv2=-eigv2;
                    //V0T.appendText("1");
                    break;
                }
                else {
                    //V0T.appendText("2");
                    for (int i = 0; i < n; i++) {
                        xk3[i] = 0;
                        for (int j = 0; j < n; j++) {
                            xk3[i] += xk2[j] * matrix[i][j];
                        }
                        if (Math.abs(xk3[i]) > Math.abs(max)) max = xk3[i];
                    }
                    eigv2 = -Math.sqrt(xk3[0] / yk[0]);
                    double maxx3=0;
                    for(int i=0;i<n;i++){
                        yk[i]=xk3[i]+eigv2*xk2[i];
                        if(Math.abs(yk[i])>Math.abs(maxx3))maxx3=yk[i];
                    }
                    for(int i=0;i<n;i++)
                    {
                        yk[i]/=maxx3;
                    }
                    break;
                }
            }
            eigv1=eigv2;
            max=0;
            //4.vector=(y0,y1,...,yn)
            for(int i=0;i<n;i++){
                xk1[i]=xk2[i];
                xk2[i]=0;
                if(xk1[i]>max)max=xk1[i];
            }
        }
        //Output
        EIGENVECTOR.appendText("(");
        for(int i=0;i<n;i++){
            EIGENVECTOR.appendText(String.format("%.4f",yk[i]));
            if(i!=n-1) EIGENVECTOR.appendText(",");
        }
        EIGENVECTOR.appendText(")");
        Eigenvalue.setText(String.format("%.6f",eigv2+p));
        times.setText(String.format("%.0f",times1));
    }

    @FXML
    void RVpower(MouseEvent event){
        EIGENVECTOR.clear();
        Eigenvalue.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            EIGENVECTOR.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double V[]=new double[n];
        double xk1[]=new double[n];//for solution
        double xk2[]=new double[n];
        double xk3[]=new double[n];
        double yk[]=new double[n];
        double vct[]=new double[n];
        double eps1;
        double p;
        double times1;
        String temp;
        temp=EPS.getText();
        eps1 = Double.parseDouble(temp);
        //temp=P.getText();
        //p=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
            }
        }
        String binput=V0T.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            V[i] = Double.parseDouble(b_mid[i]);
        }
        //Matrix Calculation
        Matrix m1=new Matrix(matrix);
        m1=m1.inverse();
        matrix = m1.getArray();
        //for(int i=0;i<n;i++){
        //    for(int j=0;j<n;j++){
        //        System.out.println(matrix[i][j]);
        //    }
        //}
        //0
        double max=0;
        double eigv1=-999,eigv2=-100;
        for(int i=0;i<n;i++){
            xk1[i]=V[i];
            xk2[i]=0;
            if(Math.abs(xk1[i])>Math.abs(max))max=xk1[i];
        }
        for(times1=0;times1<1000000;times1++) {
            //1.Yk=xk1/(xkmax)
            for (int i = 0; i < n; i++) {
                yk[i] = xk1[i] / max;
            }
            //2.xk2=A*yk
            max=0;
            for(int i=0;i<n;i++){
                xk2[i]=0;
                for(int j=0;j<n;j++) {
                    xk2[i] += yk[j] * matrix[i][j];
                }
                if(Math.abs(xk2[i])>Math.abs(max))max=xk2[i];
            }
            //3.lambda= xk+1 maxi(yi=1)
            eigv2=max;
            if(Math.abs(eigv2-eigv1)<eps1 && times1>2){
                //V0T.appendText("0");
                break;
            }
            if(times1>1500){
                V0T.appendText("2");
                for (int i = 0; i < n; i++) {
                    xk3[i] = 0;
                    for (int j = 0; j < n; j++) {
                        xk3[i] += xk2[j] * matrix[i][j];
                    }
                    if (Math.abs(xk3[i]) > Math.abs(max)) max = xk3[i];

                }
                eigv2 = -Math.sqrt(xk3[0] / yk[0]);
                double maxx3=0;
                for(int i=0;i<n;i++){
                    yk[i]=xk3[i]+eigv2*xk2[i];
                    if(Math.abs(yk[i])>Math.abs(maxx3))maxx3=yk[i];
                }
                for(int i=0;i<n;i++)
                {
                    yk[i]/=maxx3;
                }
                break;
            }
            eigv1=eigv2;
            max=0;
            //4.vector=(y0,y1,...,yn)
            for(int i=0;i<n;i++){
                xk1[i]=xk2[i];
                xk2[i]=0;
                if(xk1[i]>max)max=xk1[i];
            }
        }
        //Output
        EIGENVECTOR.appendText("(");
        for(int i=0;i<n;i++){
            EIGENVECTOR.appendText(String.format("%.4f",yk[i]));
            if(i!=n-1) EIGENVECTOR.appendText(",");
        }
        EIGENVECTOR.appendText(")");
        Eigenvalue.setText(String.format("%.6f",1/eigv2));
        times.setText(String.format("%.0f",times1));
    }

    @FXML
    void iRVpower(MouseEvent event){
        EIGENVECTOR.clear();
        Eigenvalue.clear();
        //Read from textarea
        String input = matx.getText();
        if (input.equals("")) {
            EIGENVECTOR.setText("ERROR:NO INPUT in Matx\n");
            return;
        }
        String stringArray[] = input.split("\n");
        int n=stringArray.length;
        double matrix[][] = new double[n][n];
        double V[]=new double[n];
        double xk1[]=new double[n];//for solution
        double xk2[]=new double[n];
        double xk3[]=new double[n];
        double yk[]=new double[n];
        double vct[]=new double[n];
        double eps1;
        double p;
        double times1;
        String temp;
        temp=EPS.getText();
        eps1 = Double.parseDouble(temp);
        temp=P.getText();
        p=Double.parseDouble(temp);
        for(int i=0;i<n;i++){
            String mid[] = stringArray[i].split(" ");
            for(int j=0;j<n;j++){
                matrix[i][j] = Double.parseDouble(mid[j]);
                if(i==j){
                    matrix[i][j]-=p;
                }
            }
        }
        String binput=V0T.getText();
        String b_mid[] = binput.split(" ");
        for(int i=0;i<n;i++){
            V[i] = Double.parseDouble(b_mid[i]);
        }
        //Matrix Calculation
        Matrix m1=new Matrix(matrix);
        m1=m1.inverse();
        matrix = m1.getArray();
        //0
        double max=0;
        double eigv1=-999,eigv2=-100;
        for(int i=0;i<n;i++){
            xk1[i]=V[i];
            xk2[i]=0;
            if(Math.abs(xk1[i])>Math.abs(max))max=xk1[i];
        }
        for(times1=0;times1<1000000;times1++) {
            //1.Yk=xk1/(xkmax)
            for (int i = 0; i < n; i++) {
                yk[i] = xk1[i] / max;
            }
            //2.xk2=A*yk
            max=0;
            for(int i=0;i<n;i++){
                xk2[i]=0;
                for(int j=0;j<n;j++) {
                    xk2[i] += yk[j] * matrix[i][j];
                }
                if(Math.abs(xk2[i])>Math.abs(max))max=xk2[i];
            }
            //3.lambda= xk+1 maxi(yi=1)
            eigv2=max;
            if(Math.abs(eigv2-eigv1)<eps1 && times1>2){
                //V0T.appendText("0");
                break;
            }
            if(times1>1500){
                if(xk1[0]*xk2[0]<0 && false){
                    eigv2=Math.abs(max);
                    eigv2=-eigv2;
                    //V0T.appendText("1");
                    break;
                }
                else {
                    //V0T.appendText("2");
                    for (int i = 0; i < n; i++) {
                        xk3[i] = 0;
                        for (int j = 0; j < n; j++) {
                            xk3[i] += xk2[j] * matrix[i][j];
                        }
                        if (Math.abs(xk3[i]) > Math.abs(max)) max = xk3[i];
                    }
                    eigv2 = -Math.sqrt(xk3[0] / yk[0]);
                    double maxx3=0;
                    for(int i=0;i<n;i++){
                        yk[i]=xk3[i]+eigv2*xk2[i];
                        if(Math.abs(yk[i])>Math.abs(maxx3))maxx3=yk[i];
                    }
                    for(int i=0;i<n;i++)
                    {
                        yk[i]/=maxx3;
                    }
                    break;
                }
            }
            eigv1=eigv2;
            max=0;
            //4.vector=(y0,y1,...,yn)
            for(int i=0;i<n;i++){
                xk1[i]=xk2[i];
                xk2[i]=0;
                if(xk1[i]>max)max=xk1[i];
            }
        }
        //Output
        EIGENVECTOR.appendText("(");
        for(int i=0;i<n;i++){
            EIGENVECTOR.appendText(String.format("%.4f",yk[i]));
            if(i!=n-1) EIGENVECTOR.appendText(",");
        }
        EIGENVECTOR.appendText(")");
        Eigenvalue.setText(String.format("%.6f",1/eigv2+p));
        times.setText(String.format("%.0f",times1));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        times.clear();
        EIGENVECTOR.clear();
        Eigenvalue.clear();
        assert RES != null : "fx:id=\"RES\" was not injected: check your FXML file 'sample.fxml'.";
        assert pmt != null : "fx:id=\"pmt\" was not injected: check your FXML file 'sample.fxml'.";
        assert Eigenvalue != null : "fx:id=\"Eigenvalue\" was not injected: check your FXML file 'sample.fxml'.";
        assert EPS != null : "fx:id=\"EPS\" was not injected: check your FXML file 'sample.fxml'.";
        assert matx != null : "fx:id=\"matx\" was not injected: check your FXML file 'sample.fxml'.";
        assert EIGENVECTOR != null : "fx:id=\"EIGENVECTOR\" was not injected: check your FXML file 'sample.fxml'.";
        assert V0T != null : "fx:id=\"V0T\" was not injected: check your FXML file 'sample.fxml'.";
        assert ipmt != null : "fx:id=\"ipmt\" was not injected: check your FXML file 'sample.fxml'.";
    }
}
