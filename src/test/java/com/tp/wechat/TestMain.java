package com.tp.wechat;

import com.tp.wechat.entity.OpenIdInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestMain {
    /*    public static String longestCommonPrefix(String[] strs) {
            if (strs.length == 0) return "";
            String prefix = strs[0];
            for (int i = 1; i < strs.length; i++)
                while (strs[i].indexOf(prefix) != 0) {
                    prefix = prefix.substring(0, prefix.length() - 1);
                    if (prefix.isEmpty()) return "";
                }
            return prefix;
        }*/
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;
    }
    private static void printReverse(char [] str) {
        helper(0, str);
    }

    private static void helper(int index, char [] str) {
        if (str == null || index >= str.length) {
            return;
        }
        helper(index + 1, str);
        System.out.print(str[index]);
    }
   public int f(int n){
        //f(0) = 0,f(1) = 1，等价于 n<=1时，f(n) = n。
        if(n <= 1){
            return 1;
        }
        return f(n-1) + f(n-2);
    }

    public Integer t1(int [] sz,int y){
        Integer end = null;
        for(int i=0;i<sz.length;i++){
            if(sz[i] >= y){
                end = i;
                break;
            }else if(i == (sz.length-1)){
                end = i+1;
            }
        }
        return end;
    }

    public int [] twoSum(int [] nums,int target){
        Integer x = null;
       // boolean flag = true;
        int  [] data = new int[2];
        for(int i=0;i<nums.length;i++){
          //  if(flag) {
                    x = target - nums[i];
           // }
            if(x!=null){
                data[0] = i;
                for(int j = i+1;j<nums.length;j++){
                    if(nums[j] == x){
                        data[1] = j;
                        return data;
                    }
                }
               // flag = true;
            }
        }
        return data[1]==0?null:data;
    }

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

            return l1;
        }

    public static void main(String[] args) {

        TestMain ts = new TestMain();
   /*     int[]sz = {1,3,5,6};
        System.out.println(ts.t1(sz,7));*/
        int [] nums = {-1,-2,-3,-4,-5};
       int x [] = ts.twoSum(nums,-8);
        for(int s:x){
            System.out.println(s+"");
        }
        /* System.out.println(ts.f(100));*/
       /* Solution s = new Solution();
        String str = "{{{{}}}}";
        System.out.println(s.isValid(str));*/
 /*       char [] x = {'1','2','3'};
        TestMain.printReverse(x);*/
/*        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(6);
        list.add(8);
        list.add(11);
        list.add(13);
        list.add(22);

       List<Integer> li = list.stream()
                .sorted()
                .collect(Collectors.toList());
       li.stream()
               .forEach(System.out::println);*/
     /*  for(int i = 1;i<1000000000;i++){
          if((double) (2+i)/i==1.5){
              System.out.println(i);
              break;
          }
       }

       String name = "反对反对";
       if(name.contains("反对")){
           System.out.println("true");
       }
       if(name.indexOf("ij")!=-1){
           System.out.println("存在");
       }else{
           System.out.println("不存在");
       }*/


 /*       int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }*/
/*        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));*/
       /* long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));*/


    }
}