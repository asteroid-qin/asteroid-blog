<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link rel="icon" href="favicon.ico">
    <base href="<%=url%>">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <!-- 引入bootstrap头像库 -->
    <link rel="stylesheet" href="bootstrap/bootstrap-icons.css">
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.bundle.js"></script>
    <!-- top导航栏css -->
    <link rel="stylesheet" href="css/top.css">
</head>
<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-2">
        <div class="container-fluid">
          <a class="navbar-brand" href="">小行星博客</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item" data-bs-toggle="modal" data-bs-target="#login" id="login_but" style="cursor: pointer">登录/注册</li>
                <a href="home" class="ms-2" style="display: none" id="user_name"></a>
                <a href="edit" class="ms-2" style="display: none" target="_blank" id="write_blog">写博客</a>
                <a href="exit" class="ms-2" style="display: none" id="user_exit">退出</a>
            </ul>
            <form class="d-flex">
              <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
          </div>
        </div>
    </nav>

    <!-- 分类筛选 -->
    <div class="container">
        <div class="row">
            <div class="col-2 list-group" id="category">
            </div>
            <div class="col-8">
                <div class="px-3 py-2 border-bottom">
                    <div>
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1 text-truncate">这是标题这是标题这是标题这是标题这是标题这是标题这是标题</h5>
                            <small>2012/8/10</small>
                        </div>
                        <p class="mb-1 text-truncate">你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊</p>

                    </div>
                    <small class="me-4">用户id用户id用户id用户id</small>
                    <small class="me-2"><i class="bi bi-hand-thumbs-up pe-2"></i>0</small>
                    <small class="me-2"><i class="bi bi-chat-square-dots pe-2"></i>12</small>
                    <small><i class="bi bi-eye pe-2"></i>13</small>
                </div>
            </div>       
        </div>
    </div>
    
    <!-- 登录/注册模块 -->
    <div class="modal fade" id="login" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">登录/注册</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="uname" class="form-label">请输入用户名：</label>
                    <input type="text" class="form-control" id="uname"  placeholder="用户名应在3-16字符以内">
                    <p class="invalid-feedback" style="display: none;">用户名不合法！</p>
                </div>
                <div class="mb-3">
                    <label for="upwd" class="form-label">请输入密码：</label>
                    <input type="password" class="form-control" id="upwd" placeholder="密码应在6-18字符以内">
                    <p class="invalid-feedback" style="display: none;"></p>
                </div>
                <small class="mt-2">说明：用户名存在则验证密码，不存在则自动创建</small>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="usubmit">确认</button>
            </div>
        </div>
        </div>
    </div>

    <div id="roll" class="position-absolute top-50 start-50" style="display: none;">
        <div class="spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
    </div>

    <!-- 导入导航栏文件 -->
    <script type="text/javascript" src="js/top.js"></script>
    <script type="text/javascript" src="js/index.js"></script>
</body>
</html>