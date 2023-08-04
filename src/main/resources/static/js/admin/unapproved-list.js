/**
 * board.js
 * 관리자 페이지에서 게시판 관련 처리
 */
document.addEventListener('DOMContentLoaded', () => {
	
	/**
	 * 신청한 게시판 승인
	 */
	const approve = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const url = `/api/v1/board/approve/${boardId}`;
		
		const result = confirm('랜드 생성을 승인하시겠습니까?');
		if(!result) {
			return false;
		}
		
		axios.put(url)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	}
	
	const approveBtns = document.querySelectorAll('button.approveBtn');
	for(let btn of approveBtns) {
		btn.addEventListener('click', approve);
	}
	
	/**
	 * 신청한 게시판 거절
	 */
	const decline = (e) => {
		const boardId = e.target.getAttribute('data-id');
		console.log(boardId);
		
		const url = `/api/v1/board/decline/${boardId}`;
		
		const result = confirm('랜드 생성을 거절하시겠습니까?');
		if(!result) {
			return false;
		}
		
		axios.put(url)
			.then((response) => {
				console.log(response);
				
				location.reload();
			})
			.catch((error) => {
				console.log(error);
			});
	}

	const declineBtns = document.querySelectorAll('button.declineBtn');
	for(let btn of declineBtns) {
		btn.addEventListener('click', decline);
	}
	
});