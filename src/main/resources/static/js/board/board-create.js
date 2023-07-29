/**
 * board-create.js
 * 게시판 생성 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	const form = document.querySelector('form#create-form');
	
	const createBtn = document.querySelector('button#createBtn');
	
	const createBoard = (e) => {
		console.log('validate');
		
		const boardName = document.querySelector('input#boardName').value;
		const message = document.querySelector('span#message').innerHTML;
		
		if(boardName == '') {
			alert('랜드 이름을 입력해주세요.');
			return false;
		}
		if(message == '이미 사용중인 이름입니다.') {
			alert('이미 사용중인 이릅입니다.');
			return false;
		}
		
		form.action='/board/create';
		form.method='post';
		form.submit();
		
	};
	
	createBtn.addEventListener('click', createBoard);
	
});