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
	
	// 키워드로 검색
	const readByKeyword = (data) => {
		console.log(data);
		
		const landList = document.querySelector('div#landList');
		landList.innerHTML = '';
		
		let htmlStr = '';
		
		for(let board of data) {
			htmlStr += `
				<div class="px-3">
					<a href="/board/${board.id}" class="link-dark">
						${board.boardName}
					</a>
				</div>
			`;
		}
		
		landList.innerHTML = htmlStr;
	};
	
	const searchByKeyword = async () => {
		const keyword = document.querySelector('input#keyword').value;
		const boardGrade = 'Main';
		
		const url = `/api/v1/board/searchByKeyword?keyword=${keyword}&boardGrade=${boardGrade}`;
		
		try {
			const response = await axios.get(url);
			
			console.log(response.data);
			
			readByKeyword(response.data);
			
			const resultMessage = document.querySelector('span#resultMessage');
			resultMessage.innerText = `[${keyword}]의 검색 결과`;
			
		} catch(error) {
			console.log(error);
		}
		
	};
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', searchByKeyword);
	
	// 카테고리로 검색
	const readByCategory = (data) => {
		console.log(data);
		
		const landList = document.querySelector('div#landList');
		landList.innerHTML = '';
		
		let htmlStr = '';
		
		for(let board of data) {
			htmlStr += `
				<div class="px-3">
					<a href="/board/${board.id}" class="link-dark">
						${board.boardName}
					</a>
				</div>
			`;
		}
		
		landList.innerHTML = htmlStr;
	}
	
	const searchByCategory = async (e) => {
		const name = e.target.getAttribute('data-name');
		const category = e.target.getAttribute('data-id');
		const boardGrade = 'Main';
		
		console.log(`category = {}`, category);
		
		const url = `/api/v1/board/searchByCategory?category=${category}&boardGrade=${boardGrade}`;
		
		try {
			const response = await axios.get(url);
			
			console.log(response.data);
			
			readByCategory(response.data);
			
			const resultMessage = document.querySelector('span#resultMessage');
			resultMessage.innerText = `[${name}]의 검색 결과`;
			
		} catch(error) {
			console.log(error);
		}
	}
	 
	const categoryBtns = document.querySelectorAll('a.categoryBtn');
	for(let btn of categoryBtns) {
		btn.addEventListener('click', searchByCategory);
	}
	
	
});