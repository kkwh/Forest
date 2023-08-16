/**
 * 
 */
document.getElementById("btnfindpw").addEventListener("click", function() {
        var email = document.getElementById("findPwEmailInput").value;
        var loginId = document.getElementById("findPwIdInput").value;

        const url = `/api/user/validate/findPw`;
        const data = { email, loginId };
        if (loginId.trim() === "") {
                alert("아이디를 입력해주세요.");

        } else if (email.trim() === "") {
                alert("이메일을 입력해주세요.");
        } else {
                axios.post(url, data)
                .then(function(response) {

                        console.log('pw찾기 자스 왔습니다.')
                        if (response.data == 1) {
                                alert("아이디와 이메일이 맞는지 확인해주세요.")
                        } else {
                                alert("")
                        }
                })
                .catch(function(error) {
                        console.log(error);
                });
        }

           
        
});