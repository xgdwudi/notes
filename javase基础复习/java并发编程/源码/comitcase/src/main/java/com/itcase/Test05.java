package com.itcase;

import java.util.*;

public class Test05 {
    public static void main(String[] args) {
        String adddss = counts("addsdasdasfasdasdasdasdzx");
        System.out.println(adddss);
    }
    public static String counts(String str){
        char[] chars = str.toCharArray();
//        记录查找过的字符
//        Set<Character> add = new HashSet();
        List<Character> list = new ArrayList();
        List<Map.Entry<Character, Integer>> lists = new ArrayList();
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            if(list.contains(chars[i])){
                continue;
            }
            list.add(chars[i]);
            map.put(chars[i],1);
            for (int j = i+1; j < chars.length; j++) {
                if(chars[j]==chars[i]){
                    Integer integer = map.get(chars[i]);
//                    integer++;
                    Integer integer1 = integer+1;
//                    System.out.println(integer1);
                    map.put(chars[i],integer1);
                }
            }
        }
        List<Map.Entry<Character, Integer>> listsss = new ArrayList<>(map.entrySet());
        Collections.sort(listsss, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                if(o1.getValue()>o2.getValue()){
                    return 1;
                }
                if(o1.getValue()==o2.getValue() && o1.getKey()>o2.getKey()){
                    return 1;
                }
                return -1;
            }
        });
        String out = "";
        for (int i = 0; i < listsss.size(); i++) {
            Map.Entry<Character, Integer> characterIntegerEntry = listsss.get(i);
            out=out+characterIntegerEntry.getKey()+characterIntegerEntry.getValue();
        }
        return out;
    }
}
