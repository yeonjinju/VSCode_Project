<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <style type='text/css'>
        .recall-target {
            position: absolute;
            top: 20px;
            right: 20px;
            text-align: center;
            border-radius: 50%;
            width: 200px;
            height: 200px;
            font-size: 25px;
            line-height: 26px;
            cursor: default;
        }
        .recall-target:before {
            content: '🔔';
        }
        .recall-target:hover p {
            display: block;
            transform-origin: 100% 0%;
            -webkit-animation: fadeIn 0.3s ease-in-out;
            animation: fadeIn 0.3s ease-in-out;
        }

        .recall-target p {
            display: none;
            text-align: left;
            background-color: rgb(216, 216, 255);
            padding: 20px;
            width: 200px;
            border-radius: 3px;
            box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
            right: -4px;
            color: #FFF;
            font-size: 13px;
            line-height: 1.4;
        }
        .a1 p{
            color: black;
        }
        .recall-target p:after {
            width: 100%;
            height: 40px;
            content: '';
            position: absolute;
            top: -40px;
            left: 0;
        }
        @-webkit-keyframes fadeIn {
            0% {
                opacity: 0;
                transform: scale(0.6);
            }

            100% {
                opacity: 100%;
                transform: scale(1);
            }
        }
        @keyframes fadeIn {
            0% {
                opacity: 0;
            }

            100% {
                opacity: 100%;
            }
        }
    </style>
<head>
<meta charset="utf-8">
<title>이동 이벤트 등록하기</title>

</head>

<body>
    <h1>고객 주문현황</h1>
    <div id="map" style="width:800px;height:500px;"></div>
    <p><em>지도를 움직여 주세요!</em></p>
    
    <p>안녕</p>
    <p id="result"></p>
    <br>
    <div class="recall-target">
        <div class="content">
            <p class="a1"> 신선도 알림
                <button onclick="panTo()">주연진님의 신선도가 70% 이하로 떨어졌습니다. 현재 신선도는 55% 입니다.</button>
                <br><br>
                <button onclick="panTo()">조국환님의 신선도가 70% 이하로 떨어졌습니다. 현재 신선도는 55% 입니다.</button>
                <br>

            </p>
        </div>
    </div>
    

    <script type="text/javascript"
    src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=d865c67a15044f7517639c54d9a0f65c"></script>
    <script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(37.5166572549305, 126.9830780329734), // 지도의 중심좌표
            level: 8 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 마커 이벤트

        // 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다 
        var positions = [
            {
                content: '<div style="padding:5px;">온도 : ${temperature1 }<br>습도 : ${humidity1}<br><button>리콜</button></div>', 
                latlng: new kakao.maps.LatLng(37.517236, 127.047324)
            },
            {
                content: '<div style="padding:5px;">온도 : ${temperature2 }<br>습도 : ${humidity2}<br><button>리콜</button></div>', 
                latlng: new kakao.maps.LatLng(37.516066, 127.019361)
            },
            {
                content: '<div style="padding:5px;">온도 : ${temperature3 }<br>습도 : ${humidity3}<br><button>리콜</button></div>', 
                latlng: new kakao.maps.LatLng(37.511293, 127.021324)
            },
            {
                content: '<div style="padding:5px;">온도 : ${temperature4 }<br>습도 : ${humidity4}<br><button>리콜</button></div>',
                latlng: new kakao.maps.LatLng(37.51098, 127.043593)
            }
        ];

        positions.push({
            content: '<div style="padding:5px;">온도 : ${temperature1 }<br>습도 : ${humidity1}<br><button>리콜</button></div>', 
            latlng: new kakao.maps.LatLng(37.478218, 126.952830)
        })
        positions.push({
            content: '<div style="padding:5px;">온도 : ${temperature1 }<br>습도 : ${humidity1}<br><button>리콜</button></div>', 
            latlng: new kakao.maps.LatLng(37.539950, 127.070591)
        })
        positions.push({
            content: '<div style="padding:5px;">온도 : ${temperature1 }<br>습도 : ${humidity1}<br><button>리콜</button></div>', 
            latlng: new kakao.maps.LatLng(37.560963, 126.975494)
        })
        positions.push({
            content: '<div style="padding:5px;">온도 : ${temperature1 }<br>습도 : ${humidity1}<br><button>리콜</button></div>', 
            latlng: new kakao.maps.LatLng(37.517262, 126.900983)
        })

            iwRemoveable = true; // removeable 속성을 true 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
        for (var i = 0; i < positions.length; i ++) {
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng // 마커의 위치
                
            });

            // 마커에 표시할 인포윈도우를 생성합니다 
            var infowindow = new kakao.maps.InfoWindow({
                content: positions[i].content, // 인포윈도우에 표시할 내용
                removable: iwRemoveable
            });
            kakao.maps.event.addListener(marker, 'click', makeOverListener(map, marker, infowindow));
        }

        // 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
        function makeOverListener(map, marker, infowindow) {
            return function() {
                infowindow.open(map, marker);
            };
        }

        // 인포윈도우를 닫는 클로저를 만드는 함수입니다 
        function makeOutListener(infowindow) {
            return function() {
                infowindow.close();
            };
        }

        function panTo() {
        // 이동할 위도 경도 위치를 생성합니다 
        var moveLatLon = new kakao.maps.LatLng(37.516066, 127.019361);
        
        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
        // 선택한 마커에 인포윈도우 표시 (인포윈도우 생성 및 내용 추가)
        var markerToFocus = markers[1]; // 두 번째 마커를 선택
        var infowindow = new kakao.maps.InfoWindow({
            content: markerToFocus.infowindowContent
        });

        infowindow.open(map, markerToFocus);            
    }        




    // 지도 마우스, 키보드 이벤트

    // 마우스 드래그로 지도 이동이 완료되었을 때 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'dragend', function () {

        // 지도 중심좌표를 얻어옵니다 
        var latlng = map.getCenter();

        var message = '변경된 지도 중심좌표는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';

        var resultDiv = document.getElementById('result');
        resultDiv.innerHTML = message;

    });
    // 키보드 이벤트 리스너 등록
    document.addEventListener('keydown', function (event) {
        var currentCenter = map.getCenter();
        var currentLevel = map.getLevel();
        var newCenter, newLevel;

        switch (event.key) {
            case 'ArrowUp':
                newCenter = new kakao.maps.LatLng(currentCenter.getLat() + 0.001, currentCenter.getLng());
                break;
            case 'ArrowDown':
                newCenter = new kakao.maps.LatLng(currentCenter.getLat() - 0.001, currentCenter.getLng());
                break;
            case 'ArrowLeft':
                newCenter = new kakao.maps.LatLng(currentCenter.getLat(), currentCenter.getLng() - 0.001);
                break;
            case 'ArrowRight':
                newCenter = new kakao.maps.LatLng(currentCenter.getLat(), currentCenter.getLng() + 0.001);
                break;
            case '+':
            case '=':
                newLevel = currentLevel - 1;
                break;
            case '-':
                newLevel = currentLevel + 1;
                break;
            default:
                return;
        }

        if (newCenter) {
            map.panTo(newCenter);
        }
        if (newLevel) {
            map.setLevel(newLevel);
        }
    });

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    // 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'zoom_changed', function () {

        // 지도의 현재 레벨을 얻어옵니다
        var level = map.getLevel();

            var message = '현재 지도 레벨은 ' + level + ' 입니다';
            var resultDiv = document.getElementById('result');
            resultDiv.innerHTML = message;

        });

    </script>
</body>
</html>