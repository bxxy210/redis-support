# redis-support
#Redis组件  

免配置，提供对象序列化支持，支持redis，codis，连接回收，底层封装。  

##实现动机  
使用Spring Redis比较麻烦的几点：  

1.每一种类型的操作单独封装一套接口，使用上不顺手。  

2.无论参数是String类型，还是业务对象，均进行了序列化，对存取性能有影响。  

3.对于Redis事务，multi和exec操作是两个连接，使用时造成ERR EXEC Without Multi异常。
