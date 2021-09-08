/**
 * 导航栏的专属js文件，用于校验数据合法性
 */

/**
 * 传入一个值判断是否是数字
 * @param val
 * @returns {boolean}
 */
function isRealNum(val){
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除，

    if(val === "" || val ==null){
        return false;
    }
    if(!isNaN(val)){
        //对于空数组和只有一个数值成员的数组或全是数字组成的字符串，isNaN返回false，例如：'123'、[]、[2]、['123'],isNaN返回false,
        //所以如果不需要val包含这些特殊情况，则这个判断改写为if(!isNaN(val) && typeof val === 'number' )
        return true;
    }

    else{
        return false;
    }
}

/**
 * 得到当前格式化后的时间
 * @returns {string} 格式化后的时间
 */
function getSimpleTime(){
    // 拿到格式化后的时间
    let now = new Date();
    let time = now.getHours()+':'
    time += (now.getMinutes() > 9)?now.getMinutes():'0'+now.getMinutes()
    time += ':'
    time += (now.getSeconds() > 9)?now.getSeconds():'0'+now.getSeconds()

    return time;
}


// 得到cookie的函数
function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i=0; i<ca.length; i++)
    {
        let c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}

// 校验用户名和密码的方法
function judge_uname_pwd(){
    // 先还原
    $('#uname').removeClass('is-invalid')
    $('#upwd').removeClass('is-invalid')
    $('#uname ~ p').hide()
    $('#upwd ~ p').hide()

    // 先得到用户名
    const uname = $('#uname').val()
    // 判断用户名是否合法
    if(uname.length < 3 || uname.length > 16){
        // 不合法需要弹出提示信息
        $('#uname').addClass('is-invalid')
        $('#uname ~ p').show()
        return false
    }

    // 再获取密码
    const upwd = $('#upwd').val()
    // 判断密码是否合法
    if(upwd.length < 6 || upwd.length > 18){
        // 不合法需要弹出提示信息
        $('#upwd').addClass('is-invalid')
        $('#upwd ~ p').text('密码不合法！').show()
        return false
    }

    return true 
}

// 完成注册或者登录功能
$('#usubmit').on('click', ()=>{
    // 如果合法，发送请求，注册/登录账号
    if(judge_uname_pwd()){
        /**
         * 先将窗口关闭，屏幕正中央生成滚轮，然后发送异步请求，判断用户名和密码是否匹配
         * 如果匹配失败，返回失败信息，生成信息
         * 如果匹配成功，页面需要重定向
         */

        // 窗口关闭
        $('#login').hide()
        // 生成滚轮
        $('#roll').show()

        $.post(
            "user",
            {"name":$('#uname').val(), "password":$('#upwd').val()},
            function(data){
                // 成功回调后隐藏滑轮
                $('#roll').hide()

                // 判断是成功还是失败
                if(data.code == 200){
                    // 刷新当前页面
                    location.reload()
                }else{
                    // 失败代表验证码错误，重新弹出login界面并回显错误信息
                    $('#login').show()
                    $('#upwd ~ p').text(data.message).show()
                }
            },"json"
        );
    }
})

// 尝试从cookie获取值,渲染客户端
$(()=>{
    let name = getCookie("name")
    // 如果有内容，显示name
    if(name){
        $('#login_but').hide()
        $('#user_name').text(name).show()
        $('#user_exit').show()
        $('#write_blog').show()
    }
})

