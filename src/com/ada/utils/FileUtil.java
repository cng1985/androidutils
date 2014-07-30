package com.ada.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
  
/** 
 * 文件操作輔助類 
 *  
 * @author jiangxc 
 * @version 1.0.0 <br> 
 *          <b>create Date:</b>Jul 13, 2009 1:37:44 PM<br> 
 *          <b>file name:</b>FileUtil.java<br> 
 *          <b>package name:</b>jp.co.greenblue.ecodasweb.util<br> 
 *          <b>class name:</b>FileUtil<br> 
 */  
public class FileUtil {  
    /** 
     * 将流中的文本读入一个 StringBuffer 中 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 1:44:51 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 1:44:51 PM<br> 
     * @param buffer 
     * @param is 
     * @throws IOException 
     */  
  
    public static void readToBuffer(StringBuffer buffer, InputStream is)  
            throws IOException {  
        String line; // 用来保存每行读取的内容  
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
        line = reader.readLine(); // 读取第一行  
        while (line != null) { // 如果 line 为空说明读完了  
            buffer.append(line); // 将读到的内容添加到 buffer 中  
            buffer.append("\n"); // 添加换行符  
            line = reader.readLine(); // 读取下一行  
        }  
    }  
  
    /** 
     * 将 StringBuffer 中的内容读出到流中 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 1:45:07 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 1:45:07 PM<br> 
     * @param buffer 
     * @param os 
     */  
  
    public static void writeFromBuffer(StringBuffer buffer, OutputStream os) {  
        // 用 PrintStream 可以方便的把内容输出到输出流中  
        // 其对象的用法和 System.out 一样  
        // （System.out 本身就是 PrintStream 对象）  
        PrintStream ps = new PrintStream(os);  
        ps.print(buffer.toString());  
        ps.flush();  
    }  
  
    /** 
     * 从输入流中拷贝内容到输入流中 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 1:46:18 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 1:46:18 PM<br> 
     * @param is 
     * @param os 
     * @throws IOException 
     */  
  
    public static void copyStream(InputStream is, OutputStream os)  
            throws IOException {  
        // 这个读过过程可以参阅 readToBuffer 中的注释  
        String line;  
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));  
        line = reader.readLine();  
        while (line != null) {  
            writer.println(line);  
            line = reader.readLine();  
        }  
        writer.flush();  
        // 最后确定要把输出流中的东西都写出去了  
        // 这里不关闭 writer 是因为 os 是从外面传进来的  
        // 既然不是从这里打开的，也就不从这里关闭  
        // 如果关闭的 writer，封装在里面的 os 也就被关了  
    }  
  
    /** 
     * 调用 copyStream(InputStream, OutputStream) 方法拷贝文本文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 1:46:58 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 1:46:58 PM<br> 
     * @param inFilename 
     * @param outFilename 
     * @throws IOException 
     */  
  
    public static void copyTextFile(String inFilename, String outFilename)  
            throws IOException {  
        // 先根据输入/输出文件生成相应的输入/输出流  
        InputStream is = new FileInputStream(inFilename);  
        OutputStream os = new FileOutputStream(outFilename);  
        FileUtil.copyStream(is, os); // 用 copyStream 拷贝内容  
        is.close(); // is 是在这里打开的，所以需要关闭  
        os.close(); // os 是在这里打开的，所以需要关闭  
    }  
  
    /** 
     * 获取从键盘输入的字符，写到文件中 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 1:53:45 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 1:53:45 PM<br> 
     * @param outFileName 
     */  
  
    public static void write2FileByBuffer(String outFileName) {  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        try {  
            File file = new File(outFileName);  
            String readString = br.readLine();  
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));  
            while (!"".equals(readString)) {  
                bw.write(readString);  
                readString = br.readLine();  
            }  
  
            bw.flush();  
            br.close();  
            bw.close();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        }  
    }  
  
 
  
    /** 
     * 创建与删除文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 2:46:54 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 2:46:54 PM<br> 
     * @param filePath 
     * @param fileName 
     * @return 创建成功返回true 
     * @throws IOException 
     */  
  
    public static boolean createAndDeleteFile(String filePath, String fileName)  
            throws IOException {  
        boolean result = false;  
        File file = new File(filePath, fileName);  
        if (file.exists()) {  
            file.delete();  
            result = true;  
            System.out.println("文件已经删除！");  
        } else {  
            file.createNewFile();  
            result = true;  
            System.out.println("文件已经创建！");  
        }  
        return result;  
    }  
  
    /** 
     * 创建和删除目录 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 2:47:26 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 2:47:26 PM<br> 
     * @param folderName 
     * @param filePath 
     * @return 删除成功返回true 
     */  
  
    public static boolean createAndDeleteFolder(String folderName,  
            String filePath) {  
        boolean result = false;  
        try {  
            File file = new File(filePath + folderName);  
            if (file.exists()) {  
                file.delete();  
                System.out.println("目录已经存在，已删除!");  
                result = true;  
            } else {  
                file.mkdir();  
                System.out.println("目录不存在，已经建立!");  
                result = true;  
            }  
        } catch (Exception ex) {  
            result = false;  
            System.out.println("CreateAndDeleteFolder is error:" + ex);  
        }  
        return result;  
    }  
  
    /** 
     * 输出目录中的所有文件及目录名字 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 2:48:01 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 2:48:01 PM<br> 
     * @param filePath 
     */  
  
    public static void readFolderByFile(String filePath) {  
        File file = new File(filePath);  
        File[] tempFile = file.listFiles();  
        for (int i = 0; i < tempFile.length; i++) {  
            if (tempFile[i].isFile()) {  
                System.out.println("File : " + tempFile[i].getName());  
            }  
            if (tempFile[i].isDirectory()) {  
                System.out.println("Directory : " + tempFile[i].getName());  
            }  
        }  
    }  
  
    /** 
     * 检查文件中是否为一个空 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 2:48:26 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 2:48:26 PM<br> 
     * @param filePath 
     * @param fileName 
     * @return 为空返回true 
     * @throws IOException 
     */  
  
    public static boolean fileIsNull(String filePath, String fileName)  
            throws IOException {  
        boolean result = false;  
        FileReader fr = new FileReader(filePath + fileName);  
        if (fr.read() == -1) {  
            result = true;  
            System.out.println(fileName + " 文件中没有数据!");  
        } else {  
            System.out.println(fileName + " 文件中有数据!");  
        }  
        fr.close();  
        return result;  
    }  
  
    /** 
     * 讀取指定文件中的數據，默認編碼為UTF-8 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:09 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:09 PM<br> 
     * @param fileName 
     * @return 
     * @throws IOException 
     */  
  
    public static String readTextFile(String filePath, String fileName)  
            throws IOException {  
        return FileUtil.readTextFile(filePath, fileName, null);  
    }  
  
    /** 
     * 根據encoding編碼讀取指定文件中的數據 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:13 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:13 PM<br> 
     * @param fileName 
     * @param encoding 
     * @return 
     * @throws IOException 
     */  
  
    public static String readTextFile(String filePath, String fileName,  
            String encoding) throws IOException {  
        StringBuffer result = new StringBuffer();  
  
        BufferedReader reader = null;  
        String line;  
        try {  
            if ("".equals(encoding) || encoding == null)  
                encoding = "UTF-8";  
            File file = new File(filePath);  
            if (!file.exists()) {  
                return "";  
            }  
            file = new File(filePath, fileName);  
            if (!file.exists())  
                return "";  
            reader = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(file), encoding));  
  
            while ((line = reader.readLine()) != null) {  
                result.append(line);  
            }  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } finally {  
            try {  
                if (reader != null)  
                    reader.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                throw e;  
            }  
  
        }  
  
        return result.toString();  
    }  
      
    /** 
     * 根據默認編碼UTF-8讀取指定文件中的數據,并返回List<String> LIST中的每人元素為一行 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 1:00:13 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 1:00:13 PM<br> 
     * @param filePath 
     * @param fileName 
     * @return List<String> 
     * @throws IOException 
     */  
      
    public static List<String> readTextFile2List(String filePath, String fileName) throws IOException {  
        return readTextFile2List(filePath,fileName,null);  
    }  
  
    /** 
     * 根據encoding編碼讀取指定文件中的數據,并返回List<String> LIST中的每人元素為一行 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 1:00:13 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 1:00:13 PM<br> 
     * @param filePath 
     * @param fileName 
     * @param encoding 
     * @return List<String> 
     * @throws IOException 
     */  
      
    public static List<String> readTextFile2List(String filePath, String fileName,  
            String encoding) throws IOException {  
        List<String> result = new ArrayList<String>();  
  
        BufferedReader reader = null;  
        String line;  
        try {  
            if ("".equals(encoding) || encoding == null)  
                encoding = "UTF-8";  
            File file = new File(filePath);  
            if (!file.exists()) {  
                return null;  
            }  
            file = new File(filePath, fileName);  
            if (!file.exists())  
                return null;  
            reader = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(file), encoding));  
  
            while ((line = reader.readLine()) != null) {  
                result.add(line);  
            }  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            throw e;  
        } finally {  
            try {  
                if (reader != null)  
                    reader.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                throw e;  
            }  
  
        }  
  
        return result;  
    }  
      
    /** 
     * 向指定文件寫入數據，默認編碼為UTF-8 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:18 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:18 PM<br> 
     * @param fileName 
     * @param testString 
     * @throws IOException 
     */  
  
    public static void writeTextFile(String filePath, String fileName,  
            String testString) throws IOException {  
        FileUtil.writeTextFile(filePath, fileName, testString, null);  
    }  
  
    /** 
     * 根據encoding編碼向指定文件寫入數據 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     * @param fileName 
     * @param testString 
     * @param encoding 
     * @throws IOException 
     */  
  
    public static void writeTextFile(String filePath, String fileName,  
            String testString, String encoding) throws IOException {  
        BufferedWriter bw = null;  
        try {  
            if ("".equals(encoding) || encoding == null)  
                encoding = "UTF-8";  
            File file = new File(filePath);  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            file = new File(filePath, fileName);  
            if (!file.exists())  
                file.createNewFile();  
            bw = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(file), encoding));  
            bw.write(testString);  
            bw.flush();  
        } catch (IOException ioe) {  
            throw ioe;  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                throw e;  
            }  
        }  
    }  
    /** 
     * 將List<String>數據根據默認編碼UTF-8向指定文件寫入數據，LIST中的每個元素為一行 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     * @param fileName 
     * @param textList 
     * @param encoding 
     * @throws IOException 
     */  
  
    public static void writeList2TextFile(String filePath, String fileName,  
            List<String> textList) throws IOException {  
        writeList2TextFile(filePath,fileName,textList,null);  
    }  
    /** 
     * 將List<String>數據根據encoding編碼向指定文件寫入數據，LIST中的每個元素為一行 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 3:45:21 PM<br> 
     * @param fileName 
     * @param textList 
     * @param encoding 
     * @throws IOException 
     */  
  
    public static void writeList2TextFile(String filePath, String fileName,  
            List<String> textList, String encoding) throws IOException {  
        StringBuffer sb=new StringBuffer();  
        if(textList!=null && textList.size()!=0){  
            for(String tmpstr:textList){  
                sb.append(tmpstr);  
                sb.append("\r\n");  
            }  
        }  
        BufferedWriter bw = null;  
        try {  
            if ("".equals(encoding) || encoding == null)  
                encoding = "UTF-8";  
            File file = new File(filePath);  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            file = new File(filePath, fileName);  
            if (!file.exists())  
                file.createNewFile();  
            bw = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(file), encoding));  
            bw.write(sb.toString());  
            bw.flush();  
        } catch (IOException ioe) {  
            throw ioe;  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                throw e;  
            }  
        }  
    }  
      
    /** 
     * 新建目录 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:39:22 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:39:22 PM<br> 
     * @param folderPath 
     *            String 如 c:/fqf 
     * @return boolean 
     */  
  
    public static boolean newFolder(String folderPath) {  
        boolean flag = false;  
  
        String filePath = folderPath;  
        filePath = filePath.toString();  
        File myFilePath = new File(filePath);  
        if (!myFilePath.exists()) {  
            flag = myFilePath.mkdir();  
        }  
        return flag;  
    }  
  
    /** 
     * 新建文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:38:26 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:38:26 PM<br> 
     * @param filePathAndName 
     *            String 文件路径及名称 如c:/fqf.txt 
     * @param fileContent 
     *            String 文件内容 
     * @return boolean 
     * @throws IOException 
     */  
  
    public static boolean newFile(String filePathAndName, String fileContent)  
            throws IOException {  
        boolean flag = false;  
        String filePath = filePathAndName;  
        filePath = filePath.toString();  
        File myFilePath = new File(filePath);  
        if (!myFilePath.exists()) {  
            flag = myFilePath.createNewFile();  
        }  
        FileWriter resultFile = new FileWriter(myFilePath);  
        PrintWriter myFile = new PrintWriter(resultFile);  
        String strContent = fileContent;  
        myFile.println(strContent);  
        resultFile.close();  
        return flag;  
    }  
  
    /** 
     * 删除文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:37:40 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:37:40 PM<br> 
     * @param filePathAndName 
     *            String 文件路径及名称 如c:/fqf.txt 
     * @return boolean 
     */  
  
    public static boolean delFile(String filePathAndName) {  
        boolean flag = false;  
        String filePath = filePathAndName;  
        filePath = filePath.toString();  
        File myDelFile = new File(filePath);  
        flag = myDelFile.delete();  
        return flag;  
    }  
  
    /** 
     * 删除文件夹 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:37:17 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:37:17 PM<br> 
     * @param folderPath 
     *            String 文件夹路径及名称 如c:/fqf 
     * @return boolean 
     */  
  
    public static boolean delFolder(String folderPath) {  
        boolean flag = false;  
        flag = delAllFile(folderPath); // 删除完里面所有内容  
        File myFilePath =new File(folderPath);  
        flag = flag ? myFilePath.delete() : false; // 删除空文件夹  
        return flag;  
    }  
  
    /** 
     * 删除文件夹里面的所有文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:37:00 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:37:00 PM<br> 
     * @param path 
     *            String 文件夹路径 如 c:/fqf 
     * @return boolean 
     */  
  
    public static boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                flag = temp.delete();  
            }  
            if (temp.isDirectory()) {  
                flag = delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                flag = flag ? delFolder(path + "/" + tempList[i]) : false;// 再删除空文件夹  
            }  
        }  
        return flag;  
    }  
  
    /** 
     * 复制单个文件 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:36:18 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:36:18 PM<br> 
     * @param oldPath 
     *            String 原文件路径 如：c:/fqf.txt 
     * @param newPath 
     *            String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */  
  
    public static boolean copyFile(String oldPath, String newPath) throws IOException{  
        boolean flag=false;  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPath);  
            if (oldfile.exists()) { // 文件存在时  
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件  
                FileOutputStream fs = new FileOutputStream(newPath);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; // 字节数 文件大小  
                    System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
            flag=true;  
        } catch (IOException e) {  
            throw e;  
        }  
        return flag;  
    }  
  
    /** 
     * 复制整个文件夹内容 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:35:50 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:35:50 PM<br> 
     * @param oldPath 
     *            String 原文件路径 如：c:/fqf 
     * @param newPath 
     *            String 复制后路径 如：f:/fqf/ff 
     */  
  
    public static void copyFolder(String oldPath, String newPath) {  
        try {  
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹  
            File a = new File(oldPath);  
            String[] file = a.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath  
                            + "/" + (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) {// 如果是子文件夹  
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("复制整个文件夹内容操作出错");  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 移动文件到指定目录 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:35:24 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:35:24 PM<br> 
     * @param oldPath 
     *            String 如：c:/fqf.txt 
     * @param newPath 
     *            String 如：d:/fqf.txt 
     * @throws IOException  
     */  
  
    public static void moveFile(String oldPath, String newPath) throws IOException {  
        copyFile(oldPath, newPath);  
        delFile(oldPath);  
    }  
  
    /** 
     * 移动文件到指定目录 
     *  
     * @author jiangxc<br> 
     *         <b>create Date:</b>Jul 13, 2009 5:34:54 PM<br> 
     *         <b>last modify Date:</b>Jul 13, 2009 5:34:54 PM<br> 
     * @param oldPath 
     *            String 如：c:/fqf.txt 
     * @param newPath 
     *            String 如：d:/fqf.txt 
     */  
  
    public static void moveFolder(String oldPath, String newPath) {  
        copyFolder(oldPath, newPath);  
        delFolder(oldPath);  
    }  
      
      
      
    /** 
     * 将csv格式的字符串写入文件 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 11:55:13 AM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 11:55:13 AM<br> 
     * @param pathName 
     * @param fileName 
     * @throws IOException  
     */  
      
    public static void writeStringData2CSV(String pathName,String fileName,String csvString) throws IOException{  
        writeTextFile(pathName, fileName, csvString);  
    }  
      
    /** 
     * 将符合CSV格式的字符串List<String> csvDataList存入文件，csvDataList的每个元素为一行 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:05:47 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:05:47 PM<br> 
     * @param pathName 
     * @param fileName 
     * @param csvDataList 
     * @throws IOException  
     */  
      
    public static void writeListData2CSV(String pathName,String fileName,List<String> csvDataList) throws IOException{  
        StringBuilder sb=new StringBuilder();  
        if(csvDataList!=null && csvDataList.size()!=0){  
            for(String tmpstr:csvDataList){  
                sb.append(tmpstr);  
                sb.append("\r\n");  
            }  
            writeTextFile(pathName, fileName, sb.toString());  
        }  
    }  
      
    /** 
     * 将符合CSV格式的字符串List<List<String>> csvDataList存入文件，csvDataList的每个元素为一行 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:05:51 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:05:51 PM<br> 
     * @param pathName 
     * @param fileName 
     * @param csvDataList 
     * @throws IOException  
     */  
      
    public static void writeListListData2CSV(String pathName,String fileName,List<List<String>> csvDataList) throws IOException{  
        StringBuilder sb=new StringBuilder();  
        if(csvDataList!=null && csvDataList.size()!=0){  
            for(List<String> tmpList:csvDataList){  
                for(String tmpstr:tmpList){  
                    sb.append(tmpstr);  
                    sb.append(",");  
                }  
                sb.append("\r\n");  
            }  
            writeTextFile(pathName, fileName, sb.toString());  
        }  
    }  
      
    /** 
     * 将符合CSV格式的字符串List<String[]> csvDataList存入文件，csvDataList的每个元素为一行 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:05:54 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:05:54 PM<br> 
     * @param pathName 
     * @param fileName 
     * @param csvDataList 
     * @throws IOException  
     */  
      
    public static void writeListArrayData2CSV(String pathName,String fileName,List<String[]> csvDataList) throws IOException{  
        StringBuilder sb=new StringBuilder();  
        if(csvDataList!=null && csvDataList.size()!=0){  
            for(String[] tmpArray:csvDataList){  
                for(String tmpstr:tmpArray){  
                    sb.append(tmpstr);  
                    sb.append(",");  
                }  
                sb.append("\r\n");  
            }  
            writeTextFile(pathName, fileName, sb.toString());  
        }  
    }  
      
    /** 
     * 将符合CSV格式的字符串String[][] csvDataArray存入文件 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:05:57 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:05:57 PM<br> 
     * @param pathName 
     * @param fileName 
     * @param csvDataArray 
     * @throws IOException  
     */  
      
    public static void writeArraysData2CSV(String pathName,String fileName,String[][] csvDataArray) throws IOException{  
        StringBuilder sb=new StringBuilder();  
        if(csvDataArray!=null && csvDataArray.length!=0){  
            for(String[] tmpArray:csvDataArray){  
                for(String tmpstr:tmpArray){  
                    sb.append(tmpstr);  
                    sb.append(",");  
                }  
                sb.append("\r\n");  
            }  
            writeTextFile(pathName, fileName, sb.toString());  
        }  
    }  
      
      
    /** 
     * 读取符合CSV规范的文件，并将文件内容以List<String>形式返回 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:06:01 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:06:01 PM<br> 
     * @param pathName 
     * @param fileName 
     * @return List<String> 
     * @throws IOException  
     */  
      
    public static List<String> readCSVData2ListString(String pathName,String fileName) throws IOException{  
        return readTextFile2List(pathName,fileName);  
    }  
      
      
    /** 
     * 读取符合CSV规范的文件，并将文件内容以String形式返回 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:06:06 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:06:06 PM<br> 
     * @param pathName 
     * @param fileName 
     * @return String 
     * @throws IOException  
     */  
      
    public static String readCSVData2String(String pathName,String fileName) throws IOException{  
        return readTextFile(pathName,fileName);  
    }  
      
    /** 
     * 读取符合CSV规范的文件，并将文件内容以String[][]形式返回 
     * @author jiangxc<br> 
     * <b>create Date:</b>Jul 14, 2009 12:06:09 PM<br> 
     * <b>last modify Date:</b>Jul 14, 2009 12:06:09 PM<br> 
     * @param pathName 
     * @param fileName 
     * @return 
     * @throws IOException  
     */  
      
}  