package com.ydpay.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydpay.cache.redis.RedisCache;
import com.ydpay.enums.base.ColumnType;
import com.ydpay.utils.MgException;
import com.ydpay.utils.ServerParamsUtil;
import com.ydpay.utils.StringUtil;
import com.ydpay.utils.TimeUtil;
import com.ydpay.utils.YdpayLogger;

public class ExcelUtil {
	private static int version2003 = 2003;
	private static int version2007 = 2007;
	private static int version = version2003;

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> readExcelData(File file,
			int ignoreRows, List<String> keyList) throws FileNotFoundException,
			IOException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int rowSize = 0;
		String path = file.getPath();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		// 判断导入的excel版本
		initType(path);
		if (version == 2003) {
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			Cell cell = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(sheetIndex);
				// 第一行为标题，不取
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					Row row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					Map<String, Object> values = new HashMap<String, Object>();
					boolean hasValue = false;
					int columnIndex = 0;
					for (String keyItem : keyList) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							value = ExcelUtil.getCellValue(cell);
						}
						if (columnIndex++ == 0 && value.trim().equals("")) {
							break;
						}
						values.put(keyItem, value.trim());
						hasValue = true;
					}
					if (hasValue) {
						result.add(values);
					}
				}
			}
		} else if (version == 2007) {
			XSSFWorkbook wb = new XSSFWorkbook(in);
			Cell cell = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				XSSFSheet st = wb.getSheetAt(sheetIndex);
				// 第一行为标题，不取
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					Row row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					Map<String, Object> values = new HashMap<String, Object>();
					boolean hasValue = false;
					int columnIndex = 0;
					for (String keyItem : keyList) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							value = ExcelUtil.getCellValue(cell);
						}
						if (columnIndex++ == 0 && value.trim().equals("")) {
							break;
						}
						values.put(keyItem, value);
						hasValue = true;
					}
					if (hasValue) {
						result.add(values);
					}
				}
			}
		}

		in.close();
		return result;
	}

	public static List<Map<String, Object>> readExcelData(File file,
        int ignoreRows, List<String> keyList,Integer number) throws FileNotFoundException,
        IOException {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    int rowSize = 0;
    String path = file.getPath();
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(
            file));
    // 判断导入的excel版本
    initType(path);
    if (version == 2003) {
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        Cell cell = null;
        for (int sheetIndex = 0; sheetIndex < number; sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                Row row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                Map<String, Object> values = new HashMap<String, Object>();
                boolean hasValue = false;
                int columnIndex = 0;
                for (String keyItem : keyList) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        value = ExcelUtil.getCellValue(cell);
                    }
                    if (columnIndex++ == 0 && value.trim().equals("")) {
                        break;
                    }
                    values.put(keyItem, value.trim());
                    hasValue = true;
                }
                if (hasValue) {
                    result.add(values);
                }
            }
        }
    } else if (version == 2007) {
        XSSFWorkbook wb = new XSSFWorkbook(in);
        Cell cell = null;
        for (int sheetIndex = 0; sheetIndex < number; sheetIndex++) {
            XSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                Row row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                Map<String, Object> values = new HashMap<String, Object>();
                boolean hasValue = false;
                int columnIndex = 0;
                for (String keyItem : keyList) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        value = ExcelUtil.getCellValue(cell);
                    }
                    if (columnIndex++ == 0 && value.trim().equals("")) {
                        break;
                    }
                    values.put(keyItem, value);
                    hasValue = true;
                }
                if (hasValue) {
                    result.add(values);
                }
            }
        }
    }
        in.close();
        return result;
	}
	
	public static String getCellValue(Cell cell) {
		if (cell == null)
			return "";

		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = "";
				}
			} else {
				/*
				 * value = new DecimalFormat("0").format(cell
				 * .getNumericCellValue());
				 */
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			// 导入时如果为公式生成的数据则无值
		    cell.setCellType(Cell.CELL_TYPE_STRING);
			if (!cell.getStringCellValue().equals("")) {
				value = cell.getStringCellValue();
			} else {
				value = cell.getNumericCellValue() + "";
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_ERROR:
			value = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() == true ? "Y" : "N");
			break;
		default:
			value = "";
		}

		return value;
	}

	public static void writeDataToExcel(List<Map<String, Object>> list,
			List<String> headList, List<String> keyList, String path,
			String sheetName) throws Exception {
		writeDataToExcel(list, headList, keyList, null, path, sheetName);
	}

	/**
	 * @param list
	 * @param headList
	 * @param keyList
	 * @param typeList
	 * @param path 包含文件名
	 * @param sheetName
	 * @throws Exception
	 */
	public static void writeDataToExcel(List<Map<String, Object>> list,
			List<String> headList, List<String> keyList,
			List<Integer> typeList, String path, String sheetName)
			throws Exception {
		writeDataToExcel(list, headList, keyList, typeList,sheetName, path, null);
	}
	
	/**
	 * @param list
	 * @param headList
	 * @param keyList
	 * @param typeList
	 * @param filePath 如果fileName=null,该值需要加上文件名字，否则文件夹路径
	 * @param sheetName
	 * @param fileName 文件名字
	 * @throws Exception
	 */
	public static void writeDataToExcel(List<Map<String, Object>> list,
			List<String> headList, List<String> keyList,
			List<Integer> typeList, String sheetName,String filePath, String fileName)
			throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		Row row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int headIndex = 0;
		for (String headItem : headList) {
			Cell cell = row.createCell(headIndex++);
			cell.setCellValue(headItem);
			cell.setCellStyle(style);
		}
		YdpayLogger.info("--------写表头完成------");
		try {
			XSSFCellStyle cellStyle = wb.createCellStyle();
			XSSFDataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("0.00"));// 设置单元类型
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Map<String, Object> stu = (Map<String, Object>) list.get(i);
				// 第四步，创建单元格，并设置值
				int keyIndex = 0;
				for (String keyItem : keyList) {
					String value = "";
					if (stu.containsKey(keyItem) && stu.get(keyItem) != null) {
						value = stu.get(keyItem).toString();
					}
					Cell cell = row.createCell(keyIndex);
					boolean isString = false;
					// typeList数据 2：数字，3：long，4:时间， 5：小数，6:日期 1及其他：字符
					// cell.setCellType(typeList!=null?(typeList.get(keyIndex)==2?HSSFCell.CELL_TYPE_NUMERIC:HSSFCell.CELL_TYPE_STRING):HSSFCell.CELL_TYPE_STRING);
					if (typeList != null && StringUtil.isNotEmpty(value)) {
						if (typeList.get(keyIndex) == ColumnType.INTEGER.getCode()) {
							cell.setCellValue(StringUtil.isNotEmpty(value) ? Integer
									.parseInt(value) : 0);
						} else if (typeList.get(keyIndex) == ColumnType.LONG.getCode()) {
							cell.setCellValue(StringUtil.isNotEmpty(value) ? Long
									.parseLong(value) : 0);
						} else if (typeList.get(keyIndex) == ColumnType.TIME.getCode()) {
							int dotIndex = value.lastIndexOf(".");
							if (dotIndex > 0)
								value = value.substring(0, dotIndex);
							cell.setCellValue(value);
						} else if (typeList.get(keyIndex) == ColumnType.DOUBLE.getCode()) {
							cell.setCellValue(StringUtil.isNotEmpty(value) ? Double
									.parseDouble(value) : 0.0);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellStyle(cellStyle);
						} else if (typeList.get(keyIndex) == ColumnType.DATE.getCode()) {
							int dotIndex = value.lastIndexOf(" ");
							if (dotIndex > 0)
								value = value.substring(0, dotIndex);
							cell.setCellValue(value);
						} else {
							isString = true;
							cell.setCellValue(value);
						}
					} else {
						isString = true;
						cell.setCellValue(value);
					}
					if (i == list.size() - 1) {
						if (!isString || StringUtil.isEmpty(value)) {
							sheet.autoSizeColumn(keyIndex);
						} else {
							int length = StringUtil.getChineseLength(value,
									"GBK");
							int headLength = StringUtil.getChineseLength(
									headList.get(keyIndex), "GBK");
							if (headLength > length)
								length = headLength;
							if(length > 180)
								length = 180;
							sheet.setColumnWidth(keyIndex, length * 256);
						}

					}
					keyIndex++;
				}

			}
			// for (int i = 0; i < keyList.size(); i++) {
			// sheet.autoSizeColumn(i);
			// }
		} catch (Exception e) {
			throw e;
		}
		// 第六步，将文件存到指定位置
		try {
			File file = new File(filePath);
			if(fileName!=null){
				if(!file.exists()){
					file.mkdirs();
				}
				try {
					file = new File(filePath+fileName);
					if(!file.exists()){
						file.createNewFile();
					}
				} catch (IOException e) {
					YdpayLogger.error("创建文件file["+file.getName()+"]出错:"+e, e);
					throw new MgException(-1, "创建文件出错", e);
				}
			}else{
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (IOException e) {
						YdpayLogger.error("创建文件file["+file.getName()+"]出错:"+e, e);
						throw new MgException(-1, "创建文件出错", e);
					}
				}
			}
			
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public static void batchWriteDataToExcel(
			List<List<Map<String, Object>>> dataList, List<String> headList,
			List<String> keyList, String path, String sheetName)
			throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		int k = 0;
		if (dataList.size() > 0) {
			for (List<Map<String, Object>> list : dataList) {
				++k;
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				XSSFSheet sheet = wb.createSheet(sheetName + k);
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				XSSFRow row = sheet.createRow((int) 0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				XSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				int headIndex = 0;
				for (String headItem : headList) {
					XSSFCell cell = row.createCell(headIndex++);
					cell.setCellValue(headItem);
					cell.setCellStyle(style);
				}

				try {
					for (int i = 0; i < list.size(); i++) {
						row = sheet.createRow((int) i + 1);
						Map<String, Object> stu = (Map<String, Object>) list
								.get(i);
						// 第四步，创建单元格，并设置值
						int keyIndex = 0;
						for (String keyItem : keyList) {
							String value = "";
							if (stu.containsKey(keyItem)) {
								value = stu.get(keyItem).toString();
							}
							row.createCell(keyIndex++).setCellValue(value);
						}
					}
				} catch (Exception e) {
					throw e;
				}
			}
		} else {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			XSSFSheet sheet = wb.createSheet(sheetName);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			XSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			int headIndex = 0;
			for (String headItem : headList) {
				XSSFCell cell = row.createCell(headIndex++);
				cell.setCellValue(headItem);
				cell.setCellStyle(style);
			}

		}
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream(path);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 此方法判别Excel2003和Excel2007  
	public static void initType(String name) {
		if (name != null) {
			int index = name.indexOf(".");
			String suffex = name.substring(index);
			if (".xls".endsWith(suffex)) {
				version = version2003;
			} else if (".xlsx".endsWith(suffex)) {
				version = version2007;
			}
		}
	}

	/*
	 * public static void main(String[] args) throws FileNotFoundException,
	 * IOException { List<String> keyList = new ArrayList<String>();
	 * keyList.add("姓名"); keyList.add("年龄"); readExcelData(new
	 * File("F:\\bbbb.xlsx"),1,keyList); }
	 */
	/**
	 * excel导出文件名乱码转换
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String exportFileName(String fileName) {
		if (fileName != null && !"".equals(fileName)) {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	/**
	 * @param dataList
	 *            数据集合
	 * @param templatecode
	 *            模板编码
	 * @return map:url path
	 * @throws Exception
	 */
	public static Map<String, Object> exportData(
			List<Map<String, Object>> dataList, String templatecode, String fileName, String exportpath)
			throws Exception {

		String detailStr = RedisCache.getInstance().get("exceltemplatedetail_"
				+ templatecode);
		if ("".equals(detailStr)) {
			YdpayLogger.error("未找到redis模板详情数据");
			throw new MgException(-1, "未找到redis模板详情数据");
		}
		Map<Integer, Object> treeMap = new TreeMap<Integer, Object>();
		JSONArray jsonArray = JSONArray.parseArray(detailStr);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Integer columnindex = json.getInteger("columnindex");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("columnkey", json.getString("columnkey"));
			map.put("columnname", json.getString("columnname"));
			map.put("columntype", json.getString("columntype"));
			if (treeMap.containsKey(columnindex)) {
				columnindex = columnindex + 1;
			}
			treeMap.put(columnindex, map);
		}

		List<String> headList = new ArrayList<String>();
		List<String> keyList = new ArrayList<String>();
		List<Integer> typeList = new ArrayList<Integer>();
		for (Integer i : treeMap.keySet()) {
			JSONObject obj = JSONObject.parseObject(JSONObject
					.toJSONString(treeMap.get(i)));
			headList.add(obj.getString("columnname"));
			keyList.add(obj.getString("columnkey"));
			typeList.add(obj.getInteger("columntype"));
		}

		String path = ServerParamsUtil.get("file_root_path");
		//String path = "C:\\Users\\Administrator\\Desktop\\export\\";
		String url = ServerParamsUtil.get("file_root_url");
		
		path += exportpath;
		url += exportpath + fileName;
		ExcelUtil.writeDataToExcel(dataList, headList, keyList, typeList,
				"Sheet1",path,fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("path", path+fileName);
		return map;
	}
	
	public static Map<String, Object> exportData(
			List<Map<String, Object>> dataList, String templatecode) throws Exception{
		String fileName = getRedisFileName(templatecode)+ ".xlsx";
		String exportpath = ServerParamsUtil.get("export_file_path");
		return exportData(dataList, templatecode, fileName, exportpath);
	}

	/**
	 * @param dataList
	 * @param templatecode
	 * @param exportpath 追加的文件夹，用于拼装路径
	 * @return
	 * @throws Exception
	 * </home/ydpay/ydpayfile/+exportpath>
	 */
	public static Map<String, Object> exportData(
			List<Map<String, Object>> dataList, String templatecode,String exportpath) throws Exception{
		String fileName = getRedisFileName(templatecode)+ ".xlsx";
		return exportData(dataList, templatecode, fileName, exportpath);
	}
	
	public static String getRedisFileName(String templatecode) {
		String fileName = RedisCache.getInstance().get("exceltemplate_"
				+ templatecode);
		
//		String fileName = RedisUtil.getRedisCache("exceltemplate_"
//				+ templatecode);
		if ("".equals(fileName)) {
			YdpayLogger.error("未找到redis模板数据");
			throw new MgException(-1, "未找到redis模板数据");
		}
		fileName = fileName + TimeUtil.getDateFormat("yyyyMMddHHmmss");
		return fileName;
	}
}
