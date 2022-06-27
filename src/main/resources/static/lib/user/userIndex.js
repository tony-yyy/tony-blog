var indexBlogs = new Vue({
    el: "#indexBlogs",
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
        latestBlogs: {
            "records":
                [{
                    "id":63,
                    "commentabled":true,
                    "content":"",
                    "createTime":"2022-06-17T03:57:00.000+00:00",
                    "description":"lala",
                    "firstPicture":"",
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
        recommendBlogs: [
            {
                "id":63,
                "commentabled":true,
                "content":"",
                "createTime":"2022-06-17T03:57:00.000+00:00",
                "description":"lala",
                "firstPicture":"",
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
            }
        ],
        types: [
            {id: 1, name: "Java"}
        ],
        typesMap: {},
        firstLoadFlag: false
    },
    methods: {
        getBlogs: async function(page){
            await axios.get(
                "/blogs/" + page +
                "?pageSize=5"
            ).then(function (response) {
                indexBlogs.$set(indexBlogs, "latestBlogs", response.data);
                if (indexBlogs.firstLoadFlag == true)
                    document.getElementById("blogsView").scrollIntoView();
                indexBlogs.firstLoadFlag = true;
            })
        },
        getRecommendBlogs: async function(){
            await axios.get("/blogs/recommend").then(function (response) {
                indexBlogs.$set(indexBlogs, "recommendBlogs", response.data);
            })
        },
        nextPage: function () {
            if (this.latestBlogs.current + 1 > Math.ceil(this.latestBlogs.total / this.latestBlogs.size)) return;
            this.getBlogs(this.latestBlogs.current + 1);
        },
        prePage: function () {
            if (this.latestBlogs.current - 1 < 1) return;
            this.getBlogs(this.latestBlogs.current - 1);
        },
    },
    created: async function () {
        // 请求所有分类
        await axios.get("/types/all").then(function (response) {
            indexBlogs.$set(indexBlogs, "types", response.data);
            for (let i = 0; i < indexBlogs.types.length; i++) {
                indexBlogs.typesMap[indexBlogs.types[i]["id"]] = indexBlogs.types[i]["name"];
            }
        })
        this.getBlogs(1);
        this.getRecommendBlogs();
    }

})