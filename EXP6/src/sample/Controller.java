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

    @FXML // fx:id="solution"
    private TextArea solution; // Value injected by FXMLLoader

    @FXML // fx:id="solution"
    private TextArea solution1; // Value injected by FXMLLoader

    @FXML // fx:id="solution"
    private TextArea solution2; // Value injected by FXMLLoader

    @FXML // fx:id="h"
    private TextField h; // Value injected by FXMLLoader

    @FXML // fx:id="start"
    private TextField start; // Value injected by FXMLLoader

    @FXML // fx:id="clear"
    private Button clear; // Value injected by FXMLLoader

    @FXML // fx:id="end"
    private TextField end; // Value injected by FXMLLoader

    @FXML // fx:id="F1"
    private Button F1; // Value injected by FXMLLoader

    @FXML // fx:id="F2"
    private Button F2; // Value injected by FXMLLoader

    @FXML // fx:id="F3"
    private Button F3; // Value injected by FXMLLoader

    @FXML // fx:id="F4"
    private Button F41; // Value injected by FXMLLoader

    @FXML // fx:id="F4"
    private Button F42; // Value injected by FXMLLoader

    @FXML // fx:id="F4"
    private Button F43; // Value injected by FXMLLoader

    @FXML
    double f1(int f,double x,double y,double z){
        if(f==1){
            return 4*x/y-x*y;
        }
        else if(f==2){
            return x*x-y*y;
        }
        else if(f==3){

        }
        else if(f==41){

        }
        else if(f==42){
            return y/(Math.exp(x)+1);
        }
        else if(f==43){

        }
        else if(f==44){

        }
        return 0;
    }

    double g1[]=new double[100];
    double g2[]=new double[100];
    double g3[]=new double[100];
    /*
        @FXML
        double f2(int cnt,double x){
            if(cnt==1){
                return g2[x];
            }
            if(cnt==2){
                return -g1[x];
            }
        }
     */
    @FXML
    void euler(int f,double h,double from,double to){
        solution.appendText("Euler:\n");
        double yn=-1;
        if(f==1){
            yn=3;
        }
        else if(f==2){
            yn=0;
        }
        else if(f==42){
            yn=1;
        }
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y= %.7f\n",i,yn));
            yn=yn+h*f1(f,i,yn,0);
        }
        if(f==1){
            yn=3;
        }
        else if(f==2){
            yn=0;
        }
        else if(f==42){
            yn=1;
        }
        double yn1b;
        solution.appendText("\nEuler->Better:\n");
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y= %.7f\n",i,yn));
            yn1b=yn+h*f1(f,i,yn,0);
            yn=yn+h*f1(f,i+h,yn1b,0);
        }
        return;
    }

    @FXML
    void rk(int f,double h,double from,double to){
        solution1.appendText("RK4-1:\n");
        double k1,k2,k3,k4;
        double yn=-1;
        if(f==1){
            yn=3;
        }
        else if(f==2){
            yn=0;
        }
        else if(f==42){
            yn=1;
        }
        for(double i=from;i<to+h;i+=h){
            solution1.appendText(String.format("x= %.2f  y= %.7f\n",i,yn));
            k1=f1(f,i,yn,0);
            k2=f1(f,i+h/2,yn+h*k1/2,0);
            k3=f1(f,i+h/2,yn+h*k2/2,0);
            k4=f1(f,i+h,yn+h*k3,0);
            yn=yn+h*(k1+2*k2+2*k3+k4)/6;
        }
        solution1.appendText("RK4-2:\n");
        if(f==1){
            yn=3;
        }
        else if(f==2){
            yn=0;
        }
        else if(f==42){
            yn=1;
        }
        for(double i=from;i<to+h;i+=h){
            solution1.appendText(String.format("x= %.2f  y= %.7f\n",i,yn));
            k1=f1(f,i,yn,0);
            k2=f1(f,i+h/3,yn+h*k1/3,0);
            k3=f1(f,i+2*h/3,yn+h*k2+h*k1/3,0);
            k4=f1(f,i+h,yn+h*k3+h*k1-h*k2,0);
            yn=yn+h*(k1+3*k2+3*k3+k4)/8;
        }
        return;
    }

    @FXML
    void adams(int f,double h,double from,double to){
        solution2.appendText("Adams3:\n");
        double k1,k2,k3,k4;
        double y[]=new double[120];
        double x[]=new double[120];
        if(f==1){
            y[0]=3;
        }
        else if(f==2){
            y[0]=0;
        }
        else if(f==42){
            y[0]=1;
        }
        solution2.appendText(String.format("x= %.2f  y= %.7f\n",from,y[0]));
        k1=f1(f,from,y[0],0);
        k2=f1(f,from+h/2,y[0]+h*k1/2,0);
        k3=f1(f,from+h/2,y[0]+h*k2/2,0);
        k4=f1(f,from+h,y[0]+h*k3,0);
        y[1]=y[0]+h*(k1+2*k2+2*k3+k4)/6;
        x[0]=from;
        //solution2.appendText(String.format("x= %.2f  y= %.7f\n",from+h,y[1]));
        k1=f1(f,from+h,y[1],0);
        k2=f1(f,from+h+h/2,y[1]+h*k1/2,0);
        k3=f1(f,from+h+h/2,y[1]+h*k2/2,0);
        k4=f1(f,from+h+h,y[1]+h*k3,0);
        y[2]=y[1]+h*(k1+2*k2+2*k3+k4)/6;
        x[1]=x[0]+h;
        x[2]=x[1]+h;
        for(int i=2;i<120;i++){
            solution2.appendText(String.format("x= %.2f  y= %.7f\n",x[i-1],y[i]));
            if(x[i]>=to)break;
            x[i+1]=x[i]+h;
            y[i+1]=y[i]+h/12*(23*f1(f,x[i],y[i],0)-16*f1(f,x[i-1],y[i-1],0)+5*f1(f,x[i-2],y[i-2],0));
        }
        return;
    }

    @FXML
    void func1(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        euler(1,h,from,to);
        rk(1,h,from,to);
        adams(1,h,from,to);
        return;
    }

    @FXML
    void func2(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        euler(2,h,from,to);
        rk(2,h,from,to);
        adams(2,h,from,to);
        return;
    }

    @FXML
    void func3(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        double y1n=-1,y1nb,y2n=0,y2nb,y3n=1,y3nb,y1nl=-1,y2nl=0;
        solution.appendText("Euler->Better:\nFunction1:\n");
        solution1.appendText("Euler->Better:\nFunction2:\n");
        solution2.appendText("Euler->Better:\nFunction3:\n");
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y1= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y2= %.7f\n",i,y2n));
            solution2.appendText(String.format("x= %.2f y3= %.7f\n",i,y3n));
            y1n=y1n+h*y2nl;
            //y1n=y1n+h/2*(y2nl+y1nb);
            y2n=y2n-h*y1nl;
            //y2n=y2n+h/2*(-y1nl+y2nb);
            y3n=y3n-h*y3n;
            //y3n=y3n+h/2*(y3n+y3nb);
            y1nl=y1n;
            y2nl=y2n;
        }
        y1n=-1;
        y2n=0;
        y3n=1;
        y1nl=-1;
        y2nl=0;
        double y3nl=1;
        double k1[]=new double[4];
        double k2[]=new double[4];
        double k3[]=new double[4];
        double k4[]=new double[4];
        solution.appendText("\nRK-4\n");
        solution1.appendText("\nRK-4\n");
        solution2.appendText("\nRK-4\n");
        for(double i=from;i<to+h;i+=h){
            solution.appendText(String.format("x= %.2f y1= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y2= %.7f\n",i,y2n));
            solution2.appendText(String.format("x= %.2f y3= %.7f\n",i,y3n));
            k1[1]=y2nl;
            k1[2]=-y1nl;
            k1[3]=-y3nl;
            k2[1]=y2nl+h*k1[2]/2;
            k2[2]=-y1nl-h*k1[1]/2;
            k2[3]=-y3nl-h*k1[3]/2;
            k3[1]=y2nl+h*k2[2]/2;
            k3[2]=-y1nl-h*k2[1]/2;
            k3[3]=-y3nl-h*k2[3]/2;
            k4[1]=y2nl+h*k3[2];
            k4[2]=-y1nl-h*k3[1];
            k4[3]=-y3nl-h*k3[3];
            y1n=y1n+h/6*(k1[1]+2*k2[1]+2*k3[1]+k4[1]);
            y2n=y2n+h/6*(k1[2]+2*k2[2]+2*k3[2]+k4[2]);
            y3n=y3n+h/6*(k1[3]+2*k2[3]+2*k3[3]+k4[3]);
            y1nl=y1n;
            y2nl=y2n;
            y3nl=y3n;
        }
        return;
    }

    @FXML
    void func41(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        double y1n=0,y1nb,y2n=1,y2nb,y3n=1,y3nb,y1nl=0,y2nl=1;
        solution.appendText("Euler->Better:\nFunction1:\n");
        solution1.appendText("Euler->Better:\nFunction2:\n");
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y'= %.7f\n",i,y2n));
            y1n=y1n+h*y2nl;
            y2n=y2n+h*(3*y2nl-2*y1nl);
            y1nl=y1n;
            y2nl=y2n;
        }

        y1n=0;
        y2n=1;
        y1nl=0;
        y2nl=1;
        double k1[]=new double[3];
        double k2[]=new double[3];
        double k3[]=new double[3];
        double k4[]=new double[3];
        solution.appendText("\nRK-4\n");
        solution1.appendText("\nRK-4\n");
        for(double i=from;i<to+h;i+=h){
            solution.appendText(String.format("x= %.2f y1= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y2= %.7f\n",i,y2n));
            k1[1]=y2nl;
            k1[2]=3*y2nl-2*y1nl;
            k2[1]=y2nl+h*k1[2]/2;
            k2[2]=3*(y2nl+h/2*k1[2])-2*(y1nl+h/2*k1[1]);
            k3[1]=y2nl+h*k2[2]/2;
            k3[2]=3*(y2nl+h/2*k2[2])-2*(y1nl+h/2*k2[1]);
            k4[1]=y2nl+h*k3[2];
            k4[2]=3*(y2nl+h*k3[2])-2*(y1nl+h*k3[1]);
            y1n=y1n+h/6*(k1[1]+2*k2[1]+2*k3[1]+k4[1]);
            y2n=y2n+h/6*(k1[2]+2*k2[2]+2*k3[2]+k4[2]);
            y1nl=y1n;
            y2nl=y2n;
        }
        return;
    }

    @FXML
    void func42(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        double y1n=1,y1nb,y2n=0,y2nb,y3n=1,y3nb,y1nl=1,y2nl=0;
        solution.appendText("Euler->Better:\nFunction1:\n");
        solution1.appendText("Euler->Better:\nFunction2:\n");
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y'= %.7f\n",i,y2n));
            y1n=y1n+h*y2nl;
            y2n=y2n+h*(0.1*(1-y1nl*y1nl)*y2nl-y1nl);
            y1nl=y1n;
            y2nl=y2n;
        }
        //RK
        y1n=1;
        y2n=0;
        y1nl=1;
        y2nl=0;
        double k1[]=new double[3];
        double k2[]=new double[3];
        double k3[]=new double[3];
        double k4[]=new double[3];
        solution.appendText("\nRK-4\n");
        solution1.appendText("\nRK-4\n");
        for(double i=from;i<to+h;i+=h){
            solution.appendText(String.format("x= %.2f y1= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y2= %.7f\n",i,y2n));
            k1[1]=y2nl;
            k1[2]=0.1*(1-y1nl*y1nl)*y2nl-y1nl;
            k2[1]=y2nl+h*k1[2]/2;
            k2[2]=0.1*(1-(y1nl+h/2*k1[1])*(y1nl+h/2*k1[1]))*(y2nl+h/2*k1[2])-(y1nl+h/2*k1[1]);
            k3[1]=y2nl+h*k2[2]/2;
            k3[2]=0.1*(1-(y1nl+h/2*k2[1])*(y1nl+h/2*k2[1]))*(y2nl+h/2*k2[2])-(y1nl+h/2*k2[1]);
            k4[1]=y2nl+h*k3[2];
            k4[2]=0.1*(1-(y1nl+h*k3[1])*(y1nl+h*k3[1]))*(y2nl+h*k3[2])-(y1nl+h*k3[1]);
            y1n=y1n+h/6*(k1[1]+2*k2[1]+2*k3[1]+k4[1]);
            y2n=y2n+h/6*(k1[2]+2*k2[2]+2*k3[2]+k4[2]);
            y1nl=y1n;
            y2nl=y2n;
        }
        return;
    }

    @FXML
    void func43(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        euler(42,h,from,to);
        rk(42,h,from,to);
        adams(42,h,from,to);
        return;
    }

    @FXML
    void func44(MouseEvent event){
        solution.clear();
        solution1.clear();
        solution2.clear();
        String temp;
        temp=h.getText();
        double h=Double.parseDouble(temp);
        temp=start.getText();
        double from=Double.parseDouble(temp);
        temp=end.getText();
        double to=Double.parseDouble(temp);
        double y1n=1,y1nb,y2n=0,y2nb,y3n=1,y3nb,y1nl=1,y2nl=0;
        solution.appendText("Euler->Better:\nFunction1:\n");
        solution1.appendText("Euler->Better:\nFunction2:\n");
        for(double i=from;i<=to+h;i+=h){
            solution.appendText(String.format("x= %.2f y= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y'= %.7f\n",i,y2n));
            y1n=y1n+h*y2nl;
            y2n=y2n+h*(-Math.sin(y1nl));
            y1nl=y1n;
            y2nl=y2n;
        }

        y1n=1;
        y2n=0;
        y1nl=1;
        y2nl=0;
        double k1[]=new double[3];
        double k2[]=new double[3];
        double k3[]=new double[3];
        double k4[]=new double[3];
        solution.appendText("\nRK-4\n");
        solution1.appendText("\nRK-4\n");
        for(double i=from;i<to+h;i+=h){
            solution.appendText(String.format("x= %.2f y1= %.7f\n",i,y1n));
            solution1.appendText(String.format("x= %.2f y2= %.7f\n",i,y2n));
            k1[1]=y2nl;
            k1[2]=-Math.sin(y1nl);
            k2[1]=y2nl+h*k1[2]/2;
            k2[2]=-Math.sin(y1nl+h/2*k1[1]);
            k3[1]=y2nl+h*k2[2]/2;
            k3[2]=-Math.sin(y1nl+h/2*k2[1]);
            k4[1]=y2nl+h*k3[2];
            k4[2]=-Math.sin(y1nl+h*k3[1]);
            y1n=y1n+h/6*(k1[1]+2*k2[1]+2*k3[1]+k4[1]);
            y2n=y2n+h/6*(k1[2]+2*k2[2]+2*k3[2]+k4[2]);
            y1nl=y1n;
            y2nl=y2n;
        }
        return;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        solution.clear();
        solution1.clear();
        solution2.clear();
        assert solution != null : "fx:id=\"solution\" was not injected: check your FXML file 'sample.fxml'.";
        assert h != null : "fx:id=\"h\" was not injected: check your FXML file 'sample.fxml'.";
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'sample.fxml'.";
        assert clear != null : "fx:id=\"clear\" was not injected: check your FXML file 'sample.fxml'.";
        assert end != null : "fx:id=\"end\" was not injected: check your FXML file 'sample.fxml'.";
        assert F1 != null : "fx:id=\"F1\" was not injected: check your FXML file 'sample.fxml'.";
        assert F2 != null : "fx:id=\"F2\" was not injected: check your FXML file 'sample.fxml'.";
        assert F3 != null : "fx:id=\"F3\" was not injected: check your FXML file 'sample.fxml'.";
        assert F41 != null : "fx:id=\"F4\" was not injected: check your FXML file 'sample.fxml'.";

    }
}
