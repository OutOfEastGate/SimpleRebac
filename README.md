# SimpleRebac设计文档

## ReBAC概念
ReBAC全称Relation Based Access Control ，是一种基于关系的权限模型，例如：领导A与员工B之间存在"leader"这个关系，那么A就拥有查看B的报表的权限。
基于关系的权限控制模型控制粒度更细且配置方法简单，目前已经有很多家公司基于此模型实现公司内部权限中台系统。

### ReBAC相关实现
1、Google Zanzibar [论文](https://research.google/pubs/pub48190/) [官网](https://zanzibar.academy/)

2、基于 Zanzibar 相关开源实现

[keto](https://github.com/ory/keto)

[Openfga](https://github.com/openfga/openfga)

[permify](https://github.com/Permify/permify)

[spicedb](https://github.com/authzed/spicedb)

> 目前ReBAC相关实现都是几乎都是基于谷歌的论文，且都是通过golang来实现，一定程度上和google使用golang有关

## ReBAC理解
### 权限模型背景
当前比较流行的权限模型有ACL、ABAC、RBAC，但是这些权限模型无法做到对多个系统的通用性，其中ACL即访问控制列表，实现方法是在用户和资源之间建立直接关联关系，如Linux系统就是采用这种方式；ABAC模型一般配合RBAC使用，在ABAC模型中权限粒度被细分为具体的操作，如增删改查，也引入了作用域的概念，实现多租户模型；RBAC用于建立起用户的角色体系，通过给角色赋权从而用户拥有了对资源的操作权限。
权限模型新挑战


以上几种权限模型在当下已经有了比较广泛的应用，并且在权限控制方面有很大优势，为应用的权限控制提供了解决方案。

但是，时至今日，应用的复杂度越来越大，对权限控制的粒度也越来越细，对性能、可靠性、敏捷性提出了新的要求，例如以下需求案例：

- 员工的休假申请能被自己的领导所查看和审批 
- 只有员工本人可以修改休假申请 
- 系统管理员可以查看所有休假申请 
- 部门负责人可以看到自己部门的员工休假申请 
- 其他部门的负责人无法查看员工休假申请

上述需求其实采用硬编码的方式实现并不难，但是要想通过权限模型实现，并且达到通用、高效、易懂是很难的。
并且，随着一个系统的完善，系统下的应用可能越来越多，不同的应用意味着需要一套不同的权限体系，应用数量的增多引发了两个重要的问题：

- 每次开发一个新应用都要实现权限模块编码，能否有一个权限中台，能够通过一套配置规则实现应用的权限模型 
- 应用与应用之间如果需要跨平台鉴权，例如应用A与应用B建立合作关系，应用A的VIP享有应用B的八折优惠，如何实现跨平台鉴权。
上述需求需要我们有一个权限中台来统一管控权限，权限中台要能够实现以下几个需求： 
- 不同的应用只需不同的配置就可以实现其权限规则 
- 具有高响应比，尽量减少鉴权耗时 
- 具有高可用性，权限系统崩溃意味着其他应用也无法访问 
- 权限粒度要尽可能细以满足不同应用的需求

### 新的权限模型
谷歌在2019年发表了Zanzibar全球统一权限系统相关论文，目前Calendar、Cloud、 Drive、Maps、Photos、YouTube都是基于此系统实现鉴权。

Zanzibar的核心逻辑就是Rebac鉴权模型，通过建立用户与对象关联关系实现鉴权。
## ReBAC关键定义
### 三元组
关系元组由：`命名空间(Namespace)/对象类型(Object Type)`，`对象(Object)`，`关系(Relation)`和`主题(Subject)/用户(User)`组成。
关系被描述为关系元组，使用BNF语法描述，其形式如下：

```dsl
<tuple> ::= <object>'#'<relation>'@'<user>

<object> ::= <namespace>':'<object_id>
<user> ::= <user_id> | <userset>

<userset> ::= <object>'#'<relation>
```

`app:app_1#resource_member@resource:resource_1`表示`resource`中的`resource_1`是`app`中`app_1`的`resource_member`，用中文解释就是有一个应用叫做`app_1`，有一个资源叫做`resource_1`，`resource_1`这个资源是`app_1`这个应用的`resource_member`。
### 运算
在ReBAC中，运算用于推导关系，分为并、交、差，分别用`+`、`&`、`-`，推导用`->`表示
### 关系与权限
关系定义
`relation admin: user`表示`user`存在`admin`这样一条关系
`relation resource_member: resource`表示`resource`存在`resource_member`这样一条关系
`relation function_role_memeber: function_role`表示`function_role`存在`function_role_member`这样一条关系
权限定义
`permission combined = reader + writer`表示`combined`这个权限需要有`reader`或`writer`这两个关系

```dsl
definition user {}

definition folder {
relation reader: user
permission read = reader
}

definition document {
relation parent_folder: folder

    /**
     * read defines whether a user can read the document
     */
    permission read = parent_folder->read
}
```

>上述定义的理解
> 1. 有三个实体类型：user、folder、document
> 2. document与folder之间存在关系parent_folder
> 3. folder与user之间存在关系reader
> 4. document存在权限read，read权限需要从parent_folder关系中推导出read权限
> 5. folder存在权限read，read权限需要user具有reader关系
   总结：document的read权限需要user具有对其parent_folder的read权限

```dsl
definition user {}

definition folder {
relation reader: user
permission read = reader
}

definition document {
relation parent_folder: folder
relation reader: user

    /**
     * read defines whether a user can read the document
     */
    permission read = reader + parent_folder->read
}
```
> 上述定义与前面相比，read权限增加了运算符`+`，表示具有对document的reader权限的user与parent_folder推导出read权限的user取并集
总结：具有对document的reader关系的user与具有对document的parent_folder的read权限的user取并集

## 实现思路
对于ReBAC模型目前已经有了几个开源的实现，都是借助了Zanzibar的核心思想，但是这些开源实现目前都是基于Golang语言，原因是一方面Golang对并发的支持比较优秀，另一方面Golang是编译型语言，在执行效率上也比较好。

但是当前大部分复杂的应用还是使用Java语言实现的，如果通过Golang实现权限中台系统，就意味着Java的应用要想接入权限中台，必须通过RPC远程调用，一种是HTTP调用，效率比较低，另一种就是grpc方式。

我的想法是用Java实现一个ReBAC模型，Java的应用在当下已经非常广泛，并且拥有很多优秀的框架和技术栈，如果用Java实现权限系统，即使其并发度和执行效率上不如Golang，但是Java可以凭借其他框架来实现高拓展性，在应用上也更加灵活，特别是在Java应用之间，远程调用已经变得很简单。

## 实现目标

1. 参考Zanzibar，使用Java实现权限中台系统
2. 权限中台系统应该基于分布式思想，能够接入Nacos服务配置与注册中心，实现远程调用
3. 权限中台系统应该有较高的并发度和较快的响应时间