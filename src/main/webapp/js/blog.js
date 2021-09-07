function getCategoryId(){
    // 拿到路径名字
    let url = document.location.pathname;
    return url.split("/")[3]
}

let id = getCategoryId()
let converter = new showdown.Converter()

// 渲染页面博客
$.getJSON(
    'blog',
    {"id":id},
    function (res){
        console.log(res.data)

        if(res.code == 500){
            // 出问题跳转到首页
            window.location.href = "/"
        }

        let blog = res.data
        // 修改标题
        $('#title').text(blog.title)
        // 修改作者，并修改作者的超链接
        $('#author').text(blog.authorName).attr("href", "user/" + blog.authorId)
        // 修改时间
        $('#time').text(blog.createTime)
        // 修改分类
        $('#category').text(blog.categoryName)
        // 添加内容
        $('#content').html(converter.makeHtml(blog.content))
    }
)
// 传入textarea，计算span里面的剩余字符
function calculate_remainder(){
    let remainder = parseInt( $(this).val().length )

    if(remainder > 255){
        // 超出就需要去掉多余的
        $(this).val($(this).val().substring(0,255) )
    }else{

        // 这是一个通用方法，两个评论窗口公用，但是结构不同，所以需要进行判断
        let sp
        if($(this).is($('#comment_content_top'))){
            sp = $('#comment_bot').find('span')
        }else{
            sp = $(this).next().find('span')
        }

        $(sp).text(255 - remainder);
    }
}

// 发送评论
function sender_comment(idx, content, commentId){
    $.post(
        'blog/comment/' + id,
        {
            'idx':idx,
            'content':content,
            'commentId':commentId
        },
        function(res){
            console.log(res.message);

            if(res.code === 200){
                // 发送请求，从新渲染评论页面
                render_comment();
            }
        }
    )
}

// 渲染评论页面
function render_comment(){
    $.getJSON(
        'blog/comment/' + id,
        1,
        function(res){
            // 清空，清空前把评论框移出去并隐藏
            $('#comment_card').hide()
            $('#comment').after($('#comment_card')).empty()

            // 根据博客id查到对应所有的评论
            if(!res.data){
                // 如果没有然后评论
                console.log('无评论');
                return
            }
            console.log(res)

            // 开始渲染
            $(res.data).each(function(){
                // 每一个都是一个一级评论
                let str = '<div class="comment-list border-bottom py-2" idx="1" cid="'
                // 评论id
                str += this.id;
                str += '"><a href="'
                // 插入用户id
                str += 'user/' + this.senderId;
                str += '" class="m_user sender">'
                // 插入用户名
                str += this.senderName;
                str += '</a>：<span>'
                // 插入评论
                str += this.content
                str += '</span><span class="px-1 text-secondary">'
                // 插入时间
                str += this.createTime
                str += '</span><span class="replay-btn">回复</span><div class="replay-list py-1">'


                // 判断是否有二级评论有就继续拼接
                if(this.children){
                    $(this.children).each(function(){
                        str += '<div class="replay border-start ps-3 py-2" idx="2" cid="'
                        // 拼接当前评论id
                        str += this.id
                        str += '"><a href="'
                        // 拼接用户id
                        str += 'user/' + this.senderId
                        str += '" class="m_user sender">'
                        // 拼接用户名
                        str += this.senderName
                        str += '</a><span class="mx-2 text-secondary m_small">回复</span><span class="m_user">'
                        // 拼接被回复的用户名
                        str += this.receiverName
                        str += '</span>：<span>'
                        // 拼接评论内容
                        str += this.content
                        str += '</span><span class="px-1 text-secondary">'
                        // 拼接评论时间
                        str += this.createTime
                        str += '</span><span class="replay-btn">回复</span></div>'
                    })
                }

                str += '</div></div>'

                $('#comment').append(str)
            })

            // 在这里，给二级评论回复按钮绑定事件
            $('.replay-btn').on('click', function(){
                console.log("我要回复！！")
                let par =  $(this).parent();
                $('#comment_card').slideDown()

                // 如果点击的id与评论id相同，不做任何处理(即第二次点击后)
                if($('#comment_card').attr("cid") === $(par).attr("cid")){
                    $('#comment_content').focus()
                    return;
                }

                // 为其设置评论id
                $('#comment_card').attr("cid", $(par).attr("cid"))

                // 先判断时回复的是一级还是二级评论
                if($(par).attr("idx") === "1"){
                    $(par).children('.replay-list').prepend($('#comment_card')[0])
                }else{
                    $(par).after($('#comment_card')[0])
                }
                // 清空内容并获取焦点
                $('#comment_content').val('').keydown().focus()
            })
        }
    )
}

// 发送一级评论按钮（因为要解绑和绑定，所以需要抽取成一个函数
function sender_comment_btn(){
    const content = $('#comment_content_top').val()
    if(content.length <= 0){
        // 弹出不允许评论内容为空
        return

    }
    // 发送评论前清空内容
    $('#comment_content_top').val('')
    sender_comment(1, content,"")
}

// 给一级评论添加特殊样式
function render_comment_top(){
    // 移除row
    $(this).children().removeClass('row')
    // 设置textarea
    $('#comment_content_top').css('height','')
    // 设置按钮和提示消息的属性
    $('#comment_bot').addClass('py-2')
    // 取出d-none
    $('#comment_bot > small').removeClass('d-none')
    // 设置按钮
    $('#comment_bot > button').removeClass("btn-secondary").addClass("btn-danger")
    // 获取焦点
    $('#comment_content_top').focus()
    // 给计算剩余字符添加事件
    $('#comment_content_top').on('keydown', calculate_remainder).on('keyup', calculate_remainder)
    // 给发送评论添加事件
    $('#comment_top button').on('click', sender_comment_btn)
}


// 自动查出当前博客的评论
$(()=>{
    // 渲染评论
    render_comment()

    // 一级评论框显示
    $('#comment_top').one('click', render_comment_top)

    // 键盘输入时自动计算剩余字符
    $('#comment_content').on('keydown', calculate_remainder).on('keyup', calculate_remainder)

    // 让其失去焦点时自动隐藏(鼠标点击评论区以外)
    $(document).on('mouseup',function(e) {
        let comment = $('#comment'), comment_top = $('#comment_top')

        // 二级评论失去焦点
        if(!comment.is(e.target) && comment.has(e.target).length === 0){
            $('#comment_card').hide()
            // 并且清空cid
            $('#comment_card').removeAttr("cid")
        }

        // 一级评论失去焦点
        if(!comment_top.is(e.target) && comment_top.has(e.target).length === 0){

            $('#comment_top').children().addClass('row')

            $('#comment_content_top').css('height','20px')

            $('#comment_bot').removeClass('py-2')

            $('#comment_bot > small').addClass('d-none')

            $('#comment_bot > button').removeClass("btn-danger").addClass("btn-secondary")

            // 重新绑定事件
            $('#comment_top').one('click', render_comment_top)
            // 去除事件
            $('#comment_content_top').off()
            // 去除发送评论
            $('#comment_top button').off('click')
        }
    });

    // 发送二级评论
    $('#comment_card button').on('click', function (){
        const content = $('#comment_content').val()
        if(content.length <= 0){
            // 弹出不允许评论内容为空

            return
        }

        const commentId = $('#comment_card').attr('cid')
        sender_comment(2, content, commentId)
    })
})