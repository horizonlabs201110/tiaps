package cn.npnt.tiaps.base.util;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
/**
 * 
 * @author lijia 2011-7-5
 *
 */
public class MessageSourceUtil {
	static ResourceBundleMessageSource p = new ResourceBundleMessageSource();

	public static String getMessages(String message) {
		p.setBasename("tryItApiMessage");
		return p.getMessage(message, null, null);
	}

	public static String getMessages(String message, Object[] obj) {
		p.setBasename("tryItApiMessage");
		return p.getMessage(message, obj, null);
	}

	public static String getMessages(String message, Object[] obj, Locale locale) {
		p.setBasename("tryItApiMessage");
		return p.getMessage(message, obj, locale);
	}

	public static void main(String[] args) {
		//System.out.println(MessageSourceUtil.getMessages("repealOrder.success", new Object[] { "122131233" }));
	}
}
