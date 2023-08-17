/**
 * chat-message.js
 */
    const roomId = document.querySelector('input#roomId').value;;
    const loginId = document.querySelector('input#loginId').value;

    console.log(roomId + ", " + loginId);

    const sockJs = new SockJS("/stomp/chat");
    // SockJS를 내부에 들고 있는 stomp를 내어줌
    const stomp = Stomp.over(sockJs);

	// connection이 맺어지면 실행
    stomp.connect({}, function () {
        console.log("STOMP Connection");

        stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
			var content = JSON.parse(chat.body);
			
            loadChatMessages(content);
        });

        stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, loginId: loginId}));
    });
    
    // 메세지 전송했을 때 처리하는 메서드
    const sendMessage = (e) => {
		const msg = document.querySelector('input#msg');

        console.log(loginId + ":" + msg.value);
        stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, loginId: loginId}));
        msg.value = '';
	};
    
    const sendBtn = document.querySelector('button#btnSend');
    sendBtn.addEventListener('click', sendMessage);
    
    const deleteMessage = (e) => {
		const id = e.target.getAttribute('data-id');
		
		const result = confirm('작성된 메세지를 삭제할까요?');
		if(!result) {
			return false;
		}
		
		const url = `/api/v1/chat/deleteMessage/${id}`;
		
		axios.delete(url)
			.then((response) => {
				console.log(response.data);
				
				loadChatMessages();
			})
			.catch((error) => {
				console.log(error);
			});
	};
    
    // 채팅 메세지를 불러오기 위한 메서드
    const loadChatMessages = (data) => {
		/*
		const url = `/api/v1/chat/messages/${roomId}`;
		
		const response = await axios.get(url);
		console.log(response.data);
		*/
		
		let htmlStr = '';
		for(let chat of data) {	
			if(chat.sender.loginId == loginId) {
				htmlStr += `
					<div class="d-flex align-items-center alert alert-warning border border-dark">
					  <div class="flex-shrink-0">
					    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
					  </div>
					  <div class="flex-grow-1 ms-3">
					  	<div><h5>${chat.sender.nickname}</h5></div>
					  	<div class="chat-content" style="word-break: break-word;"><p>${chat.content}</p></div>
					  	<div><p>${chat.createdTime}</p></div>
					  	<div><a class="link-dark deleteBtn" data-id="${chat.id}" type="button">삭제</a></div>			    
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
					  	<div><h5>${chat.sender.nickname}</h5></div>
					  	<div class="chat-content" style="word-break: break-word;"><p>${chat.content}</p></div>
					  	<div><p>${chat.createdTime}</p></div>					    
					  </div>
					</div>
				`;
			}
		}
		
		const deleteBtns = document.querySelectorAll('a.deleteBtn');
		for(let btn of deleteBtns) {
			btn.addEventListener('click', deleteMessage);
		}
		
		// 채팅 내역을 불러올 때 가장 최근 메세지가 보이도록 함
		const messageArea = document.querySelector('div#messages');
		messageArea.innerHTML = htmlStr;
		messageArea.scrollTop = messageArea.scrollHeight;
	};
