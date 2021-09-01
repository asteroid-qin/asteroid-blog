// 添加点击切换高亮功能并查出不同信息的功能
function get_blog(){
    // 首先去除对面的高亮,隐藏对应内容
    $('#bar > div').last().removeClass('active')  
    $('#attention').hide()  

    // 给自己添加高亮，如果存在则不添加
    $('#bar > div').first().addClass('active')
    $('#blogs').show()
}

function get_attention(){
    // 首先去除对面的高亮,隐藏对应内容
    $('#bar > div').first().removeClass('active')  
    $('#blogs').hide()  

    // 给自己添加高亮，如果存在则不添加
    $('#bar > div').last().addClass('active')
    $('#attention').show()
}

// 点击切换关注
