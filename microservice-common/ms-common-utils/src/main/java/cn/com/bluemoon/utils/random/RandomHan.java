package cn.com.bluemoon.utils.random;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 随机生成汉字
 * 
 * @author linshihao
 *
 */
public class RandomHan {
	private Random random = new Random();
	private final static int delta = 0x9fa5 - 0x4e00 + 1; 

	/**
	 * 生成指定长度的汉字串
	 * @param low
	 * @param hight
	 * @param onlySimple
	 * @return
	 */
	public String createHanString(int low, int hight, boolean onlySimple) {
		String lineName = "";
		int length = low + random.nextInt(hight - low);
		for (int i = 0; i < length; i++) {
			if (onlySimple) {
				lineName += this.createSimpleHan();
			} else {
				lineName += this.createComplexHan();
			}
		}
		return lineName;
	}

	/**
	 * 随机生成一个汉字,去掉了相当多的繁体
	 * 
	 * @return 
	 */
	private String createSimpleHan() {
		String chineseWord = null;
		
		int hight, low;// 高低位
		hight = 176 + random.nextInt(39);
		low = 161 + random.nextInt(93);
		byte[] han = new byte[2];
		han[0] = new Integer(hight).byteValue();
		han[1] = new Integer(low).byteValue();
		try {
			chineseWord = new String(han, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return chineseWord;
	}

	/**
	 * 随机生成一个汉字
	 * @return
	 */
	private String createComplexHan() {
		String chineseWord = null;
		/**
		 * * 汉字的unicode码范围:0x4E00~0x9FA5。
		 */
		char chineseWordchar; // 随机数范围
		chineseWordchar = (char) (0x4e00 + random.nextInt(delta));
		chineseWord = Character.toString(chineseWordchar);
		return chineseWord;
	}

}
