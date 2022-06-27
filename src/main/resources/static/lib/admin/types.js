var typesContext = new Vue({
    el: "#typesContext",
    data: {
        message: "提示：操作成功！",
        pageBean: {
            records: [{id: 1, name: "java"}], // 查询数据列表
            total: 10, // 总数
            size: 10, // 每页显示条数，默认 10
            current: 1 // 当前页
        }
    },
    methods: {
        getPage: function(pageNum = 1){
            axios.get("admin/types?pageNum=" + pageNum).then(
                function (response) {
                    typesContext.$set(typesContext, "pageBean", response.data)
                }
            );
        },
        nextPage: function () {
            if (this.pageBean.current + 1 > Math.ceil(this.pageBean.total / this.pageBean.size)) return;
            this.getPage(this.pageBean.current + 1);
        },
        prePage: function () {
            if (this.pageBean.current - 1 < 1) return;
            this.getPage(this.pageBean.current - 1);
        },
        deleteType: function (typeId) {
            if (confirm('确定要删除该分类吗？三思啊! 删了可就没了！'))
                axios.get("/admin/type/delete/" + typeId).then(function (response) {
                    if (response.data.error){
                        alert(response.data.errorInfo);
                        return;
                    }
                    typesContext.getPage(typesContext.pageBean.current);
                })
        }
    },
    created: function () {
        this.getPage(1);
    }
})

