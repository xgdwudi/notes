package com.wudidemiao.study.huffmancode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @author wudidemiaoa
 * @date 2022/1/20
 * @apiNote
 */
public class HuffmanCode {
    public static void main(String[] args) {
//        String str = "i like like like java do you like a java";
//        huffmanZip(str.getBytes());
//        byte[] bytes = {-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28};
//        byte[] decode = decode(huffmanCodes, bytes);
//        System.out.println(new String(decode));
//        zipFile("F:\\测试文件夹\\风景 - 16.jpg", "F:\\测试文件夹\\1.zip");
        unZipFile("F:\\测试文件夹\\1.zip","F:\\测试文件夹\\dst.jpg");
    }

    public static void unZipFile(String srcFile, String dstFile) {
        try (//        创建文件的输入流
             FileInputStream is = new FileInputStream(srcFile);
             //        创建输出流
             FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(is);) {
            byte[] o1 = (byte[])objectInputStream.readObject();
            Map<Byte,String> o = (Map<Byte,String>)objectInputStream.readObject();
            byte[] decode = decode(o, o1);
            fileOutputStream.write(decode);
            System.out.println("解压成功..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 编写方法对文件进行压缩处理
     *
     * @param srcFile 源地址
     * @param dstFile 目标地址
     */
    public static void zipFile(String srcFile, String dstFile) {

        try ( //        创建文件的输入流
              FileInputStream is = new FileInputStream(srcFile);
              //        创建输出流
              FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
              ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            byte[] bytes = new byte[is.available()];
//            读取文件
            is.read(bytes);
//            获取到对应的赫夫曼编码表
            byte[] bytes1 = huffmanZip(bytes);
//赫夫曼编码后的字节数组写进去
            objectOutputStream.writeObject(bytes1);
            //            这里我们可以以对象流的方式写入赫夫曼编码，是为了以后回复文件时使用
            objectOutputStream.writeObject(huffmanCodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    完成数据的解压
//    1. 先将处理后的字节数组重新先转为赫夫曼编码对应的二进制的字符串，
//    2. 赫夫曼编码对应的二进制的字符串=》 对照赫夫曼编码=》重新转为字符串

    /**
     * 完成对压缩数据的解码
     *
     * @param huffmanCodes 赫夫曼编码表
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 原来的字符串对应的数组
     */
    public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //.先得到 huffmanBytes对应的二进制的字符串，形式101010010111
        StringBuilder stringBuilder = new StringBuilder();
//        将byte数组转成而精致的字符串1
        for (int i = 0; i < huffmanBytes.length; i++) {
//            判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            String s = byteToBitString(!flag, huffmanBytes[i]);
            stringBuilder.append(s);
        }
//        System.out.println(stringBuilder.toString());
//       把字符串按照指定的和驸马编码进行解码
//        把赫夫曼编码表进行调换，因为反向查询  97->100
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> stringByteEntry : huffmanCodes.entrySet()) {
            map.put(stringByteEntry.getValue(), stringByteEntry.getKey());
        }
//        创建一个集合，存放Byte
        List<Byte> bytes = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;  // 小的计数器
            boolean flag = true;
            Byte b = null;
            while (flag) {
//                取出一个字符
                String key = stringBuilder.substring(i, i + count);  // i不动，让count移动，直到匹配到一个字符
                if (map.containsKey(key)) {
                    b = map.get(key);
                    flag = false;
                } else {
                    count++;
                }
            }
            bytes.add(b);
            i += count;
        }

//        当for循环结束后List就存放了所有的字符
//        把List中的数据放到byte数组中并返回
        byte[] bytes1 = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytes1[i] = bytes.get(i);
        }
        return bytes1;
    }

    /**
     * 将一个byte转为二进制的字符串
     *
     * @param b
     * @param flag 如果是true表示需要补高位，反之不补
     * @return 是该b对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b) {
//        使用一个变量保存b
        int temp = b;
//       如果是正数我们还存在补高位
        if (flag)
            temp |= 256;
        String str = Integer.toBinaryString(temp); // 返回temp对应的二进制的补码
//        System.out.println(str);
        if (flag) {
            return str.substring(str.length() - 8);
        }
        return str;
    }

    /**
     * 使用一个方法将前面的方法封装起来，便于调用，
     *
     * @param bytes
     * @return 经过赫夫曼编码压缩后返回的byte数组
     */
    private static byte[] huffmanZip(byte bytes[]) {
        List<Node> nodes = getNodes(bytes);
//         根据nodes创建的赫夫曼树
        Node huffmanTree = getHuffmanTree(nodes);
//        对应的赫夫曼编码（根据赫夫曼树）
        getBinaryCode(huffmanTree, "", stringBuilder);
//        根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        return zip(bytes, huffmanCodes);
    }

    private static byte[] zip(byte[] bytes, Map<Byte, String> huffman) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String str = huffman.get(bytes[i]);
            stringBuilder.append(str);
        }

//        将stringBuilder转位byte数组，长度
        int len = (stringBuilder.length() + 7) / 8;
//         创建1一个存储压缩后的byte数组
        byte[] by = new byte[len];
        int k = 0;
        for (int i = 0; i < stringBuilder.length(); i += 8) {  // 因为是每八位对应一个byte，所以步长应该是+8
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            by[k] = (byte) Integer.parseInt(strByte, 2);
            k++;
        }
        return by;
    }

    static StringBuilder stringBuilder = new StringBuilder();

    static Map<Byte, String> huffmanCodes = new HashMap<>();

    /**
     * 将传入的node节点的所有叶子节点的赫夫曼编码存放到huffmanCodes 集合中
     *
     * @param node          传入节点
     * @param code          路劲 ：左子节点. 0 右子节点  1
     * @param stringBuilder 适用于凭借路劲
     */
    private static void getBinaryCode(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        stringBuilder1.append(code);
        if (node != null) {
            if (node.data == null) {
//                递归处理
//                向左递归
                getBinaryCode(node.left, "0", stringBuilder1);
                getBinaryCode(node.right, "1", stringBuilder1);
            } else {
//                叶子节点
                huffmanCodes.put(node.data, stringBuilder1.toString());
            }
        }
    }

    static List<Node> getNodes(byte[] strBytes) {
        List<Node> nodes = new ArrayList<>();
//      存储每一个byte出现的次数->map
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte strByte : strBytes) {
            if (counts.containsKey(strByte)) {
                Integer integer = counts.get(strByte) + 1;
                counts.put(strByte, integer);
            } else {
                counts.put(strByte, 1);
            }
        }
//        把每个键值对转换成node对象，并加入nodes集合
        for (Map.Entry<Byte, Integer> byteIntegerEntry : counts.entrySet()) {
            nodes.add(new Node(byteIntegerEntry.getKey(), byteIntegerEntry.getValue()));
        }
        return nodes;
    }

    static Node getHuffmanTree(List<Node> nodes) {
        while (nodes.size() != 1) {
            Collections.sort(nodes);
//         去除权值最小的二叉树节点
            Node left = nodes.remove(0);
            Node right = nodes.remove(0);
            Node parents = new Node(null, left.weight + right.weight);
            parents.left = left;
            parents.right = right;
            nodes.add(parents);
        }
        return nodes.remove(0);
    }

    static class Node implements Comparable<Node> {
        Byte data;  // 存放的数据
        int weight;  // 权值  字符出现的次数
        Node left;
        Node right;

        public Node(Byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }

            if (this.right != null) {
                this.right.preOrder();
            }
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }
    }
}


