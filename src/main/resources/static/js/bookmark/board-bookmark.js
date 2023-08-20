/**
 * board-bookmark.js
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 즐겨찾기 목록에 등록되어 있지 않은 경우, 즐겨찾기 목록에 등록하는 메서드
	 */
	const addToBookmark = (result) => {
		const userId = document.querySelector('input#userId').value;
		const boardId = document.querySelector('input#boardId').value;
		
		const data = { userId, boardId };
		const url = '/api/v1/bookmark/addToBookmark';
		
		axios.post(url, data)
			.then((response) => {
				console.log(response);
				
				reloadCount(result);
			})
			.catch((error) => console.log(error));
	};
	
	/**
	 * 즐겨찾기에 이미 등록되어있는 경우, 즐겨찾기 목록에서 제거하는 메서드
	 */
	const removeFromBookmark = (result) => {
		const userId = document.querySelector('input#userId').value;
		const boardId = document.querySelector('input#boardId').value;
		
		const url = `/api/v1/bookmark/delete?boardId=${boardId}&userId=${userId}`;
		
		axios.delete(url)
			.then((response) => {
				console.log(response);
				
				reloadCount(result);
			})
			.catch((error) => console.log(error));
	};
	
	/**
	 * 즐겨찾기 수를 다시 불러오는 메서드
	 */
	const reloadCount = async (result) => {
		const boardId = document.querySelector('input#boardId').value;
		
		const url = `/api/v1/bookmark/count/${boardId}`;
		
		const response = await axios.get(url);
		const count = response.data;
		console.log(`count = ${count}`);
		
		const div = document.querySelector('div#bookmark-section');
		
		let htmlStr = '';
		
		if(result === '1' || result == 1) {
			htmlStr = `<button id="bookmarkBtn" type="button" class="btn btn-outline-warning w-100">☆ ${count}</button>`;
		} else {
			htmlStr = `<button id="bookmarkBtn" type="button" class="btn btn-warning w-100">☆ ${count}</button>`;
		}
		
		div.innerHTML = htmlStr;
		
		bookmarkBtn = document.querySelector('button#bookmarkBtn');
		bookmarkBtn.addEventListener('click', clickBookmark);
	};
	
	/**
	 * 사용자가 즐겨찾기 버튼을 클릭했을 때 이벤트를 처리하는 메서드
	 */
	const clickBookmark = async () => {
		const userId = document.querySelector('input#userId').value;
		const boardId = document.querySelector('input#boardId').value;
		
		console.log(`userId = ${userId}, boardId = ${boardId}`);
		
		if(userId == 0 || userId == '0') {
			alert('로그인이 필요한 서비스 입니다.');
			return false;
		}
		
		const url = `/api/v1/bookmark?boardId=${boardId}&userId=${userId}`;
		
		const response = await axios.get(url);
		
		const result = response.data;
		console.log(`result = ${result}`);
		
		if(result === '1' || result == 1) {
			removeFromBookmark(result);
		} else {
			addToBookmark(result);
		}

	};
	
	let bookmarkBtn = document.querySelector('button#bookmarkBtn');
	bookmarkBtn.addEventListener('click', clickBookmark);
	
});