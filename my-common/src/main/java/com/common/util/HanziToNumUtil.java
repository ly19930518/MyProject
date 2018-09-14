package com.common.util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HanziToNumUtil {

    private static String percentStr = "百分之";

    // 没有考虑兆及更大单位情况
    public static long chnNum2Digit(String chnNum) {
        // initialize map
        java.util.Map<String, Integer> unitMap = new java.util.HashMap<String, Integer>();
        unitMap.put("个", 1);// 仅在数据存储时使用
        unitMap.put("十", 10);
        unitMap.put("百", 100);
        unitMap.put("千", 1000);
        unitMap.put("万", 10000);
        unitMap.put("亿", 100000000);

        java.util.Map<String, Integer> numMap = new java.util.HashMap<String, Integer>();
        numMap.put("零", 0);
        numMap.put("一", 1);
        numMap.put("二", 2);
        numMap.put("三", 3);
        numMap.put("四", 4);
        numMap.put("五", 5);
        numMap.put("六", 6);
        numMap.put("七", 7);
        numMap.put("八", 8);
        numMap.put("九", 9);

        // 数据存储结构：
        // 单位 数量
        // "个" num
        // "十" num
        // "百" num
        // "千" num
        // "万" num
        // "亿" num
        java.util.Map<String, Long> dataMap = new java.util.LinkedHashMap<String, Long>();

        // 保存"亿"或"万"之前存在的多位数
        java.util.List<Long> multiNumList = new java.util.ArrayList<Long>();

        long tempNum = 0;
        for (int i = 0; i < chnNum.length(); i++) {
            char bit = chnNum.charAt(i);
            System.out.print(" bit:"+bit);

            // 因为"亿"或"万"存在多位情况，所以需要进行判断
            boolean isExist = false;
            // 存在"亿"或"万"情况(不计算当前索引位)
            if ((chnNum.indexOf('亿', i) != -1 || chnNum.indexOf('万', i) != -1)
                    && chnNum.charAt(i) != '亿' && chnNum.charAt(i) != '万') {
                isExist = true;
            }

            // 数字
            if (numMap.containsKey(bit + "")) {
                if (i != chnNum.length() - 1) {
                    tempNum = tempNum + numMap.get(bit + "");
                }
                // 最末位数字情况
                else {
                    dataMap.put("个", Long.valueOf(numMap.get(bit + "") + ""));
                    tempNum = 0;
                }
            } else if (bit == '亿') {
                // 存在"万亿"情况，取出"万"位值*10000,0000后重新put到map
                if (i - 1 >= 0 && chnNum.charAt(i - 1) == '万') {
                    Long dataValue = dataMap.get("万");
                    if (dataValue != null && dataValue > 0) {
                        dataMap.put("万", dataValue * unitMap.get(bit + ""));
                    }
                    continue;
                }

                // 最后一位数进list等待处理
                if (tempNum != 0) {
                    multiNumList.add(tempNum);
                }
                // 处理"亿"之前的多位数
                long sum = 0;
                for (Long num : multiNumList) {
                    sum += num;
                }
                multiNumList.clear();
                dataMap.put("亿", sum);
                tempNum = 0;
            } else if (bit == '万') {
                // 最后一位数进list等待处理
                if (tempNum != 0) {
                    multiNumList.add(tempNum);
                }
                // 处理"万"之前的多位数
                long sum = 0;
                for (Long num : multiNumList) {
                    sum += num;
                }
                multiNumList.clear();
                dataMap.put("万", sum);
                tempNum = 0;
            } else if (bit == '千' && tempNum > 0) {
                // 存在"亿"或"万"情况，临时变量值*"千"单位值进list等待处理
                if (isExist) {
                    multiNumList.add(tempNum * unitMap.get(bit + ""));
                    tempNum = 0;
                }
                // 不存在"亿"或"万"情况，临时变量值put到map
                else {
                    dataMap.put("千", tempNum);
                    tempNum = 0;
                }
            } else if (bit == '百' && tempNum > 0) {
                // 存在"亿"或"万"情况，临时变量值*"百"单位值进list等待处理
                if (isExist) {
                    multiNumList.add(tempNum * unitMap.get(bit + ""));
                    tempNum = 0;
                }
                // 不存在"亿"或"万"情况，临时变量值put到map
                else {
                    dataMap.put("百", tempNum);
                    tempNum = 0;
                }
            } else if (bit == '十') {
                // 存在"亿"或"万"情况，临时变量值*"十"单位值进list等待处理
                if (isExist) {
                    if (tempNum != 0) {
                        multiNumList.add(tempNum * unitMap.get(bit + ""));
                        tempNum = 0;
                    }
                    // 将"十"转换成"一十"
                    else {
                        tempNum = 1 * unitMap.get(bit + "");
                    }
                }
                // 不存在"亿"或"万"情况，临时变量值put到map
                else {
                    if (tempNum != 0) {
                        dataMap.put("十", tempNum);
                    }
                    // 将"十"转换成"一十"
                    else {
                        dataMap.put("十", 1l);
                    }
                    tempNum = 0;
                }
            }
        }

        // output
        System.out.println();
        long sum = 0;
        java.util.Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            Integer unitValue = unitMap.get(key);
            Long dataValue = dataMap.get(key);
            sum += unitValue * dataValue;
        }

        return sum;
    }

    /**
     * 判断是否属于百分比
     */
    public static int isBFB(String s){
        return s.indexOf(percentStr);
    }

    /**
     * 判断是否属于数字
     * @param s
     * @return
     */
    public static boolean isNum(String s){
        String strs = "个十百千万亿零一二三四五六七八九";
        if(strs.indexOf(s) == -1){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 处理百分比
     * 对百分之(%) 进行处理
     */
    public static String HandlePercent(String str){
        String resstr = "";
        String[] arrs  = str.split("");
        int percentIndex  = str.indexOf(percentStr);
        if(percentIndex != -1 && percentIndex + 2 != arrs.length){
            //存在% 需要进行转换
            String words = "";
            int index = 0;
            for(int i = percentIndex + 3 ; i < arrs.length ; i ++ ){
                String tempword = arrs[i];
                index = i;
                 if(isNum(tempword)){
                     //是汉字
                     words += tempword;
                 }else{
                     //都不是中断
                     if(words.length() > 0 ){
                         //存在需要转换的数字
                         long reswords = chnNum2Digit(words);
                         //拼接前段
                         for(int j = 0 ; j < percentIndex ; j ++){
                             resstr += arrs[j];
                         }
                         resstr = resstr + reswords;
                         resstr = resstr + "%";
                         //拼接后段
                         for(int k = index ;  k < arrs.length ; k ++){
                             resstr += arrs[k];
                         }
                         return HandlePercent(resstr);
                     }else{
                         //百分比不成立
                         String qstr = "";
                         //拼接前段
                         for(int j = 0 ; j < percentIndex ; j ++){
                             qstr += arrs[j];
                         }
                         //拼接后段
                         String hstr = "";
                         for(int k = index ;  k < arrs.length ; k ++){
                             hstr += arrs[k];
                         }
                         return qstr+percentStr+HandlePercent(hstr);
                     }
                 }

                 //如果是最后一位 则进行汉字 转换 成 数字
                 if( i == arrs.length -1 && words.length() > 0 ){
                     //存在需要转换的数字
                     long reswords = chnNum2Digit(words);
                     //拼接前段
                     for(int j = 0 ; j < percentIndex ; j ++){
                         resstr += arrs[j];
                     }
                     resstr = resstr + reswords;
                     resstr = resstr + "%";
                     return HandlePercent(resstr);
                 }
            }
        }
        return str;
    }
    /**
     *  处理百分比
     *  针对百分比后面是数字的
     */
    public static String HandlePercentNumber(String str){
        String resstr = "";
        String[] arrs  = str.split("");
        int percentIndex  = str.indexOf(percentStr);
        if(percentIndex != -1 && percentIndex + 2 != arrs.length){
            //存在% 需要进行转换
            String words = "";
            int index = 0;
            for(int i = percentIndex + 3 ; i < arrs.length ; i ++ ){
                String tempword = arrs[i];
                index = i;
                if(tempword.matches("[0-9]+")){
                    //是数字
                    words += tempword;
                }else{
                    //都不是中断
                    if(words.length() > 0 ){
                        //拼接前段
                        for(int j = 0 ; j < percentIndex ; j ++){
                            resstr += arrs[j];
                        }
                        resstr = resstr + words;
                        resstr = resstr + "%";
                        //拼接后段
                        for(int k = index ;  k < arrs.length ; k ++){
                            resstr += arrs[k];
                        }
                        return HandlePercentNumber(resstr);
                    }else{
                        String qstr = "";
                        //拼接前段
                        for(int j = 0 ; j < percentIndex ; j ++){
                            qstr += arrs[j];
                        }

                        //拼接后段
                        String hstr = "";
                        for(int k = index ;  k < arrs.length ; k ++){
                            hstr += arrs[k];
                        }
                        return qstr+percentStr+HandlePercentNumber(hstr);
                    }
                }

                //如果是最后一位 则进行汉字 转换 成 数字
                if( i == arrs.length -1  && words.length() > 0 ){
                    //拼接前段
                    for(int j = 0 ; j < percentIndex ; j ++){
                        resstr += arrs[j];
                    }
                    resstr = resstr + words;
                    resstr = resstr + "%";
                    return HandlePercentNumber(resstr);
                }
            }
        }
        return str;
    }


    public static String ChatoNumber(String str){
        //拆分字符串 并对相应的字符串进行处理
        String[] arrs = str.split("");
        String words = "";
        int index = 0;
        for(int i =  0 ; i < arrs.length ; i ++ ){
            String tempword = arrs[i];
            index = i;
            if(isNum(tempword)){
                //是汉字
                words += tempword;
            }else{
                //都不是中断
                if(words.length() > 0 ){
                    //存在需要转换的数字
                    long reswords = chnNum2Digit(words);
                    if(reswords > 9){
                        words = reswords+"";
                    }else if(reswords <= 9 && "度".equals(tempword)){ //摄氏度
                        //如果个位数字 后面跟着度 则生效
                        words = reswords+"";
                    }
                    String hstr = "";
                    for(int j = i ; j < arrs.length ; j++){
                        hstr += arrs[j];
                    }
                    return words+""+ChatoNumber(hstr);
                }else{
                    String hstr = "";
                    for(int j = i+1 ; j < arrs.length ; j++){
                        hstr += arrs[j];
                    }
                    return tempword+""+ChatoNumber(hstr);
                }
            }
            if( i == arrs.length -1  && words.length() > 0){
                long reswords = chnNum2Digit(words);
                if(reswords > 9){
                    words = reswords+"";
                }else if(reswords <= 9 && "度".equals(tempword)){ //摄氏度
                    //如果个位数字 后面跟着度 则生效
                    words = reswords+"";
                }
                return words;
            }
        }
        return str;
    }

    /**
     * 对 》= 10 以下的汉字转换成数字（例子：二五 = 25  ）
     * @param str
     * @return
     */
    public static String ConvertAll(String str){
        String[] strs = str.split("");
        String restr = "";
        for(String s : strs){
            if(isNum(s)){
                long number = chnNum2Digit(s);
                if(number < 10 ){
                    if(number == 0 && !s.equals("零")){
                        restr += s;
                    }else{
                        restr += number;
                    }

                }else{
                    restr += s;
                }

            }else{
                restr += s;
            }
        }
        return restr;
    }
    /**
     * 处理字符串
     * 找出 百分比字符串、数字字符串
     * @return
     */
    public static  String HandleStrUrl(String str){
        str = str.replaceAll(" ","");
        str = HandlePercent(str);
        str = HandlePercentNumber(str);
        str = ChatoNumber(str);
        str =  ConvertAll(str);
        try{
            str = URLEncoder.encode(str,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 处理字符串
     * 找出 百分比字符串、数字字符串
     * @return
     */
    public static  String HandleStr(String str){
        str = str.toUpperCase();
        str = str.replaceAll(" ","");
        str = HandlePercent(str);
        str = HandlePercentNumber(str);
        str = ChatoNumber(str);
        str =  ConvertAll(str);
        return str;
    }

    public static void main(String[] args) {
        System.out.println("".length());
        String str = "零七百分之5一千 二百百百 五十八*万,,1.一亿零!三千三百二70     十一我想打开213一4度 二五  fff 5%";//七百分之5一千 二百百百 五十八*万,,1.一亿零!三千三百二70     十一我想打开213一4度
        long stime = new Date().getTime();
        System.out.println(HandleStr(str));
        long etime = new Date().getTime();
        System.out.println((etime - stime) +"耗时");
    }

}
