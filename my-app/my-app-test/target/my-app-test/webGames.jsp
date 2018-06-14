<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>贪吃蛇</title>
    <style>
        .she{
            background-color: red;
            width: 10px;
            right: 10px;
        }
        .food{
            background-color: chartreuse;
            width: 10px;
            right: 10px;
        }
    </style>
</head>
<body>
    <h2>贪吃蛇</h2>
    <div id="mainregion" style="width: 500px;height: 500px;background-color: aquamarine;border: black;border-width: 10px">
        <div class="she"></div>


        <button onclick="StartGames()">开始游戏</button>
    </div>
</body>
<script>
    var mainregion = document.getElementById("mainregion");//游戏的主要区域
    //全局变量
    var food_x  = 0;
    var food_y = 0;
    //游戏开关
    var StartGames = function(){
        var she = document.getElementsByClassName("she");
        she.style.width = "140px";
        she.style.right = "44px";

        Createblock();
    }
    //生成方块
    var Createblock = function () {
        food_x = RandomNumber(1,500);
        food_y = RandomNumber(1,500);
        alert(food_x);
        alert(food_y);
        mainregion.innerHTML = "<div class='food' style='margin-left:"+food_x+"px;margin-top: "+food_y+"px'></div>";
    }
    /**
     * 生成指定范围内的随机数字
     * @param min
     * @param max
     * @constructor
     */
    var RandomNumber = function (Min,Max) {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range); //四舍五入
        return num;
    }
</script>
</html>
