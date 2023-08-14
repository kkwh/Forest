/**
 * chat-create.js
 */
document.addEventListener('DOMContentLoaded', () => {
	
	const createChatRoom = (e) => {
		e.preventDefault();
		
		const name = document.querySelector('input#name').value;
		
		if(name === '') {
			alert('이름을 입력해주세요.');
			return false;
		}
		
		const form = document.querySelector('form#create-form');
		
		form.action='/chat/room/create';
		form.method='post';
		form.submit();
	};
	
	const createBtn = document.querySelector('button#createBtn');
	createBtn.addEventListener('click', createChatRoom);
	
	const searchChatRoom = async () => {
		const keyword = document.querySelector('input#keyword').value;
		
		const url = `/api/v1/chat/getList?keyword=${keyword}`;
		const response = await axios.get(url);
		
		createChatList(response.data);
	};
	
	function formatDateTime(dateTimeString) {
	    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
	    return new Date(dateTimeString).toLocaleDateString('ko-KR', options);
	}
	
	const createChatList = (data) => {
		let htmlStr = '';
		
		for(let room of data) {
			const formattedTime = formatDateTime(room.createdTime);
			
			htmlStr += `
				<tr>
					<th scope="row">${room.id}</th>
					<td><a href="/chat/room/${room.id}">${room.name}</a></td>
					<td>Otto</td>
					<td>${formattedTime}</td>
				</tr>
			`;
		}
		
		document.querySelector('tbody#chatroom-list').innerHTML = htmlStr;
	};
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', searchChatRoom);
	
	const clear = async () => {
		document.querySelector('input#keyword').value = '';
		
		const keyword = document.querySelector('input#keyword').value;
		
		const url = `/api/v1/chat/getList?keyword=${keyword}`;
		const response = await axios.get(url);
		
		createChatList(response.data);
	};
	
	const clearBtn = document.querySelector('button#clearBtn');
	clearBtn.addEventListener('click', clear);
	
});