/**
 * board.js
 * 관리자 페이지에서 게시판 관련 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	// 검색 필터 창을 닫음
	const closeTab = () => {
		const tab = document.querySelector('div#filter-list');
		
		tab.style.display="none";
	};
	
	const closeBtn = document.querySelector('button#closeBtn');
	closeBtn.addEventListener('click', closeTab);
	
	// 검색 필터 창을 보여줌
	const openFilter = () => {
		const tab = document.querySelector('div#filter-list');
		
		tab.style.display="";
	};
	
	const openBtn = document.querySelector('button#openTabBtn');
	openBtn.addEventListener('click', openFilter);
	
	const loadBoardPage = (e) => {
		const userId = document.querySelector('input#userId').value;
		const boardId = e.target.getAttribute('data-id');
		
		console.log(`boardId = ${boardId}`);
		console.log(`userId = ${userId}`);
		
		const url = `/api/v1/board/checkAccess`;
		const data = { userId, boardId };
		axios.post(url, data)
			.then((response) => {
				console.log(response.data);
				
				if(response.data == 1) {
					alert('게시판 관리자에 의해 접근이 거부되었습니다.');
				} else {
					const boardUrl = `/board/${boardId}`;
					window.location.href = boardUrl;
				}
			})
			.catch((error) => {
				console.log(error);
			})
	};
	
	let loadBoardBtns = document.querySelectorAll('a.loadBoardBtn');
	for(let btn of loadBoardBtns) {
		btn.addEventListener('click', loadBoardPage);
	}
	
	// 키워드로 검색
	const readByKeyword = (data) => {
		writeData(data);
	};
	
	const searchByKeyword = async () => {
		const keyword = document.querySelector('input#keyword').value;
		const boardGrade = 'Main';
		
		const url = `/api/v1/board/searchByKeyword?keyword=${keyword}&boardGrade=${boardGrade}`;
		
		try {
			const response = await axios.get(url);
			
			readByKeyword(response.data);
			
			const resultMessage = document.querySelector('span#resultMessage');
			resultMessage.innerHTML = `<b>[${keyword}]</b>의 검색 결과`;
			
		} catch(error) {
			console.log(error);
		}
		
	};
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', searchByKeyword);
	
	const writeData = (data) => {
		const landList = document.querySelector('div#landList');
		landList.innerHTML = '';
		
		let htmlStr = '';
		
		for(let board of data) {
			htmlStr += `
				<div class="px-3">
					<a data-id="${board.id}" class="link-dark loadBoardBtn link-dark link-offset-2 link-underline link-underline-opacity-0">${board.boardName}</a>
				</div>
			`;
		}
		
		landList.innerHTML = htmlStr;
		
		loadBoardBtns = document.querySelectorAll('a.loadBoardBtn');
		for(let btn of loadBoardBtns) {
			btn.addEventListener('click', loadBoardPage);
		}
	};
	
	// 카테고리로 검색
	const readByCategory = (data) => {
		writeData(data);
	}
	
	const searchByCategory = async (e) => {
		const name = e.target.getAttribute('data-name');
		const category = e.target.getAttribute('data-id');
		const boardGrade = 'Main';
		
		console.log(`category = ${category}`);
		
		const url = `/api/v1/board/searchByCategory?category=${category}&boardGrade=${boardGrade}`;
		
		try {
			const response = await axios.get(url);
			
			readByCategory(response.data);
			
			const resultMessage = document.querySelector('span#resultMessage');
			resultMessage.innerHTML = `<b>[${name}]</b>의 검색 결과`;
			
		} catch(error) {
			console.log(error);
		}
	}
	 
	const categoryBtns = document.querySelectorAll('a.categoryBtn');
	for(let btn of categoryBtns) {
		btn.addEventListener('click', searchByCategory);
	}
	
});