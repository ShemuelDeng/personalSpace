<template>
    <div class="app1" v-if="$store.state.webSiteInfo.openLantern">
        <div class="lantern-box">
            <div class="lantern-left">
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">新</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">年</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
            </div>
            <div class="lantern-right">
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">快</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
                <div class="lantern">
                    <div class="lines"></div>
                    <div class="lantern-a">
                        <div class="lantern-b">
                            <div class="lantern-t">乐</div>
                        </div>
                    </div>
                    <div class="spike spike-a">
                        <div class="spike-c"></div>
                        <div class="spike-b"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "lantern-app",
    methods: {

    },
    data() {
        return {}
    }
}

</script>

<style scoped>
.lantern-box {
    position: relative;
    top: 0px;
    z-index: 9999;
    pointer-events: none;
}


.lantern-left {
    position: fixed;
    left: 40px;
    top: 20px
}


.lantern-right {
    position: fixed;
    right: 40px;
    top: 20px
}


.lantern {
    position: relative;
    width: 120px;
    height: 90px;
    background: #d8000f;
    background: rgba(216, 0, 15, 0.8);
    border-radius: 50% 50%;
    transform-origin: 50% -100px;
    animation: swing 3s infinite ease-in-out;
    box-shadow: -5px 5px 50px 4px rgba(250, 108, 0, 1);
    display: inline-block;
    margin-right: 10px;
}


.lantern-a {
    width: 100px;
    height: 90px;
    background: #d8000f;
    background: rgba(216, 0, 15, 0.1);
    margin: 0 8px 8px 8px;
    border-radius: 50% 50%;
    border: 2px solid #dc8f03;
}


.lantern-b {
    width: 50px;
    height: 90px;
    background: #d8000f;
    background: rgba(216, 0, 15, 0.1);
    margin: -4px 8px 8px 26px;
    border-radius: 50% 50%;
    border: 2px solid #dc8f03;
}


.lines {
    position: absolute;
    top: -20px;
    left: 60px;
    width: 3px;
    height: 20px;
    background: #dc8f03;
}


.spike-a {
    position: relative;
    width: 5px;
    height: 20px;
    margin: -5px 0 0 59px;
    animation: swing 4s infinite ease-in-out;
    -webkit-transform-origin: 50% -45px;
    background: #ffa500;
    border-radius: 0 0 5px 5px;
}


.spike-b {
    position: absolute;
    top: 14px;
    left: -2px;
    width: 10px;
    height: 10px;
    background: #dc8f03;
    border-radius: 50%;
}


.spike-c {
    position: absolute;
    top: 18px;
    left: -2px;
    width: 10px;
    height: 35px;
    background: #ffa500;
    border-radius: 0 0 0 5px;
}


.lantern:before {
    position: absolute;
    top: -7px;
    left: 29px;
    height: 12px;
    width: 60px;
    content: " ";
    display: block;
    z-index: 999;
    border-radius: 5px 5px 0 0;
    border: solid 1px #dc8f03;
    background: #ffa500;
    background: linear-gradient(to right, #dc8f03, #ffa500, #dc8f03, #ffa500, #dc8f03);
}


.lantern:after {
    position: absolute;
    bottom: -7px;
    left: 10px;
    height: 12px;
    width: 60px;
    content: " ";
    display: block;
    margin-left: 20px;
    border-radius: 0 0 5px 5px;
    border: solid 1px #dc8f03;
    background: #ffa500;
    background: linear-gradient(to right, #dc8f03, #ffa500, #dc8f03, #ffa500, #dc8f03);
}


.lantern-t {
    font-family: 华文行楷,
        Arial,
        Lucida Grande,
        Tahoma,
        sans-serif;
    font-size: 3.2rem;
    color: #dc8f03;
    font-weight: bold;
    line-height: 85px;
    text-align: center;
}


.lantern-t,
.lantern-box {
    background: transparent !important;
}


@keyframes swing {
    0% {
        transform: rotate(-10deg)
    }

    50% {
        transform: rotate(10deg)
    }

    100% {
        transform: rotate(-10deg)
    }
}

</style>