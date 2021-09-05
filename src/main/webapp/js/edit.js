// 自动让textarea铺满屏幕
$('#content').css("height", $(window).height() -  $('#content').offset().top + "px")

// 准备全局变量
let converter = new showdown.Converter()
let num = 0;

// 添加点击事件，基数次点击时渲染，偶数次恢复
$('#m_render').on('click',function(){
    num++;

    if(num & 1){
        // 把按钮内的消息修改
        $(this).text('恢复')

        // 将text隐藏
        $('#content').hide()
        
        // 拿到内容渲染在out中
        $('#out').html(converter.makeHtml($('#content').val()))

        // out显示
        $('#out').show()
    }else{
        // 同上，修改消息
        $(this).text('预览')
        // 将out隐藏
        $('#out').hide()
        // text显示
        $('#content').show()
    }
})

// 添加点击事件，将out中内容发送
$('#send').on('click', ()=>{
    $.post(
        "blog",
        {   "_method":"PUT",
            "title":$('#title').val(),
            "category_id":$('#category option:selected').attr('idx'),
            "content":$('#content').val()
        },
        function (res){
            // 拿到格式化后的时间
            let now = new Date();
            let time = now.getHours()+':'
            time += (now.getMinutes() > 9)?now.getMinutes():'0'+now.getMinutes()
            time += ':'
            time += (now.getSeconds() > 9)?now.getSeconds():'0'+now.getSeconds()

            // 更新日期
            $('.toast-header > small').text(time)
            // 更新消息
            $('.toast-body').text(res.message)
            console.log(res)
            // 让toast显示
            $('.toast').toast('show');
        },"json"
    )
})


$(()=>{
    // 添加多选分类
    $.get(
        'categories',
        1,
        function (res){
            // 为多选框中生成所有
            $(res.data).each(function (){
                let sel = '<option idx="'+ this.id + '">' + this.name + '</option>'
                $('#category').append(sel)
            })
            // 让第一个元素select
            $('#category > option').first().attr("selected","selected")
        }
    )

})