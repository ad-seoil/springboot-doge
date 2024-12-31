const FULL_DASH_ARRAY = 283;
const WARNING_THRESHOLD = 10;
const ALERT_THRESHOLD = 5;

const COLOR_CODES = {
  info: {
    color: "green"
  },
  warning: {
    color: "orange",
    threshold: WARNING_THRESHOLD
  },
  alert: {
    color: "red",
    threshold: ALERT_THRESHOLD
  }
};

const TIME_LIMIT = 20;
let timePassed = 0;
let timeLeft = TIME_LIMIT;
let timerInterval = null;
let remainingPathColor = COLOR_CODES.info.color;

document.getElementById("timer_app").innerHTML = `
<div class="base-timer">
  <svg class="base-timer__svg" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
    <g class="base-timer__circle">
      <circle class="base-timer__path-elapsed" cx="50" cy="50" r="45"></circle>
      <path
        id="base-timer-path-remaining"
        stroke-dasharray="283"
        class="base-timer__path-remaining ${remainingPathColor}"
        d="
          M 50, 50
          m -45, 0
          a 45,45 0 1,0 90,0
          a 45,45 0 1,0 -90,0
        "
      ></path>
    </g>
  </svg>
  <span id="base-timer-label" class="base-timer__label">
    ${formatTime(timeLeft)}
  </span>
</div>
`;

startTimer();

function onTimesUp() {
  clearInterval(timerInterval);
}

function formatTime(time) {
  let seconds = time % 60;
  return `${seconds}`
}

function startTimer() {
  timerInterval = setInterval(() => {
    timePassed = timePassed += 1;
    timeLeft = TIME_LIMIT - timePassed;

    if (timeLeft <= 0) {
      timeLeft = 0; // 숫자를 0으로 고정
      document.getElementById("base-timer-label").innerHTML = formatTime(timeLeft);
      onTimesUp(); // 타이머 종료 함수 호출
      return; // 추가적인 처리를 방지
    }

    document.getElementById("base-timer-label").innerHTML = formatTime(timeLeft);

    setCircleDasharray();
  }, 1000);
}

function calculateTimeFraction() {
  const rawTimeFraction = timeLeft / TIME_LIMIT;
  return rawTimeFraction - (1 / FULL_DASH_ARRAY) * (1 - rawTimeFraction);
}

function setCircleDasharray() {
  const circleDasharray = `${(
    calculateTimeFraction() * FULL_DASH_ARRAY
  ).toFixed(0)} 283`;
  document
  .getElementById("base-timer-path-remaining")
  .setAttribute("stroke-dasharray", circleDasharray);

  console.log("Updated dasharray: ",circleDasharray);
}