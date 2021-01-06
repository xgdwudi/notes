package recursion;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName MiGong
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/5 14:55
 **/
public class MiGong {
    public static void main(String[] args) {
        int[][] map = new int[7][8];
        for (int i = 0; i < 7; i++) {
            map[i][0]=1;
            map[i][7]=1;
        }
        for (int i = 0; i < 8; i++) {
            map[0][i]=1;
            map[6][i]=1;
//            map[3][i]=1;
        }
//        map[3][1]=1;
//        map[3][2]=1;
//        map[3][3]=1;
        System.out.println("map二位数组");
        for (int[] ints : map) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            System.out.println();
        }
        Boolean aBoolean = setWay(map, 1, 1);
        System.out.println(aBoolean);
        if(aBoolean){
            System.out.println("map二位数组是：");
            for (int[] ints : map) {
                for (int anInt : ints) {
                    System.out.print(anInt);
                }
                System.out.println();
            }
        }
    }

//    递归方法
//    传参 map[][] i j 位置
//    0代表未走过  1 代表墙 2 代表通行 3代表走过但是不通
//    四种通行方式 左  右 上 下
    public static Boolean setWay(int[][] map,int i,int j){
        if(map[5][6]==2){
            return true;
        }else{
            //        每次进来判断其选择得节点是否之前走过
            if(map[i][j]==0){
                map[i][j]=2;
                if(setWay(map,i+1,j)){
                    return true;
                }else if(setWay(map,i,j+1)){
                    return true;
                }else if(setWay(map,i-1,j)){
                    return true;
                }else if(setWay(map,i,j-1)){
                    return true;
                }else{
                    map[i][j]=3;
                    return false;
                }
            }else {
                return false;
            }
        }

    }
}
