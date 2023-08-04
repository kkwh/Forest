/**
 * board-list.js
 * 게시판 목록 불러오기
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 게시판을 메인으로 승급
	 */
	const upgrade = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const url = `/api/v1/board/updateGrade/${boardId}`;
		axios.put(url)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	}
	let upgradeBtns = document.querySelectorAll('button.upgradeBtn');
	for(let btn of upgradeBtns) {
		btn.addEventListener('click', upgrade);
	}
	
	/**
	 * 게시판을 서브로 강등
	 */
	const relegate = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const url = `/api/v1/board/updateGrade/${boardId}`;
		axios.put(url)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	}
	let relegateBtns = document.querySelectorAll('button.relegateBtn');
	for(let btn of relegateBtns) {
		btn.addEventListener('click', relegate);
	}
	
	/**
	 * 특정 조건에 따라 정렬
	 */
	const sortByType = () => {
		const userId = document.querySelector('input#userId').value;
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/filterBy`;
		const data = { categoryList, userId, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					console.log(board.id);
					
					htmlStr += `
						<tr>
							<td class="boardId">${board.id}</td>
							<td>
								<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
							</td>
							<td>${board.categoryName}</td>
							<td>
								<a href="/board/details/${board.id}" class="link-dark">${board.boardName}</a>
							</td>
							<td>${board.user.nickname}</td>
							<td>${board.createdTime}</td>
							<td>
								<a href="/board/details/${board.id}" data-id="${board.id}" data-category="${board.category}" 
									class="btn btn-outline-success modifyBtn">수정</a>							
							</td>
						</tr>
					`;
					
				}
				boardList.innerHTML = htmlStr;
				
				modifyBtns = document.querySelectorAll('button.modifyBtn');
				for(let btn of modifyBtns) {
					btn.addEventListener('click', modify);
				}
				
				upgradeBtns = document.querySelectorAll('button.upgradeBtn');
				for(let btn of upgradeBtns) {
					btn.addEventListener('click', upgrade);
				}
				
				relegateBtns = document.querySelectorAll('button.relegateBtn');
				for(let btn of relegateBtns) {
					btn.addEventListener('click', relegate);
				}
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	
	const typeBtn = document.querySelector('select#type');
	typeBtn.addEventListener('change', sortByType);
	
	/**
	 * 키워드로 검색
	 */
	const searchByKeyword = () => {
		const userId = document.querySelector('input#userId').value;
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/filterBy`;
		const data = { categoryList, userId, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					console.log(board.id);
					
					htmlStr += `
						<tr>
							<td class="boardId">${board.id}</td>
							<td>
								<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
							</td>
							<td>${board.categoryName}</td>
							<td>
								<a href="/board/details/${board.id}" class="link-dark">${board.boardName}</a>
							</td>
							<td>${board.user.nickname}</td>
							<td>${board.createdTime}</td>
							<td>
								<a href="/board/details/${board.id}" data-id="${board.id}" data-category="${board.category}" 
									class="btn btn-outline-success modifyBtn">수정</a>							
							</td>
						</tr>
					`;
					
				}
				boardList.innerHTML = htmlStr;
				
				modifyBtns = document.querySelectorAll('button.modifyBtn');
				for(let btn of modifyBtns) {
					btn.addEventListener('click', modify);
				}
				
				upgradeBtns = document.querySelectorAll('button.upgradeBtn');
				for(let btn of upgradeBtns) {
					btn.addEventListener('click', upgrade);
				}
				
				relegateBtns = document.querySelectorAll('button.relegateBtn');
				for(let btn of relegateBtns) {
					btn.addEventListener('click', relegate);
				}
			})
			.catch((error) => {
				console.log(error);
			});
		};
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', searchByKeyword);
	
	/**
	 * 카테고리로 검색
	 */
	const selectCategory = (e) => {
		const userId = document.querySelector('input#userId').value;
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/filterBy`;
		const data = { categoryList, userId, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				console.log(response);
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					htmlStr += `
						<tr>
							<td class="boardId">${board.id}</td>
							<td>
								<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
							</td>
							<td>${board.categoryName}</td>
							<td>
								<a href="/board/details/${board.id}" class="link-dark">${board.boardName}</a>
							</td>
							<td>${board.user.nickname}</td>
							<td>${board.createdTime}</td>
							<td>
								<a href="/board/details/${board.id}" data-id="${board.id}" data-category="${board.category}" 
									class="btn btn-outline-success modifyBtn">수정</a>							
							</td>
						</tr>
					`;
				}
				
				boardList.innerHTML = htmlStr;
				
				modifyBtns = document.querySelectorAll('button.modifyBtn');
				for(let btn of modifyBtns) {
					btn.addEventListener('click', modify);
				}
			})
			.catch((error) => {
				console.log(error);
			});
	}
	
	const checkBtns = document.querySelectorAll('input.checkBtn');
	for(let btn of checkBtns) {
		btn.addEventListener('click', selectCategory);
	}
	
});
