let vm = new Vue({
    el: '#app',
    data: {
        sliderInfo: {
            // 每天的数据数组；pics当天所有图片
            axiosResponse: [{
                "date": "",
                "count": 0,
                "pics": [{
                    id: 0,
                    realfilename: "",
                    savefilename: "",
                    ext: "", // 扩展名
                    dir: "", // 文件目录
                    size: 0, // 大小，byte
                    type: "", // 如：image/jpeg
                    uploadtime: "",
                    changetime: "",
                    isrecycle: 0,
                    ispublic: 0,
                    downloadtimes: "",
                    isimg: 1,
                    thumbnail: ""
                }]
            }],
            currentValue: 0,
            max: 0,
            showTooltip: true
        },
        // [map{
        //     pix: ,
        //     isLoaded: ,
        // }] // 和axiosResponse一样长
        // map{}: 像素范围: id
        pixelList: [],

    },
    methods: {
        tooltipFormat(value) {
            if (value == null) return "null";
            return this.sliderInfo.axiosResponse[value]["date"].slice(0, 10);
        },
        sliderOnChange(value) {
            console.log(value);
            var ele = document.getElementById("album");
            // ele.scrollBy(0, document.getElementById(value).scrollTop)
            // ele.scrollTop = 0
            var child = document.getElementById(value);
            // console.log(child.offsetTop)
            ele.scrollTop = child.offsetTop - 159;
        },
    },
    // 获取时间轴信息
    created: async function() { // 钩子函数，链式编程 es6新特性
        await axios.get("pictures/picturesDates")
            .then(response=>(
            this.sliderInfo.axiosResponse = response.data
            // this.vm.$set( this.sliderInfo.axiosResponse, response.data)
            ));
        this.sliderInfo.max = this.sliderInfo.axiosResponse.length - 1;
        // 将所有图片预加载...
        // 封装 pixelList
        // 1. 遍历所有的id 总共 0 ~ axiosResponse.len
        for (var i = 0; i < this.sliderInfo.axiosResponse.length; i++){
            let pix = document.getElementById(i).offsetTop;
            this.pixelList.push({"pix": pix - 159, "isLoaded": false})
        }
        album.lazyload({"target": {"scrollTop": 160}})
    },
})

var album = new Vue({
    el: "#photos",
    data: {
        vm: vm,
        timer: 0
    },
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
            switch (week_day) {
                case 1: dw = "一"; break;
                case 2: dw = "二"; break;
                case 3: dw = "三"; break;
                case 4: dw = "四"; break;
                case 5: dw = "五"; break;
                case 6: dw = "六"; break;
                case 0: dw = "日"; break;
            }
            return y + "年" + MM + "月" + d + "日" + "周" + dw;
        }
    },
    methods: {
        // 大于k的最小元素索引
        findSmallestEleButThanK: function (array, k) {
            if(array == null || array.length == 0 || k < array[0]["pix"]) return -1;
            else if (k > array[array.length-1]["pix"])
                return array.length - 1;
            var length = array.length;
            var left = 0, right = length - 1, middle = 0;
            while(left <= right){
                middle = parseInt((left + right) / 2);
                if(array[middle]["pix"] <= k) left = middle+1;
                if(array[middle]["pix"] > k) right = middle-1;
            }
            return left - 1;
        },
        // 消抖函数
        deboun: function(event, delay){
            clearTimeout(this.timer);
            this.timer = setTimeout(function () {
                album.lazyload(event)
            },delay)
        },
        lazyEvent: function (event) {
            this.deboun(event, 200);
        },
        lazyload: function (event)  {
            // console.log(event.target.scrollTop)
         /*   console.log(event)
            console.log(event.target.scrollTop)*/
            // console.log(this.vm.pixelList)
            var index1 = this.findSmallestEleButThanK(this.vm.pixelList, event.target.scrollTop);
            var index2 =  this.findSmallestEleButThanK(this.vm.pixelList, event.target.scrollTop + 650);
            var dateIndexToLoad = [];
            // 加载index1 ~ index2 区间所有"isLoaded"不为true的所有index
            if (index1 != -1 && index2 != -1){
                for (index1; index1 <= index2; index1++){
                    if (this.vm.pixelList[index1]["isLoaded"] != true){
                        dateIndexToLoad.push(index1);
                    }
                }
            }
            // console.log(dateIndexToLoad)
            if (dateIndexToLoad.length > 0){
                // 需要去请求加载的日期内的图片
                console.log(dateIndexToLoad)
                this.loadImageByData(dateIndexToLoad)
            }
        },
        loadImageByData: async function (datesIndexList) {
            var that = this;
            for (var i = 0; i < datesIndexList.length; i++){
                var index = datesIndexList[i];
                var date = this.vm.sliderInfo.axiosResponse[index]["date"].slice(0, 10);
                // 根据日期获取当天所有图片对象
                await axios.get("pictures/getFilesByDate/" + date).then(function (response) {
                    if(response.status == 200 ){
                        that.vm.pixelList[index]["isLoaded"] = true, // 表示加载过了
                        that.vm.$set(that.vm.sliderInfo.axiosResponse[index], "pics", response.data)
                        // that.vm.sliderInfo.axiosResponse[index]["pics"] = response.data
                        // console.log(that.vm.sliderInfo.axiosResponse[index]["pics"])
                    }
                });
            }

        },

        /**
         *
         * @param index 所在某天数组索引
         * @param pic 某天的图片索引
         */
        selectFile: function (index, pic) {
            // console.log(index)
            // console.log(pic)
            // 当前 figure标签对象
            var figure = $("#"+ index).parents(".day-photos-1ir5y").find("figure").eq(pic);
            // 交替选择: 有这个类uk-overlay-hover，说明还没被选中
            if (figure.hasClass("uk-overlay-hover")){
                // 没被选中，去选中它
                figure.removeClass("uk-overlay-hover")
                figure.find("figcaption").removeClass("uk-overlay-bottom")
                figure.find("figcaption").removeClass("uk-overlay-slide-bottom")
                figure.find("figcaption").addClass("uk-flex uk-flex-center uk-flex-middle uk-text-center")
                figure.find("figcaption").css("margin-left", "10px")
                figure.find("figcaption").css("height", "160px")
                figure.find(".unselected").hide();
                figure.find(".selected").show();
                selectedTopbar.selectedFileIndexs.add(JSON.stringify([index, pic]))
            }else {
                figure.addClass("uk-overlay-hover")
                figure.find("figcaption").addClass("uk-overlay-bottom uk-overlay-slide-bottom")
                figure.find("figcaption").removeClass("uk-flex uk-flex-center uk-flex-middle uk-text-center")
                figure.find(".unselected").show();
                figure.find(".selected").hide();
                figure.find("figcaption").css("margin-left", "")
                figure.find("figcaption").css("height", "")
                selectedTopbar.selectedFileIndexs.delete(JSON.stringify([index, pic]))
            }
            if (selectedTopbar.selectedFileIndexs.size == 0){
                $("#selectedTopbar").hide();
            }else {
                $("#selectedTopbar").show();
                // selectedTopbar.filesCount = selectedTopbar.selectedFileIndexs.size
            }
            selectedTopbar.filesCount = selectedTopbar.selectedFileIndexs.size;
        },

        showFileDetail: function (index, pic) {
            $("#fileDetailDisplay").show();
            fileDetailDisplay.$set(fileDetailDisplay, "currentFileDetail", this.vm.sliderInfo.axiosResponse[index].pics[pic])
            fileDetailDisplay.isDisplay = true;
            fileDetailDisplay.curIndex = index;
            fileDetailDisplay.curPic = pic;
        }
    },
})

var selectedTopbar = new Vue({
    el: "#selectedTopbar",
    data: {
        selectedFileIndexs: new Set(), // ([index,pic], [index, pic]...)
        filesCount: 0
    },
    methods: {
        // 下载图片
        downloadFiles() {
            // location.href = "/file/downloadFiles?fileIds=" + Array.from(this.selectedFiles);
            var selectedFileIds = [];
            this.selectedFileIndexs.forEach(function (element) {
                element = JSON.parse(element);
                selectedFileIds.push(vm.sliderInfo.axiosResponse[element[0]].pics[element[1]].id)
            })
            location.href = "/pictures/downloadFiles?fileIds=" + Array.from(selectedFileIds);
        },
        // 关闭topbar
        closeSelectedTopbar() {
            $("#selectedTopbar").hide();
            this.selectedFileIndexs.forEach(function (element) {
                element = JSON.parse(element);
                var figure = $("#"+ element[0]).parents(".day-photos-1ir5y").find("figure").eq(element[1]);
                figure.addClass("uk-overlay-hover")
                figure.find("figcaption").addClass("uk-overlay-bottom uk-overlay-slide-bottom")
                figure.find("figcaption").removeClass("uk-flex uk-flex-center uk-flex-middle uk-text-center")
                figure.find(".unselected").show();
                figure.find(".selected").hide();
                figure.find("figcaption").css("margin-left", "")
                figure.find("figcaption").css("height", "")
            })
            this.selectedFileIndexs.clear();
        },
        // 删除图片
        deleteSelectedFiles() {
            if (!confirm("确认删除吗？"))
                return;
            var selectedFileIds = [];
            this.selectedFileIndexs.forEach(function (element) {
                element = JSON.parse(element);
                selectedFileIds.push(vm.sliderInfo.axiosResponse[element[0]].pics[element[1]].id)
            })
            var formData = new FormData();
            formData.append("files", JSON.stringify(Array.from(selectedFileIds)))
            $.ajax({
                type: "post",
                // contentType : "application/json;charset=utf-8",
                dataType: "json",
                // url: "/file/deleteFiles",
                url: "/pictures/deleteFilesCompletely",
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                success: function (respData){
                    window.location.reload();
                }
            })
        },
        // 公开/取消公开
        publishPics(publish) {
            var selectedFileIds = [];
            this.selectedFileIndexs.forEach(function (element) {
                element = JSON.parse(element);
                selectedFileIds.push(vm.sliderInfo.axiosResponse[element[0]].pics[element[1]].id)
            })
            axios.get("/pictures/publish/"+  publish +"/?fileIds=" + Array.from(selectedFileIds)).then(function (response) {
                if (!response.data.error){
                    alert(response.data.data);
                }else {
                    alert(response.data.errorInfo)
                }
                // selectedTopbar.closeSelectedTopbar();
                window.location.reload();
            })
        }
    }

})

var fileDetailDisplay = new Vue({
    el: "#fileDetailDisplay",
    data: {
        currentFileDetail: {
            id: 0,
            realFileName: "",
            saveFileName: "",
            ext: "", // 扩展名
            dir: "", // 文件目录
            size: 0, // 大小，byte
            type: "", // 如：image/jpeg
            uploadTime: "",
            changeTime: "",
            isRecycle: 0,
            isPublic: 0,
            downLoadTimes: "",
            isImg: 1,
            thumbnail: ""
        },
        isDisplay: false,
        isDisplayDetail: false,
        curIndex: 0,// 当前图片日期索引
        curPic: 0, // 当前图片所在当日中的索引
        host: window.location.host
    },
    methods: {
        closeDetail: function () {
           $("#fileDetailDisplay").hide();
           this.isDisplay = false;
        },
        downloadFile: function () {
            window.open("/pictures/downloadFile/" + this.currentFileDetail.id)
        },
        deleteFile: function () {
            if (!confirm("确认删除吗？"))
                return;
            window.location.href = "/pictures/deleteFileByIdCompletely/" + this.currentFileDetail.id;
        },
        displayDetail: function () {
            this.isDisplayDetail = true
        },
        closeInfoDetail: function () {
            this.isDisplayDetail = false
        },
        copyToClip: function(content) {
            var aux = document.createElement("input");
            aux.setAttribute("value", content);
            document.body.appendChild(aux);
            aux.select();
            document.execCommand("copy");
            document.body.removeChild(aux);
            alert("复制成功！");
        },
        getPrePic: function() {
            var dates = vm.sliderInfo.axiosResponse; // 每天的图片
            if (this.curPic == 0 && this.curIndex != 0){
                // 当前图片在第一个，之前还有日期
                if (dates[this.curIndex - 1].pics == null){
                    album.loadImageByData([this.curIndex - 1]);
                    return;
                }
                this.curIndex = this.curIndex - 1;
                this.curPic = dates[this.curIndex].pics.length - 1;
            }else if (this.curPic == 0 && this.curIndex == 0){
                // 当前是第一张图片
                alert("没有更多图片了~")
                return;
            }else {
                this.curPic = this.curPic - 1;
            }
            $("#imgDisplay").attr("src", dates[this.curIndex].pics[this.curPic].dir + dates[this.curIndex].pics[this.curPic].thumbnail);
            this.currentFileDetail = dates[this.curIndex].pics[this.curPic];
            if (document.getElementById("video") != null)
                document.getElementById("video").load();
        },
        getNextPic: function () {
            var dates = vm.sliderInfo.axiosResponse; // 每天的图片
            console.log(dates[this.curIndex].pics)
            console.log(dates[this.curIndex].pics.length)
            if (dates[this.curIndex].pics == null) return;
            if (dates[this.curIndex].pics.length - 1 == this.curPic && this.curIndex != dates.length - 1){
                // 当前图片在当日最后一张，之后还有日期
                if (dates[this.curIndex + 1].pics == null){
                    album.loadImageByData([this.curIndex + 1]);
                    return;
                }
                this.curIndex = this.curIndex + 1;
                this.curPic = 0;
            }else if (dates[this.curIndex].pics.length - 1 == this.curPic && this.curIndex == dates.length - 1){
                // 当前是第一张图片
                alert("没有更多图片了~")
                return;
            }else {
                this.curPic = this.curPic + 1;
            }
            $("#imgDisplay").attr("src", dates[this.curIndex].pics[this.curPic].dir + dates[this.curIndex].pics[this.curPic].thumbnail);
            this.currentFileDetail = dates[this.curIndex].pics[this.curPic];
            if (document.getElementById("video") != null)
                document.getElementById("video").load();
        }
    },
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
            switch (week_day) {
                case 1: dw = "一"; break;
                case 2: dw = "二"; break;
                case 3: dw = "三"; break;
                case 4: dw = "四"; break;
                case 5: dw = "五"; break;
                case 6: dw = "六"; break;
                case 0: dw = "日"; break;
            }
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            return y + "年" + MM + "月" + d + "日" + "周" + dw  + " " + hours + ":" + minutes + ":" + seconds;
        }
    },
})

$("#album").bind('scroll',album.lazyEvent);//监听页面滚动事件
























