package sample;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.CheckBox;
import java.lang.Object.*;


public class Controller {

    @FXML
    private TextField bn_S;

    @FXML
    private TextField bn_T;

    @FXML
    private TextField bn_R;

    @FXML
    private TextField a;

    @FXML
    private TextField b;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Trapezoidal"
    private TextField Trapezoidal; // Value injected by FXMLLoader

    @FXML // fx:id="Precision"
    private TextField Precision; // Value injected by FXMLLoader

    @FXML // fx:id="F1"
    private Button F1; // Value injected by FXMLLoader

    @FXML // fx:id="Step"
    private TextField Step; // Value injected by FXMLLoader

    @FXML
    private double precision;

    @FXML // fx:id="F2"
    private Button F2; // Value injected by FXMLLoader

    @FXML // fx:id="F3"
    private Button F3; // Value injected by FXMLLoader

    @FXML // fx:id="F4"
    private Button F4; // Value injected by FXMLLoader

    @FXML // fx:id="Romberg"
    private TextField Romberg; // Value injected by FXMLLoader

    @FXML // fx:id="Simpson"
    private TextField Simpson; // Value injected by FXMLLoader

    @FXML
    private int fi = 0;

    @FXML
    double f(double x,int choose){
        double y;
        if(choose==1) {
            y = Math.sin(x);
            y = 4 - y * y;
            y = Math.sqrt(y);
            return y;
        }
        else if(choose == 2) {
            if(x==0) return 1;
            y = Math.sin(x);
            y = y / x;
            return y;
        }
        else if(choose ==3) {
            y = Math.exp(x);
            y = y / (4 + x * x);
            return y;
        }
        else {
            y = Math.log(1 + x);
            y = y / (1 + x * x);
            return y;
        }
    }

    @FXML
    void F1(MouseEvent event){
        simpson(1);
        romberg(1);
        trapezoidal(1);
        return;
    }

    @FXML
    void F2(MouseEvent event){
        simpson(2);
        romberg(2);
        trapezoidal(2);
        return;
    }

    @FXML
    void F3(MouseEvent event){
        simpson(3);
        romberg(3);
        trapezoidal(3);
        return;
    }

    @FXML
    void F4(MouseEvent event){
        simpson(4);
        romberg(4);
        trapezoidal(4);
        return;
    }

    @FXML
    private CheckBox GivenStep;

    @FXML
    private CheckBox GivenPrecision;

    @FXML
    void trapezoidal(int choose) {
        //prepare
        Trapezoidal.clear();
        int loop = 0;
        String precision_input = Precision.getText();
        precision = Double.parseDouble(precision_input);
        String a_input = a.getText();
        double a = Double.parseDouble(a_input);
        String b_input = b.getText();
        double b = Double.parseDouble(b_input);
        String n_input = Step.getText();
        long n = Long.parseLong(n_input);
        double h = (b-a)/n;
        double output,last=0;
        if(GivenPrecision.isSelected()){
            GivenStep.setSelected(false);
            loop = 1;
        }
        else{
            GivenPrecision.setSelected(false);
            GivenStep.setSelected(true);
        }
        //calc
        for(int i=0;;i++){
            output = 0.5*f(a,choose)+0.5*f(b,choose);
            for(int j=1;j<=n-1;j++){
                output = output + f(a+j*h,choose);
            }
            output *= h;
            if(loop == 0){
                break;
            }
            if(i!=0){
                if(Math.abs(last-output)<precision)
                    break;
            }
            last = output;
            n=n*2;
            h=h/2;
        }
        //output
        if(loop == 1){
            bn_T.setText(String.format("%d",n));
        }
        Trapezoidal.setText(String.format("%.8f",output));
    }

    @FXML
    void simpson (int choose) {
        Simpson.clear();
        int loop = 0;
        String precision_input = Precision.getText();
        precision = Double.parseDouble(precision_input);
        String a_input = a.getText();
        double a = Double.parseDouble(a_input);
        String b_input = b.getText();
        double b = Double.parseDouble(b_input);
        String n_input = Step.getText();
        long n = Long.parseLong(n_input);
        double h = (b-a)/n;
        double output,last=0;
        if(GivenPrecision.isSelected()){
            GivenStep.setSelected(false);
            loop = 1;
        }
        else{
            GivenPrecision.setSelected(false);
            GivenStep.setSelected(true);
        }
        //calc
        for(int i=0;;i++){
            output = f(a,choose)+f(b,choose);
            for(int j=1;j<=n/2;j++) {
                output = output + 2 * f(a + j * 2 * h, choose);
                output = output + 4 * f(a + (2 * j - 1) * h, choose);
            }
            output -= 2*f(b,choose);
            output *= h/3;
            if(loop == 0){
                break;
            }
            if(i!=0){
                if(Math.abs(last-output)<precision)
                    break;
            }
            last = output;
            n=n*2;
            h=h/2;
        }
        //output
        if(loop == 1){
            bn_S.setText(String.format("%d",n));
        }
        Simpson.setText(String.format("%.8f",output));
    }

    @FXML
    private int M=10;

    @FXML
    void romberg(int choose) {
        Romberg.clear();
        int loop = 0;
        String precision_input = Precision.getText();
        precision = Double.parseDouble(precision_input);
        String a_input = a.getText();
        double a = Double.parseDouble(a_input);
        String b_input = b.getText();
        double b = Double.parseDouble(b_input);
        String n_input = Step.getText();
        int n=0;
        n = Integer.parseInt(n_input);
        double h = b-a;
        if(GivenPrecision.isSelected()){
            GivenStep.setSelected(false);
            loop = 1;
        }
        else{
            GivenPrecision.setSelected(false);
            GivenStep.setSelected(true);
        }
        double[] R1 = new double[M+2];
        double[] R2 = new double[M+2];
        for(int i=0;i<=M+1;i++){
            R1[i] = 0;
            R2[i] = 0;
        }
        //calc
        double s =0,hk,hk_l;
        int i;
        R1[1] = (f(a,choose)+f(b,choose))*h*0.5;
        for(i=2;;i++){
            s=0;
            hk = h/Math.pow(2,i-1);
            hk_l = hk*2;
            for(int j=1;j<=Math.pow(2,i-2);j++){
                s += f(a+(2*j-1)*hk,choose);
            }
            R2[1]= (R1[1]+hk_l*s)/2;
            for(int j =2;j<=i;j++){
                R2[j] = R2[j-1] +(R2[j-1]-R1[j-1])/(Math.pow(4,j-1)-1);
            }
            if(i!=2){
                if(Math.abs(R1[i-1]-R2[i])<precision)
                    break;
            }
            for(int j=1;j<=i;j++){
                R1[j]=R2[j];
            }
        }
        //output
        bn_R.setText(String.format("%d",i-1));
        Romberg.setText(String.format("%.8f",R2[i-1]));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Trapezoidal != null : "fx:id=\"Trapezoidal\" was not injected: check your FXML file 'sample.fxml'.";
        assert Precision != null : "fx:id=\"Precision\" was not injected: check your FXML file 'sample.fxml'.";
        assert F1 != null : "fx:id=\"F1\" was not injected: check your FXML file 'sample.fxml'.";
        assert Step != null : "fx:id=\"Step\" was not injected: check your FXML file 'sample.fxml'.";
        assert F2 != null : "fx:id=\"F2\" was not injected: check your FXML file 'sample.fxml'.";
        assert F3 != null : "fx:id=\"F3\" was not injected: check your FXML file 'sample.fxml'.";
        assert F4 != null : "fx:id=\"F4\" was not injected: check your FXML file 'sample.fxml'.";
        assert Romberg != null : "fx:id=\"Romberg\" was not injected: check your FXML file 'sample.fxml'.";
        assert Simpson != null : "fx:id=\"Simpson\" was not injected: check your FXML file 'sample.fxml'.";
        bn_R.clear();
        bn_T.clear();
        bn_S.clear();
        Trapezoidal.clear();
        Simpson.clear();
        Romberg.clear();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void clear() {
        assert Trapezoidal != null : "fx:id=\"Trapezoidal\" was not injected: check your FXML file 'sample.fxml'.";
        assert Precision != null : "fx:id=\"Precision\" was not injected: check your FXML file 'sample.fxml'.";
        assert F1 != null : "fx:id=\"F1\" was not injected: check your FXML file 'sample.fxml'.";
        assert Step != null : "fx:id=\"Step\" was not injected: check your FXML file 'sample.fxml'.";
        assert F2 != null : "fx:id=\"F2\" was not injected: check your FXML file 'sample.fxml'.";
        assert F3 != null : "fx:id=\"F3\" was not injected: check your FXML file 'sample.fxml'.";
        assert F4 != null : "fx:id=\"F4\" was not injected: check your FXML file 'sample.fxml'.";
        assert Romberg != null : "fx:id=\"Romberg\" was not injected: check your FXML file 'sample.fxml'.";
        assert Simpson != null : "fx:id=\"Simpson\" was not injected: check your FXML file 'sample.fxml'.";
        bn_R.clear();
        bn_T.clear();
        bn_S.clear();
        Trapezoidal.clear();
        Simpson.clear();
        Romberg.clear();
        a.setText("0");
        b.setText("1");
        Step.setText("10");
        Precision.setText("0.000001");
    }
}
