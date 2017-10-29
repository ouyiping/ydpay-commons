package com.ydpay.utils;

public class CommandHelper {

	public static String GetMethod(String method){
		String methodname="";
		String[] methodToken = method.split("\\.");
		
		int count=0;
		for(int i=methodToken.length-1;i>=2;i--){
			if(count==0){
				methodname=methodname+methodToken[i].toLowerCase();
			}else{
				methodname=methodname+methodToken[i].substring(0,1).toUpperCase()+methodToken[i].substring(1);
			}
			
			count++;
		}
		
		return methodname;
	}
	
	public static String GetServiceName(String method){
		String servicename="";
		String[] methodToken = method.split("\\.");
		servicename=methodToken[2].toLowerCase()+methodToken[3].substring(0,1).toUpperCase()+methodToken[3].substring(1)+"ServiceImpl";
		return servicename;
	}
	
	public static String getServiceName(String method){
		String result="";
		
		String[] strs=method.split("\\.");
		if(strs.length>2){
			result=strs[1];
			result=result+"Service";
		}
		return result;
	}
}
