// 单独属于index页面的js
/**
 * 渲染页面
 * @param start 每一页开头
 * @param categoryId 分类id
 * @param size 大小
 */
// 默认分页每页10张博客
const PAGE_SIZE = 10

function renderBlog(start, categoryId, size){
    $.getJSON(
        'blogs',
        {
            "start":start,
            "size":size,
            "categoryId":categoryId
        },
        function(res){
            // 先清空
            $('#blog').empty()
            console.log(res)

            let str1 = '<div class="px-3 py-2 border-bottom"><div><div class="d-flex w-100 justify-content-between"><a href="'
            // 拼接id
            let str2 = '" class="mb-1 text-truncate h5">'
            // 拼接标题
            let str3 = '</a><small>'
            // 拼接时间
            let str4 = '</small></div><p class="mb-1 text-truncate">'
            // 拼接描述
            let str5 = '</p></div><small class="me-4">'
            // 拼接用户名
            let str6 = '</small><small class="me-2"><i class="bi bi-hand-thumbs-up pe-2"></i>0</small><small class="me-2"><i class="bi bi-chat-square-dots pe-2"></i>12</small><small><i class="bi bi-eye pe-2"></i>13</small></div>'

            // 渲染数据, 往盒子里面添加数据
            $(res.data).each(function (){
                $('#blog').append(str1+'blog/'+this.id+str2+this.title+str3+this.createTime+str4+this.description+str5+this.authorName+str6)
            })
        }
    )
}

/**
 * 根据分类id查询当前分类下的个数
 * @param categoryId
 */
function renderPagination(categoryId){
    // 发送请求计算当前分类博客下的数量
    $.getJSON(
        'blog/count',
        {"categoryId":categoryId},
        function (res){
            // 先将左右两个按钮激活,并且show
            $('.page-item').first().removeClass("disabled")
            $('.page-item').last().removeClass("disabled")
            $('.pagination').show()
            // 通过这个count进行渲染
            let page_count = Math.ceil(res.data / PAGE_SIZE)
            let cur_page = $('#cur_page').text()

            if(page_count <= 1){
                // 如果不足的话，隐藏
                $('.pagination').hide()
                return
            }
            // 如果当前页是第一页面，把左键禁用
            if(cur_page == 1){
                $('.page-item').first().addClass("disabled")
            }else if(cur_page == page_count){ // 如果当前页是最后一页，把右键禁用
                $('.page-item').last().addClass("disabled")
            }
        }
    )
}

$(()=>{
    // 查出所有的博客，以分页方式
    renderBlog(0, -1, PAGE_SIZE)
    // 渲染页面,默认查所有分类
    renderPagination(-1)

    // index页面加载时，查询数据库所有的分类并且展示
    $.getJSON(
        'categories',
        1,
        function (res){
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
        let idx = parseInt($(this).attr('idx'))
        // 先取消激活，再当前激活按钮
        $('#category > .active').removeClass("active")
        $(this).addClass("active")

        // 渲染！
        renderBlog(0, idx, PAGE_SIZE)
        renderPagination(idx)
    })

    // 给左右翻页键绑定单击事件
    $('.pagination > li:not(.active)').on('click',function (){
        if($(this).hasClass("disabled")) return

        let idx = parseInt($('#cur_page').text())
        let categoryId = $('#category > .active').attr("idx")
        if(this === $('.pagination > li').first()[0]){
            idx--;
        }else if(this === $('.pagination > li').last()[0]){
            idx++;
        }

        $('#cur_page').text(idx)
        // 渲染！
        renderBlog((idx - 1) * PAGE_SIZE, categoryId, PAGE_SIZE)
        renderPagination(categoryId)
    })
})