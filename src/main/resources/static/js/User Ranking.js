//유저에게 좋아요 버튼클릭시
function handleLike(button) {
    const row = button.parentElement.parentElement;
    const likesCell = row.querySelector(".likes-count");
    let likes = parseInt(likesCell.textContent);
    likes += 1;
    likesCell.textContent = likes;
    alert("좋아요 감사합니다!");
}