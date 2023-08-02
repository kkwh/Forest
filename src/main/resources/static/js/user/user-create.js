/**
 * user-create.js
 */
document.addEventListener('DOMContentLoaded', () => {

	const checkInput = (e) => { //회원가입 create submit
		e.preventDefault();

		const form = document.querySelector('form#signup-form');

		// 조건 체크
		const message = document.querySelector('span#duplicateMessage');
		const messageNickname = document.querySelector('span#duplicateMessageNickName');
		const confirmMsgText = confirmMsg.textContent;
		
		if (message.innerHTML == '사용 가능한 아이디 입니다.' && messageNickname.innerHTML == '사용 가능한 닉네임 입니다.' && confirmMsgText == '비밀번호 재확인 성공'
			) {

			form.action = '/user/createuser';
			form.method = 'post';
			form.submit();
		} else {
			alert('빈 칸 혹은 올바르지 않은 부분이 있습니다. 확인해주세요.');
			return false;
		}
	};

	const btnSignup = document.querySelector('button#btnSaveUser');
	btnSignup.addEventListener('click', checkInput);

	const checkName = () => {
		const loginId = document.querySelector('input#loginId').value;

		const url = '/api/user/validate/checkLoginId';
		const data = { loginId };

		axios.post(url, data)
			.then((response) => {
				console.log(response);

				const message = document.querySelector('span#duplicateMessage');



				if (loginId == '') {
					message.innerHTML = '';
					return false;
				}

				// 사용 가능
				if (response.data == 1) {
					message.style.color = 'green';
					message.innerHTML = '사용 가능한 아이디 입니다.';
				}
				// 사용중
				else {
					message.style.color = 'red';
					message.innerHTML = '이미 사용중인 아이디 입니다.';
				}
			})
			.catch((error) => {
				console.log(error);
			});

	};


	const checkNick = () => {
		const nickname = document.querySelector('input#nickname').value;

		const url = '/api/user/validate/checkLoginNickname';
		const data = { nickname };

		axios.post(url, data)
			.then((response) => {
				console.log(response);

				const messageNickname = document.querySelector('span#duplicateMessageNickName');

				if (nickname == '') {
					message.innerHTML = '';
					return false;
				}

				// 사용 가능
				if (response.data == 1) {
					messageNickname.style.color = 'green';
					messageNickname.innerHTML = '사용 가능한 닉네임 입니다.';
				}
				// 사용중
				else {
					messageNickname.style.color = 'red';
					messageNickname.innerHTML = '이미 사용중인 닉네임 입니다.';
				}
			})
			.catch((error) => {
				console.log(error);
			});
	};


	const checkEmail = () => {
		const email = document.querySelector('input#email').value;

		const url = '/api/user/validate/checkLoginEmail';
		const data = { email };

		axios.post(url, data)
			.then((response) => {
				console.log(response);

				const messageEmail = document.querySelector('span#duplicateMessageEmail');

				if (email == '') {
					messageEmail.innerHTML = '';
					return false;
				}

				// 사용 가능
				if (response.data == 1) {
					messageEmail.style.color = 'green';
					messageEmail.innerHTML = '사용 가능한 이메일 입니다.';
				}
				// 사용중
				else {
					messageEmail.style.color = 'red';
					messageEmail.innerHTML = '이미 사용중인 이메일 입니다.';
				}
			})
			.catch((error) => {
				console.log(error);
			});
	};




	const passwordInput = document.getElementById('password');
	const passwordConfirmInput = document.getElementById('passwordConfirm');
	const confirmMsg = document.getElementById('confirmMsg');

	passwordInput.addEventListener('input', () => {
		checkPasswordMatch();
	});

	passwordConfirmInput.addEventListener('input', () => {
		checkPasswordMatch();
	});

	function checkPasswordMatch() {
		const password = passwordInput.value;
		const passwordConfirm = passwordConfirmInput.value;

		if (password === passwordConfirm) {
			confirmMsg.style.color = 'green';
			confirmMsg.textContent = '비밀번호 재확인 성공';
		} else {
			confirmMsg.style.color = 'red';
			confirmMsg.textContent = '비밀번호 재확인 실패. 다시 입력해주세요.';
		}
	}


});

function checkLoginId() {
	const loginId = document.querySelector('input#loginId').value;

	const url = '/api/user/validate/checkLoginId';
	const data = { loginId };

	axios.post(url, data)
		.then((response) => {
			const message = document.querySelector('span#duplicateMessage');

			if (loginId == '') {
				message.innerHTML = '';
				return false;
			}

			// 사용 가능
			if (response.data == 1) {
				message.style.color = 'green';
				message.innerHTML = '사용 가능한 아이디 입니다.';
			}
			// 사용중
			else {
				message.style.color = 'red';
				message.innerHTML = '이미 사용중인 아이디 입니다.';
			}
		})
		.catch((error) => {
			console.log(error);
		});
}

function checkLoginNickname() {
	const nickname = document.querySelector('input#nickname').value;

	const url = '/api/user/validate/checkLoginNickname';
	const data = { nickname };

	axios.post(url, data)
		.then((response) => {
			const message = document.querySelector('span#duplicateMessageNickName');

			if (nickname === '') {
				message.innerHTML = '';
				return false;
			}

			if (response.data === 1) {
				message.style.color = 'green';
				message.innerHTML = '사용 가능한 닉네임 입니다.';
			} else {
				message.style.color = 'red';
				message.innerHTML = '이미 사용중인 닉네임 입니다.';
			}
		})
		.catch((error) => {
			console.log(error);
		});

}

	function checkLoginEmail() {
		const email = document.querySelector('input#email').value;

		const url = '/api/user/validate/checkLoginEmail';
		const data = { email };

		axios.post(url, data)
			.then((response) => {
				const message = document.querySelector('span#duplicationMessageEmail');

				if (email === '') {
					message.innerHTML = '';
					return false;
				}

				if (response.data === 1) {
					message.style.color = 'green';
					message.innerHTML = '사용 가능한 이메일 입니다.';
				} else {
					message.style.color = 'red';
					message.innerHTML = '이미 사용중인 이메일 입니다.';
				}
			})
			.catch((error) => {
				console.log(error);
			});

}