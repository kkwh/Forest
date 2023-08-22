/**
 * user-bookmark.js
 */
document.addEventListener('DOMContentLoaded', () => {
	
	
	
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