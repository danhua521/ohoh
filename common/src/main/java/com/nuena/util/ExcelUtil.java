package com.nuena.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @Description: excel操作工具
 * @author: rengb
 * @time: 2020/4/24 15:09
 */
public class ExcelUtil {

    /**
     * excel文件生成
     *
     * @param path        文件路径
     * @param filename    文件名
     * @param sheetName   excel 页签名称
     * @param headerNames excel 列名称
     * @param dataMapKeys 数据map的key们
     * @param dataMapList 数据map的集合
     * @return
     */
    public static boolean createExcel(String path, String filename, String sheetName, String[] headerNames, String[] dataMapKeys, List<Map<String, String>> dataMapList) {
        boolean flag = false;
        HSSFWorkbook wb = null;
        FileOutputStream fos = null;
        try {
            if (StringUtil.isBlank(path) || StringUtil.isBlank(filename) || dataMapKeys == null || dataMapKeys.length == 0 || ListUtil.isEmpty(dataMapList)) {
                return false;
            }

            fos = new FileOutputStream(path + "\\" + filename + ".xls");
            wb = new HSSFWorkbook();

            HSSFSheet sheet;
            if (StringUtil.isBlank(sheetName)) {
                sheet = wb.createSheet("Sheet1");
            } else {
                sheet = wb.createSheet(sheetName);
            }

            HSSFRow row = sheet.createRow(0);
            if (headerNames != null && headerNames.length > 0) {
                HSSFCell headCell = null;
                for (int i = 0; i < headerNames.length; i++) {
                    headCell = row.createCell(i);
                    headCell.setCellValue(headerNames[i]);
                }
            }

            int rowNum = 1;
            HSSFCell contentCell = null;
            String contentCellValue = null;
            for (Map<String, String> dataMap : dataMapList) {
                row = sheet.createRow(rowNum);
                int cellNum = 0;
                for (String dataMapKey : dataMapKeys) {
                    contentCell = row.createCell(cellNum);
                    contentCellValue = dataMap.get(dataMapKey);
                    if (StringUtil.isBlank(contentCellValue)) {
                        contentCell.setCellValue("");
                    } else {
                        contentCell.setCellValue(contentCellValue);
                    }
                    cellNum++;
                }
                rowNum++;
            }

            wb.write(fos);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        String[] headerNames = { "项目名称", "所属类别", "参考范围" };
        String[] dataMapKeys = { "项目名称", "所属类别", "参考范围" };
        Map<String, String> map = Maps.newHashMap();
        map.put("项目名称", "大幅度发放到");
        map.put("所属类别", "大幅度发");
        map.put("参考范围", "而非");
        Map<String, String> map2 = Maps.newHashMap();
        map2.put("项目名称", "大幅度发放到");
        map2.put("所属类别", "大幅度发");
        map2.put("参考范围", "而非");

        List<Map<String, String>> list = Lists.newArrayList();
        list.add(map);
        list.add(map2);

        ExcelUtil.createExcel("C:\\Users\\RGB\\Desktop\\调试", "tgb", null, headerNames, dataMapKeys, list);
    }

}