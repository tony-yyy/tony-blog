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
var blogEditor = new Vue({
    el: "#blogEditor",
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
            "firstPicture":"",
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
        }
    },
    methods: {
        saveBlogForm: async function(){
            var form = $("#blog-form").serialize();
            console.log(form)
            await $.ajax({
                async: false,
                type: "POST",
                url: "/admin/blog/save" + (blogEditor.blogId != null? "/" + blogEditor.blogId: ""),
                data: form,
                dataType: "json",
                success: function (res) {
                    console.log(res)
                    if (!res.error){
                        window.location.replace("/admin/editor.html?page=blogs&blogId=" + res.data);
                    }
                }
            });
        },
        saveBtn: function(){
            if ($("input[name=title]").val() == ""){
                alert("请将标题填写完整！")
                return;
            }
            $('[name="published"]').val(false);
            this.saveBlogForm();
        },
        publishBtn: function(){
            if ($("input[name=title]").val() == ""){
                alert("请将标题填写完整！")
                return;
            }
            $('[name="published"]').val(true);
            this.saveBlogForm();
        },
        loadBlog: async function () {
            if (this.blogId != null){
                // 回显数据
                 await axios.get("/admin/blog/detail/" + this.blogId).then(function (response) {
                    if (!response.data.error){
                        blogEditor.$set(blogEditor, "blogDetail", response.data.data);
                    }
                })
            }
            // 请求所有分类
            await axios.get("/admin/types/all").then(function (response) {
                blogEditor.$set(blogEditor, "types", response.data);
                for (let i = 0; i < blogEditor.types.length; i++) {
                    blogEditor.typesMap[blogEditor.types[i]["id"]] = blogEditor.types[i]["name"];
                };
            })
        }
    },
    created: function () {
        this.blogId = getUrlParam("blogId")
        this.loadBlog()

    },
    updated: function () {
        var _this = this;
        const ce = editormd("md-content", {
            width   : "100%",
            height  : 640,
            syncScrolling : "single",
            path    : "/lib/editormd/lib/",
            imageUpload : true,
            saveHTMLToTextarea : true,//注意3：这个配置，方便post提交表单
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],//支持接收的图片上传的格式
            imageUploadURL : "/admin/blogs/uploadMarkDownImage", //你的controller里为上传图片所设计的路径
            onload: function () {
                initPasteDragImg(this);
            }
        });
        _this.contentEditor = ce;
    }
})





