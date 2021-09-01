// 自动让textarea铺满屏幕
$('#content').css("height", $(window).height() -  $('#content').offset().top + "px");
// 设置out的宽度为浏览器的2/3
$('#out').css("width", $(window).width() * 2 / 3 + 'px')
console.log($(window).height());

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
    const res = $('#out').html()
    console.log(res);
})
