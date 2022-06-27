var blogs = new Vue({
    el: "#blogs",
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
        blogsPage: {
            "records":
                [{
                    "id":63,
                    "commentabled":true,
                    "content":"lalalaaa",
                    "createTime":"2022-06-17T03:57:00.000+00:00",
                    "description":"lala",
                    "firstPicture":"/cloudDisk/markdown/20220617111315_0c6688740f4c4ad3b21fd207b4a475c9_链表合并一次通过hahaha.png",
                    "flag":"原创",
                    "published":true,
                    "recommend":true,
                    "shareStatement":false,
                    "title":"Java spring boot 学习笔记",
                    "updateTime":"2022-06-17T03:57:00.000+00:00",
                    "views":0,
                    "typeId":59,
                    "userId":null,
                    "commentCount":0
            }],
            "total":2,
            "size":10,
            "current":1,
            "pages":1
        },
        types: [
            {id: 1, name: "Java"}
        ],
        typesMap: {},
        titleInput: "",
    },
    created: async function () {
        // 请求所有分类
        await axios.get("/admin/types/all").then(function (response) {
            blogs.$set(blogs, "types", response.data);
            for (let i = 0; i < blogs.types.length; i++) {
                blogs.typesMap[blogs.types[i]["id"]] = blogs.types[i]["name"];
            }
        })
        this.getBlogs(1, null, null);
    },
    methods: {
        getBlogs: async function(page, type, title){
            await axios.get(
                    "/admin/blogs/" + page +
                    "?pageSize=10" +
                    (type != null && type != ""? "&typeId=" + type: "") +
                    (title != null && title != ""? "&title=" + title: "")
                ).then(function (response) {
                blogs.$set(blogs, "blogsPage", response.data);
            })
        },
        searchBtn: function () {
            let typeId = $("input[name=typeId]").val();
            this.getBlogs(1, typeId, this.titleInput);
        },
        findBlogs: function(){
            $('.ui.type.dropdown')
                .dropdown('clear')
            ;
            this.getBlogs(1, null, null);
        },
        nextPage: function () {
            if (this.blogsPage.current + 1 > Math.ceil(this.blogsPage.total / this.blogsPage.size)) return;
            let typeId = $("input[name=typeId]").val();
            this.getBlogs(this.blogsPage.current + 1, typeId, this.titleInput);
        },
        prePage: function () {
            if (this.blogsPage.current - 1 < 1) return;
            let typeId = $("input[name=typeId]").val();
            this.getBlogs(this.blogsPage.current - 1, typeId, this.titleInput);
        },
        deleteBlog: function (blogId) {
            if (confirm('确定要删除该文章吗？三思啊! 删了可就没了！')){
                axios.get("/admin/blog/delete/" + blogId).then(function (response) {
                    // window.location.reload();
                    let typeId = $("input[name=typeId]").val();
                    blogs.getBlogs(blogs.blogsPage.current, typeId, blogs.titleInput)
                });
            }
        }
    }
});