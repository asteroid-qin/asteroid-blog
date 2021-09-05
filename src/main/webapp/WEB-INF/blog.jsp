<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>博客</title>
    <base href="<%=url%>">
    <link href="favicon.ico" rel="shortcut icon">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <!-- 引入bootstrap头像库 -->
    <link rel="stylesheet" href="bootstrap/bootstrap-icons.css">
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="showdown/showdown.js"></script>
    <link rel="stylesheet"  href="css/top.css">
    <link rel="stylesheet"  href="css/blog.css">
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

    <!-- 内容 -->
    <div class="container-fluid">
        <div class="row">
            <div id="box" class="col-md-8 col-sm-10 mx-auto">
                <!-- 先显示标题 -->
                <h1 id="title"></h1>
                <!-- 再显示作者和时间 -->
                <p>作者:<a href="#" id="author" class="small link-primary ms-2"></a></p>
                <p>时间:<small id="time" class="ms-2"></small></p>
                <p>分类:<small id="category" class="ms-2"></small></p>

                <!-- 分割线 -->
                <div class="border-top mb-1" style="width: 70%;"></div>

                <!-- 博客内容 -->
                <div id="content"></div>

                <!-- 继续分割线，显示点赞、评论、收藏等信息 -->
                <div id="inf" class="d-flex justify-content-end p-3 bg-light border-bottom border-4 border-dark">
                    <small class="me-2"><i class="bi bi-hand-thumbs-up pe-2"></i>0</small>
                    <small class="me-2"><i class="bi bi-chat-square-dots pe-2"></i>12</small>
                    <small><i class="bi bi-collection pe-2"></i>13</small>
                </div>

                <!-- 评论 -->
                <div id="comment_top" class="container-fluid my-3">
                    <div class="row">
                        <div class="col">
                            <textarea id="comment_content_top" class="form-control" placeholder="点击评论" style="resize: none; height: 20px;"  ></textarea>
                        </div>
                        <div class="col-auto d-flex justify-content-end" id="comment_bot">
                            <small class="d-flex align-items-center mx-2 d-none">还能输入<span>255</span>个字符</small>
                            <button class="btn btn-secondary">发表评论</button>
                        </div>
                    </div>
                </div>
                <div id="comment" class="mt-2 px-2 small"></div>
                <div id="comment_card" class="border-start ps-3 py-1" style="display: none;">
                    <textarea class="form-control" id="comment_content" placeholder="点击评论" style="resize: none;"></textarea>
                    <div class="d-flex justify-content-end py-2">
                        <small class="d-flex align-items-center mx-2">还能输入<span>255</span>个字符</small>
                        <button class="btn btn-danger">发表评论</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 导入导航栏文件 -->
    <script type="text/javascript" src="js/top.js"></script>
    <!-- 完成所有功能：包括查blog和评论 -->
    <script type="text/javascript" src="js/blog.js"></script>
</body>
</html>