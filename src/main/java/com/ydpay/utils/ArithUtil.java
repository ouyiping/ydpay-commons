package com.ydpay.utils;

import java.math.BigDecimal;

/**  
 *   由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入、两个数的大小比较。
 *   @author HYF
 *   @version 1.0
 *   @CreatedTime 2015-06-10 
 */
public class ArithUtil
{

	/**默认除法运算精度 */ 
	private static final int DEF_DIV_SCALE = 2;    
	//这个类不能实例化  
	private ArithUtil(){}  
	
	/**  
	 *   提供精确的加法运算。  
	 *   @param   v1   被加数  
	 *   @param   v2   加数  
	 *   @return   两个参数的和  
	 */
	public static double add(Double v1,Double v2)
	{   
		if(v1 == null)
		{
			throw new RuntimeException("传入被加数v1不能为空.");
		}
		if(v2 == null)
		{
			throw new RuntimeException("传入加数v2不能为空.");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));    
		BigDecimal b2 = new BigDecimal(Double.toString(v2));    
		return  b1.add(b2).doubleValue();    
	}  
	
	/**  
	 *   提供精确的减法运算。  
	 *   @param   v1   被减数  
	 *   @param   v2   减数  
	 *   @return   两个参数的差  
	 */
	public static double subtract(Double v1,Double v2)
	{    
		if(v1 == null)
		{
			throw new RuntimeException("传入被减数v1不能为空.");
		}
		if(v2 == null)
		{
			throw new RuntimeException("传入减数v2不能为空.");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));    
		BigDecimal b2 = new BigDecimal(Double.toString(v2));    
		return b1.subtract(b2).doubleValue();    
	}  
	
	/**  
     *   提供精确的乘法运算。  
     *   @param   v1   被乘数  
     *   @param   v2   乘数  
     *   @return   两个参数的积  
     */
	public static double multiply(Double v1,Double v2)
	{    
		if(v1 == null)
		{
			throw new RuntimeException("传入被乘数v1不能为空.");
		}
		if(v2 == null)
		{
			throw new RuntimeException("传入乘数v2不能为空.");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));    
		BigDecimal b2 = new BigDecimal(Double.toString(v2));    
		return b1.multiply(b2).doubleValue();    
	}   
	
	/**  
     *   提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后 DEF_DIV_SCALE(ArithUtil的值类常量)位，以后的数字四舍五入。  
     *   
     *   @param   v1   被除数  
     *   @param   v2   除数  
     *   @return   两个参数的商  
     */
	public static double divide(Double v1,Double v2)
	{    
		//注意以下相除会抛出异常,原因: 通过BigDecimal的divide方法进行除法时当不整除，出现无限循环小数时，就会抛异常
		return divide(v1,v2,DEF_DIV_SCALE);    
	}  
	
	/**  
     *   提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。  
     *   @param   v1   被除数  
     *   @param   v2   除数  
     *   @param   scale 表示表示需要精确到小数点以后几位。  
     *   @return   两个参数的商  
     */
	public static double divide(Double v1,Double v2,Integer scale)
	{    
		if(scale == null)
		{
			throw new RuntimeException("传入参数精确到小数点位数scale不能为空.");
		}
		if(scale < 0)
		{    
			throw new IllegalArgumentException("The scale must be a positive integer or zero");    
		}
		if(v1 == null)
		{
			throw new RuntimeException("传入被除数v1不能为空.");
		}
		if(v2 == null)
		{
			throw new RuntimeException("传入除数v2不能为空.");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));    
		BigDecimal b2 = new BigDecimal(Double.toString(v2));    
		//ROUND_HALF_UP: 遇到.5的情况时往上近似,例: 1.5 --> 2
		//ROUND_HALF_DOWN : 遇到.5的情况时往下近似,例: 1.5 --> 1
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();    
	} 
	
	/**  
     *   提供精确的小数位四舍五入处理。  
     *   @param   v   需要四舍五入的数字  
     *   @param   scale  小数点后保留几位  
     *   @return   四舍五入后的结果  
     */
	public static double round(Double v,Integer scale)
	{    
		if(scale == null)
		{
			throw new RuntimeException("传入参数精确到小数点位数scale不能为空.");
		}
		if(scale < 0)
		{    
			throw new IllegalArgumentException("The scale must be a positive integer or zero");    
	    }    
		if(v == null)
		{
			throw new RuntimeException("传入参数v不能为空.");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));    
		BigDecimal one = new BigDecimal("1");    
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();    
	}    
	
	/**  
     *   提供两个数的比较大小。  v1与v2比较
     *   @param   v1   当前数
     *   @param   v2   比较数
     *   @return   -1(v1小于v2) 或  0(v1等于v2) 或 1(v1大于v2) 
     */
	public static int compareTo(Double v1,Double v2)
	{    
		if(v1 == null)
		{
			throw new RuntimeException("传入当前数v1不能为空.");
		}
		if(v2 == null)
		{
			throw new RuntimeException("传入比较数v2不能为空.");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));    
		BigDecimal b2 = new BigDecimal(Double.toString(v2));    
		return b1.compareTo(b2);
	}    
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args)
	{
		Double d1 = null;
		double d2 = 1.035;
		System.out.println(ArithUtil.add(d1, d2));
	}
}
