import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileOutputStream;

/**
 * @Author: Weiyin
 * @Create: 2025/6/22 - 20:31
 */
public class CreateChart {

    public static void main(String[] args) throws Exception{

        XMLSlideShow ppt = new XMLSlideShow();

        XSLFSlide slide = ppt.createSlide();

        XSLFChart chart = ppt.createChart(slide);

        slide.addChart(chart,new java.awt.Rectangle(50, 50, 500, 300));

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("分类");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("值");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        // 数据源
        String[] categories = {"A", "B", "C"};
        Double[] values = {10.0, 20.0, 30.0};

        XDDFDataSource<String> cat = XDDFDataSourcesFactory.fromArray(categories, null);
        XDDFNumericalDataSource<Double> val = XDDFDataSourcesFactory.fromArray(values, null);

        // 创建柱状图数据
        XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
        data.setVaryColors(true);

        // 添加系列
        XDDFChartData.Series series = data.addSeries(cat, val);
        series.setTitle("示例数据", null);

        // 设置图表方向（竖着的柱子）
        XDDFBarChartData bar = (XDDFBarChartData) data;
        bar.setBarDirection(BarDirection.COL);

        // 绘制图表
        chart.plot(data);




        // 输出文件
        try (FileOutputStream out = new FileOutputStream("bar-chart-demo.pptx")) {
            ppt.write(out);
        }

        System.out.println("PPTX 文件已生成：bar-chart-demo.pptx");






    }
}
