/**
 * chat-message.js
 */
let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

/**
 * 날짜 데이터의 형식을 변환해주는 메서드
 */
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 만듭니다.
  const day = String(date.getDate()).padStart(2, '0'); // 일을 2자리로 만듭니다.
  const hours = String(date.getHours()).padStart(2, '0'); // 시를 2자리로 만듭니다.
  const minutes = String(date.getMinutes()).padStart(2, '0'); // 분을 2자리로 만듭니다.

  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;
  
  return formattedDate;
}

/**
 * loadMessages()에서 넘어온 메세지들을 화면에 보여주는 메서드
 */
function writeMessages(data) {
	console.log(data);
	
	const nickname = $('#nickname').val();

	let htmlStr = '';
	for(let chat of data) {	
		
		const formattedDate = formatDate(new Date(chat.createdTime.replace("T", " ")));
		
		if(chat.nickname == nickname) {
			htmlStr += `
				<div class="d-flex align-items-center alert alert-warning border border-dark">
				  <div class="flex-shrink-0">
				    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
				  </div>
				  <div class="flex-grow-1 ms-3">
				  	<div><h5>${chat.nickname}</h5></div>
				  	<div class="chat-content" style="word-break: break-word;"><p>${chat.message}</p></div>
				  	<div><p>${formattedDate}</p></div>	    
				  </div>
				</div>
			`;
		} else {
			htmlStr += `
				<div class="d-flex align-items-center alert alert-primary border border-dark">
				  <div class="flex-shrink-0">
				    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
				  </div>
				  <div class="flex-grow-1 ms-3">
				  	<div><h5>${chat.nickname}</h5></div>
				  	<div class="chat-content" style="word-break: break-word;"><p>${chat.message}</p></div>
				  	<div><p>${formattedDate}</p></div>					    
				  </div>
				</div>
			`;
		}
	}
	
	// 채팅 내역을 불러올 때 가장 최근 메세지가 보이도록 함
	const messageArea = document.querySelector('div#messages');
	messageArea.innerHTML = htmlStr;
	messageArea.scrollTop = messageArea.scrollHeight;
}

/**
 * 채팅방에 들어오면 채팅 메세지들을 불러오는 메서드
 */
function loadMessages() {
	const roomId = $('#roomId').val();
	
	const reqUrl = `/api/v1/chat/messages/${roomId}`;
	
	axios.get(reqUrl)
		.then((response) => {
			writeMessages(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
}

/**
 * 채팅방에 들어오면 connect 메서드를 호출
 */
$(document).ready(function() {
	connect();
});

$(window).on('beforeunload', function () {
    disconnect();
});

/**
 * 웹 소켓 연결하는 메서드
 */
function connect() {
    const socket = new SockJS(location.host + '/stomp-chat');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function (frame) {
		loadMessages();
        setConnected(true);
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/chatting', function (message) {
            appendChat(JSON.parse(message.body));
        });
    });
}

/**
 * 메세지를 전송 후 작성된 메세지를 append하는 메서드
 */
const appendChat = (chat) => {
	const nickname = $('#nickname').val();
	
	console.log(chat);
	
	const formattedDate = formatDate(new Date());
	
	if(chat.nickname === nickname) {
		$("#messages").append(
			`
				<div class="d-flex align-items-center alert alert-warning border border-dark">
				  <div class="flex-shrink-0">
				    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
				  </div>
				  <div class="flex-grow-1 ms-3">
				  	<div><h5>${chat.nickname}</h5></div>
				  	<div class="chat-content" style="word-break: break-word;"><p>${chat.message}</p></div>
				  	<div><p>${formattedDate}</p></div>	    
				  </div>
				</div>
			`
		);
	} else {
		$("#messages").append(
			`
				<div class="d-flex align-items-center alert alert-primary border border-dark">
				  <div class="flex-shrink-0">
				    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
				  </div>
				  <div class="flex-grow-1 ms-3">
				  	<div><h5>${chat.nickname}</h5></div>
				  	<div class="chat-content" style="word-break: break-word;"><p>${chat.message}</p></div>
				  	<div><p>${formattedDate}</p></div>	    
				  </div>
				</div>
			`
		);
	}
	
	const messageArea = document.querySelector('div#messages');
	messageArea.scrollTop = messageArea.scrollHeight;
};

/**
 * 소켓 연결을 종료하는 메서드
 */
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 * 작성된 메세지를 서버에 넘겨주는 메서드
 */
function sendMessage() {
	const roomId = $('#roomId').val();
	const nickname = $('#nickname').val();
	const message = $('#message').val();
	
	const data = {
		'roomId': roomId,
		'nickname': nickname,
		'message': message
	};
	
	stompClient.send('/app/sendMessage', {}, JSON.stringify(data));
}

/**
 * 작성된 메세지를 DB에 저장하는 메서드
 */
function saveMessage() {
	const roomId = $('#roomId').val();
	const nickname = $('#nickname').val();
	const message = $('#message').val();
	
	const url = '/api/v1/chat/save';
	const data = { roomId, nickname, message };
	
	axios.post(url, data)
		.then((response) => {
			console.log(response.data);
			$('#message').val('');
		})
		.catch((error) => {
			console.log(error);
		});
}

/**
 * 작성 버튼이 클릭됐을 때의 이벤트를 처리하는 메서드
 */
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#btnSend").click(function() {
		console.log('send btn clicked');
		sendMessage();
		saveMessage();
	});
});