package graphing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import model.Tecaj;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

public class Graph {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] makeMeAChart(Map<String, List<Tecaj>> dataset) throws IOException {

		// results: {completed, successful}
		Boolean[] results = new Boolean[] { false, false };

		SwingUtilities.invokeLater(() -> {

			// Initialize FX Toolkit
			new JFXPanel();
			
			
			Platform.runLater(() -> {

				//Defining the x an y axes
				CategoryAxis xAxis = new CategoryAxis();
				NumberAxis yAxis = new NumberAxis();
				yAxis.setAutoRanging(true);
				yAxis.setForceZeroInRange(false);
				//Setting labels for the axes
				xAxis.setLabel("Datum");
				yAxis.setLabel("Tečaj");

				//Map<String, List<Tecaj>> dataset = tecajnica.getSortedTecajnica();
				List<String> keyList = new ArrayList<String>(dataset.keySet());
				LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);

				//How many lines
				int no_of_currencies = dataset.entrySet().iterator().next().getValue().size();
				
				//For every currency make a new series
				for (int i = 0; i<no_of_currencies; i++) {
					XYChart.Series series = new XYChart.Series();
					//Name the series
					series.setName(dataset.entrySet().iterator().next().getValue().get(i).getOznaka());
					//Iterate the data points for the size of dataset (how many dates) 
					for (int j = 0; j<dataset.size(); j++) {
						//Parse string to double
						double y_value = Double.parseDouble(dataset.get(keyList.get(j)).get(i).getTecaj());
						//Add date(x) and currency value(y) to Data and add to series
						series.getData().add(new XYChart.Data(keyList.get(j).toString(), y_value));
					}
					linechart.getData().addAll(series);
				}
				StackPane pane = new StackPane(linechart);
				linechart.setCreateSymbols(false);
				linechart.setTitle("Gibanje tečaja");
				pane.setPadding(new Insets(15, 15, 15, 15));
				pane.setStyle("-fx-background-color: BEIGE");
				//Setting the Scene
				Scene scene = new Scene(pane, 1024, 768);
				File file = new File("chart.png");
				WritableImage image = scene.snapshot(null);
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
					results[1] = true;
				} catch (Exception e) {
					results[0] = true;
				} finally {
					results[0] = true;
				}
			});
		});

		while (!results[0]) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return getGraph();
	}

	public static byte[] getGraph() throws IOException {
		FileInputStream fis = new FileInputStream("chart.png");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int read= 0;
		while( (read = fis.read(buffer)) > -1){ 
			baos.write(buffer, 0, read);
		}
		fis.close();
		baos.close();
		byte pgnBytes [] = baos.toByteArray();
		return pgnBytes;
	}
}
