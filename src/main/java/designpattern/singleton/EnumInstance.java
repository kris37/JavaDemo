package designpattern.singleton;

/**
 *  enum 单例模式
 */
public enum EnumInstance {
    INSTANCE;
    EnumInstance(){
    }
    public void todo(int i){
        System.out.println(i);
    }
}
