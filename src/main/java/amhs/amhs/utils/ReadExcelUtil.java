package amhs.amhs.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcelUtil {
    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static List<String[]> readExcel(MultipartFile file) throws Exception {
        //检查文件
        checkFile(file);
        //获得workbook的工作薄对象
        Workbook wb = getWorkBook(file);
        //创建返回对象，把每行的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (wb != null) {
            //for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet的工资表
                Sheet sheet = wb.getSheetAt(0);
                //if (sheet == null) {
               //     continue;
                //}
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //以表头为基准的开始列
                int firstCellNum = sheet.getRow(0).getFirstCellNum();
                //以表头为基准的列数
                int lastCellNum = sheet.getRow(0).getLastCellNum();
                //循环所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    String[] cells = new String[lastCellNum];
                    if (lastCellNum != 0) {
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            if (cell == null) {
                               cells[cellNum] = null;
                            } else {
                                cells[cellNum] = getCellValue(cell);
                            }
                        }
                    }
                    list.add(cells);
                }

           // }
            wb.close();
        }
        return list;

    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        short format = cell.getCellStyle().getDataFormat();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (Constants.EXCEL_FORMAT_INDEX_TIME_DATE.contains(format)) {
                    //处理日期格式，时间格式
                    cellValue = getDateValue(cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString(),
                            cell.getNumericCellValue());
                } else {
                    //返回数值类型得值
                    Long round = Math.round(cell.getNumericCellValue());
                    Double doubleVal = cell.getNumericCellValue();
                    if (Double.parseDouble(round + ".0") == doubleVal) {
                        cellValue = round.toString();
                    } else {
                        cellValue = doubleVal.toString();
                    }
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA://公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK://控制
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR://故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;

        }
        return cellValue;
    }


    private static Workbook getWorkBook(MultipartFile file) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        InputStream is = file.getInputStream();
        //判断文件为03 还是07
        if (fileName.endsWith(Constants.EXCEL_SUFFIX_03)) {
            workbook = new HSSFWorkbook(is);
        } else if (fileName.endsWith(Constants.EXCEL_SUFFIX_07)) {
            workbook = new XSSFWorkbook(is);
        }

        return workbook;
    }

    private static void checkFile(MultipartFile file) throws Exception {
        if (null == file) {
            throw new FileNotFoundException("文件不存在");
        }
        String fileName = file.getOriginalFilename();
        //判断是否是excel文件
        if (!fileName.endsWith(Constants.EXCEL_SUFFIX_03) && !fileName.endsWith(Constants.EXCEL_SUFFIX_07)) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    private static String getDateValue(short dataFormat, String dataFormatString, double value) {
        if (!DateUtil.isValidExcelDate(value)) {
            return null;
        }
        Date date = DateUtil.getJavaDate(value);
        /**
         * 年月日时分秒
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_NYRSFM_STRING.contains(dataFormatString)) {
            return Constants.COMMON_DATE_FORMAT.format(date);
        }
        /**
         * 年月日
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_CNYR_STRING.contains(dataFormatString)) {
            return Constants.COMMON_DATE_FORMAT_NYR.format(date);
        }
        /**
         * 年月日
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_NYR_STRING.contains(dataFormatString)) {
            return Constants.COMMON_DATE_FORMAT_NYR.format(date);
        }
        /**
         * 年月
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_NY_STRING.contains(dataFormatString) ||
                Constants.EXCEL_FORMAT_INDEX_DATA_EXACT_NY.equals(dataFormat)) {
            return Constants.COMMON_DATE_FORMAT_NY.format(date);
        }
        /**
         * 月日
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_YR_STRING.contains(dataFormatString) ||
                Constants.EXCEL_FORMAT_INDEX_DATA_EXACT_YR.equals(dataFormat)) {
            return Constants.COMMON_DATE_FORMAT_YR.format(date);
        }
        /**
         * 月
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_Y_STRING.contains(dataFormatString)) {
            return Constants.COMMON_DATE_FORMAT_Y.format(date);
        }
        /**
         * 星期
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_XQ_STRING.contains(dataFormatString)) {
            return Constants.COMMON_TIME_FORMAT_XQ + CommonUtil.dateToWeek(date);
        }
        /**
         * 周
         */
        if (Constants.EXCEL_FORMAT_INDEX_DATE_Z_STRING.contains(dataFormatString)) {
            return Constants.COMMON_TIME_FORMAT_Z + CommonUtil.dateToWeek(date);
        }
        /**
         * 时间格式
         */
        if (Constants.EXCEL_FORMAT_INDEX_TIME_STRING.contains(dataFormatString) ||
                Constants.EXCEL_FORMAT_INDEX_TIME_EXACT.contains(dataFormat)) {
            return Constants.COMMON_TIME_FORMAT.format(DateUtil.getJavaDate(value));
        }
        /**
         * 单元格为其他覆盖到得类型
         */
        if (DateUtil.isADateFormat(dataFormat, dataFormatString)) {
            return Constants.COMMON_TIME_FORMAT.format(value);
        }
        return null;
    }

}
