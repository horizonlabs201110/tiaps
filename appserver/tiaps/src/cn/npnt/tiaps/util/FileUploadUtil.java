package cn.npnt.tiaps.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class FileUploadUtil {
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> scaleImage(HttpServletRequest request) throws Exception{
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart == true){
			//创建一个存储在硬盘的上的工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();		
			/*
			 *  以下设置factory 一些约束限制 
			 */
			//factory.setSizeThreshold(yourMaxMemorySize);
			//factory.setRepository(yourTempDirectory);		
			
			//禁用跟踪临时文件夹
			factory.setFileCleaningTracker(null);
			// 创建一个上传文件的操作者
			ServletFileUpload upload = new ServletFileUpload(factory);			
			//设置每个文件上传的最大字节数,-1表示没有限制
			upload.setFileSizeMax(-1L);
			//设置整个请求大小的限制
			// Set overall request size constraint		
			Map<String,Object> params = new HashMap<String,Object>();
			List<FileItem> imageItems = new ArrayList<FileItem>();
			List<FileItem>  items = upload.parseRequest(request);	
			for(FileItem item:items){
				//如果是属性是非字符串项
				if (item.isFormField() == false) {
					//如果内容类型包含图像信息
					imageItems.add(item);
				}else{
					String fieldName = item.getFieldName();
					String value = item.getString("UTF-8");
					params.put(fieldName, value);
				}
			}
			params.put("imageItems", imageItems);
			return params;
		}
		return null;
	}
}
