package cn.npnt.tiaps.base.util;

import java.util.UUID;
/**
 * 
 * @author lijia 2011-5-12
 *
 */
public class UUIDUtil {
	/**
	 * 生成唯一编码
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public static void main(String[] args){
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
	}
}
