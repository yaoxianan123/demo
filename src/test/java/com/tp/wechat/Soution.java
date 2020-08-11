package com.tp.wechat;

import java.util.HashMap;
import java.util.Stack;

class Solution {

    // Hash table that takes care of the mappings.
    private HashMap<Character, Character> mappings;

    // Initialize hash map with mappings. This simply makes the code easier to read.
    public Solution() {
        this.mappings = new HashMap<Character, Character>();
        this.mappings.put(')', '(');
        this.mappings.put('}', '{');
        this.mappings.put(']', '[');
    }

    public  boolean isValid(String s) {

        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // If the current character is a closing bracket.
            if (this.mappings.containsKey(c)) {

                // 获取堆栈的顶部元素。如果堆栈为空，则设置一个伪值
                char topElement = stack.empty() ? '#' : stack.pop();

                // 如果此括号的映射与堆栈的顶部元素不匹配，则返回false。
                if (topElement != this.mappings.get(c)) {
                    return false;
                }
            } else {
                //如果是一个开口支架，推到堆栈上。
                stack.push(c);
            }
        }

        // 如果堆栈仍然包含元素，则它是一个无效表达式。
        return stack.isEmpty();
    }
}


