package util;

import org.apache.poi.sl.usermodel.PlaceableShape;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


/**
 * @Author: Weiyin
 * @Create: 2025/6/22 - 17:55
 */
public class PowerPointUtil {

    public static void main(String[] args) throws Exception {


        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("slideshow.pptx"));


        // get slides
        List<XSLFSlide> slides = ppt.getSlides();


        for (int i = 0; i < slides.size(); i++) {
            XSLFSlide slide = slides.get(i);
            System.out.println("=== 幻灯片 #" + (i + 1) + " ===");

            for (XSLFShape shape : slide.getShapes()) {
                Rectangle2D anchor = shape.getAnchor();
                String anchorInfo = String.format("位置 [x=%.2f, y=%.2f, w=%.2f, h=%.2f]",
                        anchor.getX(), anchor.getY(), anchor.getWidth(), anchor.getHeight());

                // 1. 文本类（包括 TextBox、标题等）
                if (shape instanceof XSLFTextShape textShape) {
                    String text = textShape.getText();
                    System.out.println("类型: TextShape");
                    System.out.println("内容: " + (text == null ? "(空)" : text.trim()));
                    System.out.println(anchorInfo);

                    if (text != null & text.contains("Redis原理篇")) {
                        String newText = text.replace("Redis原理篇", "HeDa讲Redis");
                        textShape.setText(newText);
                    }
                }

                // 2. 表格
                else if (shape instanceof XSLFTable table) {
                    System.out.println("类型: Table");
                    int rowNum = table.getNumberOfRows();
                    int colNum = table.getNumberOfColumns();
                    System.out.println("表格大小: " + rowNum + " 行 × " + colNum + " 列");
                    for (int r = 0; r < rowNum; r++) {
                        for (int c = 0; c < colNum; c++) {
                            XSLFTableCell cell = table.getCell(r, c);
                            if (cell != null) {
                                System.out.print(cell.getText() + "\t");
                            }
                        }
                        System.out.println();
                    }
                    System.out.println(anchorInfo);
                }

                // 3. 图表、SmartArt等图形容器
                else if (shape instanceof XSLFGraphicFrame frame) {
                    System.out.println("类型: GraphicFrame (可能是图表/SmartArt)");
                    System.out.println("名称: " + frame.getShapeName());
                    System.out.println(anchorInfo);

                    XSLFChart chart = frame.getChart();
                    if (chart == null) {
                        continue;
                    }

                    for (XDDFChartData chartData : chart.getChartSeries()) {

                        System.out.println("图表类型: " + chartData.getClass().getSimpleName());

                        for (XDDFChartData.Series series : chartData.getSeries()) {
//                                System.out.println("系列名: " + series.getTitle());


                            XDDFDataSource<?> categories = series.getCategoryData();
                            XDDFNumericalDataSource<? extends Number> values = series.getValuesData();

                            for (int j = 0; j < categories.getPointCount(); j++) {
                                System.out.println("  分类: " + categories.getPointAt(j)
                                        + "，值: " + values.getPointAt(j));
                            }
                        }
                    }

                }
            }

            System.out.println(); // 幻灯片之间换行
        }


        try (FileOutputStream out = new FileOutputStream("table.pptx")) {
            ppt.write(out);
        }
        System.out.println("表格写入完毕！");
    }


}
