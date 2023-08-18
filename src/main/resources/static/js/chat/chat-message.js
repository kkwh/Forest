/**
 * chat-message.js
 */
const roomId = document.querySelector('input#roomId').value;;
const loginId = document.querySelector('input#loginId').value;

console.log(roomId + ", " + loginId);

let stompClient = null;