/**
 * board-list.js
 * 게시판 목록 불러오기
 */
document.addEventListener('DOMContentLoaded', () => {
	
	const modify = (e) => {
		console.log(e.target);
	}
	let modifyBtns = document.querySelectorAll("button.modifyBtn");
	for(let btn of modifyBtns) {
		btn.addEventListener('click', modify);
	}
	
	const sortByType = () => {
		const type = document.querySelector('select#type').value;
		const userId = document.querySelector('input#userId').value;
		
		const url = '/api/v1/board/sortBy';
		const data = { userId, type };
		
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
							<td>${board.category.value}</td>
							<td>${board.boardName}</td>
							<td>${board.user.nickname}</td>
							<td class="createdDate">${board.createdTime}</td>
							<td>
								<button type="button" data-id="${board.id}" class="btn btn-outline-success modifyBtn">수정하기</button>
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
	};
	
	
	const typeBtn = document.querySelector('select#type');
	typeBtn.addEventListener('change', sortByType);
	
	const searchByKeyword = () => {
		const userId = document.querySelector('input#userId').value;
		const keyword = document.querySelector('input#keyword').value;
		
		const url = '/api/v1/board/search';
		const data = { userId, keyword };
		
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
							<td>${board.category.value}</td>
							<td>${board.boardName}</td>
							<td>${board.user.nickname}</td>
							<td>${board.createdTime}</td>
							<td>
								<button type="button" data-id="${board.id}" class="btn btn-outline-success modifyBtn">수정하기</button>
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
		};
	
	const searchBtn = document.querySelector('button#searchBtn');
	searchBtn.addEventListener('click', searchByKeyword);
});
