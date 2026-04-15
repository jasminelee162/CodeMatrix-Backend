package com.csu.ecbackend.util;




import com.csu.ecbackend.dao.localCompeteDao;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.UnsupportedEncodingException;

public class StringUtils {
    private final static int[] areaCode = { 1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static String[] letters = { "a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z" };



    public  static  int getFirstLetterIndex(String keyword) {

        StringBuilder convert = new StringBuilder();
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isBlank(keyword)) {
            return 0;
        }

        char word = keyword.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
        if (pinyinArray != null) {
            word = pinyinArray[0].charAt(0);
        }
        if (word >= 'A' && word <= 'Z') {
            word += 32;
//            System.out.println("这里的大写" + (char) (word - 32) + "被转换成了" + word);
//            System.out.println("这里的大写" + (word - 32) + "被转换成了" + word);
        }

        return (int) word - 96;
    }

    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= areaCode[i]
                            && li_SecPosCode < areaCode[i + 1]) {
                        chinese = letters[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese.toUpperCase();
    }

    /**
     * 字符串编码转换
     * @param str 要转换编码的字符串
     * @param charsetName 原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName,String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

//    public static void main(String[] args) {
//       /* String s = getFirstLetter("不非鱼");
//       char temp=s.charAt(0);
//        System.out.println(temp-'A');
//        System.out.println(s); // => "z"*/
///*
//        String key="啊奇偶";
//        String compete_word="大家十";
//        double compete=2.435;
//        String s = getFirstLetter(key);
//        char temp=s.charAt(0);
//        int i=temp-'A';
//        System.out.println(competeDao.addCompete_word(key,compete_word,compete,i+1));*/
//
//      /*  remoteCompeteDao tool=new remoteCompeteDao();
//        tool.init();
//        String key="不好";
//        String compete_word="护士";
//        double compete=5.435;
//        String s = getFirstLetter(key);
//        char temp=s.charAt(0);
//        int i=temp-'A';
//         tool.insertData(key,compete_word,compete,i+1);*/
//       /* //场景1 没有从Redis中找到，就到从MySQL服务器中去读
//        System.out.println(tool.getNameByID("10"));
//        //场景2，当前ID=10的数据已存在于Redis,所有直接读缓存
//        System.out.println(tool.getNameByID("10"));*/
//
//       /*competeDao competeDao=new competeDao();
//        String key="啊好";
//        String compete_word="护士";
//        double compete=5.435;
//        String s = getFirstLetter(key);
//        char temp=s.charAt(0);
//        int i=temp-'A';
//
//        competeDao.addCompete_word(key,compete_word,compete,i+1);*/
//
//        localCompeteDao competeDao=new localCompeteDao();
//        String key="啊好";
//        String compete_word="护士";
//        double compete=5.435;
//        String s = getFirstLetter(key);
//        char temp=s.charAt(0);
//        int i=temp-'A';
//
//        competeDao.addCompete_word(key,compete_word,compete,i+1);
//
//
//
//    }

}

