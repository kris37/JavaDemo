package datastructure.graph;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/21 下午12:23
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     Dijkstra 实现
 * <br>
 */
public class Dijkstra {
    private static class  Node<T extends Comparable> implements Comparable<Node>{

        T t;
        Integer wight;
        T parent;
        public Node(T t,T parent,Integer wight){
            this.t = t;
            this.wight = wight;
            this.parent = parent;
        }
        @Override
        public int compareTo(Node o) {
            return this.wight.compareTo(o.wight);
        }
    }
    public static  class Graph<K extends Comparable>{

        private HashMap<K,HashMap<K,Integer>> neiborTable ;
        public Graph(){
            neiborTable  = new HashMap<K,HashMap<K,Integer>>();
        }

        public HashMap<K, HashMap<K, Integer>> getNeiborTable() {
            return neiborTable;
        }
        public void  addDirectEdge(K start,K end,int weight){
            if(end ==null) return;
            if(this.neiborTable.containsKey(start)){
                this.neiborTable.get(start).put(end,weight);
            }else {
                this.neiborTable.put(start,new HashMap<K,Integer>(){{
                    put(end,weight);
                }});
            }
        }
    }
    public static <T extends Comparable> void run(Graph<T> graph,T src){
        HashMap<T, HashMap<T, Integer>> neiborTable = graph.getNeiborTable();
        HashMap<T, T> parent = new HashMap<>();
        HashMap<T, Integer> distance = new HashMap<>();

        PriorityQueue<Node<T>> queue = new PriorityQueue();// 默认小顶堆
        queue.add(new Node<T>(src,null,0));
        while(!queue.isEmpty()) {
            Node<T> poll = queue.poll();
            // 是否在 dis 中
            if(!distance.containsKey(poll.t)){
                distance.put(poll.t,poll.wight);
                parent.put(poll.t,poll.parent);

                // 使用queue 则保存在 distance 中的顶点已经是最短路径了。
                HashMap<T, Integer> map = neiborTable.get(poll.t);
                if(map == null) continue;

                map.forEach(
                        (v,d)->{
                            queue.add(new Node<T>(v,poll.t,poll.wight+d));
                        }
                );

            }
        }

        // 输出 distance 并且输出路径
        distance.forEach(
                (dest,dis) ->{
                    System.out.println(src.toString() + " to "+dest.toString() + " shortest distance is : "+dis +" paths :"+ finderPath(parent,dest));
                }
        );




    }

    private static <T extends Comparable> String finderPath(HashMap<T, T> parent,T dest){
        T t = parent.get(dest);
        ArrayList<String> strings = Lists.<String>newArrayList();
        while (t != null){
            strings.add(t.toString());
            t = parent.get(t);
        }
        return Lists.reverse(strings).toString();

    }

    public static void main(String [] args){
        Graph<String> graph = new Graph<>();
        graph.addDirectEdge("a","b",1);
        graph.addDirectEdge("a","c",2);
        graph.addDirectEdge("b","c",2);
        graph.addDirectEdge("c","b",1);
        graph.addDirectEdge("b","d",4);
        graph.addDirectEdge("c","d",2);
        graph.addDirectEdge("c","e",1);
        graph.addDirectEdge("e","d",3);
        graph.addDirectEdge("e","f",5);
        graph.addDirectEdge("e","g",7);
        graph.addDirectEdge("d","f",2);
        graph.addDirectEdge("f","g",6);
        Dijkstra.run(graph,"a");


    }
}
