/**
 * 
 */
// 메인화면 페이지 로드 함수
            $(document).ready(function () {
                $('#summernote').summernote({
                    placeholder: '내용을 작성하세요',
                    height: 400,
                    maxHeight: 400
                });
                
             // 서머노트에 text 쓰기
                $('#summernote').summernote('insertText', '써머노트에 쓸 텍스트');

                // 서머노트 쓰기 비활성화
                $('#summernote').summernote('disable');

                // 서머노트 쓰기 활성화
                $('#summernote').summernote('enable');


                // 서머노트 리셋
                $('#summernote').summernote('reset');


                // 마지막으로 한 행동 취소 ( 뒤로가기 )
                $('#summernote').summernote('undo');
                // 앞으로가기
                $('#summernote').summernote('redo');
                
                $('.summernote').summernote({
                    toolbar: [
                          // [groupName, [list of button]]
                          ['fontname', ['fontname']],
                          ['fontsize', ['fontsize']],
                          ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
                          ['color', ['forecolor','color']],
                          ['table', ['table']],
                          ['para', ['ul', 'ol', 'paragraph']],
                          ['height', ['height']],
                          ['insert',['picture','link','video']],
                          ['view', ['fullscreen', 'help']]
                        ],
                      fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
                      fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
                });
                
                /**
                 * 이미지 파일 업로드
                 */
                
                $('#summernote').summernote({
                    callbacks: {
                        onImageUpload: function(files) {
                            var formData = new FormData();
                            formData.append('file', files[0]);

                            $.ajax({
                                url: '/image/upload',
                                method: 'POST',
                                data: formData,
                                processData: false,
                                contentType: false,
                                success: function(data) {
                                    $('#summernote').summernote('insertImage', data);
                                }
                            });
                        }
                    }
                });
                
            
            });