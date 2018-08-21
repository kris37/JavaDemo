## 字典序算法
* 给定一个字符串或者数组，对其进行全排列

### 字典序算法过程
 1. 从数组A右边开始找到第一组相邻 A[i] < A[i+1]
 2. 在 i 的右侧找到 A[j] = Min{A[i+1 ... n] > A[i] 因为A[i]之后一直是递减的，所以按顺序找到即可}
 3. 交换 Swap(A[i],A[j])
 4. reserve(A[i+1]...A[n])
 
 ![示意图](https://album-album.oss-cn-beijing.aliyuncs.com/algorithm/Next_Permutation.gif)