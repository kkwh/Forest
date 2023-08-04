/**
 * user-create.
 */
document.addEventListener('DOMContentLoaded', () => {

	const checkInput = (e) => { //회원가입 create submit
		e.preventDefault();

		const form = document.querySelector('form#signup-form');

		// 조건 체크
		const message = document.querySelector('span#duplicateMessage'); //어아디 중복체크
		const messageNickname = document.querySelector('span#duplicateMessageNickName'); //닉네임 중복체크		
		const confirmMsgText = confirmMsg.textContent; //비밀번호 재확인
		const messageEmail = document.querySelector('span#duplicateMessageEmail'); //이메일 중복체크
		
		const loginIdT = document.querySelector('input#loginId').value; 
		const nicknameT = document.querySelector('input#nickname').value; 
		const emailT = document.querySelector('input#email').value; 
		const passwordT = document.querySelector('input#password').value; 
		const passwordConfirmT = document.querySelector('input#passwordConfirm').value; 
	

		if(loginIdT == ''){
			alert('아이디를 작성해주세요.')
			
		}else if(message.innerHTML == '이미 사용중인 아이디 입니다.'){
			alert('사용중인 아이디 입니다. 다른 아이디를 사용해주세요.')
			
		}else if(nicknameT == ''){
			alert('닉네임을 적성해주세요')
			
		}else if(messageNickname.innerHTML == '이미 사용중인 닉네임 입니다.'){
			alert('사용중인 닉네임 입니다. 다른 닉네임을 사용해주세요.')
			
		}else if(emailT == ''){
			alert('이메일을 작성해주세요')
			
		}else if(messageEmail.innerHTML == '이미 사용중인 이메일 입니다.'){
			alert('사용중인 이메일 입니다. 다른 이메일을 사용해주세요.')
			
		}else if(authCode == null){
                        alert('이메일 인증을 해주세요.')
                        
                }else if(isMatched == false){
                        alert('이메일 인증번호가 맞지 않습니다.')
                        
                }else if(passwordT == ''){
			alert('비밀번호를 작성해주세요')
			
		}else if(passwordConfirmT == ''){
			alert('비밀번호 재확인을 해야합니다.')
			
		}else if(confirmMsgText !== '비밀번호 재확인 성공'){
			alert('비밀번호 재확인이 틀렸습니다.')
			
		}else{
			form.action = '/user/signup';
			form.method = 'post';
			form.submit();
		}
	
	/*
	회원 가입할때 이메일 없이 가능 .  아이디 이메일 null로 넣어주세요
	else if(authCode == null){
			alert('이메일 인증을 해주세요.')
			
		}else if(isMatched == false){
			alert('이메일 인증번호가 맞지 않습니다.')
			
		}
	*/
	};
	const btnSignup = document.querySelector('button#btnSaveUser');
	btnSignup.addEventListener('click', checkInput);
});


//이메일 확인 하기
	const btnSend = document.querySelector('button#btnSend')
	const btnConfirm = document.querySelector('button#btnConfirm');
	const messageEmail = document.querySelector('span#duplicateMessageEmail');
	
	let authCode;
	let isMatched;
	
	btnSend.addEventListener('click', (e) => {
		e.preventDefault();

	  const email = document.querySelector('input#email').value;
          const url = `/api/user/validate/emailConfirm?email=${email}`;


		if(messageEmail.innerHTML == '이미 사용중인 이메일 입니다.')	{
			alert('이메일은 사용중인 이메일입니다. 다른 이메일로 작성해주세요.')
		}else{
		
		axios.get(url, email)
			.then((response) => {
				console.log(response.data);
				authCode = response.data;
			})
			.catch((error) => {
				console.log(error);
			});
		}
	});

	btnConfirm.addEventListener('click', (e) => {
		e.preventDefault();
			
		const inputCode = document.querySelector('input[placeholder=인증번호]').value;
		if (authCode == inputCode) {
			isMatched = true;
			alert('확인되었습니다.');
		} else {
			isMatched = false;
			alert('인증번호가 일치하지 않습니다.');
		}
	});

	const checkMail = () => {
		const email = document.querySelector('input#email').value;

		const url = '/api/user/validate/emailCheck';
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
					messageEmail.innerHTML = '';
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

	

	const checkName = () => {
		const loginId = document.querySelector('input#loginId').value;

		const url = '/api/user/validate/checkLoginEmail';
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




function checkLoginId() {
	const loginId = document.querySelector('input#loginId').value;

	const url = '/api/user/validate/checkLoginId';
	const data = { loginId };

	axios.post(url, data)
		.then((response) => {
			const message = document.querySelector('span#duplicateMessage');
			
			//console.log('자스 찾았습니다.')
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
} //checkLoginId end

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
}//checkLoginNickname end


function checkLoginEmail() {
	const email = document.querySelector('input#email').value;

	const url = '/api/user/validate/checkLoginEmail'
	const data = { email };

	axios.post(url, data)
		.then((response) => {

			const messageEmail = document.querySelector('span#duplicateMessageEmail');

			if (email === '') {
				messageEmail.innerHTML = '';
				return false;
			}

			if (response.data === 1) {
				messageEmail.style.color = 'green';
				messageEmail.innerHTML = '';
			} else {
				messageEmail.style.color = 'red';
				messageEmail.innerHTML = '이미 사용중인 이메일 입니다.';
			}
		})
		.catch((error) => {
			console.log(error);
		});
} //checkLoginEmail end


