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
var typeInput = new Vue({
    el: "#typeInput",
    data: {
        types: [
            {id: 1, name: "Java"}
        ],
        name: "",
        id: getUrlParam("id")
    },
    created: async function(){
        // 请求所有分类
        await axios.get("/admin/types/all").then(function (response) {
            typeInput.$set(typeInput, "types", response.data);
            for (let i = 0; i < typeInput.types.length; i++) {
                if (typeInput.id == typeInput.types[i]["id"]){
                    typeInput.name =  typeInput.types[i]["name"];
                    break;
                }
            }
        })
    },
    methods: {
        addNewType: function () {
            axios.post("/admin/type/addNewType" + (this.id == null || this.id == ""? "": "/update"), {
                id: this.id == null || this.id == ""? null: this.id,
                name: this.name
            }).then(function (response) {
                if (!response.data.error){
                    window.location.replace("/admin/types.html");
                }
            })
        },
    }
})