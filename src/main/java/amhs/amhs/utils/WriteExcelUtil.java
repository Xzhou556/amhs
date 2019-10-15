package amhs.amhs.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class WriteExcelUtil {
    public static void exportExcel(List<String[]> resource, OutputStream outputStream) throws IOException {
        //创建一个内存excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个表格
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //创建表头和内容
        String[] headerStr = resource.get(0);
        HSSFRow headerRow = sheet.createRow(0);
        //设置列宽
        for (int i = 0; i < headerStr.length; i++) {
            sheet.setColumnWidth(i, 5000);
        }
        //设置头单元格格式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        //设置字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontName("宋体");
        cellStyle.setFont(font);

        //定义表头内容
        for (int i = 0; i < headerStr.length; i++) {
            //创建一个单元格
            HSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(headerStr[i]);
        }
        //表体内容
        //样式
       HSSFCellStyle bodyStyle =  workbook.createCellStyle();
        bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置字体
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setColor(HSSFColor.BLACK.index);
        bodyFont.setFontName("宋体");
        bodyStyle.setFont(bodyFont);

        for (int row = 1; row < resource.size(); row++) {
            //输出行得数据
            String[] temp = resource.get(row);
            //创建行
            HSSFRow bodyRow = sheet.createRow(row);
            //循环创建列
            for (int cell = 0; cell <temp.length ; cell++) {
                HSSFCell bodyCell = bodyRow.createCell(cell);
                bodyCell.setCellStyle(bodyStyle);
                bodyCell.setCellValue(temp[cell]);

            }
        }
        workbook.write(outputStream);

    }
}
