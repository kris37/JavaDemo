package datastructure.graph;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/20 下午2:17
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     图对象
 *     DAG
 *     BFS
 *     DFS
 * <br>
 */
public class Graph<K extends Comparable> {
    // 邻接表
    private HashMap<K,HashSet<K>> neiborTable ;
    // 存贮每个顶点的入度
    private HashMap<K,Integer> indegree ;
    public Graph(){
        this.neiborTable  = new HashMap<K,HashSet<K>>();
        this.indegree = new HashMap<K,Integer>();
    }

    public void addDirectEdge(@NotNull K begin,@NotNull K end){
        if(neiborTable.get(begin) == null){
            neiborTable.put(begin,new HashSet<K>(){{
                add(end);
            }});

        }else {
            neiborTable.get(begin).add(end);
        }

        // add  end indegree
        if(indegree.containsKey(end)){
            Integer newValue = indegree.get(end) + 1;
            indegree.put(end,newValue);
        }else {
            indegree.put(end,1);
        }
        // create start indegree
        if(!indegree.containsKey(begin)){
            indegree.put(begin,0);
        }
    }


    public HashMap<K, HashSet<K>> getNeiborTable() {
        return neiborTable;
    }

    public HashMap<K, Integer> getIndegree() {
        return indegree;
    }

    /**
     *  本质是检测 Graph 是否有回环
     * @return
     */
    public  boolean isDAGByDFS(){
        HashMap<K,Integer> visitRecord = new HashMap<>();
        LinkedList<K> queue = new LinkedList<>();
        for (Map.Entry<K, HashSet<K>> entry : this.neiborTable.entrySet()) {

                    if(!visitRecord.containsKey(entry.getKey())){
                        visitRecord.put(entry.getKey(),0);
                        boolean isDag = isDAGByDFS_Visit(entry.getKey(), entry.getValue(), visitRecord, queue);
                        if(!isDag){
                            return false;
                        }
                    }
        }

        System.out.println(queue.toString());
        return true;
    }
    private boolean isDAGByDFS_Visit(K k,HashSet<K> adj,HashMap<K,Integer> visitRecord,LinkedList<K> queue){
        if(adj == null || adj.size() == 0){
            visitRecord.put(k,1);
            queue.push(k);
            return true;
        }
        for (K v : adj) {
            if(!visitRecord.containsKey(v)) {
                visitRecord.put(v,0);
                boolean isDAG = isDAGByDFS_Visit(v, this.neiborTable.get(v), visitRecord, queue);
                if(!isDAG){
                    return false;
                }

            }else if(visitRecord.get(v) == 0){
                // 说明k 访问到了 自己的父链顶点
                    // 存在回环
                    return false;
            }
        }
        visitRecord.put(k,1);

        // 全部遍历完 push
        queue.push(k);
        return true;

    }

    /**
     * 1 从 DAG 图中选择一个 没有前驱（即入度为0）的顶点并输出。
       2 从图中删除该顶点和所有以它为起点的有向边。
     重复 1 和 2 直到当前的 DAG 图为空或当前图中不存在无前驱的顶点为止。后一种情况说明有向图中必然存在环。
     * @return
     */
    public  boolean isDAGByDegree(){
        HashMap<K, Integer> clone = new HashMap<>();
        this.indegree.forEach(clone::put);
        LinkedList<K> queue = new LinkedList<>();
        Iterator<Map.Entry<K, Integer>> iterator = clone.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<K, Integer> next = iterator.next();
            if(next.getValue().intValue() == 0){
                queue.push(next.getKey());
                iterator.remove();
            }
        }
        // queue pop
        int count = 0;
        while (!queue.isEmpty()){
            count ++;
            K pop = queue.pop();
            System.out.print(pop.toString() + "->");
            HashSet<K> ks = this.neiborTable.get(pop);
            if(ks ==null || ks.size() ==0){
                continue;
            }
            //update which (pop -> vertices) vertices indegree - 1;
            for (K k : ks) {
                //
                if(clone.containsKey(k)){
                    // 入度 将为 0 直接进入 queue
                    if(clone.get(k).intValue() == 1){
                        queue.push(k);
                        clone.remove(k);
                    }else{
                        // 入度 -1
                        clone.put(k,clone.get(k) - 1);
                    }
                }
            }

        }
        if(count < this.neiborTable.size()){
            return false;
        }
        return true;

    }

    public  void printDFS(K start){

        if(start == null) {
            neiborTable.forEach(
                    (v, set) -> {
                        HashMap<K, Integer> visitRecord = new HashMap<>();
                        if (!visitRecord.containsKey(v)) {
                            visitRecord.put(v, 0);
                            System.out.print(v.toString() + " -> ");
                            DFS_visit(this.neiborTable.get(v), visitRecord);
                            //parent.put(v,null);
                        }
                        System.out.println("");
                    }

            );
        }else {
            HashSet<K> ks = neiborTable.get(start);
            HashMap<K, Integer> visitRecord = new HashMap<>();
            if (!visitRecord.containsKey(start)) {
                visitRecord.put(start, 0);
                System.out.print(start.toString() + " -> ");
                DFS_visit( ks, visitRecord);
                //parent.put(v,null);
            }


        }

    }
    private void DFS_visit(HashSet<K> adj, HashMap<K, Integer> visitRecord){
        if(adj == null|| adj.size() ==0){
            return;
        }
        adj.forEach(
                v ->{
                    if(!visitRecord.containsKey(v)){
                        System.out.print(v.toString() + " -> ");
                        visitRecord.put(v,0);
                        DFS_visit(this.neiborTable.get(v),visitRecord);
                    }
                }
        );
    }

    public static void BFS(Graph graph){

    }

    public static void main (String [] args){
        Graph<String> graph = new Graph<>();
        graph.addDirectEdge("o","a");
        graph.addDirectEdge("b","a");
        graph.addDirectEdge("a","c");
        graph.addDirectEdge("a","d");
        graph.addDirectEdge("d","e");
        graph.addDirectEdge("e","h");
        graph.addDirectEdge("c","g");
        graph.addDirectEdge("g","f");
        //graph.addDirectEdge("f","c");

        //System.out.println(graph.isDAGByDegree());
        //graph.printDFS("a");
        System.out.println(graph.isDAGByDFS());


    }


}
