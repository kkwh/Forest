/**
 * board.js
 * 관리자 페이지에서 게시판 관련 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 게시판 관리자 권한을 다른 유저에게 부여하는 메서드
	 */
	const revoke = (e) => {
		const userId = e.target.getAttribute('data-board-user-id'); // 현재 게시판 관리자 아이디
		const userToId = e.target.getAttribute('data-user-id'); // 권한을 부여할 유저 아이디
		const boardId = e.target.getAttribute('data-board-id'); // 게시판 아이디
		
		console.log(`userId = ${userId}, boardId = ${boardId}`);
		
		const result = confirm(`랜드의 관리자 권한을 유저 ${userToId}에게 넘기시겠습니까?`);
		if(!result) {
			return false;
		}
		
		const url = '/api/v1/board/revoke';
		const data = { userId, boardId, userToId };
		
		axios.put(url, data)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	const openModal = async (e) => {
		console.log(e.target);
		const boardId = e.target.getAttribute('data-board-id');
		const userId = e.target.getAttribute('data-user-id');
		
		const modal = document.querySelector('div#userListModal');
		
		modal.style.display = "block";
		modal.addEventListener('click', (e) => {
			if(e.target === modal) {
				modal.style.display = 'none';
			}
		});
		
		const modalCloseBtns = document.querySelectorAll('button.btn-close');
		for(let btn of modalCloseBtns) {
			btn.addEventListener('click', () => {
				modal.style.display = 'none';
			});
		}
		
		const response = await axios.get('/api/v1/board/getUserList');
		console.log(response.data);
		
		let htmlStr = '';
		
		for(let user of response.data) {
			if(user.id == userId) {
				htmlStr += `
					<li class="list-group-item">
			    		<div class="row">
			    			<div class="col-9">
			    				${user.nickname}
			    			</div>
			    			<div class="col-3">
			    				<button type="button" class="btn btn-dark w-100" data-board-id="${boardId}" 
			    					data-user-id="${user.id}" data-board-user-id="${userId}">관리자</button>
			    			</div>
			    			<div class="d-none">
			    				<input type="hidden" value="${boardId}" id="userBoardId" >
			    			</div>
			    		</div>
		    		</li>
				`;
			} else {
				htmlStr += `
					<li class="list-group-item">
			    		<div class="row">
			    			<div class="col-9">
			    				${user.nickname}
			    			</div>
			    			<div class="col-3">
			    				<button type="button" class="btn btn-outline-success grantBtn w-100" data-board-id="${boardId}" 
			    					data-user-id="${user.id}" data-board-user-id="${userId}">권한 부여</button>
			    			</div>
			    			<div class="d-none">
			    				<input type="hidden" value="${boardId}" id="userBoardId" >
			    			</div>
			    		</div>
		    		</li>
				`;
			}

		}
		
		document.querySelector('ul#user-list').innerHTML = htmlStr;
		
		const grantBtns = document.querySelectorAll('button.grantBtn');
		for(let btn of grantBtns) {
			btn.addEventListener('click', revoke);
		}
	};
	
	
	let revokeBtns = document.querySelectorAll('button.revokeBtn');
	for(let btn of revokeBtns) {
		btn.addEventListener('click', openModal);
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
								<td>
									<button type="button" data-user-id="${board.user.id}"  
										data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 넘기기</button>
								</td>
								<td>
									<button type="button" data-board-id="${board.id}" 
										class="btn btn-outline-primary deleteBtn">삭제</button>
								</td>
							</tr>
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
								<td>
									<button type="button" data-user-id="${board.user.id}"  
										data-board-id="${board.id}" class="btn btn-outline-danger revokeBtn">권한 넘기기</button>
								</td>
								<td>
									<button type="button" data-board-id="${board.id}" 
										class="btn btn-outline-primary deleteBtn">삭제</button>
								</td>
							</tr>
						`
					}
				}
				boardList.innerHTML = htmlStr;
				
				revokeBtns = document.querySelectorAll('button.revokeBtn');
				for(let btn of revokeBtns) {
					btn.addEventListener('click', openModal);
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
	
	let checkBtns = document.querySelectorAll('input.checkBtn');
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
	
	/**
	 * 선택된 카테고리, 정렬 순서, 검색어를 초기 상태로 초기화하는 메서드
	 */
	const resetFilter = () => {
		// 정렬 기준을 최신순으로 초기화
		document.querySelector('option[value="recent"]').selected = true;
		
		// 모든 카테고리가 선택 여부 초기화
		const checkBtns = document.querySelectorAll('input.checkBtn');
		for(let btn of checkBtns) {
			btn.checked = true;
		}
		
		// 입력된 검색어를 초기화
		document.querySelector('input#keyword').value = '';
		
		loadBoardListByFilter();
	};
	
	const clearBtn = document.querySelector('button#clearBtn');
	clearBtn.addEventListener('click', resetFilter);
	
	const searchUserList = async () => {
		const userId = document.querySelector('input#userId').value;
		const keyword = document.querySelector('input#userKeyword').value;
		const boardId = document.querySelector('input#userBoardId').value;
		
		const url = `/api/v1/board/getUserList?keyword=${keyword}`;
		
		const response = await axios.get(url);
		
		let htmlStr = '';
		
		for(let user of response.data) {
			if(user.id == userId) {
				htmlStr += `
					<li class="list-group-item">
			    		<div class="row">
			    			<div class="col-9">
			    				${user.nickname}
			    			</div>
			    			<div class="col-3">
			    				<button type="button" class="btn btn-dark w-100" data-board-id="${boardId}" 
			    					data-user-id="${user.id}" data-board-user-id="${userId}">관리자</button>
			    			</div>
			    			<div class="d-none">
			    				<input type="hidden" value="${boardId}" id="userBoardId" >
			    			</div>
			    		</div>
		    		</li>
				`;
			} else {
				htmlStr += `
					<li class="list-group-item">
			    		<div class="row">
			    			<div class="col-9">
			    				${user.nickname}
			    			</div>
			    			<div class="col-3">
			    				<button type="button" class="btn btn-outline-success grantBtn w-100" data-board-id="${boardId}" 
			    					data-user-id="${user.id}" data-board-user-id="${userId}">권한 부여</button>
			    			</div>
			    			<div class="d-none">
			    				<input type="hidden" value="${boardId}" id="userBoardId" >
			    			</div>
			    		</div>
		    		</li>
				`;
			}

		}
		
		document.querySelector('ul#user-list').innerHTML = htmlStr;
		
		const grantBtns = document.querySelectorAll('button.grantBtn');
		for(let btn of grantBtns) {
			btn.addEventListener('click', revoke);
		}
	};
	
	const userSearchBtn = document.querySelector('button#userSearchBtn');
	userSearchBtn.addEventListener('click', searchUserList);
	
	const clearUserSearch = () => {
		document.querySelector('input#userKeyword').value = '';
		
		searchUserList();
	};
	
	const userClearBtn = document.querySelector('button#userClearBtn');
	userClearBtn.addEventListener('click', clearUserSearch);
	
});