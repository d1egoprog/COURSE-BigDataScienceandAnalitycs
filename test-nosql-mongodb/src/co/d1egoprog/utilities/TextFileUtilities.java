package co.d1egoprog.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * File Utilities Class
 * 
 * @author Diego Alberto Rincón (d1egoprog)
 */
public class TextFileUtilities {
	
	/**
	 * 
	 * @param filePath path where the file is going to be saved
	 * @param fileName file name and extention
	 * @param text Content of the file
	 * @return if the file was correctly saved
	 */
	public static boolean storeFileInUTF8(String filePath, String fileName, String text) {
		 FileOutputStream fos = null;
	        OutputStreamWriter osw = null;
	        BufferedWriter bw = null;
	        try {
	        	File path = new File(filePath); 
	            fos = new FileOutputStream(path + "/"+ fileName);
	            osw = new OutputStreamWriter(fos, "UTF-8");
	            bw = new BufferedWriter(osw);
	            bw.write(text);
	            bw.flush();
	        }catch (IOException e) {
	        	System.err.println("Error Writiting file");
	        	return false;
	        } finally {
	            if (bw != null) {
	                try {
	                    bw.close();
	                } catch (IOException ignore) {
	                }
	            }
	            if (osw != null) {
	                try {
	                    osw.close();
	                } catch (IOException ignore) {
	                }
	            }
	            if (fos != null) {
	                try {
	                    fos.close();
	                } catch (IOException ignore) {
	                }
	            }
	        }
	        return true;
	}
	
	/**
	 * 
	 * @param filePath path where the file is going to be extract
	 * @param fileName file name and extention
	 * @return content of the file
	 */
	public static String readFileInUTF8(String filePath,String fileName) {
		StringBuffer stringBuffer = new StringBuffer();
		
		try {
			File fileDir = new File(filePath + "/" + fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
			String str;
			
			while ((str = in.readLine()) != null) {
				stringBuffer.append(str).append("\n");
			}
			
			in.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return "false";
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "false";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "false";
		}
		return stringBuffer.toString();
	}
	
	

}
