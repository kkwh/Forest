/**
 * board-create.js
 * 게시판 생성 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	const form = document.querySelector('form#create-form');
	
	const createBtn = document.querySelector('button#createBtn');
	
	const createBoard = (e) => {
		const boardName = document.querySelector('input#boardName').value;
		const message = document.querySelector('span#message').innerHTML;
		const boardInfo = document.querySelector('textarea#boardInfo').value;
		const imageFile = document.querySelector('input#imageFile');
		
		if(boardName == '') {
			alert('랜드 이름을 입력해주세요.');
			return false;
		}
		if(message == '이미 사용중인 이름입니다.') {
			alert('이미 사용중인 이름입니다.');
			return false;
		}
		if(boardInfo.length == 0) {
			alert('랜드 소개글을 입력해주세요.');
			return false;
		}
		if(imageFile.files.length == 0) {
			alert('랜드의 배경사진을 등록해주세요.');
			return false;
		}
		
		form.action='/board/create';
		form.method='post';
		form.submit();
		
	};
	
	createBtn.addEventListener('click', createBoard);
	
});

function loadImage(input) {
	console.log(input);
	
	const preview = document.getElementById('image-show');
	
	if(input.files.length == 0) {
		// 입력된 이미지 파일이 없으면 미리보기 창을 지움
		preview.innerHTML = '';
	} else {
		// 사용자가 입력한 이미지 파일을 보여주는 div 영역을 추가
		const file = input.files[0];   
	    const imgSrc = URL.createObjectURL(file);     
	    const htmlStr = `
	    	<label for="preview" class="form-label">미리보기</label>
	    	<img src="${imgSrc}" id="preview" class="img w-100">
		`;
	
	    preview.innerHTML = htmlStr;
    }
}