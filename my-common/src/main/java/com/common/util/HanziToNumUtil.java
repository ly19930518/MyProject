package com.common.util;

import java.util.List;
import java.util.Map;

public class HanziToNumUtil {
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
        String strs = "百分之";
        return s.indexOf(strs);
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
     * 处理字符串
     * 找出 百分比字符串、数字字符串
     * @return
     */
    public static  String HandleStr(String src){
        String reStr = "";
        //处理如果出现百分比字符串
        boolean isover = false;
        while(!isover){
            int isbfb = isBFB(src);
            if(isbfb != -1){
                //存在
                String tempHead = src.substring(0,isbfb);
                String tempin = src.substring(isbfb,isbfb+3);//取出百分比
                String tempAfter = src.substring(isbfb+3,src.length());
                //获取百分比数字
                String[] tempAfters = tempAfter.split("");
                String tempsuzis = "";
                long suzi = 0;
                for(int i = 0 ; i < tempAfters.length ;  i++){
                    String af = tempAfters[i];
                    if(isNum(af)){
                        tempsuzis += af;
                    }else{
                        //不存在
                        if(i == 0){//第一位不是 则认为不是%格式忽略  并用 *** 格式替换掉 防止下次在次进入
                            tempin = "***";
                        }else{
                            suzi = chnNum2Digit(tempsuzis);
                            tempin = "%";
                        }
                        break;
                    }
                    //如果是数字结尾
                    if(!"".equals(tempsuzis) && i ==  tempAfters.length - 1){
                        tempin = "%";
                        suzi +=  chnNum2Digit(tempsuzis);
                    }
                }
                if("%".equals(tempin)){
                    src = tempHead+""+ tempAfter.replace(tempsuzis,suzi == 0 ? "": (suzi+"")+""+tempin);
                }else{
                    src = tempHead+""+tempin+""+ tempAfter.replace(tempsuzis,suzi == 0 ? "": (suzi+""));
                }
            }else{
                //拆分字符串 并对相应的字符串进行处理
                String tempStr = "";
                String tempsuzis = "";
                String[] srcs = src.split("");
                for(int i = 0 ; i < srcs.length ;  i++){
                       String temp =  srcs[i];
                       if(isNum(temp)){
                           tempsuzis += temp;
                       }else{
                           //中断
                           if(!"".equals(tempsuzis)){
                               //如果数字是属于各位则忽略 || 如果下一位是跟随着“度” 则转数字
                               //正常十 以上数字进行转换
                               long resnum = chnNum2Digit(tempsuzis);
                               if(resnum > 9){
                                   tempStr += resnum;
                                   tempsuzis = "";//赋值为空  防止重复转换
                               }else if(resnum <= 9 && "度".equals(temp)){ //摄氏度
                                   //如果个位数字 后面跟着度 则生效
                                   tempStr += resnum;
                                   tempsuzis = "";//赋值为空  防止重复转换
                               }else{
                                   tempStr += tempsuzis;//拼接字符
                                   tempsuzis = "";//赋值为空  防止重复转换
                               }
                           }
                           tempStr += temp;
                       }
                }

                // 循环完毕 ，转换未转换的数字（防止数字结尾）
                if(!"".equals(tempsuzis)){
                    //如果数字是属于各位则忽略 || 如果下一位是跟随着“度” 则转数字
                    //正常十 以上数字进行转换
                    long resnum = chnNum2Digit(tempsuzis);
                    if(resnum > 9){
                        tempStr += resnum;
                        tempsuzis = "";//赋值为空  防止重复转换
                    }else{
                        tempStr += tempsuzis;//拼接字符
                        tempsuzis = "";//赋值为空  防止重复转换
                    }
                }
                src = tempStr;

                isover = true;
            }
        }
        //还原替换符
        src  = src.replace("***","百分之");
        return src;
    }

    public static void main(String[] args) {
        System.out.println("".length());
        String str = "七百分之一千二百百百五十八万亿1零三千三百二十一我想打开213一4度";
        System.out.println(HandleStr(str));
    }



}
