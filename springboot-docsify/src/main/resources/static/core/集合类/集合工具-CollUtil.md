## 介绍

这个工具主要增加了对数组、集合类的操作。

### `join` 方法
将集合转换为字符串，这个方法还是挺常用，是`StrUtil.split`的反方法。这个方法的参数支持各种类型对象的集合，最后连接每个对象时候调用其`toString()`方法。栗子如下：
```Java
String[] col= new String[]{"a","b","c","d","e"};
List<String> colList = CollUtil.newArrayList(col);

String str = CollUtil.join(colList, "#"); //str -> a#b#c#d#e
```

### `sortPageAll`、`sortPageAll2`方法
这个方法其实是一个真正的组合方法，功能是：将给定的多个集合放到一个列表（`List`）中，根据给定的`Comparator`对象排序，然后分页取数据。这个方法非常类似于数据库多表查询后排序分页，这个方法存在的意义也是在此。`sortPageAll2`功能和`sortPageAll`的使用方式和结果是 一样的，区别是`sortPageAll2`使用了`BoundedPriorityQueue`这个类来存储组合后的列表，不知道哪种性能更好一些，所以就都保留了。使用此方法，栗子如下：
```Java
//Integer比较器
Comparator<Integer> comparator = new Comparator<Integer>(){
	@Override
	public int compare(Integer o1, Integer o2) {
		return o1.compareTo(o2);
	}
};

//新建三个列表，CollUtil.newArrayList方法表示新建ArrayList并填充元素
List<Integer> list1 = CollUtil.newArrayList(1, 2, 3);
List<Integer> list2 = CollUtil.newArrayList(4, 5, 6);
List<Integer> list3 = CollUtil.newArrayList(7, 8, 9);

//参数表示把list1,list2,list3合并并按照从小到大排序后，取0~2个（包括第0个，不包括第2个），结果是[1,2]
@SuppressWarnings("unchecked")
List<Integer> result = CollUtil.sortPageAll(0, 2, comparator, list1, list2, list3);
System.out.println(result);     //输出 [1,2]
```

### `sortEntrySetToList`方法
这个方法主要是对`Entry<Long, Long>`按照Value的值做排序，使用局限性较大，我已经忘记哪里用到过了……

### `popPart`方法
这个方法传入一个栈对象，然后弹出指定数目的元素对象，弹出是指`pop()`方法，会从原栈中删掉。

### `append`方法
在给定数组里末尾加一个元素，其实List.add()也是这么实现的，这个方法存在的意义是只有少量的添加元素时使用，因为内部使用了`System.arraycopy`,每调用一次就要拷贝数组一次。这个方法也是为了在某些只能使用数组的情况下使用，省去了先要转成`List`，添加元素，再转成Array。

### 7. `resize`方法
重新调整数据的大小，如果调整后的大小比原来小，截断，如果比原来大，则多出的位置空着。（貌似List在扩充的时候会用到类似的方法）

### `addAll`方法
将多个数据合并成一个数组

### `sub`方法
对集合切片，其他类型的集合会转换成`List`，封装`List.subList`方法，自动修正越界等问题，完全避免`IndexOutOfBoundsException`异常。

### `isEmpty`、`isNotEmpty`方法
判断集合是否为空（包括null和没有元素的集合）。

### `zip`方法
此方法也是来源于[Python](https://www.python.org/)的一个语法糖，给定两个集合，然后两个集合中的元素一一对应，成为一个Map。此方法还有一个重载方法，可以传字符，然后给定分分隔符，字符串会被split成列表。栗子：

```Java
Collection<String> keys = CollUtil.newArrayList("a", "b", "c", "d");
Collection<Integer> values = CollUtil.newArrayList(1, 2, 3, 4);

// {a=1,b=2,c=3,d=4}
Map<String, Integer> map = CollUtil.zip(keys, values);
```