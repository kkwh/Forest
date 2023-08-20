/**
 * user-bookmark.js
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 사용자가 클릭한 게시판을 즐겨찾기 목록에서 삭제하는 메서드
	 */
	const deleteBookmark = (e) => {
		const id = e.target.getAttribute('data-id');
		
		const url = `/api/v1/bookmark/delete/${id}`;
		
		axios.delete(url)
			.then((response) => {
				console.log(response.data);
				
				window.location.href='/user/bookmarkList';
				//loadBookmarkList();
			})
			.catch((error) => {
				console.log(error);
			});
	};
	
	const makeBookmarkList = (data) => {
		let htmlStr = '';
		
		for(let bookmark of data) {
			htmlStr += `
				<tr>
					<td>${bookmark.boardId}</td>
					<td>
						<a href="/board/${bookmark.boardId}">
							<img src="${bookmark.file.uploadPath}" alt="Not found" width="100px" height="auto" />
						</a>
					</td>
					<td>
						<a href="/board/${bookmark.boardId}" class="link-dark">${bookmark.boardName}</a>
					</td>
					<td>${bookmark.category}</td>
					<td>
						<button type="button" class="btn btn-warning w-100 bookmarkBtn" data-id="${bookmark.id}"> ☆ </button>
					</td>
				</tr>
			`;
		}
		
		document.querySelector('tbody#bookmark-list').innerHTML = htmlStr;
		
		bookmarkBtns = document.querySelectorAll('button.bookmarkBtn');
		for(let btn of bookmarkBtns) {
			btn.addEventListener('click', deleteBookmark);
		}
	};
	
	const loadBookmarkList = async () => {
		const userId = document.querySelector('input#userId').value;
		
		const url = `/api/v1/bookmark/getList?userId=${userId}`;
		
		const response = await axios.get(url);
		
		makeBookmarkList(response.data);
	};
	
	let bookmarkBtns = document.querySelectorAll('button.bookmarkBtn');
	for(let btn of bookmarkBtns) {
		btn.addEventListener('click', deleteBookmark);
	}
	
});