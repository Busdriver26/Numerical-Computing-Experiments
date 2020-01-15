package sample;

/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import javax.print.attribute.standard.NumberUp;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="x_input"
    private TextField x_input; // Value injected by FXMLLoader

    @FXML // fx:id="poly_output"
    private TextArea poly_output; // Value injected by FXMLLoader

    @FXML // fx:id="button_2"
    private Button button_2; // Value injected by FXMLLoader

    @FXML // fx:id="button_1"
    private Button button_1; // Value injected by FXMLLoader

    @FXML // fx:id="point_output"
    private TextField point_output; // Value injected by FXMLLoader

    @FXML // fx:id="y_axis"
    private NumberAxis y_axis; // Value injected by FXMLLoader

    @FXML // fx:id="x_axis"
    private CategoryAxis x_axis; // Value injected by FXMLLoader

    @FXML // fx:id="point_insert"
    private TextField point_insert; // Value injected by FXMLLoader

    @FXML // fx:id="points_insert"
    private TextField points_insert; // Value injected by FXMLLoader

    @FXML // fx:id="point_insert"
    private TextField step_insert;

    @FXML // fx:id="point_insert"
    private TextField times_insert; // Value injected by FXMLLoader

    @FXML // fx:id="Controller"
    private GridPane Controller; // Value injected by FXMLLoader

    @FXML // fx:id="y_input"
    private TextField y_input; // Value injected by FXMLLoader

    @FXML // fx:id="chart"
    private LineChart<String,Number> chart; // Value injected by FXMLLoader

    @FXML
    int upload = 1000;

    @FXML
    private TextField upload_input;
    @FXML
    void Lagrange(MouseEvent event) {
        poly_output.clear();
        point_output.clear();
        String input_upload = upload_input.getText();
        if (input_upload.matches("[0-99999999]+")) {
            if (Integer.parseInt(input_upload) > 3) {
                upload = Integer.parseInt(input_upload);
            }
        }
        //Read the data
        double calc_sum = 0,calc_pow=1;
        int n = 0;
        int i, j,i_y,p=0;
        double x = 0;
        double fx_x[] = new double[upload];
        double fx_y[] = new double[upload];
        double calc_sum_fx[] = new double[upload];
        double calc_pow_fx[] = new double[upload];
        double split = 0;
        double max = 0,min = 0;
        int sig = 0;
        String input_xpp = point_insert.getText();
        if (!input_xpp.equals("")) {
            x = Double.parseDouble(input_xpp);
            sig = 1;
        }
        String input_x = x_input.getText();
        if (input_x.equals("")) {
            poly_output.setText("ERROR:NO INPUT in X\n");
            return;
        }
        String stringArray_x[] = input_x.split(" ");
        double num_x[] = new double[stringArray_x.length];
        for (i = 0; i < stringArray_x.length; i++) {
            num_x[i] = Double.parseDouble(stringArray_x[i]);
        }
        String input_y = y_input.getText();
        if (input_y.equals("")) {
            poly_output.setText("ERROR:NO INPUT in Y\n");
            return;
        }
        String stringArray_y[] = input_y.split(" ");
        double num_y[] = new double[stringArray_y.length];
        for (i_y = 0; i_y < stringArray_y.length; i_y++) {
            num_y[i_y] = Double.parseDouble(stringArray_y[i_y]);
        }
        if(i_y!=i){
            poly_output.setText("ERROR:The number of y doesn't fit the number of x.");
            return;
        }
        n = i - 1;
        double[] lag = new double[n+1];
        //split the dots on the splines
        max = num_x[0];
        min = num_x[0];
        for (i=0;i<=n;i++){
            if(max<num_x[i]){
                max = num_x[i];
            }
            if(min>num_x[i]){
                min=num_x[i];
            }
        }
        split = (max-min)/upload;
        for(i=0;i<upload;i++)
        {
            fx_x[i] = min+split*i;
            calc_pow_fx[i] = 1;
            calc_sum_fx[i] = 0;
        }
        //lagrange
        for (i = 0; i <= n; i++) {
            calc_pow = 1;
            for (p=0;p<upload;p++){
                calc_pow_fx[p] =1;
            }
            lag[i] = 1;
            for (j = 0; j <= n; j++) {
                if (i != j) {
                    lag[i] = lag[i] * (num_x[i] - num_x[j]);
                }
            }
            lag[i] = 1 / lag[i];
            calc_pow *= (num_y[i]*lag[i]);
            for (p=0;p<upload;p++){
                calc_pow_fx[p] *= (num_y[i]*lag[i]);
            }
            poly_output.appendText("l" + i + "(x)=" + String.format("%.6f",lag[i]));
            for (int k = 0; k <= n; k++){
                if (k != i) {
                    poly_output.appendText("(x-" + String.format("%.6f",num_x[k]) + ")");
                    calc_pow = calc_pow * (x-num_x[k]);
                    for (p=0;p<upload;p++){
                        calc_pow_fx[p] = calc_pow_fx[p] * (fx_x[p]-num_x[k]);
                    }
                }
            }
            poly_output.appendText("\n");
            calc_sum += calc_pow;
            for (p=0;p<upload;p++){
                calc_sum_fx[p] += calc_pow_fx[p];
            }
        }
        point_output.appendText(String.format("%.6f",calc_sum)+"\n");
        //draw the chart
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Lagrange");
        for(p=0;p<upload;p++){
            series.getData().add(new XYChart.Data<>(String.format("%.2f",fx_x[p]),calc_sum_fx[p]));
        }
        chart.getData().add(series);
        return;
    }

    @FXML
    void LagrangePiece(MouseEvent event) {
        poly_output.clear();
        point_output.clear();
        //Read the data
        String input_upload = upload_input.getText();
        if (input_upload.matches("[0-99999999]+")) {
            if (Integer.parseInt(input_upload) > 3) {
                upload = Integer.parseInt(input_upload);
            }
        }
        double calc_sum = 0,calc_pow=1;
        int n = 0;
        int i, j,i_y,p=0;
        double x = 0;
        double pw_fx_x[] = new double[upload];
        double fx_y[] = new double[upload];
        double pw_calc_sum_fx[] = new double[upload];
        double calc_pow_fx[] = new double[upload];
        double split = 0;
        double max = 0,min = 0;
        int sig = 0;
        String input_xpp = point_insert.getText();
        if (!input_xpp.equals("")) {
            x = Double.parseDouble(input_xpp);
            sig = 1;
        }
        String input_x = x_input.getText();
        if (input_x.equals("")) {
            poly_output.setText("ERROR:NO INPUT in X\n");
            return;
        }
        String stringArray_x[] = input_x.split(" ");
        double num_x[] = new double[stringArray_x.length];
        for (i = 0; i < stringArray_x.length; i++) {
            num_x[i] = Double.parseDouble(stringArray_x[i]);
        }
        String input_y = y_input.getText();
        if (input_y.equals("")) {
            poly_output.setText("ERROR:NO INPUT in Y\n");
            return;
        }
        String stringArray_y[] = input_y.split(" ");
        double num_y[] = new double[stringArray_y.length];
        for (i_y = 0; i_y < stringArray_y.length; i_y++) {
            num_y[i_y] = Double.parseDouble(stringArray_y[i_y]);
        }
        if(i_y!=i){
            poly_output.setText("ERROR:The number of y doesn't fit the number of x.");
            return;
        }
        String input_times = times_insert.getText();
        int times;
        times = Integer.parseInt(input_times)-1;
        if(times<1){
            poly_output.setText("ERROR:Time is Less than 2");
            return;
        }
        String input_step = step_insert.getText();
        int step;
        step = Integer.parseInt(input_step);
        if(step<1){
            poly_output.setText("ERROR:Step is Less than 1");
            return;
        }
        n = i - 1;
        double[] lag = new double[n+1];
        //split the dots on the splines
        for(int f=0,g=0;f+step<=n&&f+times<=n;f+=step,g++) {
            int drew = 0;
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("L-P3-"+(g+1));
            poly_output.appendText((g+1)+" try:\n");
            max = num_x[f];
            min = num_x[f];
            for (i = f; i <= f+times; i++) {
                if (max < num_x[i]) {
                    max = num_x[i];
                }
                if (min > num_x[i]) {
                    min = num_x[i];
                }
            }
            split = (max - min) / upload;
            for (i = 0; i < upload; i++) {
                pw_fx_x[i] = min + split * i;
                calc_pow_fx[i] = 1;
                pw_calc_sum_fx[i] = 0;
            }
            //lagrange
            calc_sum = 0;
            for (i = f; i <= f+times; i++) {
                calc_pow = 1;
                for (p = 0; p < upload; p++) {
                    calc_pow_fx[p] = 1;
                }
                lag[i] = 1;
                for (j = f; j <= f+times; j++) {
                    if (i != j) {
                        lag[i] = lag[i] * (num_x[i] - num_x[j]);
                    }
                }
                lag[i] = 1 / lag[i];
                calc_pow *= (num_y[i] * lag[i]);
                for (p = 0; p < upload; p++) {
                    calc_pow_fx[p] *= (num_y[i] * lag[i]);
                }
                poly_output.appendText("l" + i + "(x)=" + String.format("%.6f", lag[i]));
                for (int k = f; k <= f+times; k++) {
                    if (k != i) {
                        poly_output.appendText("(x-" + String.format("%.6f", num_x[k]) + ")");
                        calc_pow = calc_pow * (x - num_x[k]);
                        for (p = 0; p < upload; p++) {
                            calc_pow_fx[p] = calc_pow_fx[p] * (pw_fx_x[p] - num_x[k]);
                        }
                    }
                }
                poly_output.appendText("\n");
                calc_sum += calc_pow;
                for (p = 0; p < upload; p++) {
                    pw_calc_sum_fx[p] += calc_pow_fx[p];
                }
            }
            if(x>=num_x[f]&&x<num_x[f+times]) {
                point_output.setText("SEEBELOW" + "\n");
                poly_output.appendText("The Estimated y="+String.format("%.6f",calc_sum)+'\n');
                drew = 1;
            }
            //draw the chart
            for (p = 0; p < upload; p++) {
                series.getData().add(new XYChart.Data<>(String.format("%.2f", pw_fx_x[p]), pw_calc_sum_fx[p]));
            }
            chart.getData().add(series);
        }
        return;
    }

    //@FXML //Warning: Piecewise isn't a active method to use in the app.
    @FXML
    void Piecewise(MouseEvent event) {
        poly_output.clear();
        point_output.clear();
        //Read the data
        double calc_sum = 0,calc_pow=1;
        int n = 0;
        int i, j,i_y;
        double x = 0;
        int sig = 0;
        String input_xpp = point_insert.getText();
        if (!input_xpp.equals("")) {
            x = Double.parseDouble(input_xpp);
            sig = 1;
        }
        String input_x = x_input.getText();
        if (input_x.equals("")) {
            poly_output.setText("ERROR:NO INPUT in X\n");
            return;
        }
        String stringArray_x[] = input_x.split(" ");
        double num_x[] = new double[stringArray_x.length];
        for (i = 0; i < stringArray_x.length; i++) {
            num_x[i] = Double.parseDouble(stringArray_x[i]);
        }
        String input_y = y_input.getText();
        if (input_y.equals("")) {
            poly_output.setText("ERROR:NO INPUT in Y\n");
            return;
        }
        String stringArray_y[] = input_y.split(" ");
        double num_y[] = new double[stringArray_y.length];
        for (i_y = 0; i_y < stringArray_y.length; i_y++) {
            num_y[i_y] = Double.parseDouble(stringArray_y[i_y]);
        }
        if(i_y!=i){
            poly_output.setText("ERROR:The number of y doesn't fit the number of x.");
            return;
        }
        n = i - 1;
        double max,min,split;
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("PIECEWISE_LINEAR");
        double[] fx_x = new double[upload+1];
        double[] calc_pow_fx = new double[upload+1];
        double[] calc_sum_fx = new double[upload+1];
        max = num_x[0];
        min = num_x[0];
        for (i=0;i<=n;i++){
            if(max<num_x[i]){
                max = num_x[i];
            }
            if(min>num_x[i]){
                min=num_x[i];
            }
        }
        split = (max-min)/upload;
        for(i=0;i<upload;i++)
        {
            fx_x[i] = min+split*i;
            calc_pow_fx[i] = 1;
            calc_sum_fx[i] = 0;
        }
        //Piecewise
        double xt[] = new double[n];
        //detect x in range x[i],x[i+1]
        //poly_output.appendText("n="+n);
        for(int m=0;m<n;m++){
            xt[m] = 1/(num_x[m]-num_x[m+1]);
            if(x>num_x[m]&&x<num_x[m+1]) {
                calc_sum = xt[m] * num_y[m] * (x - num_x[m+1]) - xt[m] * num_y[m + 1] * (x - num_x[m]);
            }
            for(int p=0;p<upload  ;p++){
                if(fx_x[p]>num_x[m]&&fx_x[p]<=num_x[m+1]){
                    calc_sum_fx[p] = xt[m] * num_y[m] * (fx_x[p] - num_x[m+1]) - xt[m] * num_y[m + 1] * (fx_x[p] - num_x[m]);}
            }
            poly_output.appendText("p"+m+"(x)="+String.format("%.4f",xt[m]*num_y[m])+"(x-"+String.format("%.4f",num_x[m])+")+"+String.format("%.4f",(-xt[m]*num_y[m+1]))+"(x-"+String.format("%.4f",num_x[m+1])+")\n");
        }
        //two point insert
        point_output.appendText(String.format("%.6f",calc_sum)+"\n");
        for (int p = 1; p < upload; p++) {
            series.getData().add(new XYChart.Data<>(String.format("%.2f", fx_x[p]), calc_sum_fx[p]));
        }
        chart.getData().add(series);
        return;
    }


    @FXML
    void Newton(MouseEvent event) {
        poly_output.clear();
        point_output.clear();
        //Read the data
        String input_upload = upload_input.getText();
        if (input_upload.matches("[0-99999999]+")) {
            if (Integer.parseInt(input_upload) > 3) {
                upload = Integer.parseInt(input_upload);
            }
        }
        double calc_sum = 0,calc_pow=1;
        double NTSUM = 0;
        double POW = 1;
        double NPOW = 1;
        double O_SUM = 0;
        int n = 0;
        int i, j,i_y,p=0;
        double x = 0;
        double fx_x_N[] = new double[upload];
        double fx_y[] = new double[upload];
        double calc_sum_fx_N[] = new double[upload];
        double calc_pow_fx_N[] = new double[upload];
        double split = 0;
        double max = 0,min = 0;
        int sig = 0;
        String input_xpp = point_insert.getText();
        if (!input_xpp.equals("")) {
            x = Double.parseDouble(input_xpp);
            sig = 1;
        }
        String input_x = x_input.getText();
        if (input_x.equals("")) {
            poly_output.setText("ERROR:NO INPUT in X\n");
            return;
        }
        String stringArray_x[] = input_x.split(" ");
        double num_x[] = new double[stringArray_x.length];
        for (i = 0; i < stringArray_x.length; i++) {
            num_x[i] = Double.parseDouble(stringArray_x[i]);
        }
        String input_y = y_input.getText();
        if (input_y.equals("")) {
            poly_output.setText("ERROR:NO INPUT in Y\n");
            return;
        }
        String stringArray_y[] = input_y.split(" ");
        double num_y[] = new double[stringArray_y.length];
        for (i_y = 0; i_y < stringArray_y.length; i_y++) {
            num_y[i_y] = Double.parseDouble(stringArray_y[i_y]);
        }
        if(i_y!=i){
            poly_output.setText("ERROR:The number of y doesn't fit the number of x.");
            return;
        }
        n = i - 1;
        double[] New = new double[n+1];
        //split the dots on the splines
        max = num_x[0];
        min = num_x[0];
        for (i=0;i<=n;i++){
            if(max<num_x[i]){
                max = num_x[i];
            }
            if(min>num_x[i]){
                min=num_x[i];
            }
        }
        split = (max-min)/upload;
        for(i=0;i<upload;i++)
        {
            fx_x_N[i] = min+split*i;
            calc_pow_fx_N[i] = 1;
            calc_sum_fx_N[i] = 0;
        }
        //Newton
        double rx = 0;
        for(int k = 1;k<=n;k++)
        {
            NTSUM = 0;
            NPOW = 1;
            for (p=0;p<upload;p++){
                calc_pow_fx_N[p] = 1;
            }
            for(i=0;i<=k;i++){
                POW = 1;
                for(j = 0;j<=k;j++){
                    if(i!=j){
                        POW *= num_x[i] - num_x[j];
                    }
                }
                NTSUM += num_y[i]/POW;
            }
            for(j=0;j<=k-1;j++)
            {
                NPOW *= x-num_x[j];
                for (p=0;p<upload;p++){
                    calc_pow_fx_N[p] *= fx_x_N[p] -num_x[j];
                }
            }
            poly_output.appendText("f[x0,...,x"+k+"]="+String.format("%.6f",NTSUM)+"\n");
            O_SUM += NPOW * NTSUM;
            for (p=0;p<upload;p++){
                calc_sum_fx_N[p] += NTSUM * calc_pow_fx_N[p];
            }
        }
        O_SUM += num_y[0];
        //RX
        double rx_sum=0;
        double rx_pow=1;
        for(i = 0;i<=n+1;i++)
        {
            rx_pow = 1;
            for(j=0;j<=n+1;j++){
                if(j==n+1&&i!=j){
                    rx_pow*=num_x[i]-x;
                    break;
                }
                if(i==n+1&&i!=j)
                {
                    rx_pow*=x-num_x[j];
                }
                else if(j!=i){
                    rx_pow*=num_x[i]-num_x[j];
                }
            }
            if(i==n+1){
                rx_sum += O_SUM/rx_pow;
                break;
            }
            rx_sum += num_y[i]/rx_pow;
        }
        for(i=0;i<=n;i++){
            rx_sum*= x-num_x[i];
        }
        point_output.setText(String.format("%.6f",O_SUM)+'\n');

        //draw the chart
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Newton");

        for(p=0;p<upload;p++){
            calc_sum_fx_N[p]+= num_y[0];
            series.getData().add(new XYChart.Data<>(String.format("%.2f",fx_x_N[p]),calc_sum_fx_N[p]));
        }
        chart.getData().add(series);
        poly_output.appendText("R(X)="+rx_sum);
        return;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert x_input != null : "fx:id=\"x_input\" was not injected: check your FXML file 'sample.fxml'.";
        assert poly_output != null : "fx:id=\"poly_output\" was not injected: check your FXML file 'sample.fxml'.";
        assert button_2 != null : "fx:id=\"button_2\" was not injected: check your FXML file 'sample.fxml'.";
        assert button_1 != null : "fx:id=\"button_1\" was not injected: check your FXML file 'sample.fxml'.";
        assert point_output != null : "fx:id=\"point_output\" was not injected: check your FXML file 'sample.fxml'.";
        assert y_axis != null : "fx:id=\"y_axis\" was not injected: check your FXML file 'sample.fxml'.";
        assert x_axis != null : "fx:id=\"x_axis\" was not injected: check your FXML file 'sample.fxml'.";
        assert point_insert != null : "fx:id=\"point_insert\" was not injected: check your FXML file 'sample.fxml'.";
        assert Controller != null : "fx:id=\"Controller\" was not injected: check your FXML file 'sample.fxml'.";
        assert y_input != null : "fx:id=\"y_input\" was not injected: check your FXML file 'sample.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'sample.fxml'.";
        point_output.clear();
        poly_output.clear();
        chart.getData().removeAll(chart.getData());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void clearall() {
        assert x_input != null : "fx:id=\"x_input\" was not injected: check your FXML file 'sample.fxml'.";
        assert poly_output != null : "fx:id=\"poly_output\" was not injected: check your FXML file 'sample.fxml'.";
        assert button_2 != null : "fx:id=\"button_2\" was not injected: check your FXML file 'sample.fxml'.";
        assert button_1 != null : "fx:id=\"button_1\" was not injected: check your FXML file 'sample.fxml'.";
        assert point_output != null : "fx:id=\"point_output\" was not injected: check your FXML file 'sample.fxml'.";
        assert y_axis != null : "fx:id=\"y_axis\" was not injected: check your FXML file 'sample.fxml'.";
        assert x_axis != null : "fx:id=\"x_axis\" was not injected: check your FXML file 'sample.fxml'.";
        assert point_insert != null : "fx:id=\"point_insert\" was not injected: check your FXML file 'sample.fxml'.";
        assert Controller != null : "fx:id=\"Controller\" was not injected: check your FXML file 'sample.fxml'.";
        assert y_input != null : "fx:id=\"y_input\" was not injected: check your FXML file 'sample.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'sample.fxml'.";
        point_output.clear();
        poly_output.clear();
        chart.getData().removeAll(chart.getData());
        point_insert.clear();
        x_input.clear();
        y_input.clear();
    }
}
