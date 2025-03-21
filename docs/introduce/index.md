---
# 同时设置导航名称和顺序，order 越小越靠前，默认为 0
group: 开发介绍
title: 开发介绍
order: 3
---

## 摘要

权限控制一直以来都是一个软件领域的一个重要部分，它关乎着每个人的数据安全与隐私安全，随着互联网不断发展，业务复杂度不断上升，权限控制与管理也迎来新的挑战，一方面，数据量的剧增导致鉴权的性能和延迟要求较高，另一方面更细粒度的权限控制需求被提出。
为了能解决这个难题，一些新的权限控制思想被提出，如谷歌在2019年提出的Rebac权限控制模型，并开发出Zanzibar权限中台，ReBAC全称Relation Based Access Control ，是一种基于关系的权限模型，例如：领导A与员工B之间存在"leader"这个关系，那么A就拥有查看B的报表的权限。基于关系的权限控制模型控制粒度更细且配置方法简单，目前已经有部分公司和开源组织基于此模型实现内部权限中台系统。
本篇论文将基于Rebac权限控制思想实现一个权限中台系统，并对已有的模型通过领域驱动架构进行改进，并加入了前端的权限演示中台。使用领域驱动设计，使得权限模块具有拓展性和外部依赖无关性，另外，权限中台作为鉴权模块，要具备分布式和微服务技术，在本系统中，将引入RPC、消息队列等技术，与外部系统对接，让权限控制变得更加简单易用，并且能够满足更多的业务场景。


关键词：Rebac，Zanzibar，领域驱动，鉴权，权限中台

## 绪论
### 引言

权限控制本身已有一些模型，本文将分析权限的应用场景，已有的权限模型和适用场景，并对比现有的权限模型有哪些不足之处，Rebac模型和这些模型相比有哪些优势。权限领域一般分为数据权限和功能权限，数据权限一般粒度较细，如控制到某行中的某列，主要实现数据安全，而功能权限实现相对简单，主要为了实现操作的安全。

### 研究背景
传统的权限方案主要有以下几种：

(1) ACL模型，也叫访问控制列表，用户与权限直接关联，直接维护用户与列表中的资源关系，从而达到权限控制 ，通过用于与资源之间直接建立关系，在理解上非常简单，一个资源对与那些用户有那些操作，但是如果需要给某个用户配置一批资源的权限的时候，那赋权操作非常复杂。

(2) RBAC模型，基于角色的访问控制，用户与角色关联，角色与权限关联，用户成为相应的角色而获得相应的权限，通过在用户与资源之间建立角色的概念，通过用户拥有角色，角色拥有对资源的权限， 来进行权限的配置。

(3) ABAC模型，基于属性的访问控制，通过各种属性来动态判断一个操作是否可以被允许，一个操作是否被允许是基于对象、资源、操作和环境信息共同计算决定的。
这些权限模型已经在软件领域和硬件领域有着广泛的应用，如在Java中Shiro、Spring Security都是采用RBAC和ABAC模型，并且对其有着较好的封装，路由器和交换机通常采用ACL模型，能够有效控制用户对网络的访问。

#### 现有模型的不足之处
随着应用的复杂度提高，迭代速度提高，权限其实并不简单，以上模型在应用中通常采用硬编码的方式，这就造成了权限对代码的入侵，而一般而言，权限是业务无关的，随着业务迭代，权限会变得复杂且难以维护，并且对于公司而言，每一款应用都有着自己权限逻辑，每一套应用都需要维护一套权限逻辑。

得益于微服务技术的广泛应用，权限中台思想也得以推广，权限服务一般作为一个独立服务，提供权限校验的服务，其他应用通过远程调用的方式实现鉴权，权限开始从业务中脱离出来，应用只需要向权限中台同步元数据，并调用鉴权接口即可。

权限中台在解耦权限和业务的同时，也带来了新的问题：

(1)权限很难做到通用性，一般业务稍微复杂的应用都需要定制权限策略，但是仅靠现有的权限模型无法实现，所以需要结合着硬编码的方式实现权限。

(2)权限模块独立后，元数据同步会引发新的问题，应用在接入权限中台时，需要向权限模块同步该应用的用户信息、资源信息等等，需要做一层转换，这个转换的逻辑依然耦合在业务逻辑中。

(3)权限功能的拓展性较弱，现有的权限模型比较单一，鉴权过程相对简单，所以权限模块几乎没有任何拓展性。

#### 新的权限模型

新的权限模型Rebac模型能解决以上问题吗，首先Rebac模型的核心思想是通过关系进行鉴权，实际上是对现有的权限思想进行一层封装，更符合现实世界中的鉴权逻辑，现实中我们对一个资源的使用权通过是某种关系推导过来的，如小明是一个笔记本电脑的拥有者，所以小明拥有对电脑的使用权，小美因为是小明的朋友，所以有对电脑的借用权。Rebac通过对权限思想的封装，使得权限的使用更加简单。

具有通过关系推导权限的能力之后，权限的通用性得到提高，并且关系的建立通常是基于一个图的模型，而图模型就能使对象之间的关系更清晰，通过合理的关系推导，基本能满足大部分的权限应用场景。

传统的权限模型存在元数据同步的问题，是因为对象之间没有任何关联，如对多个对象赋权，在传统权限模型中，每次需要全量同步权限配置，使得元数据的维护和管理变得困难。在Rebac中，并不需要维护复杂的权限元数据，而是维护对象之间的关系即可，对于调用方来说，权限的元数据同步不再像之前那么复杂。

对权限应用了关系的思想之后，权限的拓展性得到了提高，Rebac通过关系模型进行推导一个请求者是否对请求资源有某种权限，这个推导过程是可以自定义的，一般Rebac模型会分租户的概念，每个租户有自己的权限模型，在自己的权限模型中，租户可以自定义一些关系、权限的定义，并且在定义权限时可以定义哪些关系具备该权限，而定义中可以使用简单的运算符如：且、或、继承等等，通过这些运算符可以使鉴权更加灵活，而权限中台在对权限推导优化的同时，也可以对运算符进行拓展，进行更加强大的权限计算。

#### 领域驱动设计


