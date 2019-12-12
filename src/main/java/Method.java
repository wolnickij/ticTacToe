public class Method {
    public static int getNumberOfMaxParam(int a, int b, int c) {
        if(a >= b && a >= c){
            return a;
        } else if (b >= c){
            return b;
        } else {
            return c;
        }
    }
}
