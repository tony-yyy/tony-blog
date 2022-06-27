function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) {
        return decodeURI(r[2]);
    } else {
        return null; //返回参数值
    }
}
var blogs = new Vue({
    el: "#blogsView",
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
        curKeyWord: getUrlParam("keyWord") == ""? null: getUrlParam("keyWord")
    },
    created: async function () {
        // 请求所有分类
        await axios.get("/types/all").then(function (response) {
            blogs.$set(blogs, "types", response.data);
            for (let i = 0; i < blogs.types.length; i++) {
                blogs.typesMap[blogs.types[i]["id"]] = blogs.types[i]["name"];
            }
        })
        this.getBlogs(1, null, this.curKeyWord);
    },
    methods: {
        getBlogs: async function(page, type, title){
            await axios.get(
                "/blogs/" + page +
                "?pageSize=10" +
                (type != null && type != ""? "&typeId=" + type: "") +
                (title != null && title != ""? "&title=" + title: "")
            ).then(function (response) {
                blogs.$set(blogs, "blogsPage", response.data);
                document.getElementById("search_header").scrollIntoView();
            })
        },
        nextPage: function () {
            if (this.blogsPage.current + 1 > Math.ceil(this.blogsPage.total / this.blogsPage.size)) return;
            this.getBlogs(this.blogsPage.current + 1, null, this.curKeyWord);
        },
        prePage: function () {
            if (this.blogsPage.current - 1 < 1) return;
            this.getBlogs(this.blogsPage.current - 1, null, this.curKeyWord);
        },
        getBlogsByKeyWord: function (keyword) {
            this.curKeyWord = keyword;
            this.getBlogs(1, null, this.curKeyWord);
        }
    }
});
