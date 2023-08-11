/**
 * board.js
 * 관리자 페이지에서 게시판 관련 처리
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
	
	let revokeBtns = document.querySelectorAll('button.revokeBtn');
	for(let btn of revokeBtns) {
		btn.addEventListener('click', revoke);
	}
	
	/**
	 * 게시판 승급
	 */
	const upgrade = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const result = confirm('게시판을 메인 랜드르 승급시키겠습니까?');
		if(!result) {
			return false;
		}
		
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
	 * 게시판 강등
	 */
	const relegate = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const result = confirm('게시판을 서브 랜드로 강등시키겠습니까?');
		if(!result) {
			return false;
		}
		
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
	 * 게시판을 불러오는 메서드
	 */
	const loadBoardListByFilter = () => {
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
								<td>${board.categoryName}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-success upgradeBtn">승격</button>
								</td>
						`;
						
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<td>
									<button type="button" th:data-user-id="${board.user.id}" 
										th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						htmlStr += `
								<button type="button" th:data-board-id="${board.id}" class="btn btn-outline-primary deleteBtn">삭제</button>
							</td></tr>
						`;
						
					} else {
						htmlStr += `
							<tr>
								<td class="boardId">${board.id}</td>
								<td>
									<img src="${board.file.uploadPath}" alt="Not found" width="100px" height="auto" />
								</td>
								<td>${board.categoryName}</td>
								<td>${board.boardName}</td>
								<td>${board.user.nickname}</td>
								<td>${board.createdTime}</td>
								<td>
									<button type="button" data-id="${board.id}" class="btn btn-outline-danger relegateBtn">강등</button>
								</td>
						`;
						if(board.user.role != 'ADMIN') {
							htmlStr += `
								<td>
									<button type="button" th:data-user-id="${board.user.id}" 
										th:data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 뺏기</button>
							`;
						}
						htmlStr += `
								<button type="button" th:data-board-id="${board.id}" class="btn btn-outline-primary deleteBtn">삭제</button>
							</td></tr>
						`;
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
				
				deleteBtns = document.querySelectorAll('button.deleteBtn');
				for(let btn of deleteBtns) {
					btn.addEventListener('click', deleteBoard);
				}
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	const typeBtn = document.querySelector('select#type');
	typeBtn.addEventListener('change', loadBoardListByFilter);
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', loadBoardListByFilter);
	
	const checkBtns = document.querySelectorAll('input.checkBtn');
	for(let btn of checkBtns) {
		btn.addEventListener('click', loadBoardListByFilter);
	}
	
	/**
	 * 관리자 권한으로 게시판을 삭제
	 */
	const deleteBoard = (e) => {
		const boardId = e.target.getAttribute('data-board-id');
		
		const url = `/api/v1/board/delete/${boardId}`;
		
		const result = confirm('관리자 권한으로 게시판을 삭제하시겠습니까?');
		if(!result) {
			return false;
		}
		
		axios.delete(url)
			.then((response) => {
				console.log(response.data);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	let deleteBtns = document.querySelectorAll('button.deleteBtn');
	for(let btn of deleteBtns) {
		btn.addEventListener('click', deleteBoard);
	}
	
});