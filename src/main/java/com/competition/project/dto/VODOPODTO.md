# VO、PO、DO、DTO
```
VO：View Object，视图层，其作用是将指定页面的展示数据封装起来。

DTO：Data Transfer Object，数据传输对象

DO：Domain Object，领域对象

PO：Persistent Object，持久化对象
```

用户发出请求（填写表单），表单的数据被展示层匹配为VO

展示层把VO转换为服务层对应方法所要求的DTO，提交给服务层

服务层先将DTO的数据构造（或重建）一个DO，调用DO的业务方法完成具体业务

服务层再将DO转换为持久层对应的PO，调用持久层的持久化方法，把PO传递持久化方法，完成持久化操作