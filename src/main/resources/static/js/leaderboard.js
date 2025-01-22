//유저에게 좋아요 버튼클릭시
function handleLike(button) {
    const row = button.parentElement.parentElement;
    const likesCell = row.querySelector(".likes-count");
    let likes = parseInt(likesCell.textContent);
    likes += 1;
    likesCell.textContent = likes;
    alert("좋아요 감사합니다!");
}
//정렬기능  - 포인트,이름,좋아요 수 등에따라 테이블 정리 가능
function sortTable(columnIndex, ascending) {
    const table = document.querySelector("table tbody");
    const rows = Array.from(table.rows);

    rows.sort((a, b) => {
        const aValue = a.cells[columnIndex].innerText.toLowerCase();
        const bValue = b.cells[columnIndex].innerText.toLowerCase();

        return ascending ? aValue.localeCompare(bValue) : bValue.localeCompare(aValue);
    });

    rows.forEach(row => table.appendChild(row));
}
// 테이블 정렬 함수
function sortTable(columnIndex, ascending = true) {
    const table = document.querySelector("table tbody");
    const rows = Array.from(table.rows);

    // 행 데이터를 정렬
    rows.sort((a, b) => {
        const aText = a.cells[columnIndex].innerText.toLowerCase();
        const bText = b.cells[columnIndex].innerText.toLowerCase();

        if (ascending) {
            return aText.localeCompare(bText);
        } else {
            return bText.localeCompare(aText);
        }
    });

    // 정렬된 행을 다시 테이블에 추가
    rows.forEach(row => table.appendChild(row));
}

// 버튼 클릭 이벤트 등록
document.addEventListener("DOMContentLoaded", () => {
    document.querySelector("button:nth-child(1)").addEventListener("click", () => sortTable(0)); // ALL (순위로 정렬)
    document.querySelector("button:nth-child(2)").addEventListener("click", () => sortTable(5, false)); // COMPANY (포인트 내림차순)
    document.querySelector("button:nth-child(3)").addEventListener("click", () => sortTable(2)); // USERS (이름 오름차순)
    document.querySelector("button:nth-child(4)").addEventListener("click", () => sortTable(4)); // INQUIRY (학습 언어 순)
    document.querySelector("button:nth-child(5)").addEventListener("click", () => sortTable(5, true)); // DEPOSIT (포인트 오름차순)
});