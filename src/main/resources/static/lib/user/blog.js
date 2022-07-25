function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) {
        return unescape(r[2]);
    } else {
        return null; //返回参数值
    }
}
var blogView = new Vue({
    el: "#blogView",
    filters: {
        formatTimer: function(value) {
            let date = new Date(value);
            let y = date.getFullYear();
            let MM = date.getMonth() + 1;
            let week_day = date.getDay();
            MM = MM < 10 ? "0" + MM : MM;
            let d = date.getDate();
            d = d < 10 ? "0" + d : d;
            var dw = "";
            var hour = date.getHours().toString()
            var minute = date.getMinutes().toString()
            var sec = date.getSeconds().toString()
            switch (week_day) {
                case 1: dw = "一"; break;
                case 2: dw = "二"; break;
                case 3: dw = "三"; break;
                case 4: dw = "四"; break;
                case 5: dw = "五"; break;
                case 6: dw = "六"; break;
                case 0: dw = "日"; break;
            }
            return y + "年" + MM + "月" + d + "日" + "周" + dw + " " + hour + ":" + minute + ":" + sec;
        }
    },
    data: {
        types: [{"id": 1, "name": "JS"}],
        blogId: null,
        contentEditor: {},
        typesMap: {},
        blogDetail: {
            "id": 1,
            "commentabled":true,
            "content":"",
            "createTime":"",
            "description":"",
            "firstPicture":"asdsa",
            "flag":"原创",
            "published":true,
            "recommend":true,
            "shareStatement":false,
            "title":"",
            "updateTime":"",
            "views":0,
            "typeId":59,
            "userId":null,
            "commentCount":0
        },
        comments: [
            {
                id: 1,
                createTime: "2022,1,1",
                nickname: "tony",
                email: "12321@qq.com",
                content: "六六六",
                blogId: 1,
                parentCommentId: 1,
                isAdminComment: 0,
                //回复评论
                replyComments: [],
                // parentComment: 1
                parentNickname: ""
            }
        ]
    },
    methods: {
        loadBlog: async function () {
            if (this.blogId != null){
                // 回显数据
                await axios.get("/blog/detail/" + this.blogId).then(function (response) {
                    if (!response.data.error)
                        blogView.$set(blogView, "blogDetail", response.data.data);

                })
            }
        },
        loadComment: function () {
            if (this.blogId != null)
                axios.get("/comment/" + this.blogId).then(function (response) {
                    blogView.$set(blogView, "comments", response.data);
                })
        },
        checkForm: function () {
            var boo = $('.ui.form').form('validate form');
            if (boo) {
                console.log('校验成功!');
                this.postData();
            } else {
                console.log('校验失败');
            }
        },
        postData: function () {
            axios.post("/comment/addComment", {
                "parentCommentId" : $("[name='parentCommentId']").val(),
                "blogId" : this.blogId,
                "nickname": $("[name='nickname']").val(),
                "email"   : $("[name='email']").val(),
                "content" : $("[name='content']").val()
            }).then(function (response){
                $(window).scrollTo($('#goto'),500);
                blogView.loadComment();
                blogView.clearContent();
                // $("#commentpost-btn").attr("disabled", false);
            })
        },
        clearContent: function() {
            $("[name='nickname']").val('');
            $("[name='email']").val('');
            $("[name='content']").val('');
            $("[name='parentComment.id']").val(-1);
            $("[name='content']").attr("placeholder", "请输入评论信息...");
        },
        reply: function (commentId, commentNickname) {
            // var commentId = $(obj).data('commentid');
            // var commentNickname = $(obj).data('commentnickname');
            $("[name='content']").attr("placeholder", "@"+commentNickname).focus();
            $("[name='parentCommentId']").val(commentId);
            $(window).scrollTo($('#comment-form'),500);
        },
        deleteComment: function (commentId){
            if (!confirm('确定要删除该评论吗？三思啊! 删了可就没了！')) return;
            axios.get("/comment/deleteComment/" + commentId).then(function (response){
                if (response.data.error){
                    alert(response.data.errorInfo);
                }else {
                    alert(response.data.data);
                    blogView.loadComment();
                }
            })
        }
    },
    created: async function () {
        // 请求所有分类
        await axios.get("/types/all").then(function (response) {
            blogView.$set(blogView, "types", response.data);
            for (let i = 0; i < blogView.types.length; i++) {
                blogView.typesMap[blogView.types[i]["id"]] = blogView.types[i]["name"];
            };
        })
        this.blogId = getUrlParam("id");
        this.loadBlog();
        this.loadComment();
    },
    updated: function (){

        Prism.highlightAll();
        $('.toc.button').popup({
            popup : $('.toc-container.popup'),
            on : 'click',
            position: 'left center'
        });
        tocbot.init({
            // Where to render the table of contents.
            tocSelector: '.js-toc',
            // Where to grab the headings to build the table of contents.
            contentSelector: '.js-toc-content',
            // Which headings to grab inside of the contentSelector element.
            headingSelector: 'h1, h2, h3',
        });

/*        $(".js-toc").find("a").click(function(e){
            e.preventDefault();
            $("#doc-oc-demo1").offCanvas('close');
            scrollToId = decodeURI(e.currentTarget.hash).substring(1);
            return false;
        })*/

    }
})
