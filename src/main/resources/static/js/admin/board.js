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
				
				window.location.href='/admin/board/approved';
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
	
	const revokeBtns = document.querySelectorAll('button.revokeBtn');
	for(let btn of revokeBtns) {
		btn.addEventListener('click', revoke);
	}
	
});