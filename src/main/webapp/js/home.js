/**
 * 完成home页所有必要的交互
 */
// 尝试获取当前页面的userid
let str = location.pathname.split("/")
const userId = str[str.length - 1];
const isUser = isRealNum(userId)
/**
 * 查询用户id下的博客,并往content中渲染
 */
function renderBlogs(){
    let MURL = isUser?('user/' + userId + '/blogs'):'home/blogs'

    $.getJSON(
        MURL,
        1,
        function(res){
            console.log(res);
            if(res.code === 500){
                console.log('出现错误！');
                return;
            }
            // 清空内容
            $('#content').empty()

            // 遍历博客渲染
            $(res.data).each(function(){
                let str1 = '<div class="px-3 py-2 border-bottom"><a href="'
                // 这里连接该博客的id
                let str2 = '" class="h5 mb-1 text-truncate">'
                // 这里连接博客的标题
                let str3 = '</a><p class="mb-1 text-truncate">'
                // 这里连接博客的简述
                let str4 = '</p><div class="d-flex justify-content-between"><div><small class="me-2"><i class="bi bi-chat-square-dots pe-2"></i>'
                // 这里连接评论数
                let str5 = '</small></div><small>'
                // 这里连接时间
                let str6 = '</small></div></div>'

                $('#content').append(str1+"blog/"+this.id+str2+this.title+str3+this.description+str4+this.commentCount+str5+this.createTime+str6)
            })

            // 设置名字
            $('#blog_uname').text(res.message)
            // 设置文章数量
            $('#blog_size').text(res.data.length)
        }
    )
}

// 每次页面载入时，自动调用
$(()=>{
    // 自动渲染他的博客
    renderBlogs()
})