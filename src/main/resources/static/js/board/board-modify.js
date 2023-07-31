/**
 * board-modify.js
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 게시판 관리자 권한 뺏기
	 */
	const revoke = (e) => {
		const userId = e.target.getAttribute('data-user-id');
		const boardId = e.target.getAttribute('data-board-id');
		
		console.log(`userId = ${userId}, boardId = ${boardId}`);
		
		const result = confirm('랜드의 관리자 권한을 뺏으시겠습니까?');
		if(!result) {
			return false;
		}
		
		const url = '/api/v1/board/revoke';
		const data = { userId, boardId };
		
		axios.put(url, data)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	const revokeBtns = document.querySelectorAll('button.revokeBtn');
	for(let btn of revokeBtns) {
		btn.addEventListener('click', revoke);
	}
	
	const modify = () => {
		const form = document.querySelector('form#modify-form');
		
		const imageInput = document.querySelector('input#imageFile');
		
		if(imageInput.files.length == 0) {
			alert('변경된 사항이 없습니다.');
		} else {
			console.log('modify');
			form.action='/board/modify';
			form.method='post';
			form.submit();
		}
	};
	
	const modifyBtn = document.querySelector('button#modifyBtn');
	modifyBtn.addEventListener('click', modify);
	
	const loadImage = () => {
		console.log('loadImage');
		
		const preview = document.getElementById('image-show');
		
		const input = document.querySelector('input#imageFile');
	
		if(input.files.length == 0) {
			// 입력된 이미지 파일이 없으면 미리보기 창을 지움
			preview.innerHTML = '';
		} else {
			// 사용자가 입력한 이미지 파일을 보여주는 div 영역을 추가
			const file = input.files[0];   
		    const imgSrc = URL.createObjectURL(file);     
		    const htmlStr = `
		    	<img src="${imgSrc}" id="preview" class="img w-100">
			`;
		
		    preview.innerHTML = htmlStr;
	    }
	};
	
	const imageFile = document.querySelector('input#imageFile');
	imageFile.addEventListener('change', loadImage);
	
	
	const toggleFile = () => {
		const toggle = document.querySelector('input#toggleBtn');
		const status = toggle.getAttribute('data-switch');
		
		const fileArea = document.querySelector('div#file-section');
		
		if(status == 'off') {
			console.log('on');
			
			toggle.setAttribute('data-switch', 'on');
			fileArea.style.display = 'block';
		} else {
			console.log('off');
			
			toggle.setAttribute('data-switch', 'off');
			fileArea.style.display = 'none';
		}
	};
	
	const toggleBtn = document.querySelector('input#toggleBtn');
	toggleBtn.addEventListener('click', toggleFile);
	
	const toggleList = () => {
		const toggle = document.querySelector('input#toggleBtn2');
		const status = toggle.getAttribute('data-switch');
		
		const userList = document.querySelector('div#user-list');
		
		if(status == 'off') {
			console.log('on');
			
			toggle.setAttribute('data-switch', 'on');
			userList.style.display = 'block';
		} else {
			console.log('off');
			
			toggle.setAttribute('data-switch', 'off');
			userList.style.display = 'none';
		}
	};
	
	const toggleBtn2 = document.querySelector('input#toggleBtn2');
	toggleBtn2.addEventListener('click', toggleList);
	
	
	const blockUser = (e) => {
		console.log(e.target);
		
		const userId = e.target.getAttribute('data-id');
		const boardId = document.querySelector('input#boardId').value;
		
		const url = '/api/v1/board/blockById';
		const data = { userId, boardId };
		
		axios.put(url, data)
			.then((response) => {
				console.log(response);
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	const blockBtns = document.querySelectorAll('button.blockBtn');
	for(let btn of blockBtns) {
		btn.addEventListener('click', blockUser);
	}
	
});


