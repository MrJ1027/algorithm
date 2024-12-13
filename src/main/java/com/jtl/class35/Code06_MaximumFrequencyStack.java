package com.jtl.class35;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Mr.J
 * @time 2024/11/18 10:47
 * @Version 1.0
 * @description 设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
 */
public class Code06_MaximumFrequencyStack {

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(4);
        freqStack.push(5);
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());

    }

    static class FreqStack {
        //key:词频
        //value:该词频的所有数字
        private HashMap<Integer, ArrayList<Integer>> map;
        //词频表
        private HashMap<Integer,Integer> cnts;
        //整个结构的最大词频
        private int maxCnt;

        public FreqStack() {
            this.map = new HashMap<>();
            this.cnts = new HashMap<>();
            this.maxCnt = 0;
        }

        /**
         * 往最大频率栈中加入val
         * @param val
         */
        public void push(int val) {
            //如果val第一次进栈,在词频表中应该不存在
            if(!cnts.containsKey(val)){
                cnts.put(val,1);
            }else{
                //如果val已经存在,词频++
                cnts.put(val,cnts.get(val) + 1);
            }
            //更新最大词频
            maxCnt = Math.max(maxCnt,cnts.get(val));
            int nowCnt = cnts.get(val);
            //如果map中没有当前词频的链表,创建并加入val
            if (!map.containsKey(nowCnt)) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(val);
                map.put(maxCnt,list);

            }else {
                //如果map中已经有最大词频的链表,直接把val加入
                ArrayList<Integer> list = map.get(nowCnt);
                list.add(val);
            }
        }

        /**
         * 从最大频率栈中频率最大的元素
         * @return
         */
        public int pop() {
            //拿到频率最大的链表,并移除最后一个
            ArrayList<Integer> list = map.get(maxCnt);
            int removeNum = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            //如果链表中没有元素了,从map中删除;如果删除了一行链表,此时maxCnt要跟着变化
            if(list.size() == 0){
                map.remove(maxCnt);
                maxCnt--;
            }
            //然后更新词频表,如果该元素的词频被减为0,则直接从cnts中移除
            int newCnt = cnts.get(removeNum) - 1;
            if(newCnt == 0){
                cnts.remove(removeNum);
            }else{
                //否则把词频-1,然后更新最大词频
                cnts.put(removeNum,newCnt);
            }
            return removeNum;
        }
    }

}
