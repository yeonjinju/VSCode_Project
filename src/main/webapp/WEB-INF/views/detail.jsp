<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <style type='text/css'>
        .help-tip {
            position: absolute;
            top: 300px;
            right: 300px;
            text-align: center;
            background-color: beige;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            font-size: 25px;
            line-height: 26px;
            cursor: default;
        }

        .help-tip:before {
            content: '🔔';
            font-weight: bold;
            color: #fff;
        }

        .help-tip:hover p {
            display: block;
            transform-origin: 100% 0%;
            -webkit-animation: fadeIn 0.3s ease-in-out;
            animation: fadeIn 0.3s ease-in-out;
        }

        .help-tip p {
            display: none;
            text-align: left;
            background-color: #1E2021;
            padding: 20px;
            width: 300px;
            position: absolute;
            border-radius: 3px;
            box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
            right: -4px;
            color: #FFF;
            font-size: 13px;
            line-height: 1.4;
        }

        .help-tip p:before {
            position: absolute;
            content: '';
            width: 0;
            height: 0;
            border: 6px solid transparent;
            border-bottom-color: #1E2021;
            right: 10px;
            top: -12px;
        }
        .help-tip p:after {
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
</head>
<body>
    <div class="recall">
        <h2>고객 주문현황</h2>
        <div class="help-tip">
            <a href="#">${dto.name }님의 신선도가 68%입니다.</a>
            <br>
            <a href="#">${dto.name}님의 신선도가 48%입니다.</a>
        </div>
    </div>
    <div id="map" style="width:800px;height:500px;"></div>
	<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=b93928dad09d361cf00ce25bfeacf240"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(37.5166572549305, 126.9830780329734),
			level: 8
		};

		var map = new kakao.maps.Map(container, options);
	</script>
</body>
</html>