<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户页</title>
    <base href="<%=url%>">
    <link href="favicon.ico" rel="shortcut icon">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <!-- 引入bootstrap头像库 -->
    <link rel="stylesheet" href="bootstrap/bootstrap-icons.css">
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.bundle.js"></script>
    <!-- top导航栏css -->
    <link rel="stylesheet" href="css/top.css">
    <style>
        #content a{
            color: black;
            text-decoration: none;
        }
        #content a:hover{
            text-decoration: underline;
        }
    </style>
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
            </div>
        </div>
    </nav>
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

    <!-- 个人页 -->
    <div class="container">
      <!-- 头部简单介绍 -->
      <h3 class="text-center my-3" id="blog_uname"></h3>
      <div class="row justify-content-center">
        <div class="col col-md-2 text-center">
          <small id="blog_size"></small>
          <p>文章量</p>
        </div>
      </div>
    </div>
    </div>

    <!-- 中间留白 -->
    <div class="bg-light" style="height: 20px;"></div>

    <!-- 主体内容 -->
    <div class="container">
        <div class="row text-center mt-2">
            <div class="col py-2 position-relative border-bottom border-dark border-2 fw-bold">文章<i></i></div>
        </div>
        <!-- 博客分类 -->
        <!-- 用户分类 -->
        <!-- 所有用户 -->
        <div id="content" class="row"></div>
    </div>

    <!-- 导入导航栏文件 -->
    <script type="text/javascript" src="js/top.js"></script>
    <!-- 自定义js -->
    <script type="text/javascript" src="js/home.js"></script>
</body>
</html>