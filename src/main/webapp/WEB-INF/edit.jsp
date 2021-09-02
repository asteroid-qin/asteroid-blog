<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编写</title>
    <base href="<%=url%>">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" href="bootstrap/bootstrap.css">
    <!-- 引入bootstrap头像库 -->
    <link rel="stylesheet" href="bootstrap/bootstrap-icons.css">
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="bootstrap/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="showdown/showdown.js"></script>
    <style>
        .toast-container{
            position: fixed;
            right: 0;
            top: 20%;
        }
    </style>
</head>
<body>
    <!-- 得输入标题 -->
    <input class="form-control mt-1 shadow-sm" id="title" type="text" placeholder="请输入博客的标题">

    <!-- 两个按钮，从左往右依次是发布、预览 -->

    <div class="container-fluid my-3">
        <div class="row justify-content-between">
            <div class="col-md3 col-sm-5 row">
                <label  class="col-sm-4 col-form-label">分类：</label>
                <div class="col-sm-8">
                    <select class="form-select" id="category"></select>
                </div>
            </div>
            <div class="col d-flex justify-content-end">
                <button type="button" class="btn btn-secondary" id="m_render">预览</button>
                <button type="button" class="btn btn-light ms-2" id="send">发布</button>
            </div>
        </div>
    </div>


    <div class="toast" role="alert" aria-live="assertive" data-bs-autohide="true" aria-atomic="true">
        <div class="toast-header">
            <img src="favicon.ico" class="rounded me-2" width="20" height="20" alt="...">
            <strong class="me-auto">小行星博客</strong>
            <small class="text-muted"></small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body"></div>
    </div>

    <!-- 输入内容 -->
    <textarea id="content" class="form-control" placeholder="编写内容处（使用makedown语法）......"></textarea>
    <div id="out" class="mx-auto" style="display: none;"></div>
    <script type="text/javascript" src="js/edit.js"></script>
</body>
</html>