package com.fast.demo.basic.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/*
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
*/

/**
 * 驗證文件上傳的類型
 * 
 * 
 *
 */
public class FileUtil {
	private static Map<String, String> map = new HashMap<>();
	private static List<String> list = new ArrayList<>();

	private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private static void getAllFileType() {
		FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
		FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
		FILE_TYPE_MAP.put("47494638396126026f01", "gif");
		FILE_TYPE_MAP.put("49492a00227105008037", "tif");
		FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); // 16色位图(bmp)
		FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); // 24位位图(bmp)
		FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); // 256色位图(bmp)
		FILE_TYPE_MAP.put("41433130313500000000", "dwg");
		FILE_TYPE_MAP.put("3c21444f435459504520", "html");
		FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
		FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
		FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
		FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
		FILE_TYPE_MAP.put("38425053000100000000", "psd");
		FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
		FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); // MS Excel、Word、Msi
		FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd");
		FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
		FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf");
		FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); // rmvb、rm
		FILE_TYPE_MAP.put("464c5601050000000900", "flv"); // flv、f4v
		FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
		FILE_TYPE_MAP.put("49443303000000002176", "mp3");
		FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
		FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); // wmv、asf
		FILE_TYPE_MAP.put("52494646e27807005741", "wav");
		FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
		FILE_TYPE_MAP.put("4d546864000000060001", "mid");
		FILE_TYPE_MAP.put("504b0304140000000800", "zip");
		FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
		FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
		FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
		FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
		FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
		FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
		FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
		FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
		FILE_TYPE_MAP.put("7061636b616765207765", "java");
		FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
		FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
		FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");
		FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
		FILE_TYPE_MAP.put("49545346030000006000", "chm");
		FILE_TYPE_MAP.put("04000000010000001300", "mxp");
		FILE_TYPE_MAP.put("504b0304140006000800", "docx");
		FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");// WPS(wps、et、dps)
		FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
		FILE_TYPE_MAP.put("6D6F6F76", "mov");
		FILE_TYPE_MAP.put("FF575043", "wpd");
		FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
		FILE_TYPE_MAP.put("2142444E", "pst");
		FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
		FILE_TYPE_MAP.put("E3828596", "pwl");
		FILE_TYPE_MAP.put("2E7261FD", "ram");
	}

	/**
	 * 得到上传文件的文件头
	 * 
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (null == src || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取文件类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileType(MultipartFile file) {
		String res = null;
		FileInputStream fis = null;
		try {
			fis = (FileInputStream) file.getInputStream();
			byte[] b = new byte[10];
			fis.read(b, 0, b.length);
			String fileCode = bytesToHexString(b);
//			if (!StringUtils.hasLength(fileCode)) {
//				return res;
//			}
			if (fileCode == null || fileCode == "") {
				return res;
			}
			Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
			while (keyIter.hasNext()) {
				String key = keyIter.next();
				if (key.toLowerCase(Locale.ENGLISH).startsWith(fileCode.toLowerCase(Locale.ENGLISH))
						|| fileCode.toLowerCase(Locale.ENGLISH).startsWith(key.toLowerCase(Locale.ENGLISH))) {
					res = FILE_TYPE_MAP.get(key);
					break;
				}
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	static {
		map.put("application/msword", ".doc");
		map.put("application/vnd.ms-powerpoint", ".ppt");
		map.put("application/vnd.ms-excel", ".xls");
		map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
		map.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
		map.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
		map.put("application/x-rar-compressed", ".rar");
		map.put("application/zip", ".zip");
		map.put("application/pdf", ".pdf");
		map.put("text/plain", ".txt");
		map.put("image/gif", ".gif");
		map.put("image/png", ".png");
		map.put("image/jpeg", ".jpeg");
		map.put("image/bmp", ".bmp");

		list.add(".doc");
		list.add(".ppt");
		list.add(".xls");
		list.add(".docx");
		list.add(".pptx");
		list.add(".xlsx");
		list.add(".rar");
		list.add(".zip");
		list.add(".pdf");
		list.add(".txt");
		list.add(".gif");
		list.add(".png");
		list.add(".jpeg");
		list.add(".bmp");

		getAllFileType();
	}

	// 判断文件后缀和文件MIME类型是否匹配
	public static boolean validateFile(MultipartFile file) {
		boolean flag = true;

		String contentType = file.getContentType();
		String originalFilename = file.getOriginalFilename();
		// 获取文件后缀名
		String suffix = getFileType(file);
		if (suffix == null) {
			suffix = getSuffix(originalFilename);
		}
		// 检测文件后缀名是否是规定范围内的文件格式
		if (!list.contains(suffix)) {
			// throw new RuntimeException(suffix + "类型的文件不符合系统安全要求的文件格式");
			flag = false;
		}

		// 检查文件MIME类型和后缀是否匹配
		String extension = map.get(contentType);
		if (extension == null) {
			// throw new RuntimeException(suffix + "类型的文件不符合系统安全要求的文件格式");
			flag = false;
		}
		if (!suffix.equals(extension)) {
			// throw new RuntimeException("文件后缀名和文件MIME类型不匹配");
			flag = false;
		}
		return flag;
	}

	// 获取文件名后缀
	public static String getSuffix(String fileName) {
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex == -1) {
			throw new RuntimeException("文件无后缀");
		}
		String suffix = fileName.substring(lastIndex);
		if (suffix.length() == 1) {
			throw new RuntimeException("文件无后缀");
		}

		return suffix;
	}

	public static InputStream getResourcesFileInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
	}

	public static String getPath() {
		return FileUtil.class.getResource("/").getPath();
	}

	public static File createNewFile(String pathName) {
		File file = new File(getPath() + pathName);
		if (file.exists()) {
			file.delete();
		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
		return file;
	}

	public static File readFile(String pathName) {
		return new File(getPath() + pathName);
	}

//	public static File readUserHomeFile(String pathName) {
//		return new File(System.getProperty("user.home") + File.separator + pathName);
//	}

}
