package com.premaservices.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************
 * @author Anna A. Semyonova aka Prema
 * @version 2.0
 */

public class TransliterateUtil {
	
	private static final HashMap<Character, String> CIRILLIC_TRANSLIT_TABLE = new HashMap<Character, String>();
	
	static {
		CIRILLIC_TRANSLIT_TABLE.put('\u0400', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0401', "YO");
		CIRILLIC_TRANSLIT_TABLE.put('\u0402', "YE");
		CIRILLIC_TRANSLIT_TABLE.put('\u0403', "G`");
		CIRILLIC_TRANSLIT_TABLE.put('\u0404', "YE");
		CIRILLIC_TRANSLIT_TABLE.put('\u0405', "Z`");
		CIRILLIC_TRANSLIT_TABLE.put('\u0406', "I'");
		CIRILLIC_TRANSLIT_TABLE.put('\u0407', "YI");
		CIRILLIC_TRANSLIT_TABLE.put('\u0408', "J");		
		CIRILLIC_TRANSLIT_TABLE.put('\u0409', "L`");		
		CIRILLIC_TRANSLIT_TABLE.put('\u040A', "N`");
		CIRILLIC_TRANSLIT_TABLE.put('\u040B', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u040C', "K`");
		CIRILLIC_TRANSLIT_TABLE.put('\u040D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u040E', "U'");
		CIRILLIC_TRANSLIT_TABLE.put('\u040F', "DH");								
		CIRILLIC_TRANSLIT_TABLE.put('\u0410', "A");	//А										
		CIRILLIC_TRANSLIT_TABLE.put('\u0411', "B");	//Б	
		CIRILLIC_TRANSLIT_TABLE.put('\u0412', "V");	//В
		CIRILLIC_TRANSLIT_TABLE.put('\u0413', "G");	//Г
		CIRILLIC_TRANSLIT_TABLE.put('\u0414', "D");	//Д
		CIRILLIC_TRANSLIT_TABLE.put('\u0415', "E");	//Е
		CIRILLIC_TRANSLIT_TABLE.put('\u0416', "ZH");//Ж
		CIRILLIC_TRANSLIT_TABLE.put('\u0417', "Z");	//З	
		CIRILLIC_TRANSLIT_TABLE.put('\u0418', "I");	//И
		CIRILLIC_TRANSLIT_TABLE.put('\u0419', "Y");	//Й		
		CIRILLIC_TRANSLIT_TABLE.put('\u041A', "K");	//К
		CIRILLIC_TRANSLIT_TABLE.put('\u041B', "L");	//Л
		CIRILLIC_TRANSLIT_TABLE.put('\u041C', "M");	//М
		CIRILLIC_TRANSLIT_TABLE.put('\u041D', "N");	//Н
		CIRILLIC_TRANSLIT_TABLE.put('\u041E', "O");	//О
		CIRILLIC_TRANSLIT_TABLE.put('\u041F', "P");	//П				
		CIRILLIC_TRANSLIT_TABLE.put('\u0420', "R");	//Р
		CIRILLIC_TRANSLIT_TABLE.put('\u0421', "S");	//С
		CIRILLIC_TRANSLIT_TABLE.put('\u0422', "T");	//Т
		CIRILLIC_TRANSLIT_TABLE.put('\u0423', "U");	//У
		CIRILLIC_TRANSLIT_TABLE.put('\u0424', "F");	//Ф
		CIRILLIC_TRANSLIT_TABLE.put('\u0425', "X");	//Х
		CIRILLIC_TRANSLIT_TABLE.put('\u0426', "CZ");//Ц		
		CIRILLIC_TRANSLIT_TABLE.put('\u0427', "CH");//Ч
		CIRILLIC_TRANSLIT_TABLE.put('\u0428', "SH");//Ш
		CIRILLIC_TRANSLIT_TABLE.put('\u0429', "SHH");//Щ	
		CIRILLIC_TRANSLIT_TABLE.put('\u042A', "``");//Ъ
		CIRILLIC_TRANSLIT_TABLE.put('\u042B', "Y'");//Ы
		CIRILLIC_TRANSLIT_TABLE.put('\u042C', "`");	//Ь
		CIRILLIC_TRANSLIT_TABLE.put('\u042D', "E`");//Э
		CIRILLIC_TRANSLIT_TABLE.put('\u042E', "YU");//Ю
		CIRILLIC_TRANSLIT_TABLE.put('\u042F', "YA");//Я			
		CIRILLIC_TRANSLIT_TABLE.put('\u0430', "a");	//а
		CIRILLIC_TRANSLIT_TABLE.put('\u0431', "b");	//б
		CIRILLIC_TRANSLIT_TABLE.put('\u0432', "v");	//в
		CIRILLIC_TRANSLIT_TABLE.put('\u0433', "g");	//г
		CIRILLIC_TRANSLIT_TABLE.put('\u0434', "d");	//д
		CIRILLIC_TRANSLIT_TABLE.put('\u0435', "e");	//е	
		CIRILLIC_TRANSLIT_TABLE.put('\u0436', "zh");//ж
		CIRILLIC_TRANSLIT_TABLE.put('\u0437', "z");	//з
		CIRILLIC_TRANSLIT_TABLE.put('\u0438', "i");	//и
		CIRILLIC_TRANSLIT_TABLE.put('\u0439', "y");	//й	
		CIRILLIC_TRANSLIT_TABLE.put('\u043A', "k");	//к
		CIRILLIC_TRANSLIT_TABLE.put('\u043B', "l");	//л
		CIRILLIC_TRANSLIT_TABLE.put('\u043C', "m");	//м
		CIRILLIC_TRANSLIT_TABLE.put('\u043D', "n");	//н
		CIRILLIC_TRANSLIT_TABLE.put('\u043E', "o");	//о
		CIRILLIC_TRANSLIT_TABLE.put('\u043F', "p");	//п				
		CIRILLIC_TRANSLIT_TABLE.put('\u0440', "r");	//р
		CIRILLIC_TRANSLIT_TABLE.put('\u0441', "s");	//с
		CIRILLIC_TRANSLIT_TABLE.put('\u0442', "t");	//т
		CIRILLIC_TRANSLIT_TABLE.put('\u0443', "u");	//у
		CIRILLIC_TRANSLIT_TABLE.put('\u0444', "f");	//ф		
		CIRILLIC_TRANSLIT_TABLE.put('\u0445', "x");	//х	
		CIRILLIC_TRANSLIT_TABLE.put('\u0446', "cz");//ц
		CIRILLIC_TRANSLIT_TABLE.put('\u0447', "ch");//ч
		CIRILLIC_TRANSLIT_TABLE.put('\u0448', "sh");//ш
		CIRILLIC_TRANSLIT_TABLE.put('\u0449', "shh");//щ	
		CIRILLIC_TRANSLIT_TABLE.put('\u044A', "``");//ъ
		CIRILLIC_TRANSLIT_TABLE.put('\u044B', "y'");//ы
		CIRILLIC_TRANSLIT_TABLE.put('\u044C', "`");	//ь
		CIRILLIC_TRANSLIT_TABLE.put('\u044D', "e`");//э
		CIRILLIC_TRANSLIT_TABLE.put('\u044E', "YU");//ю
		CIRILLIC_TRANSLIT_TABLE.put('\u044F', "YA");//я				
		CIRILLIC_TRANSLIT_TABLE.put('\u0450', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0451', "yo");
		CIRILLIC_TRANSLIT_TABLE.put('\u0452', "ye");
		CIRILLIC_TRANSLIT_TABLE.put('\u0453', "g`");
		CIRILLIC_TRANSLIT_TABLE.put('\u0454', "ye");
		CIRILLIC_TRANSLIT_TABLE.put('\u0455', "z`");
		CIRILLIC_TRANSLIT_TABLE.put('\u0456', "i'");	
		CIRILLIC_TRANSLIT_TABLE.put('\u0457', "yi");
		CIRILLIC_TRANSLIT_TABLE.put('\u0458', "j");
		CIRILLIC_TRANSLIT_TABLE.put('\u0459', "l`");		
		CIRILLIC_TRANSLIT_TABLE.put('\u045A', "n`");
		CIRILLIC_TRANSLIT_TABLE.put('\u045B', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u045C', "k`");
		CIRILLIC_TRANSLIT_TABLE.put('\u045D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u045E', "u'");
		CIRILLIC_TRANSLIT_TABLE.put('\u045F', "dh");						
		CIRILLIC_TRANSLIT_TABLE.put('\u0460', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0461', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0462', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0463', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0464', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0465', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0466', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0467', "");	
		CIRILLIC_TRANSLIT_TABLE.put('\u0468', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0469', "");		
		CIRILLIC_TRANSLIT_TABLE.put('\u046A', "O'");
		CIRILLIC_TRANSLIT_TABLE.put('\u046B', "o'");
		CIRILLIC_TRANSLIT_TABLE.put('\u046C', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u046D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u046E', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u046F', "");				
		CIRILLIC_TRANSLIT_TABLE.put('\u0470', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0471', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0472', "FH");
		CIRILLIC_TRANSLIT_TABLE.put('\u0473', "fh");
		CIRILLIC_TRANSLIT_TABLE.put('\u0474', "YH");
		CIRILLIC_TRANSLIT_TABLE.put('\u0475', "yh");
		CIRILLIC_TRANSLIT_TABLE.put('\u0476', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0477', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0478', "");	
		CIRILLIC_TRANSLIT_TABLE.put('\u0479', "");		
		CIRILLIC_TRANSLIT_TABLE.put('\u047A', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u047B', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u047C', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u047D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u047E', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u047F', "");								
		CIRILLIC_TRANSLIT_TABLE.put('\u0480', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0481', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0482', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0483', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0484', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0485', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0486', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0487', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0488', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0489', "");			
		CIRILLIC_TRANSLIT_TABLE.put('\u048A', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u048B', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u048C', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u048D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u048E', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u048F', "");						
		CIRILLIC_TRANSLIT_TABLE.put('\u0490', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0491', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0492', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0493', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0494', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0495', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0496', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0497', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0498', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u0499', "");		
		CIRILLIC_TRANSLIT_TABLE.put('\u049A', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u049B', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u049C', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u049D', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u049E', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u049F', "");				
		CIRILLIC_TRANSLIT_TABLE.put('\u04A0', "");	
		CIRILLIC_TRANSLIT_TABLE.put('\u04A1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04A9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AD', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04AF', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B0', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04B9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BD', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04BF', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C0', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04C9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CD', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04CF', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D0', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04D9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DD', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04DF', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E0', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04E9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04EA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04EB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04EC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04ED', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04EE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04EF', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F0', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F1', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F2', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F3', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F4', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F5', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F6', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F7', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F8', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04F9', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FA', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FB', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FC', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FD', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FE', "");
		CIRILLIC_TRANSLIT_TABLE.put('\u04FF', "");			
	}
	
	private static String SPACE_REPLASEMENT = "__";
	
	private TransliterateUtil () {		
	}
	
	public static String transliterate (String s) {
		
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);	
			if (CIRILLIC_TRANSLIT_TABLE.containsKey(c)) {
				buffer.append(CIRILLIC_TRANSLIT_TABLE.get(c));
			}
			else {
				buffer.append(c);
			}						
		}
		return buffer.toString();
	}
	
	public static boolean checkAlphabetIsValid (String str) {				
		
		Pattern pattern = Pattern.compile("[\\w\\u002E\\u005F!#$%,;=@`{}~\\u0026\\u0027\\u0028\\u0029\\u002B\\u005B\\u005D\\u005E\\u002D]+");	
		Matcher m = pattern.matcher(str);	
		return !m.matches();
	
	}
	
	public static boolean hasSpaces (String str) {		
		if (str != null) {
			return str.indexOf('\u0020') >= 0;
		}
		return false;		
	}
	
	public static String replaceSpaces (String str) {
		if (!str.isEmpty() && str.indexOf(' ') > -1) {
			str = str.replaceAll("[\\u0020]", SPACE_REPLASEMENT);
		}
		return str;
	}
	
	public static String replaceApostrofs (String str) {
		if (!str.isEmpty() && str.indexOf('`') > -1) {			
			str = str.replaceAll("`", "");
		}
		if (!str.isEmpty() && str.indexOf('\'') > -1) {			
			str = str.replaceAll("'", "");
		}
		return str;
	}
		
}
