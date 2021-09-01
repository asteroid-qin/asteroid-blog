// 单独属于index页面的js
$(()=>{
    // index页面加载时，查询数据库所有的分类并且展示
    $.getJSON(
        'categories',
        1,function (res){
            // 成功后，渲染页面
            $(res.data).each(function (){
                let but = '<button type="button" idx="'+ this.id +'" class="list-group-item list-group-item-action">'+ this.name +'</button>';
                $('#category').append(but)
            })
            // 给第一个元素加高光，并根据第一个元素查出内容
            $('#category > button').first().addClass("active")
        }
    )

    // 利用事件委托给每个button绑定上单击事件
    $('#category').on('click', 'button', function(ev) {
        // 需要发送ajax请求，查询blog
        console.log("这里是"+ $(this).val() + ", 属于：" + $(this).attr('idx'));
    })
})