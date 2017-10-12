package com.atguigu.datingsite.test;

public class Test {

    public static void main(String[] args) {
        System.out.println(args);

        /*new SolrTest() {
            {
            SolrTest.main(null);
        }
        }.equals("abcde");*/

        if (args == null || new Test() {{
            Test.main(null);
        }}.equals("abcde")) {
            System.out.println("a");
        } else {
            System.out.println("b");
        }
    }

    @org.junit.Test
    public void test01(){
        byte b1 = 3, b2 = 4, b;
        b = (byte) (b1 + b2);
        System.out.println(b);
    }
    @org.junit.Test
    public void test02(){
        byte b = -127;
        System.out.println(b);
    }

}
