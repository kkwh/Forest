/**
 * board.js
 * 관리자 페이지에서 게시판 관련 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	const closeTab = () => {
		const tab = document.querySelector('div#filter-list');
		
		tab.style.display="none";
	};
	
	const closeBtn = document.querySelector('button#closeBtn');
	closeBtn.addEventListener('click', closeTab);
	
	const openFilter = () => {
		const tab = document.querySelector('div#filter-list');
		
		tab.style.display="";
	};
	
	const openBtn = document.querySelector('button#searchBtn');
	openBtn.addEventListener('click', openFilter);
	
	const sortByType = () => {
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/searchApproved`;
		const data = { categoryList, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					console.log(board.id);
					
					console.log(board.user.role);
					
					if(board.boardGrade == 'Sub') {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-success upgradeBtn">승격</button>
						`;
						
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					} else {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-danger relegateBtn">강등</button>
						`;
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					}
				}
				boardList.innerHTML = htmlStr;
				
				revokeBtns = document.querySelectorAll('button.revokeBtn');
				for(let btn of revokeBtns) {
					btn.addEventListener('click', revoke);
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
	
	const searchByKeyword = () => {
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/searchApproved`;
		const data = { categoryList, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					console.log(board.id);
					
					console.log(board.user.role);
					
					if(board.boardGrade == 'Sub') {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-success upgradeBtn">승격</button>
						`;
						
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					} else {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-danger relegateBtn">강등</button>
						`;
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					}
				}
				boardList.innerHTML = htmlStr;
				
				revokeBtns = document.querySelectorAll('button.revokeBtn');
				for(let btn of revokeBtns) {
					btn.addEventListener('click', revoke);
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
	
	const selectCategory = (e) => {
		const keyword = document.querySelector('input#keyword').value;
		const type = document.querySelector('select#type').value;
		
		let categoryList = [];
		for(let btn of checkBtns) {
			if(btn.checked == true) {
				categoryList.push(btn.id);
			}
		}
		
		console.log(categoryList);
		
		const url = `/api/v1/board/searchApproved`;
		const data = { categoryList, keyword, type };
		
		axios.post(url, data)
			.then((response) => {
				console.log(response);
				
				const boardList = document.querySelector('tbody#board-list');
				
				let htmlStr = '';
				
				for(let board of response.data) {
					console.log(board.id);
					
					console.log(board.user.role);
					
					if(board.boardGrade == 'Sub') {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-success upgradeBtn">승격</button>
						`;
						
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					} else {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.category}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-danger relegateBtn">강등</button>
						`;
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<button type="button" th:data-user-id="${board.user.id}" 
									th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						
						htmlStr += `</td></tr>`;
					}
				}
				boardList.innerHTML = htmlStr;
				
				revokeBtns = document.querySelectorAll('button.revokeBtn');
				for(let btn of revokeBtns) {
					btn.addEventListener('click', revoke);
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
	}
	
	const checkBtns = document.querySelectorAll('input.checkBtn');
	for(let btn of checkBtns) {
		btn.addEventListener('click', selectCategory);
	}
	
});