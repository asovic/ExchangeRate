package graphing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import model.Tecaj;

public class Plotter {

	public byte[] initUI(Map<String, List<Tecaj>> dataset) throws IOException {

		TimeSeriesCollection graph_dataset = createDataset(dataset);
		JFreeChart chart = createChart(graph_dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		try {
			ChartUtils.saveChartAsPNG(new File("chart.png"), chart, 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getGraph();
	}

	private TimeSeriesCollection createDataset(Map<String, List<Tecaj>> dataset) {

		List<String> keyList = new ArrayList<String>(dataset.keySet());
		int no_of_currencies = dataset.entrySet().iterator().next().getValue().size();

		List<TimeSeries> allSeries = new ArrayList<TimeSeries>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i<no_of_currencies; i++) {
			TimeSeries series = new TimeSeries(dataset.entrySet().iterator().next().getValue().get(i).getOznaka());

			for (int j = 0; j<dataset.size(); j++) {
				String datum = keyList.get(j).toString();
				Date xxx = null;
				try {
					xxx = sdf.parse(datum);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				double y_value = Double.parseDouble(dataset.get(keyList.get(j)).get(i).getTecaj());
				series.add(new Day(xxx), y_value);
			}
			allSeries.add(series);
		}

		TimeSeriesCollection graph_dataset = new TimeSeriesCollection();
		for (TimeSeries ena_crta : allSeries) {
			graph_dataset.addSeries(ena_crta);
		}

		return graph_dataset;
	}

	private JFreeChart createChart(final XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Gibanje tečaja",
				"Datum",
				"Tečaj",
				dataset,
				true,
				true,
				false
				);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(1.0f));
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesStroke(1, new BasicStroke(1.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle("Gibanje tečaja"));

		return chart;
	}

	public byte[] getGraph() throws IOException {
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
